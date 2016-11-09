package org.apache.hive.storagehandler.db.mysql;

import org.apache.hive.storagehandler.db.HiveJDBCTypeBridge;

public class HiveMySqlTypeBridge implements HiveJDBCTypeBridge {
  @Override
  public <F, T> T toHiveType(Object sqlValue, Class<F> sqlType, Class<T> hiveType) {
    return null;
  }

  @Override
  public <F, T> T toSqlType(Object hiveValue, Class<T> sqlType, Class<F> hiveType) {
    return null;
  }
}
