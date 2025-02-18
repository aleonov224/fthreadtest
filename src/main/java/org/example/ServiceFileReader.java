
package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceFileReader {
    private final BufferedReader reader;
    private final ExecutorService service;

    private Thread thread;

    public ServiceFileReader(BufferedReader reader) {
        this.reader = reader;
        this.service = Executors.newSingleThreadExecutor();
    }


    public void start() {
        service.submit(this::doit);
    }

    public void stop() {
        thread.interrupt();
        service.shutdown();
    }

    private void doit() {
        thread = new Thread(this::run);
        thread.start();
    }

    public void run() {

        try {
            Thread.sleep(10L);
            String s;
            s = reader.readLine();

            while(Objects.nonNull(s)) {
                System.out.println(s);
                Thread.sleep(10L);
                s = reader.readLine();

            }

        } catch (IOException | InterruptedException e) {
           //TODO
        }
    }
}
