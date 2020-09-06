package org.hjujgfg;

import java.util.Map;
import java.util.Set;

public class ResultFormatter {

    public String format(Map.Entry<String, Set<String>> entry) {
        return String.format("%s -> %s",
            entry.getKey().substring(0, entry.getKey().length() - 36),
            String.join(", ", entry.getValue()) // 6th O(n)
        );
    }
}
