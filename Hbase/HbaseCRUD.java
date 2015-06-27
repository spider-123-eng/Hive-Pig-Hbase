package com.hbase.data.upload;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;



public class HbaseCRUD {
	public static String tableName = "userDetails";
	public static void createTable(){
		Configuration conf = HBaseConfiguration.create();
		try{
		HBaseAdmin admin = new HBaseAdmin(conf);

		if (admin.tableExists(tableName)) {
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}

		HTableDescriptor tableDes = new HTableDescriptor(tableName);

		HColumnDescriptor cf1 = new HColumnDescriptor("FirstName");
		HColumnDescriptor cf2 = new HColumnDescriptor("LastName");
		HColumnDescriptor cf3 = new HColumnDescriptor("EmailId");
		HColumnDescriptor cf4 = new HColumnDescriptor("City");
		tableDes.addFamily(cf1);
		tableDes.addFamily(cf2);
		tableDes.addFamily(cf3);
		tableDes.addFamily(cf4);
		admin.createTable(tableDes);
		}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error processing  caused by " + e.getMessage());
        }
	}
	public static void putData(){
		
		Configuration conf = HBaseConfiguration.create();
		try{
		HTable hTable = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes("Jagan Anna"));
		put.add(Bytes.toBytes("FirstName"), Bytes.toBytes("first_name"), Bytes.toBytes("Jagan"));			
        put.add(Bytes.toBytes("LastName"), Bytes.toBytes("last_name"), Bytes.toBytes("Reddy"));			
        put.add(Bytes.toBytes("EmailId"), Bytes.toBytes("emailId"), Bytes.toBytes("gun@gmail.com"));			
        put.add(Bytes.toBytes("City"), Bytes.toBytes("city"), Bytes.toBytes("Pulivendula-Pulli"));
		hTable.put(put);
		hTable.close();
		}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error processing  caused by " + e.getMessage());
        }
		
	}
	
	public static void deleteData(){
		
		Configuration conf = HBaseConfiguration.create();
		try{
		HTable hTable = new HTable(conf, "user");
		
		// TO delete only one column
		
		/*Delete delete1 = new Delete(Bytes.toBytes("Jagan Anna"));
		delete1.deleteColumns(Bytes.toBytes("FirstName"), Bytes.toBytes("first_name"));
		hTable.delete(delete1);*/
		
		//To delete entire Row
		Delete delete = new Delete(Bytes.toBytes("Jagan Anna"));
		hTable.delete(delete);
		
		hTable.close();		
		}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error processing  caused by " + e.getMessage());
        }
		
	}
	public static void getData(){
		
		Configuration conf = HBaseConfiguration.create();
		try{
		HTable hTable = new HTable(conf, "user");
		Get get = new Get(Bytes.toBytes("Jagan Anna"));
		Result result = hTable.get(get);
		System.out.println("RowId: " + Bytes.toString(result.getRow()));
		
		byte [] val1 = result.getValue(Bytes.toBytes("FirstName"), Bytes.toBytes("first_name"));
		System.out.println("FirstName : "+Bytes.toString(val1));
		
		byte [] lastName = result.getValue(Bytes.toBytes("LastName"), Bytes.toBytes("last_name"));
		System.out.println("LasttName : "+Bytes.toString(lastName));
		
		byte [] email = result.getValue(Bytes.toBytes("EmailId"), Bytes.toBytes("emailId"));
		System.out.println("EmailId : "+Bytes.toString(email));
		
		byte [] city = result.getValue(Bytes.toBytes("City"), Bytes.toBytes("city"));
		System.out.println("City : "+Bytes.toString(city));
		
		hTable.close();		
		}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error processing  caused by " + e.getMessage());
        }
		
	}
	
	public static void main(String[] args)  {
		HbaseCRUD.createTable();
		HbaseCRUD.putData();
		//HbaseCRUD.deleteData();
		HbaseCRUD.getData();
		
		}

}
