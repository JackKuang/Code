package com.hurenjieee.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jack
 * @date 2019/7/27 19:19
 */
public class CuratorClient {
    //定义zookeeper集群的ip地址
    private final static String ZK_SERVERS = "node1:2181,node2:2181,node3:2181";

    CuratorFramework client;

    @Before
    public void initClient() {
        client = CuratorFrameworkFactory.newClient(ZK_SERVERS, new RetryNTimes(10, 5000));
        client.start();
    }

    @After
    public void closeClient() {
        client.close();
    }

    @Test
    public void createNode() throws Exception {
        Object o = client.create()
                .creatingParentsIfNeeded()
                .forPath("/node1", "node".getBytes());
        System.out.println(o.toString());
    }

    @Test
    public void ls() throws Exception{
        Object o = client.getChildren().forPath("/");
        System.out.println(o.toString());
    }

    @Test
    public void getData() throws Exception{
        byte[] o = client.getData().forPath("/node");
        System.out.println(new String(o));
    }

    @Test
    public void setData() throws Exception{
        Stat stat = client.setData().forPath("/node","new_node".getBytes());
        System.out.println(stat.toString());
    }

    @Test
    public void delete() throws Exception{
        client.delete().forPath("/node");

    }

}
