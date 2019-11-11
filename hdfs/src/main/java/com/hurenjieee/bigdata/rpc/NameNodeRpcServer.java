package com.hurenjieee.bigdata.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

import java.io.IOException;

/**
 * RPC服务端
 *
 * @author Jack
 * @date 2019/7/28 22:33
 */
public class NameNodeRpcServer implements ClientProtocol {

    @Override
    public void makeDir(String path) {
        System.out.println("Server create path:" + path);
    }

    public static void main(String[] args) throws IOException {
        Server server = new RPC.Builder(new Configuration())
                .setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(ClientProtocol.class)
                .setInstance(new NameNodeRpcServer())
                .build();
        server.start();
    }

}
