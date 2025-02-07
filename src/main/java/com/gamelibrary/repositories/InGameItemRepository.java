package com.gamelibrary.repositories;

import com.gamelibrary.models.InGameItem;

import java.util.ArrayList;
import java.util.List;

public class InGameItemRepository {
    private List<InGameItem> items = new ArrayList<>();

    public void addItem(InGameItem item) {
        items.add(item);
    }

    public InGameItem getItemById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public List<InGameItem> getAllItems() {
        return items;
    }
}