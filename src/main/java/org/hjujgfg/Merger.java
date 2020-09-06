package org.hjujgfg;

import java.util.*;
import java.util.stream.Stream;

public class Merger {

    private final Map<String, String> emailToUser;
    private final Map<String, Set<String>> userToEmails;

    public Merger() {
        emailToUser = new HashMap<>();
        userToEmails = new HashMap<>();
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

        Set<String> uniqEmails = new HashSet<>();
        String potentialUser = null;
        for (String email : emails) { // 3rd O(n)
            if (email.trim().isEmpty()) { // we might skip this check, the only problem will be ugly output, same as input
                throw new IllegalArgumentException(String.format("Empty email specified in string: \"%s\"", userLine));
            }
            uniqEmails.add(email.trim()); // O(1) as it's hash set
            if (potentialUser == null) {
                String existingUser = emailToUser.get(email.trim()); // O(1) as it's hash map
                if (existingUser != null) {
                    potentialUser = existingUser;
                }
            }
        }

        if (potentialUser == null) {
            String uniqName = name + UUID.randomUUID();
            userToEmails.put(uniqName, uniqEmails); // O(1) as it's hash map
            uniqEmails.forEach(e -> emailToUser.put(e.trim(), uniqName)); // 4th O(n) + O(1) for .put()
        } else {
            userToEmails.get(potentialUser).addAll(uniqEmails); // O(1) for .get() + 4th O(n) for .addAll() - iteration is inside
            for (String e : uniqEmails) { // 5th O(n)
                emailToUser.putIfAbsent(e.trim(), potentialUser); // O(1)
            }
        }

        // Worst case - 5 * O(n)
    }

    public Stream<Map.Entry<String, Set<String>>> streamResult() {
        return userToEmails.entrySet().stream(); // O(m)
    }
}
