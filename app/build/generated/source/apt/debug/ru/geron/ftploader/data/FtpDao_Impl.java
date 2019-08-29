package ru.geron.ftploader.data;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FtpDao_Impl implements FtpDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FtpEntity> __insertionAdapterOfFtpEntity;

  private final EntityDeletionOrUpdateAdapter<FtpEntity> __deletionAdapterOfFtpEntity;

  private final EntityDeletionOrUpdateAdapter<FtpEntity> __updateAdapterOfFtpEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfSetActive;

  public FtpDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFtpEntity = new EntityInsertionAdapter<FtpEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ftp_table` (`id`,`result`,`ip`,`port`,`connection_type`,`directory`,`login`,`password`,`active`,`name`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FtpEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getResult() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getResult());
        }
        if (value.getIp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIp());
        }
        if (value.getPort() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPort());
        }
        if (value.getConnection_type() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getConnection_type());
        }
        if (value.getDirectory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDirectory());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLogin());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPassword());
        }
        stmt.bindLong(9, value.getActive());
        if (value.getName() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getName());
        }
      }
    };
    this.__deletionAdapterOfFtpEntity = new EntityDeletionOrUpdateAdapter<FtpEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ftp_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FtpEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfFtpEntity = new EntityDeletionOrUpdateAdapter<FtpEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ftp_table` SET `id` = ?,`result` = ?,`ip` = ?,`port` = ?,`connection_type` = ?,`directory` = ?,`login` = ?,`password` = ?,`active` = ?,`name` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FtpEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getResult() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getResult());
        }
        if (value.getIp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIp());
        }
        if (value.getPort() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPort());
        }
        if (value.getConnection_type() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getConnection_type());
        }
        if (value.getDirectory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDirectory());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLogin());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPassword());
        }
        stmt.bindLong(9, value.getActive());
        if (value.getName() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getName());
        }
        stmt.bindLong(11, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ftp_table";
        return _query;
      }
    };
    this.__preparedStmtOfSetActive = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE ftp_table SET active = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final FtpEntity ftp) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFtpEntity.insert(ftp);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final FtpEntity ftp) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFtpEntity.handle(ftp);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final FtpEntity ftp) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFtpEntity.handle(ftp);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void setActive(final int active_id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetActive.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, active_id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetActive.release(_stmt);
    }
  }

  @Override
  public LiveData<List<FtpEntity>> getAllFtp() {
    final String _sql = "SELECT * FROM ftp_table ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"ftp_table"}, false, new Callable<List<FtpEntity>>() {
      @Override
      public List<FtpEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfIp = CursorUtil.getColumnIndexOrThrow(_cursor, "ip");
          final int _cursorIndexOfPort = CursorUtil.getColumnIndexOrThrow(_cursor, "port");
          final int _cursorIndexOfConnectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "connection_type");
          final int _cursorIndexOfDirectory = CursorUtil.getColumnIndexOrThrow(_cursor, "directory");
          final int _cursorIndexOfLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "login");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final List<FtpEntity> _result = new ArrayList<FtpEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FtpEntity _item;
            final String _tmpResult;
            _tmpResult = _cursor.getString(_cursorIndexOfResult);
            final String _tmpIp;
            _tmpIp = _cursor.getString(_cursorIndexOfIp);
            final String _tmpPort;
            _tmpPort = _cursor.getString(_cursorIndexOfPort);
            final String _tmpConnection_type;
            _tmpConnection_type = _cursor.getString(_cursorIndexOfConnectionType);
            final String _tmpDirectory;
            _tmpDirectory = _cursor.getString(_cursorIndexOfDirectory);
            final String _tmpLogin;
            _tmpLogin = _cursor.getString(_cursorIndexOfLogin);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final int _tmpActive;
            _tmpActive = _cursor.getInt(_cursorIndexOfActive);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item = new FtpEntity(_tmpName,_tmpResult,_tmpIp,_tmpPort,_tmpConnection_type,_tmpDirectory,_tmpLogin,_tmpPassword,_tmpActive);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public FtpEntity getActive() {
    final String _sql = "SELECT * FROM ftp_table WHERE id = (SELECT active FROM ftp_table limit 1)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
      final int _cursorIndexOfIp = CursorUtil.getColumnIndexOrThrow(_cursor, "ip");
      final int _cursorIndexOfPort = CursorUtil.getColumnIndexOrThrow(_cursor, "port");
      final int _cursorIndexOfConnectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "connection_type");
      final int _cursorIndexOfDirectory = CursorUtil.getColumnIndexOrThrow(_cursor, "directory");
      final int _cursorIndexOfLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "login");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final FtpEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpResult;
        _tmpResult = _cursor.getString(_cursorIndexOfResult);
        final String _tmpIp;
        _tmpIp = _cursor.getString(_cursorIndexOfIp);
        final String _tmpPort;
        _tmpPort = _cursor.getString(_cursorIndexOfPort);
        final String _tmpConnection_type;
        _tmpConnection_type = _cursor.getString(_cursorIndexOfConnectionType);
        final String _tmpDirectory;
        _tmpDirectory = _cursor.getString(_cursorIndexOfDirectory);
        final String _tmpLogin;
        _tmpLogin = _cursor.getString(_cursorIndexOfLogin);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        final int _tmpActive;
        _tmpActive = _cursor.getInt(_cursorIndexOfActive);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result = new FtpEntity(_tmpName,_tmpResult,_tmpIp,_tmpPort,_tmpConnection_type,_tmpDirectory,_tmpLogin,_tmpPassword,_tmpActive);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FtpEntity getFtp(final int id) {
    final String _sql = "SELECT * FROM ftp_table WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
      final int _cursorIndexOfIp = CursorUtil.getColumnIndexOrThrow(_cursor, "ip");
      final int _cursorIndexOfPort = CursorUtil.getColumnIndexOrThrow(_cursor, "port");
      final int _cursorIndexOfConnectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "connection_type");
      final int _cursorIndexOfDirectory = CursorUtil.getColumnIndexOrThrow(_cursor, "directory");
      final int _cursorIndexOfLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "login");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final FtpEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpResult;
        _tmpResult = _cursor.getString(_cursorIndexOfResult);
        final String _tmpIp;
        _tmpIp = _cursor.getString(_cursorIndexOfIp);
        final String _tmpPort;
        _tmpPort = _cursor.getString(_cursorIndexOfPort);
        final String _tmpConnection_type;
        _tmpConnection_type = _cursor.getString(_cursorIndexOfConnectionType);
        final String _tmpDirectory;
        _tmpDirectory = _cursor.getString(_cursorIndexOfDirectory);
        final String _tmpLogin;
        _tmpLogin = _cursor.getString(_cursorIndexOfLogin);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        final int _tmpActive;
        _tmpActive = _cursor.getInt(_cursorIndexOfActive);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result = new FtpEntity(_tmpName,_tmpResult,_tmpIp,_tmpPort,_tmpConnection_type,_tmpDirectory,_tmpLogin,_tmpPassword,_tmpActive);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
