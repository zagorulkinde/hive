package org.apache.hive.storagehandler.db.mysql;

import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hive.storagehandler.db.HiveJDBCTypeBridge;
import org.apache.hive.storagehandler.db.HiveJDBCVendorBridge;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HiveMySqlVendorBridge implements HiveJDBCVendorBridge {
  @Override
  public ResultSetMetaData getResultSetMetaData(DBConfiguration configuration) {
    return null;
  }

  @Override
  public HiveJDBCTypeBridge getTypeBridge() {
    return null;
  }

  @Override
  public boolean supportsInsert() {
    return false;
  }

  @Override
  public PreparedStatement getInsertStatement(DBConfiguration configuration) throws SQLException {
    return null;
  }

  @Override
  public boolean supportsUpsert() {
    return false;
  }

  @Override
  public PreparedStatement getUpsertStatement(DBConfiguration configuration) throws SQLException {
    return null;
  }

  @Override
  public boolean supportsDelete() {
    return false;
  }

  @Override
  public PreparedStatement getDeleteStatement(DBConfiguration configuration) throws SQLException {
    return null;
  }
}
