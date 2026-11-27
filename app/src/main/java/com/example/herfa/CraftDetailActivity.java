
package com.example.herfa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

public class CraftDetailActivity extends AppCompatActivity {

    private ImageView imgCraft;
    private TextView txtCraftName, txtRegion;
    private TextView txtDescription, txtTools;
    private LinearLayout toolsSection;

    private CraftViewModel viewModel;
    private Craft currentCraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_craft_detail);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });

        imgCraft = findViewById(R.id.imgCraft);
        txtCraftName = findViewById(R.id.txtCraftName);
        txtRegion = findViewById(R.id.txtRegion);
        txtDescription = findViewById(R.id.txtDescription);
        txtTools = findViewById(R.id.txtTools);
        toolsSection = findViewById(R.id.toolsSection);

        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());
        ImageButton btnShare = findViewById(R.id.btnShare);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);

        LinearLayout adminButtons = findViewById(R.id.adminButtons);
        TextView adminBanner = findViewById(R.id.adminBanner);

// Check admin mode
        boolean isAdmin = getIntent().getBooleanExtra("is_admin", false);

        if (isAdmin) {
            adminButtons.setVisibility(View.VISIBLE);
            adminBanner.setVisibility(View.VISIBLE);
        } else {
            adminButtons.setVisibility(View.GONE);
            adminBanner.setVisibility(View.GONE);
        }

// Delete button
        btnDelete.setOnClickListener(v -> {
            if (isAdmin && currentCraft != null) {
                showDeleteDialog();
            }
        });

// Share button
        btnShare.setOnClickListener(v -> {
            if (currentCraft != null) {
                shareCraft();
            }
        });

        // Receive the selected craft ID
        int craftId = getIntent().getIntExtra("craft_id", -1);

        if (craftId == -1) {
            finish();
            return;
        }

        // Connect to the ViewModel
        viewModel = new ViewModelProvider(this)
                .get(CraftViewModel.class);

        // Get craft details from the database
        viewModel.getCraftById(craftId).observe(this, craft -> {

            if (craft != null) {
                currentCraft = craft;
                displayCraft(craft);
            } else {
                finish();
            }

        });
    }

    private void displayCraft(Craft craft) {

        txtCraftName.setText(craft.getCraftName());

        txtRegion.setText(getString(
                R.string.detail_region, craft.getRegion()));

        txtDescription.setText(craft.getDescription());

        // Hide tools section if empty
        if (craft.getTools() == null ||
                craft.getTools().trim().isEmpty()) {

            toolsSection.setVisibility(View.GONE);

        } else {

            toolsSection.setVisibility(View.VISIBLE);
            txtTools.setText(craft.getTools());
        }

        // Display craft image
        int imageId = 0;

        if (craft.getImagePath() != null) {

            imageId = getResources().getIdentifier(
                    craft.getImagePath(),
                    "drawable",
                    getPackageName());
        }

        if (imageId != 0) {
            imgCraft.setImageResource(imageId);
        } else {
            imgCraft.setImageResource(
                    android.R.drawable.ic_menu_gallery);
        }
    }
    private void showDeleteDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.detail_delete_title)
                .setMessage(R.string.detail_delete_message)
                .setPositiveButton(R.string.detail_delete,
                        (d, which) -> {
                            viewModel.delete(currentCraft);
                            finish();
                        })
                .setNegativeButton(R.string.detail_cancel,
                        (d, which) -> d.dismiss())
                .create();

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().getDecorView()
                    .setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(0xFF991B1F);

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(0xFF991B1F);
    }
    private void shareCraft() {

        String shareText = currentCraft.getCraftName()
                + "\n"
                + currentCraft.getRegion()
                + "\n\n"
                + currentCraft.getDescription();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(
                shareIntent, getString(R.string.detail_share)));
    }
}
