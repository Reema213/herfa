package com.example.herfa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Shows the crafts in the catalog, one item_craft card per craft.
 * (Lab 9: like WordListAdapter.)
 */
public class CraftListAdapter extends RecyclerView.Adapter<CraftListAdapter.CraftViewHolder> {

    /** What happens when the user taps a craft. MainActivity decides. */
    public interface OnCraftClickListener {
        void onCraftClick(Craft craft);
    }

    private final LayoutInflater mInflater;
    private final OnCraftClickListener mListener;
    private List<Craft> mCrafts; // Empty until the database sends the list.

    /** Makes the adapter and remembers what to do on a tap. */
    public CraftListAdapter(Context context, OnCraftClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    /** Makes one empty card from item_craft.xml. */
    @NonNull
    @Override
    public CraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_craft, parent, false);
        return new CraftViewHolder(itemView);
    }

    /** Fills a card with the craft at this position. */
    @Override
    public void onBindViewHolder(@NonNull CraftViewHolder holder, int position) {
        Craft current = mCrafts.get(position);
        holder.nameView.setText(current.getCraftName());
        holder.regionView.setText(current.getRegion());
        holder.imageView.setImageResource(
                getImageId(holder.itemView.getContext(), current.getImagePath()));
        holder.itemView.setOnClickListener(v -> mListener.onCraftClick(current));
    }

    /** Gives the adapter a new list and redraws the cards. */
    public void setCrafts(List<Craft> crafts) {
        mCrafts = crafts;
        notifyDataSetChanged();
    }

    /** Returns how many cards to show. */
    @Override
    public int getItemCount() {
        return mCrafts == null ? 0 : mCrafts.size();
    }

    /** Turns a picture name like "sadu" into its drawable, or the default picture. */
    static int getImageId(Context context, String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            int id = context.getResources().getIdentifier(
                    imagePath, "drawable", context.getPackageName());
            if (id != 0) {
                return id;
            }
        }
        return android.R.drawable.ic_menu_gallery;
    }

    /** Holds the views of one card so they are found only once. */
    public static class CraftViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;
        final TextView regionView;

        CraftViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.craft_image);
            nameView = itemView.findViewById(R.id.craft_name);
            regionView = itemView.findViewById(R.id.craft_region);
        }
    }
}