/*
 * Copyright (c) 2019. Budeanu Vasile
 */

package net.iliabvf.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ProcessedStore {

    private List<String> store;

    public ProcessedStore() {
        store = new ArrayList<>();
    }

    public synchronized boolean checkOrAddFile(String fileName) {

        if (!store.contains(fileName)){
            store.add(fileName);
            return false;
        }
        return true;

    }

}
