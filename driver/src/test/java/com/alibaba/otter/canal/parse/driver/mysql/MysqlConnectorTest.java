package com.alibaba.otter.canal.parse.driver.mysql;

import com.alibaba.otter.canal.parse.driver.mysql.packets.server.FieldPacket;
import com.alibaba.otter.canal.parse.driver.mysql.packets.server.ResultSetPacket;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * http://www.tianshouzhi.com/api/tutorials/canal/403
 */
public class MysqlConnectorTest {

    @Test
    public void testQuery() {

        MysqlConnector connector = new MysqlConnector(new InetSocketAddress("127.0.0.1", 3306),
                "root", "root");
        try {
            //1 创建数据库连接
            connector.connect();

            //2 构建查询执行器，并执行查询
            MysqlQueryExecutor executor = new MysqlQueryExecutor(connector);

            //3 ResultSetPacket作用类似于ResultSet
            // 显示系统变量 https://blog.csdn.net/zztfj/article/details/6181379
            ResultSetPacket result = executor.query("show variables like '%char%';");
            System.out.println(" -------------------- QUERY RESULT -------------------- ");
            System.out.println(result);

            result = executor.query("select version()");
            System.out.println(" -------------------- QUERY RESULT -------------------- ");
            //4 对查询结果进行解析
            //FieldPacket中封装的字段的一些源信息，如字段的名称，类型等
            List<FieldPacket> fieldDescriptors = result.getFieldDescriptors();
            //打印字段名称
            for (FieldPacket fieldDescriptor : fieldDescriptors) {
                String fieldName = fieldDescriptor.getName();
                System.out.print(fieldName + "  ");
            }
            //5 对查询结果进行解析
            //字段的值使用String表示，jdbc编程中使用的getInt，getBoolean，getDate等方法，实际上都是都是字符串转换得到的
            System.out.println(result.getFieldValues());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                connector.disconnect();
            } catch (IOException e) {
                Assert.fail(e.getMessage());
            }
        }
    }

    // @Test
    public void testUpdate() {

        MysqlConnector connector = new MysqlConnector(new InetSocketAddress("127.0.0.1", 3306), "xxxxx", "xxxxx");
        try {
            connector.connect();
            MysqlUpdateExecutor executor = new MysqlUpdateExecutor(connector);
            executor.update("insert into test.test2(id,name,score,text_value) values(null,'中文1',10,'中文2')");
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                connector.disconnect();
            } catch (IOException e) {
                Assert.fail(e.getMessage());
            }
        }
    }
}
