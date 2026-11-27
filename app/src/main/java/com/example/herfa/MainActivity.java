package com.example.herfa;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/** The catalog screen (U1 and A2): shows every craft and opens its details. */
public class MainActivity extends AppCompatActivity {

    private CraftListAdapter mAdapter;
    private TextView mCraftCount;
    private List<Craft> mAllCrafts = new ArrayList<>(); // Full list, kept for search and filter.

    /** Sets up the toolbar, the list, and the link to the database. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // The toolbar becomes the app bar, so the lock menu shows in it (Lab 6).
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView listTitle = findViewById(R.id.list_title);
        listTitle.setText(R.string.all_crafts);
        mCraftCount = findViewById(R.id.craft_count);

        // The list: one card per craft (Lab 9).
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new CraftListAdapter(this, this::openCraftDetail);
        recyclerView.setAdapter(mAdapter);

        // Watch the crafts; the list updates by itself after add, edit, or delete (FR1).
        CraftViewModel viewModel = new ViewModelProvider(this).get(CraftViewModel.class);
        viewModel.getAllCrafts().observe(this, crafts -> {
            mAllCrafts = crafts;
            showCrafts(crafts);
        });
    }

    /** Shows a list of crafts and updates the count. Search and filter call this too. */
    void showCrafts(List<Craft> crafts) {
        mAdapter.setCrafts(crafts);
        mCraftCount.setText(getString(R.string.craft_count, crafts.size()));
    }

    /** Opens the detail screen for the tapped craft (Lab 3). */
    private void openCraftDetail(Craft craft) {
        // Turn on after Member 3's CraftDetailActivity is merged into main:
        // Intent intent = new Intent(this, CraftDetailActivity.class);
        // intent.putExtra("craft_id", craft.getId());
        // startActivity(intent);
    }

    /** Adds the lock icon to the toolbar (Lab 6). */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Handles a tap on the lock icon. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_lock) {
            // Member 5's PIN dialog opens here (week 4).
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}