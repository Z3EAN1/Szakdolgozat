package com.example.szakdolgozat;

import java.io.Serializable;

public class Product implements Serializable {


        private String name;
        private String location;
        private String mImageUrl ;
        private String price ;
        private String shortdescript ;
        private String longdescript;
        private String store;
        private String contact;

    public Product(String name, String location, String mImageUrl, String price, String shortdescript, String longdescript, String store, String contact) {
        this.name = name;
        this.location = location;
        this.mImageUrl = mImageUrl;
        this.price = price;
        this.shortdescript = shortdescript;
        this.longdescript = longdescript;
        this.store = store;
        this.contact = contact;
    }

    public Product(String name, String mImageUrl, String price, String shortdescript, String longdescript, String contact) {
        this.name = name;
        this.mImageUrl = mImageUrl;
        this.price = price;
        this.shortdescript = shortdescript;
        this.longdescript = longdescript;
        this.contact = contact;
    }

    public Product() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortdescript() {
        return shortdescript;
    }

    public void setShortdescript(String shortdescript) {
        this.shortdescript = shortdescript;
    }

    public String getLongdescript() {
        return longdescript;
    }

    public void setLongdescript(String longdescript) {
        this.longdescript = longdescript;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
