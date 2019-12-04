/*
 * Copyright (c) 2019. Budeanu Vasile
 */

package net.iliabvf.concurrent;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class AppRunner {

    private final static String WELCOME_STRING1 =  "Welcome to Concurrent files app v.0.1 (by Budeanu Vasile).";

    private static String defaultDir =  "C://files";

    volatile static LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        ProcessedStore processedStore = new ProcessedStore();

        System.out.println(WELCOME_STRING1);

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available number of cores: " + cores);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < cores; i++) {
            FilesProcessor filesProcessor = new FilesProcessor(queue, processedStore, defaultDir, i);
            executorService.execute(filesProcessor);
        }

        executorService.shutdown();

    }
}
