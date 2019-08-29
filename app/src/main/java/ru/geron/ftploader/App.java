package ru.geron.ftploader;


import android.app.Application;
import android.content.Context;

public class App extends Application {

    private String productId = "";

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}