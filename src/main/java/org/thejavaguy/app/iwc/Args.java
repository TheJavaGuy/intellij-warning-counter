package org.thejavaguy.app.iwc;

import com.beust.jcommander.Parameter;

public final class Args {
    @Parameter(names = "--dir", required = true, description = "A directory with exported IntelliJ Inspection Results")
    private String dir;

    public final String dir() {
        return dir;
    }
}
