package org.thejavaguy.app.iwc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

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

    public static void main(String[] args) throws IOException {
        LOGGER.debug("App START");
        Args objArgs = new Args();
        new JCommander(objArgs, args);
        System.out.println(objArgs.dir());
        App app = new App();
        Set<Path> filesInDir = app.filesInDir(Paths.get(objArgs.dir()), 1);
        List<XML> problems = new ArrayList<>();
        for (Path path : filesInDir) {
            XML intellijWarnings = new XMLDocument(path);
            problems.addAll(intellijWarnings.nodes("//problem"));
        }
        LOGGER.info("Total problems: {}", problems.size());
        LOGGER.debug("App END");
    }
}
