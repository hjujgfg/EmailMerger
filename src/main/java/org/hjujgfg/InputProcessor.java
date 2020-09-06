package org.hjujgfg;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InputProcessor {

    private final Scanner scanner;

    public InputProcessor(InputStream is) {
        scanner = new Scanner(is);
        scanner.useDelimiter("\n");
    }

    public Stream<String> produce() {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<String>(
            Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.NONNULL) {
            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                if (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    if (!nextLine.isEmpty()) {
                        action.accept(nextLine);
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        }, false).onClose(scanner::close);
    }
}
