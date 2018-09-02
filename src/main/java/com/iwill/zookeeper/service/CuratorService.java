package com.iwill.zookeeper.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuratorService implements InitializingBean {

    @Autowired
    private CuratorFramework curatorClient;

    @Autowired
    private DataChangeWatcher dataChangeWatcher;

    @Override
    public void afterPropertiesSet() throws Exception {
        curatorClient.getData().usingWatcher(dataChangeWatcher).forPath("/path");
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorClient, "/path", true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

            /**
             * Called when a change has occurred
             *
             * @param client the client
             * @param event  describes the change
             * @throws Exception errors
             */
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED) {
                    List<ChildData> childDataList = pathChildrenCache.getCurrentData();
                    for (ChildData childData : childDataList) {
                        System.out.println("==" + childData.getPath() + "," + new String(childData.getData(), "UTF-8"));
                    }
                }
            }
        });
        pathChildrenCache.start();

        NodeCache nodeCache = new NodeCache(curatorClient, "/path");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            /**
             * Called when a change has occurred
             */
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("data change in the path");
            }
        });
        nodeCache.start();
    }
}
