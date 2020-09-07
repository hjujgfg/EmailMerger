package org.hjujgfg;

import java.io.InputStream;
import java.util.function.Consumer;

public class Main {

    private final InputProcessor ip;
    private final Merger merger;
    private final ResultFormatter formatter;
    private final Consumer<String> linesConsumer;

    public Main(InputStream inputStream, Consumer<String> outputConsumer) {
        ip = new InputProcessor(inputStream);
        merger = new Merger();
        formatter = new ResultFormatter();
        this.linesConsumer = outputConsumer;
    }

    public void run() {
        ip.produce().forEach(merger::processLine);
        merger.getResult().entrySet()
            .stream()
            .map(formatter::format)
            .forEach(linesConsumer);
    }

    public static void main(String... args) {
        System.out.println("Enter emails");
        Main m = new Main(System.in, System.out::println);
        m.run();
        System.out.println("Done, your users should be merged well now");
    }
}
