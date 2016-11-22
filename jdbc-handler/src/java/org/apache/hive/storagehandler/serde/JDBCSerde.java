package org.apache.hive.storagehandler.serde;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.Constants;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeSpec;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.Nullable;

import static java.util.Arrays.asList;

@SerDeSpec(schemaProps = { Constants.JDBC_STORAGE_HANDLER_DRIVER_CLASS,
    Constants.JDBC_STORAGE_HANDLER_MAPRED_TABLE_NAME,
    Constants.JDBC_STORAGE_HANDLER_MAPRED_URL }) public class JDBCSerde extends AbstractSerDe {
  private static final Logger LOG = LoggerFactory.getLogger(JDBCSerde.class);

  private String[] strings = { "book_id", "book_name", "author_name", "book_isbn" };
  private Connection dbConnection;
  private int columnsNum;

  @Override public void initialize(@Nullable Configuration conf, Properties tbl)
      throws SerDeException {
    Objects.requireNonNull(conf, "Hadoop configuration must not be null!");
    Objects.requireNonNull(conf, "Tbl props must not be null");

    conf.set(DBConfiguration.INPUT_CLASS_PROPERTY, CustomDBWritable.class.getName());
    DBConfiguration configuration = new DBConfiguration(conf);

    DBConfiguration.configureDB(conf, tbl.getProperty(Constants.JDBC_STORAGE_HANDLER_DRIVER_CLASS),
        tbl.getProperty(Constants.JDBC_STORAGE_HANDLER_MAPRED_URL),
        tbl.getProperty(Constants.JDBC_STORAGE_HANDLER_MAPRED_USER),
        tbl.getProperty(Constants.JDBC_STORAGE_HANDLER_MAPRED_PASSWORD));

    configuration.setInputFieldNames(strings);

    //super.configureJobConf(tableDesc, jobConf);
    // org.apache.hadoop.mapreduce.InputFormat
    Job job = null;
    try {
      job = Job.getInstance();
    } catch (IOException e) {
      LOG.error("Job error: {}", e);
    }
    //String[] strings = { "book_id", "book_name", "author_name", "book_isbn" };

    LOG.debug("DBInput format init:");

    if (job != null) {
      DBInputFormat.setInput(job, CustomDBWritable.class, "books", null, null, strings);
      try {
        DBOutputFormat.setOutput(job, "books", strings);
      } catch (IOException e) {
        LOG.error("OutError");
      }
    }

    try {
      dbConnection = configuration.getConnection();

      LOG.info("Schema: {}", dbConnection.getSchema());

      Statement statement = dbConnection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM books WHERE ROWNUM = 1");
      columnsNum = rs.getMetaData().getColumnCount();
      LOG.debug("Columns num: {}", columnsNum);
    } catch (ClassNotFoundException e) {
      LOG.error("Could't find driver: {}",
          tbl.getProperty(Constants.JDBC_STORAGE_HANDLER_DRIVER_CLASS));
    } catch (SQLException e) {
      LOG.error("SQL error", e);
    }
  }

  @Override public Class<? extends Writable> getSerializedClass() {
    return null;
  }

  @Override public Writable serialize(Object obj, ObjectInspector objInspector)
      throws SerDeException {

    LOG.info("Invoker serilize with: {} {}", obj, objInspector);

    return null;
  }

  @Override public SerDeStats getSerDeStats() {
    return null;
  }

  @Override public Object deserialize(Writable blob) throws SerDeException {
    LOG.info("BLOB! {}", blob);

    List<Object> row = new ArrayList<>(strings.length);

    CustomDBWritable input = (CustomDBWritable) blob;
    Text columnKey = new Text();

    for (int i = 0; i < columnsNum; i++) {
      columnKey.set(strings[i]);
    }

    row.add(input.getBookId());
    row.add(input.getBookName());
    row.add(input.getAuthorName());
    row.add(input.getBookIsbn());

    return row;
  }

  @Override public ObjectInspector getObjectInspector() throws SerDeException {
    List<ObjectInspector> fieldInspectors = new ArrayList<>(3);
    for (int i = 0; i < columnsNum; i++) {
      fieldInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }
    return ObjectInspectorFactory
        .getStandardStructObjectInspector(asList(strings), fieldInspectors);
  }

  public static class CustomDBWritable
      implements org.apache.hadoop.mapred.lib.db.DBWritable, Writable {

    private long bookId;
    private String bookName;
    private String authorName;
    private String bookIsbn;

    public long getBookId() {
      return bookId;
    }

    public String getBookName() {
      return bookName;
    }

    public String getAuthorName() {
      return authorName;
    }

    public String getBookIsbn() {
      return bookIsbn;
    }

    public void readFields(DataInput in) throws IOException {
      this.bookId = in.readLong();
      this.bookName = Text.readString(in);
      this.authorName = Text.readString(in);
      this.bookIsbn = Text.readString(in);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
      LOG.debug("READ FIELDS:{}", resultSet.getMetaData());
      this.bookId = resultSet.getLong(1);
      this.bookName = resultSet.getString(2);
      this.authorName = resultSet.getString(3);
      this.bookIsbn = resultSet.getString(4);
    }

    public void write(DataOutput out) throws IOException {
      out.writeLong(this.bookId);
      Text.writeString(out, bookName);
      Text.writeString(out, authorName);
      Text.writeString(out, bookIsbn);
    }

    public void write(PreparedStatement stmt) throws SQLException {
      stmt.setLong(1, bookId);
      stmt.setString(2, bookName);
      stmt.setString(3, authorName);
      stmt.setString(4, bookIsbn);
    }
  }
}
