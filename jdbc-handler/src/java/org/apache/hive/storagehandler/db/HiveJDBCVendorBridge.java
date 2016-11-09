package org.apache.hive.storagehandler.db;

import org.apache.hadoop.mapred.lib.db.DBConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * HiveJDBCVendorBridge encapsulates the vendor specific differences between database products.
 * <P>
 * Implementations are responsible for generating SQL template strings that can be used in
 * PreparedStatements to write records back to the database. Implementations are also responsible
 * for providing concrete implementations of {@link HiveJDBCTypeBridge}. This class handles coercing
 * values between Hive types and JDBC types.
 */
public interface HiveJDBCVendorBridge {
  /**
   * Return an instance of ResultSetMetaData that the storage handler can use to determine
   * the column names and types for the underlying database table.
   * <p>
   * The value of {@link ResultSetMetaData#getColumnCount()} must match the number of columns
   * on the Hive table.
   *
   * @param configuration a DBConfiguration instance configured with the mapred.jdbc.* values from
   *                      the TBLPROPERTIES
   */
  ResultSetMetaData getResultSetMetaData(DBConfiguration configuration);

  /**
   * Return an implementation of {@link HiveJDBCTypeBridge} that will coerce values between
   * the types defined in the Hive CREATE TABLE statement and the types returned by calls to
   * {@link ResultSetMetaData#getColumnClassName(int)} on the ResultSetMetaData instance returned
   * by {@link HiveJDBCVendorBridge#getResultSetMetaData(DBConfiguration)}.
   */
  HiveJDBCTypeBridge getTypeBridge();

  /**
   * Whether the table backed by this vendor bridge can be used as the target of INSERT statements.
   */
  boolean supportsInsert();

  /**
   * Provide a PreparedStatement that can be used to INSERT records into the database.
   * <p>
   * The storage handler will call {@link PreparedStatement#setObject(int, Object)} once
   * for each column described by the object returned by
   * {@link HiveJDBCVendorBridge#getResultSetMetaData(DBConfiguration)}.
   *
   * @param configuration A DBConfiguration instance configured with the mapred.jdbc.* values from
   *                      the TBLPROPERTIES
   */
  PreparedStatement getInsertStatement(DBConfiguration configuration) throws SQLException;

  /**
   * Whether the table backed by this vendor bridge can be used as the target of UPSERT statements.
   */
  boolean supportsUpsert();

  /**
   * Provide a PreparedStatement that can be used to UPSERT records into the database.
   * <p>
   * The storage handler will call {@link PreparedStatement#setObject(int, Object)} once
   * for each column described by the object returned by
   * {@link HiveJDBCVendorBridge#getResultSetMetaData(DBConfiguration)}.
   *
   * @param configuration A DBConfiguration instance configured with the mapred.jdbc.* values from
   *                      the TBLPROPERTIES
   */
  PreparedStatement getUpsertStatement(DBConfiguration configuration) throws SQLException;

  /**
   * Whether the table backed by this vendor bridge can be used as the target of DELETE statements.
   */
  boolean supportsDelete();

  /**
   * Provide a PreparedStatement that can be used to DELETE records in the database.
   * <p>
   * The storage handler will call {@link PreparedStatement#setObject(int, Object)} once
   * for each column described by the object returned by
   * {@link HiveJDBCVendorBridge#getResultSetMetaData(DBConfiguration)}.
   *
   * @param configuration A DBConfiguration instance configured with the mapred.jdbc.* values from
   *                      the TBLPROPERTIES
   */
  PreparedStatement getDeleteStatement(DBConfiguration configuration) throws SQLException;
}
