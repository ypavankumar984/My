package com.example.my;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ItemAdapter itemAdapter;
    private List<Item> itemList; // Replace with actual item model
    private List<Item> filteredList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize item list (replace with actual data source)
        itemList = getDiscountedItems(); // Fetch items with discounts from database or backend
        filteredList = new ArrayList<>(itemList);

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(filteredList); // Custom adapter for RecyclerView
        recyclerView.setAdapter(itemAdapter);

        // Set up search functionality
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint(getString(R.string.search_hint)); // Set a hint text for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    // Method to filter items based on search query
    @SuppressLint("NotifyDataSetChanged")
    private void filter(String text) {
        if (text == null || text.isEmpty()) {
            filteredList.clear();
            filteredList.addAll(itemList); // Show all items if query is empty
        } else {
            filteredList.clear();
            for (Item item : itemList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        itemAdapter.notifyDataSetChanged(); // Notify adapter of data changes
    }

    // Replace this method with actual data retrieval logic
    private List<Item> getDiscountedItems() {
        List<Item> items = new ArrayList<>();
        // Add items with discounts to the list (dummy data for illustration)
        items.add(new Item("Apple airpods", "Apple AirPods (3rd generation) Bluetooth  (White, True Wireless)" +
                "50% off", 10000));
        items.add(new Item("Discounted Milk", "Organic milk with 15% off", 30));
        // Add more items as needed
        return items;
    }
}