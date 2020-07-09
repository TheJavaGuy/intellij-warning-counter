package org.thejavaguy.app.iwc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eaxy.Document;
import org.eaxy.Element;
import org.eaxy.Xml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavaguy.app.iwc.domain.Problem;
import org.thejavaguy.app.iwc.domain.ProblemClass;
import org.thejavaguy.app.iwc.domain.Report;

import com.beust.jcommander.JCommander;

/**
 * Entry point for IntelliJ Warning Counter application.
 */
public final class App {
    private static final Logger LOGGER = LoggerFactory.getLogger("App");

    public App() {
    }

    public Set<Path> filesInDir(Path dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(dir, depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toSet());
        }
    }

    public Report reportForDir(Path dir) throws IOException {
        Report ret = new Report();
        Set<Path> filesInDir = filesInDir(dir, 1);
        for (Path path : filesInDir) {
            LOGGER.trace("Processing {}", path);
            // temporary workaround to speed up processing until I find better solution for XML parsing
//            if (path.toAbsolutePath().toString().contains("JavaDoc.xml")) {
//                continue;
//            }
//            if (path.toAbsolutePath().toString().contains("unused.xml")) {
//                continue;
//            }
//            if (path.toAbsolutePath().toString().contains("/.xml")) {
//                continue;
//            }
            Document doc = Xml.read(path.toAbsolutePath().toFile());
            List<Element> problemElements = doc.find("problem").elements();
            for (Element problemElement : problemElements) {
                String file = problemElement.find("file").first().text();
                String severity = problemElement.find("problem_class").first().attr("severity");
                ProblemClass problemClass = new ProblemClass(severity);
                ret.add(new Problem(file, problemClass));
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        LOGGER.debug("App START");
        Args objArgs = new Args();
        new JCommander(objArgs, args);
        LOGGER.debug("dir {}", objArgs.dir());
        App app = new App();
        Report report = app.reportForDir(Paths.get(objArgs.dir()));
        LOGGER.info("Total problems: {}", report.size());
        LOGGER.info("Problems by severities: {}", report.problemCountBySeverities());
        if (objArgs.dirToCompare() != null) {
            LOGGER.debug("dir to compare {}", objArgs.dirToCompare());
            Report reportToCompare = app.reportForDir(Paths.get(objArgs.dirToCompare()));
            LOGGER.info("Total problems: {}", reportToCompare.size());
            LOGGER.info("Problems by severities: {}", reportToCompare.problemCountBySeverities());
            LOGGER.info("Diff: {}", reportToCompare.diff(report));
        }
        LOGGER.debug("App END");
    }
}
