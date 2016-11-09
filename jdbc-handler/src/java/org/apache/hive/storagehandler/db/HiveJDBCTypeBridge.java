package org.apache.hive.storagehandler.db;

public interface HiveJDBCTypeBridge {
  /**
   * Coerces a sqlValue returned from a ResultSet to the type expected by Hive.
   *
   * @param sqlValue an Object returned from {@link ResultSet#getObject}
   * @param sqlType  the Class object returned from {@link ResultSetMetaData#getColumnClassName}
   * @param hiveType the Class object returned from {@link PrimitiveObjectInspector#getJavaPrimitiveClass}
   *                 for the target Hive column
   * @return the sqlValue as an instance of hiveType
   */
  <F, T> T toHiveType(Object sqlValue, Class<F> sqlType, Class<T> hiveType);

  /**
   * Coerces a hiveValue to the type expected by the PreparedStatement that will
   * write it to the database.
   *
   * @param hiveValue an Object that is part of a hive row
   * @param sqlType   the Class object returned from {@link ResultSetMetaData#getColumnClassName}
   * @param hiveType  the Class object returned from {@link PrimitiveObjectInspector#getJavaPrimitiveClass}
   * @return the hiveValue as an instance of sqlType
   */
  <F, T> T toSqlType(Object hiveValue, Class<T> sqlType, Class<F> hiveType);
}
