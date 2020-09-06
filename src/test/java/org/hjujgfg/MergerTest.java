package org.hjujgfg;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MergerTest {

    private Merger merger;

    @Before
    public void setup() {
        merger = new Merger();
    }

    @Test
    public void should_fill_for_single_user() {
        Map<String, Set<String>> actual = runForInputs("q -> 123, 321");
        assertEquals(1, actual.size());
        actual.forEach((key, value) -> {
            assertEquals("q", key.substring(0, key.length() - 36));
            assertEquals(2, value.size());
            assertTrue(value.contains("123"));
            assertTrue(value.contains("321"));
        });
    }

    @Test
    public void should_fill_for_multiple_users() {
        Map<String, Set<String>> actual = runForInputs("q -> 123, 321", "p -> 345, 456");
        assertEquals(2, actual.size());
        actual.forEach((key, value) -> {
            String actualkey = key.substring(0, key.length() - 36);
            assertTrue(actualkey.equals("p") || actualkey.equals("q"));
            if (actualkey.equals("q")) {
                assertEquals(2, value.size());
                assertTrue(value.contains("123"));
                assertTrue(value.contains("321"));
            } else {
                assertEquals(2, value.size());
                assertTrue(value.contains("345"));
                assertTrue(value.contains("456"));
            }
        });
    }

    @Test
    public void should_fill_for_multiple_users_with_intersecting_emails() {
        Map<String, Set<String>> actual = runForInputs(
            "q -> 123, 321",
            "p -> 123, 456");
        assertEquals(1, actual.size());
        actual.forEach((key, value) -> {
            String actualkey = key.substring(0, key.length() - 36);
            assertEquals("q", actualkey);
            assertEquals(3, value.size());
            assertTrue(value.contains("123"));
            assertTrue(value.contains("321"));
            assertTrue(value.contains("456"));
        });
    }

    @Test
    public void should_fill_for_multiple_users_with_intersecting_and_not_intersecting_emails() {
        Map<String, Set<String>> actual = runForInputs(
            "q -> 123, 321",
            "p -> 321, 456",
            "w -> 678, 789");
        assertEquals(2, actual.size());
        actual.forEach((key, value) -> {
            String actualkey = key.substring(0, key.length() - 36);
            assertTrue(actualkey.equals("q") || actualkey.equals("w"));
            if (actualkey.equals("q")) {
                assertEquals(3, value.size());
                assertTrue(value.contains("123"));
                assertTrue(value.contains("321"));
                assertTrue(value.contains("456"));
            } else {
                assertEquals(2, value.size());
                assertTrue(value.contains("678"));
                assertTrue(value.contains("789"));
            }
        });
    }

    @Test
    public void should_fill_for_multiple_users_with_same_name_and_not_intersecting_emails() {
        Map<String, Set<String>> actual = runForInputs(
            "q -> 123, 321",
            "q -> 678, 789");
        assertEquals(2, actual.size());
        actual.forEach((key, value) -> {
            String actualkey = key.substring(0, key.length() - 36);
            assertEquals("q", actualkey);
            assertEquals(2, value.size());
            assertTrue(
                (value.contains("123") && value.contains("321")) ||
                    (value.contains("678") && value.contains("789"))
            );
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_for_user_without_emails() {
        runForInputs("q ->    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_for_line_without_user() {
        runForInputs(" ->  ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_for_line_without_an_arrow() {
        runForInputs("pwemnvpiwernf pirnei");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_for_null() {
        runForInputs(null, null);
    }

    private Map<String, Set<String>> runForInputs(String... inputs) {
        for (String s : inputs) {
            merger.processLine(s);
        }
        return merger.streamResult().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s1, s2) -> {
            throw new RuntimeException("Programming error, duplicate entries returned!");
        }));
    }
}
