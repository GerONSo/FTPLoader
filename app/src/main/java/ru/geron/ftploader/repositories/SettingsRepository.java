package ru.geron.ftploader.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.geron.ftploader.data.FtpDao;
import ru.geron.ftploader.data.FtpEntity;
import ru.geron.ftploader.data.FtpLoaderDb;


public class SettingsRepository {
    private FtpDao ftpDao;
    private LiveData<List<FtpEntity>> allFtp;

    public SettingsRepository(Application application) {
        FtpLoaderDb database = FtpLoaderDb.getInstance(application);
        ftpDao = database.ftpDao();
        allFtp = ftpDao.getAllFtp();
    }

    public void insert(FtpEntity ftp) {
        new InsertFtpAsyncTask(ftpDao).execute(ftp);
    }

    public void delete(FtpEntity ftp) {
        new DeleteFtpAsyncTask(ftpDao).execute(ftp);
    }

    public void update(FtpEntity ftp) {
        new UpdateFtpAsyncTask(ftpDao).execute(ftp);
    }

    public void deleteAll() {
        new DeleteAllFtpAsyncTask(ftpDao).execute();
    }

    public void setActive(FtpEntity ftp) {
        try {
            String res2 = String.valueOf(new SetActiveAsyncTask(ftpDao).execute(ftp).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FtpEntity getFtp(int id) {
        return ftpDao.getFtp(id);
    }

    public LiveData<List<FtpEntity>> getAllFtp() {
        return allFtp;
    }

    public FtpEntity getActive() {
        return ftpDao.getActive();
    }

    private static class InsertFtpAsyncTask extends AsyncTask<FtpEntity, Void, Void> {
        private FtpDao ftpDao;

        public InsertFtpAsyncTask(FtpDao ftpDao) {
            this.ftpDao = ftpDao;
        }

        @Override
        protected Void doInBackground(FtpEntity... ftpServerEntities) {
            ftpDao.insert(ftpServerEntities[0]);
            return null;
        }
    }

    private static class UpdateFtpAsyncTask extends AsyncTask<FtpEntity, Void, Void> {
        private FtpDao ftpDao;

        public UpdateFtpAsyncTask(FtpDao ftpDao) {
            this.ftpDao = ftpDao;
        }

        @Override
        protected Void doInBackground(FtpEntity... ftpServerEntities) {
            ftpDao.update(ftpServerEntities[0]);
            return null;
        }
    }

    private static class DeleteFtpAsyncTask extends AsyncTask<FtpEntity, Void, Void> {
        private FtpDao ftpDao;

        public DeleteFtpAsyncTask(FtpDao ftpDao) {
            this.ftpDao = ftpDao;
        }

        @Override
        protected Void doInBackground(FtpEntity... ftpServerEntities) {
            ftpDao.delete(ftpServerEntities[0]);
            return null;
        }
    }

    private static class DeleteAllFtpAsyncTask extends AsyncTask<Void, Void, Void> {
        private FtpDao ftpDao;

        public DeleteAllFtpAsyncTask(FtpDao ftpDao) {
            this.ftpDao = ftpDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ftpDao.deleteAll();
            return null;
        }
    }

    private static class SetActiveAsyncTask extends AsyncTask<FtpEntity, Void, Void> {
        private FtpDao ftpDao;

        public SetActiveAsyncTask(FtpDao ftpDao) {
            this.ftpDao = ftpDao;
        }

        @Override
        protected Void doInBackground(FtpEntity... FtpEntities) {
            ftpDao.setActive(FtpEntities[0].getId());
            return null;
        }
    }
}
