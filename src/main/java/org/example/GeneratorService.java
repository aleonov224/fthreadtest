
package org.example;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GeneratorService {
    private final ConcurrentLinkedQueue<Long> queue;
    private final long limitValues;
    private final int sleepTimeMs;
    private final boolean odd;
    private final ExecutorService service;
    private Thread thread;
    private final Random random;

    public GeneratorService(ConcurrentLinkedQueue<Long> queue, long limitValues, int sleepTimeMs, boolean odd) {
        this.queue = queue;
        this.limitValues = limitValues;
        this.sleepTimeMs = sleepTimeMs;
        this.odd = odd;
        this.service = Executors.newSingleThreadExecutor();
        this.random = new Random();
    }

    public void start() {
        service.submit(this::doit);
    }

    public void stop() {
        thread.interrupt();
        service.shutdown();
    }

    private void doit() {
        thread = new Thread(this::generate);
        thread.start();
    }
    private void generate() {
        for(long i = 0L; i < this.limitValues; ++i) {
            long v = this.random.nextLong();
            if (this.odd) {
                v = (v >> 10 << 1) + 1L;
            } else {
                v = v >> 10 << 1;
            }

            this.queue.add(v);

            try {
                Thread.sleep(this.sleepTimeMs);
            } catch (InterruptedException e) {
                //TODO
            }
        }

    }
}
