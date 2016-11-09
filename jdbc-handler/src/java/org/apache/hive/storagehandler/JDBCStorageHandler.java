package org.apache.hive.storagehandler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.HiveMetaHook;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.ql.metadata.DefaultStorageHandler;
import org.apache.hadoop.hive.ql.metadata.HiveStoragePredicateHandler;
import org.apache.hadoop.hive.ql.plan.ExprNodeDesc;
import org.apache.hadoop.hive.serde2.Deserializer;
import org.apache.hadoop.hive.serde2.SerDe;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hive.storagehandler.serde.JDBCSerde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCStorageHandler extends DefaultStorageHandler
    implements HiveMetaHook, HiveStoragePredicateHandler {
  private static final Logger LOG = LoggerFactory.getLogger(JDBCStorageHandler.class);

  @Override public void preCreateTable(Table table) throws MetaException {
    if (!MetaStoreUtils.isExternalTable(table)) {
      throw new MetaException("Table in Druid needs to be declared as EXTERNAL");
    }
    LOG.debug("Got created table {}", table);
  }

  @Override public Class<? extends InputFormat> getInputFormatClass() {

    // TODO
    return super.getInputFormatClass();
  }

  @Override public Class<? extends OutputFormat> getOutputFormatClass() {
    // TODO
    return super.getOutputFormatClass();
  }

  @Override public Class<? extends SerDe> getSerDeClass() {
    return JDBCSerde.class;
  }

  @Override public HiveMetaHook getMetaHook() {
    return super.getMetaHook();
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

  @Override public void rollbackCreateTable(Table table) throws MetaException {

  }

  @Override public void commitCreateTable(Table table) throws MetaException {

  }

  @Override public void preDropTable(Table table) throws MetaException {

  }

  @Override public void rollbackDropTable(Table table) throws MetaException {

  }

  @Override public void commitDropTable(Table table, boolean deleteData) throws MetaException {

  }

  @Override public DecomposedPredicate decomposePredicate(JobConf jobConf,
      Deserializer deserializer, ExprNodeDesc predicate) {
    return null;
  }
}
