package com.example.herfa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Every way the app reads or changes crafts. (Lab 9: like WordDao.)
 * Room writes the code for these methods by itself.
 */
@Dao
public interface CraftDao {

    /** Adds a new craft (FR4). */
    @Insert
    void insert(Craft craft);

    /** Saves changes to an existing craft, found by its id (FR5). */
    @Update
    void update(Craft craft);

    /** Removes a craft, found by its id (FR6). */
    @Delete
    void delete(Craft craft);

    /** All crafts, sorted by name (FR1). LiveData updates the screen by itself. */
    @Query("SELECT * FROM craft_table ORDER BY craft_name ASC")
    LiveData<List<Craft>> getAllCrafts();

    /** One craft, for the detail and edit screens (FR2, FR5). */
    @Query("SELECT * FROM craft_table WHERE id = :id")
    LiveData<Craft> getCraftById(int id);
}
