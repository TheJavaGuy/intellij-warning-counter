package org.thejavaguy.app.iwc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Report {
    private final List<Problem> problems;

    public Report() {
        this.problems = new ArrayList<>();
    }

    public void add(Problem problem) {
        problems.add(problem);
    }

    public int size() {
        return problems.size();
    }

    public Map<String, Integer> problemCountBySeverities() {
        Map<String, Integer> ret = new TreeMap<>();
        for (Problem problem : problems) {
            String severity = problem.severity();
            if (!ret.containsKey(severity)) {
                ret.put(severity, 1);
            } else {
                Integer oldValue = ret.get(severity);
                ret.put(severity, oldValue + 1);
            }
        }
        return ret;
    }

    public Map<String, Integer> diff(Report other) {
        Map<String, Integer> ret = new TreeMap<>();
        Map<String, Integer> thisCountBySeverities = this.problemCountBySeverities();
        Map<String, Integer> otherCountBySeverities = other.problemCountBySeverities();
        for (String thisSeverity : thisCountBySeverities.keySet()) {
            if (otherCountBySeverities.containsKey(thisSeverity)) {
                ret.put(thisSeverity, thisCountBySeverities.get(thisSeverity) - otherCountBySeverities.get(thisSeverity));
            } else {
                ret.put(thisSeverity, thisCountBySeverities.get(thisSeverity));
            }
        }
        for (String otherSeverity : otherCountBySeverities.keySet()) {
            if (!thisCountBySeverities.containsKey(otherSeverity)) {
                ret.put(otherSeverity, otherCountBySeverities.get(otherSeverity));
            }
        }
        return ret;
    }
}
