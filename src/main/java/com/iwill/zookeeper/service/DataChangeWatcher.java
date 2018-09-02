package com.iwill.zookeeper.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.springframework.stereotype.Service;

@Service
public class DataChangeWatcher implements CuratorWatcher ,CuratorListener {

    /**
     * Called when a background task has completed or a watch has triggered
     *
     * @param client client
     * @param event  the event
     * @throws Exception any errors
     */
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        System.out.println("receive listener event");
    }

    /**
     * Same as {@link Watcher#process(WatchedEvent)}. If an exception
     * is thrown, Curator will log it
     *
     * @param event the event
     * @throws Exception any exceptions to log
     */
    @Override
    public void process(WatchedEvent event) throws Exception {
        System.out.println("receive watcher event");
    }
}
