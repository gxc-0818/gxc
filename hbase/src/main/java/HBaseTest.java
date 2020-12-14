import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;

public class HBaseTest {
    public static void main(String[] args) throws IOException {
        final Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();


        NamespaceDescriptor.Builder aaa = NamespaceDescriptor.create("aaa");
        admin.createNamespace(aaa.build());

        TableName tableName = TableName.valueOf("aaa:test");
        admin.tableExists(tableName);

        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
        ColumnFamilyDescriptorBuilder info = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("info"));
        tableDescriptorBuilder.setColumnFamily(info.build());
        admin.createTable(tableDescriptorBuilder.build());

        admin.deleteTable(tableName);
        admin.close();

        admin.flush(tableName);


        Table table = connection.getTable(tableName);
        Put put = new Put(Bytes.toBytes("1001"));
        put.addColumn(Bytes.toBytes("info"),
                      Bytes.toBytes("age"),
                      Bytes.toBytes("100"));
        table.put(put);


        Delete delete = new Delete(Bytes.toBytes("1001"));
        delete.addFamily(Bytes.toBytes("info"));
        table.delete(delete);


        Get get = new Get(Bytes.toBytes("1001"));
        get.addFamily(Bytes.toBytes("info"));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
        }

        ResultScanner scanner = table.getScanner(new Scan());
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result next = iterator.next();
            Cell[] cells1 = next.rawCells();
            for (Cell cell : cells1) {
                System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
            }
        }

        table.close();
        connection.close();

    }
}
