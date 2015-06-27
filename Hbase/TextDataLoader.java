package com.hbase.data.upload;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


//This class is used to load bulk data from a text file in to Hbase table
public class TextDataLoader {

    private static final String TABLE_NAME = "user";

    public static void main(String[] args) throws Exception {

        //Configuration conf = HBaseConfiguration.create();
        //change here if you want to change the HBase installation. 
        //conf.set("hbase.master", "localhost:60000");

        Configuration config = HBaseConfiguration.create();
	   // config.set("hbase.master", "localhost:60020");
        HTable table = new HTable(config, TABLE_NAME);
        

        //Change here if you want to change the input file. 
        BufferedReader reader = new BufferedReader(new FileReader("/home/cloudera/ebooks/Hbase/user.txt"));

        try {
            String line = null;
            // skip first line
            reader.readLine();
            while ((line = reader.readLine()) != null ) {
                try {

                    String[] tokens = line.split(",");
                    if(tokens.length>1){
                    String rowKey = tokens[0];
                                      
                    Put put = new Put(Bytes.toBytes(rowKey));
                    put.add(Bytes.toBytes("FirstName"), Bytes.toBytes("first_name"), Bytes.toBytes(tokens[1]));			
                    put.add(Bytes.toBytes("LastName"), Bytes.toBytes("last_name"), Bytes.toBytes(tokens[2]));			
                    put.add(Bytes.toBytes("EmailId"), Bytes.toBytes("emailId"), Bytes.toBytes(tokens[3]));			
                    put.add(Bytes.toBytes("City"), Bytes.toBytes("city"), Bytes.toBytes(tokens[4]));
                    table.put(put);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error processing " + line + " caused by " + e.getMessage());
                }
            }
        } catch (IOException e) {
            try {
                reader.close();
            } catch (IOException e1) {
                   e1.printStackTrace();
            }
        }
    }
}
