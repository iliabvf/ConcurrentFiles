/*
 * Copyright (c) 2019. Budeanu Vasile
 */

package net.iliabvf.concurrent;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

public class FilesProcessor implements Runnable {
    private LinkedBlockingQueue<File> queue;
    private int processorNumber;
    private ProcessedStore processedStore;
    private String defaultDir;

    public FilesProcessor(LinkedBlockingQueue<File> queue, ProcessedStore processedStore, String defaultDir, int processorNumber) {
        this.queue = queue;
        this.processorNumber = processorNumber;
        this.processedStore = processedStore;
        this.defaultDir = defaultDir;
    }

    @Override
    public void run() {

        boolean processed;

        Thread.currentThread().setName("Files processor " + processorNumber);
        System.out.println("Starting " + Thread.currentThread().getName());

        while (true) {

            // Find files
            File folder = new File(defaultDir);

            boolean foundUnprocessedFiles = false;

            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    continue;
                }

                processed = processedStore.checkOrAddFile(fileEntry.getName());
                if (!processed) {
                    foundUnprocessedFiles = true;

                    String extension = "";
                    int i = fileEntry.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = fileEntry.getName().substring(i+1);
                    }

                    System.out.println("Processing " + fileEntry.getName() + " by " + Thread.currentThread().getName() + ", extension: " + extension + ", size: " + fileEntry.length());
                }

            }

            if (!foundUnprocessedFiles){
                break;
            }

        }

    }
}
