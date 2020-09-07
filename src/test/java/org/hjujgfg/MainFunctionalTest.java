package org.hjujgfg;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class MainFunctionalTest {

    @Test
    public void should_produce_correct_lines() {
        String initialString =
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, foo@gmail.com, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n";

        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        Main main = new Main(targetStream, s -> {
            assertTrue(s.startsWith("user4") || s.startsWith("user5"));
            if (s.startsWith("user4")) {
                assertTrue(s.contains("xxx@ya.ru"));
                assertTrue(s.contains("foo@gmail.com"));
                assertTrue(s.contains("lol@mail.ru"));
                assertTrue(s.contains("ups@pisem.net"));
                assertTrue(s.contains("aaa@bbb.ru"));
            } else {
                assertTrue(s.contains("xyz@pisem.net"));
                assertTrue(s.contains("vasya@pupkin.com"));
            }
        });
        main.run();
    }

}
