package org.apache.hive.storagehandler.serde;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.Constants;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeSpec;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.annotation.Nullable;

@SerDeSpec(schemaProps = {
    Constants.JDBC_HIVE_STORAGE_HANDLER_DRIVER_CLASS,
    Constants.JDBC_HIVE_STORAGE_HANDLER_MAPRED_TABLE_NAME,
    Constants.JDBC_HIVE_STORAGE_HANDLER_MAPRED_URL})
public class JDBCSerde extends AbstractSerDe {
  private static final Logger LOG = LoggerFactory.getLogger(JDBCSerde.class);

  @Override
  public void initialize(@Nullable Configuration conf, Properties tbl) throws SerDeException {
    LOG.debug("!Properties");

    if (conf != null && tbl != null) {
      LOG.debug(conf.toString());
      LOG.debug(tbl.toString());
    }

  }

  @Override
  public Class<? extends Writable> getSerializedClass() {
    return null;
  }

  @Override
  public Writable serialize(Object obj, ObjectInspector objInspector) throws SerDeException {
    return null;
  }

  @Override
  public SerDeStats getSerDeStats() {
    return null;
  }

  @Override
  public Object deserialize(Writable blob) throws SerDeException {
    return null;
  }

  @Override
  public ObjectInspector getObjectInspector() throws SerDeException {
    return null;
  }
}
