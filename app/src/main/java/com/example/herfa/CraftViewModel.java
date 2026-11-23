package com.example.herfa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * What every screen uses to reach the crafts. It survives screen rotation,
 * so the list is not loaded again. (Lab 9: like WordViewModel.)
 *
 * In an activity:
 *     CraftViewModel viewModel = new ViewModelProvider(this).get(CraftViewModel.class);
 */
public class CraftViewModel extends AndroidViewModel {

    private final CraftRepository mRepository;
    private final LiveData<List<Craft>> mAllCrafts;

    public CraftViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CraftRepository(application);
        mAllCrafts = mRepository.getAllCrafts();
    }

    /** All crafts, for the catalog (Member 2). */
    public LiveData<List<Craft>> getAllCrafts() {
        return mAllCrafts;
    }

    /** One craft, for the detail and edit screens (Members 3 and 5). */
    public LiveData<Craft> getCraftById(int id) {
        return mRepository.getCraftById(id);
    }

    /** Adds a craft (Member 5). */
    public void insert(Craft craft) {
        mRepository.insert(craft);
    }

    /** Saves an edited craft; call setId first (Member 5). */
    public void update(Craft craft) {
        mRepository.update(craft);
    }

    /** Deletes a craft (Member 3). */
    public void delete(Craft craft) {
        mRepository.delete(craft);
    }
}
