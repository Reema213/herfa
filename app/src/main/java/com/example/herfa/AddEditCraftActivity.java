package com.example.herfa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

// Add and edit form for a craft (screens A4 and A5): without a craft id it adds, with one it edits.
public class AddEditCraftActivity extends AppCompatActivity {

    public static final String EXTRA_CRAFT_ID = "craft_id";

    private static final String STATE_NAME = "state_name";
    private static final String STATE_DESCRIPTION = "state_description";
    private static final String STATE_TOOLS = "state_tools";
    private static final String STATE_REGION = "state_region";
    private static final String STATE_IMAGE_PATH = "state_image_path";

    private EditText mEditName;
    private EditText mEditDescription;
    private EditText mEditTools;
    private RadioGroup mRadioRegion;
    private CraftViewModel mViewModel;
    private int mCraftId = -1;
    private String mImagePath = null;

    // Sets up the form, and fills it when editing or after the phone rotates.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_craft);

        View root = findViewById(R.id.main);
        final int pad = root.getPaddingLeft();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + pad, bars.top + pad, bars.right + pad, bars.bottom + pad);
            return insets;
        });

        mEditName = findViewById(R.id.edit_name);
        mEditDescription = findViewById(R.id.edit_description);
        mEditTools = findViewById(R.id.edit_tools);
        mRadioRegion = findViewById(R.id.radio_group_region);
        Button buttonSave = findViewById(R.id.button_save);

        mViewModel = new ViewModelProvider(this).get(CraftViewModel.class);
        mCraftId = getIntent().getIntExtra(EXTRA_CRAFT_ID, -1);

        if (mCraftId == -1) {
            setTitle(R.string.add_craft);
        } else {
            setTitle(R.string.edit_craft);
        }

        if (savedInstanceState != null) {
            mEditName.setText(savedInstanceState.getString(STATE_NAME));
            mEditDescription.setText(savedInstanceState.getString(STATE_DESCRIPTION));
            mEditTools.setText(savedInstanceState.getString(STATE_TOOLS));
            mRadioRegion.check(savedInstanceState.getInt(STATE_REGION, -1));
            mImagePath = savedInstanceState.getString(STATE_IMAGE_PATH);
        } else if (mCraftId != -1) {
            loadCraft();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCraft();
            }
        });
    }

    // Keeps the typed text and chosen region when the phone rotates (Lab 5).
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_NAME, mEditName.getText().toString());
        outState.putString(STATE_DESCRIPTION, mEditDescription.getText().toString());
        outState.putString(STATE_TOOLS, mEditTools.getText().toString());
        outState.putInt(STATE_REGION, mRadioRegion.getCheckedRadioButtonId());
        outState.putString(STATE_IMAGE_PATH, mImagePath);
    }

    // Loads the craft being edited from the database once and fills the form (Lab 9).
    private void loadCraft() {
        final LiveData<Craft> craftData = mViewModel.getCraftById(mCraftId);
        craftData.observe(this, new Observer<Craft>() {
            @Override
            public void onChanged(Craft craft) {
                craftData.removeObserver(this);
                if (craft == null) {
                    return;
                }
                mEditName.setText(craft.getCraftName());
                mEditDescription.setText(craft.getDescription());
                mEditTools.setText(craft.getTools());
                checkRegion(craft.getRegion());
                mImagePath = craft.getImagePath();
            }
        });
    }

    // Checks that the required fields are filled, then adds or updates the craft and closes the form.
    private void saveCraft() {
        String name = mEditName.getText().toString().trim();
        String description = mEditDescription.getText().toString().trim();
        String tools = mEditTools.getText().toString().trim();
        String region = getSelectedRegion();

        if (name.isEmpty() || description.isEmpty() || region == null) {
            Toast.makeText(this, R.string.fill_required, Toast.LENGTH_SHORT).show();
            return;
        }
        if (tools.isEmpty()) {
            tools = null;
        }

        Craft craft = new Craft(name, description, tools, region, mImagePath);
        if (mCraftId == -1) {
            mViewModel.insert(craft);
        } else {
            craft.setId(mCraftId);
            mViewModel.update(craft);
        }

        Toast.makeText(this, R.string.craft_saved, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    // Returns the chosen region exactly as spelled in strings.xml, or null if none is chosen.
    private String getSelectedRegion() {
        int checkedId = mRadioRegion.getCheckedRadioButtonId();
        if (checkedId == R.id.radio_east) {
            return getString(R.string.region_east);
        } else if (checkedId == R.id.radio_central) {
            return getString(R.string.region_central);
        } else if (checkedId == R.id.radio_west) {
            return getString(R.string.region_west);
        } else if (checkedId == R.id.radio_south) {
            return getString(R.string.region_south);
        } else if (checkedId == R.id.radio_north) {
            return getString(R.string.region_north);
        }
        return null;
    }

    // Ticks the radio button that matches a saved region.
    private void checkRegion(String region) {
        if (region.equals(getString(R.string.region_east))) {
            mRadioRegion.check(R.id.radio_east);
        } else if (region.equals(getString(R.string.region_central))) {
            mRadioRegion.check(R.id.radio_central);
        } else if (region.equals(getString(R.string.region_west))) {
            mRadioRegion.check(R.id.radio_west);
        } else if (region.equals(getString(R.string.region_south))) {
            mRadioRegion.check(R.id.radio_south);
        } else if (region.equals(getString(R.string.region_north))) {
            mRadioRegion.check(R.id.radio_north);
        }
    }
}