package org.thejavaguy.app.iwc.domain;

public final class ProblemClass {
    private final String severity;

    public ProblemClass(String severity) {
        this.severity = severity;
    }

    public String severity() {
        return severity;
    }

    @Override
    public String toString() {
        return "ProblemClass{" +
                "severity='" + severity + '\'' +
                '}';
    }
}
