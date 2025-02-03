package org.example;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
        BufferedReader fileRead;
        FileWriter fileWrite;

        try {
            fileWrite = new FileWriter("test.txt");
            fileRead = new BufferedReader(new FileReader("test.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue();
        GeneratorService genOdd = new GeneratorService(queue, 100L, 3, true);
        GeneratorService genEven = new GeneratorService(queue, 100L, 3, false);
        QueueFileWriter writer = new QueueFileWriter(queue, fileWrite);
        ServiceFileReader reader = new ServiceFileReader(fileRead);


        genOdd.start();
        genEven.start();
        writer.start();
        reader.start();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        genOdd.stop();
        genEven.stop();
        writer.stop();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        reader.stop();

        try {
            fileRead.close();
            fileWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
