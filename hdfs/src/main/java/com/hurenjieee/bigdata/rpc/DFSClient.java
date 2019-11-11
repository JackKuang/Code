package com.hurenjieee.bigdata.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Jack
 * @date 2019/7/28 22:39
 */
public class DFSClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientProtocol protocol = RPC.getProxy(ClientProtocol.class,
                1234L,
                new InetSocketAddress("localhost", 8888),
                new Configuration());
        protocol.makeDir("/test");
        Thread.sleep(1000);
    }
}
