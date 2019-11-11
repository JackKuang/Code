package com.hurenjieee.bigdata.rpc;

/**
 * 协议
 * @author Jack
 * @date 2019/7/28 22:32
 */
public interface ClientProtocol {
    long versionID=1234L;
    void makeDir(String path);

}
