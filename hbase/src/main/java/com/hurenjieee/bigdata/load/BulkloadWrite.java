package com.hurenjieee.bigdata.load;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;

import java.io.IOException;


/**
 * @author Jack
 * @date 2019/8/27 23:35
 */
public class BulkloadWrite {

    public static void main(String[] args) throws Exception {
        Configuration configuration = HBaseConfiguration.create();

        configuration.set("hbase.zookeeper.quorum", "node1:2181,node2:2181,node3:2181");

        Connection connection = ConnectionFactory.createConnection(configuration);

        Admin admin = connection.getAdmin();

        TableName tableName = TableName.valueOf("t4");
        Table table = connection.getTable(tableName);
        LoadIncrementalHFiles loadIncrementalHFiles = new LoadIncrementalHFiles(configuration);
        loadIncrementalHFiles.doBulkLoad(new Path("hdfs://node1:9000/output_HFile"), admin, table, connection.getRegionLocator(tableName));
    }
}
