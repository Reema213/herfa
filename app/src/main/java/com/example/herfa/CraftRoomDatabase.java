package com.example.herfa;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * The Herfa database, stored on the phone so the app works offline.
 * (Lab 9: like WordRoomDatabase.)
 */
@Database(entities = {Craft.class}, version = 1, exportSchema = false)
public abstract class CraftRoomDatabase extends RoomDatabase {

    /** Room gives us the DAO through this method. */
    public abstract CraftDao craftDao();

    // Only one copy of the database is ever opened.
    private static CraftRoomDatabase INSTANCE;

    /** Returns the database, creating it the first time. */
    static CraftRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CraftRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CraftRoomDatabase.class, "craft_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Fills the database with the starter crafts.
     * We use onCreate, not onOpen as in the lab: onCreate runs only the first time
     * the database is made, so crafts the admin adds later are never wiped.
     */
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /** Inserts the starter crafts in the background (Lab 9 and Lab 11: AsyncTask). */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CraftDao mDao;

        PopulateDbAsync(CraftRoomDatabase db) {
            mDao = db.craftDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            for (Craft craft : CraftSeedData.getStarterCrafts()) {
                mDao.insert(craft);
            }
            return null;
        }
    }
}
