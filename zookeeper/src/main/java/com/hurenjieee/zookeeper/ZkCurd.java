package com.hurenjieee.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jack
 * @date 2019/7/27 17:08
 */
public class ZkCurd {

    //定义会话的超时时间
    private final static int SESSION_TIME = 30000;
    //定义zookeeper集群的ip地址
    private final static String ZK_SERVERS = "node1:2181,node2:2181,node3:2181";

    static ZooKeeper zooKeeper = null;

    Watcher watcher = new Watcher() {
        public void process(WatchedEvent watchedEvent) {
            System.out.println("event:" + watchedEvent.toString());
        }
    };

    @Before
    public void initZookeeper() throws Exception {
        zooKeeper = new ZooKeeper(ZK_SERVERS, SESSION_TIME, watcher);
        Long sessionId = zooKeeper.getSessionId();
        System.out.println("sesssionId:" + sessionId);
    }

    @After
    public void closeZookeeper() throws InterruptedException {
        zooKeeper.close();
    }

    /**
     * 创建持久化节点
     *
     * @throws Exception
     */
    @Test
    public void createPersistentNode() throws Exception {
        String result = zooKeeper.create("/persistentNode", "persistentNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("create result:" + result);
        System.out.println("节点是否存在:" + zooKeeper.exists("/persistentNode", null));
    }

    /**
     * 删除持久化节点
     *
     * @throws Exception
     */
    @Test
    public void deletePersistentNode() throws Exception {
        zooKeeper.delete("/persistentNode", 0);
        System.out.println("节点是否存在:" + zooKeeper.exists("/persistentNode", null));
    }

    /**
     * 创建临时节点
     *
     * @throws Exception
     */
    @Test
    public void createEphemeralNode() throws Exception {
        String result = zooKeeper.create("/ephemeralNode", "ephemeralNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("create result:" + result);
        System.out.println("节点是否存在:" + zooKeeper.exists("/ephemeralNode", null));
        // get /ephemeralNode 可匹配
        Thread.sleep(10000);
        // 程序运行结束，session断开，get /ephemeralNode找不到节点了
    }

    /**
     * 判断是否存在节点
     *
     * @throws Exception
     */
    @Test
    public void exitsNode() throws Exception {
        Stat stat = zooKeeper.exists("/persistentNode", false);
//        Stat stat = zooKeeper.exists("/persistentNode",false);
        if (stat != null) {
            System.out.println(stat.getAversion());
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }

    /**
     * 获取节点数据
     *
     * @throws Exception
     */
    @Test
    public void getData() throws Exception {
        byte[] data = zooKeeper.getData("/persistentNode", null, null);
        System.out.println(new String(data));
    }


    /**
     * 测试
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void testGetData1() throws KeeperException, InterruptedException {
        //获得/test节点的数据；观察连接的zkServer是哪个？
        byte[] data = zooKeeper.getData("/persistentNode", null, null);
        System.out.println("data is :" + new String(data));

        Thread.sleep(10000);
        //在休眠期间，关闭连接的zkServer

        //问：能否再次获得/test的数据？
        byte[] data1 = zooKeeper.getData("/persistentNode", null, null);
        System.out.println("data is :" + new String(data1));
    }


    @Test
    public void testGetDataWatch() throws Exception{
        byte[] data = zooKeeper.getData("/persistentNode", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("===>>> event type: " + event.getType());
                System.out.println("triger watcher!");
            }
        }, null);
        System.out.println("------------->>> data is " + new String(data));

        //观察现象
        System.out.println("the first time to set data");
        zooKeeper.setData("/persistentNode", "persistentNode2".getBytes(), -1);

        //观察现象
        System.out.println("the second time to set data");
        zooKeeper.setData("/persistentNode", "persistentNode3".getBytes(), -1);

        //Watcher监听只能监听一次事件
    }
}
