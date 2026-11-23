package com.example.herfa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * One traditional craft. Room turns this class into the table "craft_table".
 * The columns match Table 2 in our proposal. (Lab 9: like the Word class.)
 */
@Entity(tableName = "craft_table")
public class Craft {

    // Room gives every new craft the next number by itself.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "craft_name")
    private String mCraftName;

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription;

    // Optional: the admin may leave the tools empty.
    @Nullable
    @ColumnInfo(name = "tools")
    private String mTools;

    // Must be one of: الشرقية، الوسطى، الغربية، الجنوبية، الشمالية
    @NonNull
    @ColumnInfo(name = "region")
    private String mRegion;

    // Name of the picture in res/drawable, for example "sadu".
    // Null means the screen shows the default picture.
    @Nullable
    @ColumnInfo(name = "image_path")
    private String mImagePath;

    /** Makes a new craft. The id is set by Room when the craft is saved. */
    public Craft(@NonNull String craftName, @NonNull String description,
                 @Nullable String tools, @NonNull String region,
                 @Nullable String imagePath) {
        this.mCraftName = craftName;
        this.mDescription = description;
        this.mTools = tools;
        this.mRegion = region;
        this.mImagePath = imagePath;
    }

    /** Returns the craft's number in the database. */
    public int getId() {
        return mId;
    }

    /** Room uses this to set the id. The edit screen uses it to update an existing craft. */
    public void setId(int id) {
        this.mId = id;
    }

    /** Returns the craft's name. */
    @NonNull
    public String getCraftName() {
        return mCraftName;
    }

    /** Returns the craft's description. */
    @NonNull
    public String getDescription() {
        return mDescription;
    }

    /** Returns the tools, or null if there are none. */
    @Nullable
    public String getTools() {
        return mTools;
    }

    /** Returns the craft's region. */
    @NonNull
    public String getRegion() {
        return mRegion;
    }

    /** Returns the picture name, or null for the default picture. */
    @Nullable
    public String getImagePath() {
        return mImagePath;
    }
}
