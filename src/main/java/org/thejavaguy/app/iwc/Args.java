package org.thejavaguy.app.iwc;

import com.beust.jcommander.Parameter;

public final class Args {
    @Parameter(names = "--dir", required = true, description = "A directory with exported IntelliJ Inspection Results")
    private String dir;

    @Parameter(names = "--compareWith", description = "A directory to compare with the first one")
    private String dirToCompare;

    public final String dir() {
        return dir;
    }

    public final String dirToCompare() {
        return dirToCompare;
    }
}
