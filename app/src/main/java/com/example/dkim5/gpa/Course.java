package com.example.dkim5.gpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

;


public class Course implements Serializable
{

    private ArrayList<Item> items;

    public Course()
    {

        items = new ArrayList<>();
        items.add(new Item());

    }

    public void additem(Item item)
    {
        items.add(item);
    }
    public void deleteAllitem(Collection<Item> remove_item)
    {
        items.removeAll(remove_item);
    }

    public Item getItem(int i)
    {
        return items.get(i);
    }

    public int getNumOfItem() {
        return items.size();
    }




}
