package org.apache.hive.storagehandler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hive.storagehandler.serde.JDBCSerde;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class TestJDBCStorageHandler {
  private JDBCStorageHandler jdbcStorageHandler;
  @Before public void setUp() throws Exception {
    jdbcStorageHandler = new JDBCStorageHandler();

    Configuration configuration = new Configuration();
/*
*
* CREATE EXTERNAL TABLE books(
 book_id            INT,
 book_name          STRING,
 author_name        STRING,
 book_isbn          STRING
)
STORED BY "org.apache.hive.storagehandler.JDBCStorageHandler"
TBLPROPERTIES (
 "mapred.jdbc.driver.class" = "oracle.jdbc.OracleDriver",
 "mapred.jdbc.url" = "jdbc:oracle:thin:@//localhost:49161/XE",
 "mapred.jdbc.input.table.name" = "books",
 "mapred.jdbc.username" = "system",
 "mapred.jdbc.password" = "oracle",
 "hive.jdbc.update.on.duplicate" = "true"
);
*
* */
    configuration.set("mapred.jdbc.driver.class" , "oracle.jdbc.OracleDriver");
    configuration.set("mapred.jdbc.url" , "jdbc:oracle:thin:@//localhost:49161/XE");

    configuration.set("mapred.jdbc.input.table.name", "books");
    configuration.set("mapred.jdbc.username", "system");
    configuration.set("mapred.jdbc.password", "oracle");

    jdbcStorageHandler.setConf(configuration);


    JDBCSerde serde = new JDBCSerde();

    Properties properties = new Properties();
    properties.setProperty("mapred.jdbc.driver.class" , "oracle.jdbc.OracleDriver");
    properties.setProperty("mapred.jdbc.url" , "jdbc:oracle:thin:@//localhost:49161/XE");
    properties.setProperty("mapred.jdbc.input.table.name", "books");
    properties.setProperty("mapred.jdbc.username", "system");
    properties.setProperty("mapred.jdbc.password", "oracle");

    serde.initialize(configuration, properties );

  }

  @After public void tearDown() throws Exception {

  }

  @Test public void jdbcStorageHandlerTest() {

    jdbcStorageHandler.getConf();

  }


}