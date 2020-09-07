package org.hjujgfg;

import java.util.*;

public class Merger {

    private final Map<String, String> emailToUsers;

    public Merger() {
        emailToUsers = new HashMap<>();
    }

    public void processLine(String userLine) {
        if (userLine == null) {
            throw new IllegalArgumentException("No email could be extracted from nothing :(");
        }
        int arrowIndex = userLine.lastIndexOf("->"); // 1st O(n)
        if (arrowIndex < 0) {
            throw new IllegalArgumentException(String.format("Wrong message format for string: \"%s\"", userLine));
        }
        String name = userLine.substring(0, arrowIndex).trim();

        if (name.isEmpty()) { // we might skip this check, the only problem will be ugly output, same as input
            throw new IllegalArgumentException(String.format("No user specified in string: \"%s\"", userLine));
        }

        String[] emails = userLine.substring(arrowIndex + 2).split(","); // 2nd O(n)
        String uniqName = uniqName(name);
        Set<String> uniqEmails = new HashSet<>();
        Set<String> closureUsers = new HashSet<>();
        for (String e : emails) { // 3rd O(n)
            String email = e.trim();
            if (email.isEmpty()) { // we might skip this check, the only problem will be ugly output, same as input
                throw new IllegalArgumentException(String.format("Empty email specified in string: \"%s\"", userLine));
            }
            uniqEmails.add(email); // O(1)
            String previousUser = emailToUsers.putIfAbsent(email, uniqName); // O(1)
            if (previousUser != null) {
                closureUsers.add(previousUser); // O(1)
            }
        }
        if (!closureUsers.isEmpty()) {
            // here we have some kind of a progression with fluctuating difference
            // but the complexity is n - Σ(mx), where x ∈ ( m .. M ]; if m == M -> 0 idk how to put it correctly in this notation)
            // where n is the total number of emails by the end of the input,
            // mx - number of emails for x-th user,
            // m - index of a current user
            // M - total number of users
            for (String email : emailToUsers.keySet()) {
                if (closureUsers.contains(emailToUsers.get(email))) {
                    emailToUsers.put(email, uniqName);
                }
            }
        }
    }

    public Map<String, Set<String>> getResult() {
        Map<String, Set<String>> result = new HashMap<>();
        emailToUsers.forEach((email, user) -> result.computeIfAbsent(user, s -> new HashSet<>()).add(email));
        return result;
    }

    private String uniqName(String name) {
        return name + UUID.randomUUID();
    }
}
