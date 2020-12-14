import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;

public class HBaseAPI {

    public static void main(String[] args) throws IOException {

        final Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();

        //Todo DDL 数据定义
        //todo 1创建命名空间
        //createNameSpace(admin);

        //todo 2判断表是否存在
        //tableExists(admin,TableName.valueOf("gxc:test"));

        //todo 3创建表
        //createTable(admin);

        //todo 4 删除表
        //deleteTable(admin,TableName.valueOf("gxc:test1"));

        //Todo DML 数据操作

        //todo 1 插入数据
        //put(connection,TableName.valueOf("gxc:test"));
        //todo 2 单条数据查询
        //getOneRow(connection,TableName.valueOf("gxc:test"));
        //todo 3 扫描数据
        //scan(connection,TableName.valueOf("gxc:test"));
        //todo 4 删除数据
        delete(connection, TableName.valueOf("gxc:test"));

        admin.close();
        connection.close();





    }

    public static void createNameSpace(Admin admin){
        NamespaceDescriptor.Builder builder = NamespaceDescriptor.create("gxc1");
        try {
            admin.createNamespace(builder.build());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("命名空间创建成功");;
    }

    public static boolean tableExists(Admin admin,TableName tableName) throws IOException {
        return admin.tableExists(tableName);
    }

    public static void createTable(Admin admin) throws IOException {
        TableName tableName = TableName.valueOf("gxc:test3");
        if (tableExists(admin,tableName)){
            System.out.println("表已存在");
            return;
        }

        //先建个表builder
        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);

        //再建个列族的builder
        ColumnFamilyDescriptorBuilder columnBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("info"));

        //列族的信息再赋给表builder
        builder.setColumnFamily(columnBuilder.build());

        try {
            admin.createTable(builder.build());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("表创建成功");
    }

    public static void deleteTable(Admin admin,TableName tableName) throws IOException {
        if (!tableExists(admin,tableName)){
            System.out.println("表不存在 无法删除");
            return;
        }
        //删除表之前需要先把表禁用
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        System.out.println("删除成功");
    }

    public static void put(Connection connection,TableName tableName) throws IOException {

        Table table = connection.getTable(tableName);
        if (table != null){
            Put put = new Put(Bytes.toBytes("1001"));
            put.addColumn(Bytes.toBytes("info"), //列族
                          Bytes.toBytes("age"),   //列名
                          Bytes.toBytes("200"));  //值
            table.put(put);
            table.close();
            System.out.println("put成功");
        }

    }

    public static void getOneRow(Connection connection,TableName tableName) throws IOException {
        Table table = connection.getTable(tableName);
        Get get = new Get(Bytes.toBytes("1001"));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        if (cells != null) {
            for (Cell cell : cells) {
                System.out.println("family:"+Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("column:"+Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("value:"+Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("tm:"+cell.getTimestamp());
            }
        }
        table.close();
    }

    public static void scan(Connection connection,TableName tableName) throws IOException {
        Table table = connection.getTable(tableName);
        ResultScanner scanner = table.getScanner(new Scan());
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.printf("family:"+Bytes.toString(CellUtil.cloneFamily(cell))+" ");
                System.out.printf("column:"+Bytes.toString(CellUtil.cloneQualifier(cell))+" ");
                System.out.printf("value:"+Bytes.toString(CellUtil.cloneValue(cell))+" ");
                System.out.println("tm:"+cell.getTimestamp());
            }
        }
        table.close();

    }

    public static void delete(Connection connection,TableName tableName) throws IOException {
        try {
            Table table = connection.getTable(tableName);
            Delete delete = new Delete(Bytes.toBytes("1002"));
            delete.addColumn(Bytes.toBytes("info"),Bytes.toBytes("age"));
            table.delete(delete);
            table.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("删除成功");

    }
}
