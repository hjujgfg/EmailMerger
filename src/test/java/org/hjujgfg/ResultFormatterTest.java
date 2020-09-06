package org.hjujgfg;

import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class ResultFormatterTest {

    private final ResultFormatter formatter = new ResultFormatter();

    @Test
    public void should_build_string_with_valid_format_for_several_emails() {
        Set<String> emails = new TreeSet<>();
        emails.add("123");
        emails.add("321");
        emails.add("456");
        String actual = formatter.format(
            new AbstractMap.SimpleImmutableEntry<>("test" + UUID.randomUUID(), emails)
        );

        Assert.assertEquals("test -> 123, 321, 456", actual);
    }

    @Test
    public void should_build_string_with_valid_format_for_single_email() {
        Set<String> emails = new TreeSet<>();
        emails.add("123");
        String actual = formatter.format(
            new AbstractMap.SimpleImmutableEntry<>("test" + UUID.randomUUID(), emails)
        );

        Assert.assertEquals("test -> 123", actual);
    }

}
