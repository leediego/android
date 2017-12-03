package com.hfad.bitsandpizzas;

/**
 * Created by diego on 23/11/17.
 */

public class Store {
    private String name;
    private int imageResourceId;

    public static final Store[] stores = {
            new Store("Cambridge", R.drawable.cambridge),
            new Store("Sebastopol", R.drawable.sebastopol)
    };

    private Store(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
