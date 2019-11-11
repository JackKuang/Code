package com.hurenjieee.bigdata.operate;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jack
 * @date 2019/8/26 21:02
 */
public class HBaseOperate {

    private Configuration conf = null;

    private Connection connection = null;

    private Admin admin = null;

    @Before
    public void init() throws Exception {
        conf = HBaseConfiguration.create();
        //对于hbase的客户端来说，只需要知道hbase所使用的zookeeper集群地址就可以了
        conf.set("hbase.zookeeper.quorum", "node1:2181,node2:2181,node3:2181");
        connection = ConnectionFactory.createConnection(conf);
        admin = connection.getAdmin();
    }

    @After
    public void destory() throws Exception {
        admin.close();
        connection.close();
    }

    /**
     * create 'tableName','列族1'，'列族2'
     * @throws Exception
     */
    @Test
    public void createTable() throws Exception {

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("t_user_info".getBytes()));
        HColumnDescriptor hColumnDescriptor1 = new HColumnDescriptor("base_info");
        HColumnDescriptor hColumnDescriptor2 = new HColumnDescriptor("extra_info");
        hColumnDescriptor2.setVersions(1, 3);
        tableDescriptor.addFamily(hColumnDescriptor1).addFamily(hColumnDescriptor2);

        admin.createTable(tableDescriptor);
    }

    /**
     * alter 't_user_info' ,'base_info',
     * alter 't1', NAME => 'f1', VERSIONS => 5
     * @throws Exception
     */
    @Test
    public void modifyTable() throws Exception {
        HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf("t_user_info"));

        HColumnDescriptor hColumnDescriptor = tableDescriptor.getFamily("extra_info".getBytes());
        hColumnDescriptor.setVersions(2, 4);

        tableDescriptor.addFamily(new HColumnDescriptor("other_info"));
        admin.modifyTable(TableName.valueOf("t_user_info"), tableDescriptor);
    }

    /**
     * put 't_user_info','rk00001','base_info:name','lisi'
     * @throws Exception
     */
    @Test
    public void putData() throws Exception {
        Table table = connection.getTable(TableName.valueOf("t_user_info"));
        List<Put> puts = new ArrayList<Put>();

        Put put01 = new Put("user001".getBytes());
        put01.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhangsan"));

        Put put02 = new Put("user001".getBytes());
        put02.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("password"), Bytes.toBytes("123456"));

        Put put03 = new Put("user002".getBytes());
        put03.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("lisi"));
        put03.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put04 = new Put("zhang_sh_01".getBytes());
        put04.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang01"));
        put04.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put05 = new Put("zhang_sh_02".getBytes());
        put05.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang02"));
        put05.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put06 = new Put("liu_sh_01".getBytes());
        put06.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("liu01"));
        put06.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put07 = new Put("zhang_bj_01".getBytes());
        put07.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang03"));
        put07.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put08 = new Put("zhang_bj_01".getBytes());
        put08.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang04"));
        put08.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        //把所有的put对象添加到一个集合中
        puts.add(put01);
        puts.add(put02);
        puts.add(put03);
        puts.add(put04);
        puts.add(put05);
        puts.add(put06);
        puts.add(put07);
        puts.add(put08);

        table.put(puts);
        table.close();

    }

    /**
     *   get 't_user_info',"rowkey"
     * @throws Exception
     */
    @Test
    public void getOne() throws Exception {
        Table table = connection.getTable(TableName.valueOf("t_user_info"));

        Get get = new Get("user001".getBytes());
        Result result = table.get(get);
        List<Cell> cells = result.listCells();
        for (Cell c : cells) {
            // 行
            String row = new String(CellUtil.cloneRow(c));
            // 列族
            String family = new String(CellUtil.cloneFamily(c));
            // 列名称
            String qualifier = new String(CellUtil.cloneQualifier(c));
            // 列字段
            String value = new String(CellUtil.cloneValue(c));

            System.out.println(row + "::::" + family + "::::" + qualifier + "::::" + value);
        }
        table.close();
    }

    /**
     *   scan 't_user_info'
     * @throws Exception
     */
    @Test
    public void scanData() throws Exception {
        Table table = connection.getTable(TableName.valueOf("t_user_info"));

        Scan scan = new Scan();

        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell c : cells) {
                // 行
                String row = new String(CellUtil.cloneRow(c));
                // 列族
                String family = new String(CellUtil.cloneFamily(c));
                // 列名称
                String qualifier = new String(CellUtil.cloneQualifier(c));
                // 列字段
                String value = new String(CellUtil.cloneValue(c));

                System.out.println(row + "::::" + family + "::::" + qualifier + "::::" + value);
            }

        }
        table.close();
    }

    /**
     * delete 't_user_info','user001','base_info:password'
     * @throws Exception
     */
    @Test
    public void deleteOne() throws Exception {
        Table table = connection.getTable(TableName.valueOf("t_user_info"));

        Delete delete = new Delete("user001".getBytes());
        delete.addColumn("base_info".getBytes(), "password".getBytes());
        table.delete(delete);
        table.close();
    }

    @Test
    public void filterData() throws Exception {
        // 行键前缀过滤器
//        Filter filter = new PrefixFilter("liu".getBytes());
//        scanData(filter);

        // 行过滤器，需要一个比较运算符和比较器
//        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator("user002".getBytes()));
//        scanData(rowFilter);

        // 包含01
//        RowFilter rf2 = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("01"));//rowkey包含"01"子串的
//        scanData(rf2);

        // 针对列族名的过滤器   返回结果中只会包含满足条件的列族中的数据
//        FamilyFilter familyFilter1 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("base_info")));
//        FamilyFilter familyFilter2 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryPrefixComparator(Bytes.toBytes("base")));
//        scanData(familyFilter2);


        //针对列名的过滤器 返回结果中只会包含满足条件的列的数据
//		QualifierFilter qualifierFilter1 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("password")));
//		QualifierFilter qualifierFilter2 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryPrefixComparator(Bytes.toBytes("user")));
//		scanData(qualifierFilter2);

        // 针对某一列的value的比较器来过滤
//        ByteArrayComparable comparable1 = new RegexStringComparator("^zhang");
//        ByteArrayComparable comparable2 = new SubstringComparator("si");
//        SingleColumnValueExcludeFilter filter = new SingleColumnValueExcludeFilter("base_info".getBytes(),"username".getBytes(), CompareFilter.CompareOp.EQUAL,comparable1);
//        scanData(filter);


        //多个过滤器同时使用   select * from t1 where id >10 and age <30

        FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryPrefixComparator(Bytes.toBytes("base")));
        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter("password".getBytes());
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(familyFilter);
        filterList.addFilter(columnPrefixFilter);
        scanData(filterList);

    }

    public void scanData(Filter filter) throws Exception {
        Table table = connection.getTable(TableName.valueOf("t_user_info"));
        Scan scan = new Scan();
        scan.setFilter(filter);

        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell c : cells) {
                // 行
                String row = new String(CellUtil.cloneRow(c));
                // 列族
                String family = new String(CellUtil.cloneFamily(c));
                // 列名称
                String qualifier = new String(CellUtil.cloneQualifier(c));
                // 列字段
                String value = new String(CellUtil.cloneValue(c));

                System.out.println(row + "::::" + family + "::::" + qualifier + "::::" + value);
            }

        }
    }


    /**
     * disable 't_user_info'
     * drop 't_user_info'
     * @throws Exception
     */
    @Test
    public void testDrop() throws Exception {
        Admin admin = connection.getAdmin();
        admin.disableTable(TableName.valueOf("t_user_info"));
        admin.deleteTable(TableName.valueOf("t_user_info"));
        admin.close();
    }
}
