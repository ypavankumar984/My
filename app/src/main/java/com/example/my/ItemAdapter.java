package com.example.my;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final List<Item> itemList;

    // Constructor to initialize itemList
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Get the item at the current position
        Item item = itemList.get(position);

        // Bind the item data to the view
        holder.itemName.setText(item.getName());
        holder.itemDescription.setText(item.getDescription());

        // Use a string resource for the price label to avoid hardcoded text
        @SuppressLint("StringFormatMatches") String priceText = holder.itemView.getContext().getString(R.string.price_label, item.getPrice());
        holder.itemPrice.setText(priceText);
    }

    @Override
    public int getItemCount() {
        // Return the size of the itemList, or 0 if itemList is null
        return itemList != null ? itemList.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDescription, itemPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews by finding them by their ID
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
