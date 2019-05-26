package com.example.mdi;

import android.graphics.Bitmap;

public class Drug {
    private String drug_name;
    private String drug_image;
    private Bitmap drug_image_bitmap;
    private String drug_type;
    private String drug_shape;
    private String drug_temper;
    private String drug_frontColor;
    private String drug_backColor;
    private String drug_frontText;
    private String drug_backText;
    private String drug_longSize;
    private String drug_shortSize;
    private String drug_ratio;

    public Drug(Drug drug) {
        this.drug_name = drug.getDrug_name();
        this.drug_image = drug.getDrug_image();
        this.drug_image_bitmap = drug.getDrug_image_bitmap();
        this.drug_type = drug.getDrug_type();
        this.drug_shape = drug.getDrug_shape();
        this.drug_temper = drug.getDrug_temper();
        this.drug_frontColor = drug.getDrug_frontColor();
        this.drug_backColor = drug.getDrug_backColor();
        this.drug_frontText = drug.getDrug_frontText();
        this.drug_backText = drug.getDrug_backText();
        this.drug_longSize = drug.getDrug_longSize();
        this.drug_shortSize = drug.getDrug_shortSize();
        this.drug_ratio = drug.getDrug_ratio();
    }

    public Drug(String drug_name, String drug_image, Bitmap drug_image_bitmap, String drug_type, String drug_shape, String drug_temper,
                String drug_frontColor, String drug_backColor, String drug_frontText, String drug_backText, String drug_longSize, String drug_shortSize) {
        this.drug_name = drug_name;
        this.drug_image = drug_image;
        this.drug_image_bitmap = drug_image_bitmap;
        this.drug_type = drug_type;
        this.drug_shape = drug_shape;
        this.drug_temper = drug_temper;
        this.drug_frontColor = drug_frontColor;
        this.drug_backColor = drug_backColor;
        this.drug_frontText = drug_frontText;
        this.drug_backText = drug_backText;
        this.drug_longSize = drug_longSize;
        this.drug_shortSize = drug_shortSize;
        this.drug_ratio = null;
    }
    public Drug(String drug_name, String drug_image, String drug_type, String drug_shape, String drug_temper,
                String drug_frontColor, String drug_backColor, String drug_frontText, String drug_backText, String drug_longSize, String drug_shortSize) {
        this.drug_name = drug_name;
        this.drug_image = drug_image;
        this.drug_image_bitmap = null;
        this.drug_type = drug_type;
        this.drug_shape = drug_shape;
        this.drug_temper = drug_temper;
        this.drug_frontColor = drug_frontColor;
        this.drug_backColor = drug_backColor;
        this.drug_frontText = drug_frontText;
        this.drug_backText = drug_backText;
        this.drug_longSize = drug_longSize;
        this.drug_shortSize = drug_shortSize;
        this.drug_ratio = null;
    }
    public Drug(String drug_shape, String drug_frontColor, String drug_backColor, String drug_frontText, String drug_ratio) {
        this.drug_name = null;
        this.drug_image = null;
        this.drug_image_bitmap = null;
        this.drug_type = null;
        this.drug_shape = drug_shape;
        this.drug_temper = null;
        this.drug_frontColor = drug_frontColor;
        this.drug_backColor = drug_backColor;
        this.drug_frontText = drug_frontText;
        this.drug_backText = null;
        this.drug_longSize = null;
        this.drug_shortSize = null;
        this.drug_ratio = drug_ratio;
    }

    public String getDrug_name() { return drug_name; }
    public String getDrug_image() { return drug_image; }
    public Bitmap getDrug_image_bitmap() { return drug_image_bitmap; }
    public String getDrug_type() { return drug_type; }
    public String getDrug_shape() { return drug_shape; }
    public String getDrug_temper() { return drug_temper; }
    public String getDrug_frontColor() { return drug_frontColor; }
    public String getDrug_backColor() { return drug_backColor; }
    public String getDrug_frontText() { return drug_frontText; }
    public String getDrug_backText() { return drug_backText; }
    public String getDrug_longSize() { return drug_longSize; }
    public String getDrug_shortSize() { return drug_shortSize; }
    public String getDrug_ratio() { return drug_ratio; }

    public void setDrug_name(String drug_name) { this.drug_name = drug_name; }
    public void setDrug_image(String drug_image) { this.drug_image = drug_image; }
    public void setDrug_image_bitmap(Bitmap drug_image_bitmap) { this.drug_image_bitmap = drug_image_bitmap; }
    public void setDrug_type(String drug_type) { this.drug_type = drug_type; }
    public void setDrug_shape(String drug_shape) { this.drug_shape = drug_shape; }
    public void setDrug_temper(String drug_temper) { this.drug_temper = drug_temper; }
    public void setDrug_frontColor(String drug_frontColor) { this.drug_frontColor = drug_frontColor; }
    public void setDrug_backColor(String drug_backColor) { this.drug_backColor = drug_backColor; }
    public void setDrug_frontText(String drug_frontText) { this.drug_frontText = drug_frontText; }
    public void setDrug_backText(String drug_backText) { this.drug_backText = drug_backText; }
    public void setDrug_longSize(String drug_largeSize) { this.drug_longSize = drug_longSize; }
    public void setDrug_shortSize(String drug_shortSize) { this.drug_shortSize = drug_shortSize; }
    public void setDrug_ratio(String drug_ratio) { this.drug_ratio = drug_ratio; }
}
