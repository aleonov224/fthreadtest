
package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class QueueFileWriter {
    private final ConcurrentLinkedQueue<Long> queue;
    private final FileWriter file;
    private final ExecutorService service;

    private Thread thread;

    public QueueFileWriter(ConcurrentLinkedQueue<Long> queue, FileWriter file) {
        this.queue = queue;
        this.file = file;
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
            while(true) {
                Thread.sleep(2L);
                if (Objects.nonNull(this.queue.peek())) {
                    file.write(queue.poll().toString() + "\n");
                    file.flush();
                    Thread.sleep(2L);
                }
            }
        } catch (IOException | InterruptedException e) {
            //TODO
        }

    }
}
