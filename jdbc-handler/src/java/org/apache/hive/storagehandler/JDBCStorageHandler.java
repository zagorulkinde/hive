package org.apache.hive.storagehandler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.HiveMetaHook;
import org.apache.hadoop.hive.ql.metadata.DefaultStorageHandler;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde2.SerDe;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hive.storagehandler.serde.JDBCSerde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused") public class JDBCStorageHandler extends DefaultStorageHandler {
  private static final Logger LOG = LoggerFactory.getLogger(JDBCStorageHandler.class);

  @Override public Class<? extends InputFormat> getInputFormatClass() {
    return DBInputFormat.class;
  }

  @Override public Class<? extends OutputFormat> getOutputFormatClass() {
    return DBOutputFormat.class;
  }

  @Override public Class<? extends SerDe> getSerDeClass() {
    return JDBCSerde.class;
  }

  @Override public HiveMetaHook getMetaHook() {
    return null;
  }

  @Override public void configureJobConf(TableDesc tableDesc, JobConf jobConf) {


  }

  @Override public Configuration getConf() {
    return super.getConf();
  }

  @Override public void setConf(Configuration conf) {
    super.setConf(conf);
  }

  @Override public String toString() {
    return super.toString();
  }

}


