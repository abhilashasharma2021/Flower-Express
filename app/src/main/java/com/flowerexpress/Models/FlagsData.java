package com.flowerexpress.Models;

public class FlagsData {

    private int image;
    private String countryName;

    public FlagsData(String countryName) {
        this.countryName = countryName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
