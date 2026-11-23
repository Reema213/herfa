package com.example.herfa;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The one place the screens get crafts from. It runs every insert, update and
 * delete in the background, because Room does not allow them on the main thread.
 * (Lab 9: like WordRepository. Lab 11: AsyncTask.)
 */
public class CraftRepository {

    private final CraftDao mCraftDao;
    private final LiveData<List<Craft>> mAllCrafts;

    /** Opens the database and loads the list of crafts. */
    CraftRepository(Application application) {
        CraftRoomDatabase db = CraftRoomDatabase.getDatabase(application);
        mCraftDao = db.craftDao();
        mAllCrafts = mCraftDao.getAllCrafts();
    }

    /** All crafts. LiveData runs the query in the background by itself. */
    LiveData<List<Craft>> getAllCrafts() {
        return mAllCrafts;
    }

    /** One craft by its id. */
    LiveData<Craft> getCraftById(int id) {
        return mCraftDao.getCraftById(id);
    }

    /** Adds a craft in the background. */
    public void insert(Craft craft) {
        new InsertAsyncTask(mCraftDao).execute(craft);
    }

    /** Saves changes to a craft in the background. */
    public void update(Craft craft) {
        new UpdateAsyncTask(mCraftDao).execute(craft);
    }

    /** Deletes a craft in the background. */
    public void delete(Craft craft) {
        new DeleteAsyncTask(mCraftDao).execute(craft);
    }

    private static class InsertAsyncTask extends AsyncTask<Craft, Void, Void> {
        private final CraftDao mAsyncTaskDao;

        InsertAsyncTask(CraftDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Craft... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Craft, Void, Void> {
        private final CraftDao mAsyncTaskDao;

        UpdateAsyncTask(CraftDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Craft... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Craft, Void, Void> {
        private final CraftDao mAsyncTaskDao;

        DeleteAsyncTask(CraftDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Craft... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
