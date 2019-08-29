package ru.geron.ftploader.data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FtpLoaderDb_Impl extends FtpLoaderDb {
  private volatile FtpDao _ftpDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ftp_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `result` TEXT, `ip` TEXT, `port` TEXT, `connection_type` TEXT, `directory` TEXT, `login` TEXT, `password` TEXT, `active` INTEGER NOT NULL, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4b85cc9682c2d0405ce0fa8ee9b36bcf')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ftp_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFtpTable = new HashMap<String, TableInfo.Column>(10);
        _columnsFtpTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("result", new TableInfo.Column("result", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("ip", new TableInfo.Column("ip", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("port", new TableInfo.Column("port", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("connection_type", new TableInfo.Column("connection_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("directory", new TableInfo.Column("directory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("login", new TableInfo.Column("login", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("active", new TableInfo.Column("active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFtpTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFtpTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFtpTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFtpTable = new TableInfo("ftp_table", _columnsFtpTable, _foreignKeysFtpTable, _indicesFtpTable);
        final TableInfo _existingFtpTable = TableInfo.read(_db, "ftp_table");
        if (! _infoFtpTable.equals(_existingFtpTable)) {
          return new RoomOpenHelper.ValidationResult(false, "ftp_table(ru.geron.ftploader.data.FtpEntity).\n"
                  + " Expected:\n" + _infoFtpTable + "\n"
                  + " Found:\n" + _existingFtpTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4b85cc9682c2d0405ce0fa8ee9b36bcf", "96e905cb3cf38f07b812cbd840357691");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "ftp_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ftp_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FtpDao ftpDao() {
    if (_ftpDao != null) {
      return _ftpDao;
    } else {
      synchronized(this) {
        if(_ftpDao == null) {
          _ftpDao = new FtpDao_Impl(this);
        }
        return _ftpDao;
      }
    }
  }
}
