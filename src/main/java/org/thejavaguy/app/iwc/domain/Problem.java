package org.thejavaguy.app.iwc.domain;

public final class Problem {
    private final String file;
    private final ProblemClass problemClass;

    public Problem(String file, ProblemClass problemClass) {
        this.file = file;
        this.problemClass = problemClass;
    }

    public String severity() {
        return problemClass.severity();
    }

    @Override
    public String toString() {
        return "Problem{" +
                "file='" + file + '\'' +
                ", problemClass=" + problemClass +
                '}';
    }
}
