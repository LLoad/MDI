package com.example.mdi;

import android.graphics.Bitmap;

public class Drug {
    private String drug_id;
    private String drug_name;
    private String drug_image;
    private Bitmap drug_image_bitmap;
    private String drug_type;
    private String drug_shape;
    private String drug_color;
    private String drug_frontText;
    private String drug_backText;

    public Drug(Drug drug) {
        this.drug_id = drug.getDrug_id();
        this.drug_name = drug.getDrug_name();
        this.drug_image = drug.getDrug_image();
        this.drug_image_bitmap = drug.getDrug_image_bitmap();
        this.drug_type = drug.getDrug_type();
        this.drug_shape = drug.getDrug_shape();
        this.drug_color = drug.getDrug_color();
        this.drug_frontText = drug.getDrug_frontText();
        this.drug_backText = drug.getDrug_backText();
    }

    public Drug(String drug_id, String drug_name, String drug_image, Bitmap drug_image_bitmap, String drug_type, String drug_shape, String drug_color, String drug_frontText, String drug_backText) {
        this.drug_id = drug_id;
        this.drug_name = drug_name;
        this.drug_image = drug_image;
        this.drug_image_bitmap = drug_image_bitmap;
        this.drug_type = drug_type;
        this.drug_shape = drug_shape;
        this.drug_color = drug_color;
        this.drug_frontText = drug_frontText;
        this.drug_backText = drug_backText;
    }
    public Drug(String drug_id, String drug_name, String drug_image, String drug_type, String drug_shape, String drug_color, String drug_frontText, String drug_backText) {
        this.drug_id = drug_id;
        this.drug_name = drug_name;
        this.drug_image = drug_image;
        this.drug_image_bitmap = null;
        this.drug_type = drug_type;
        this.drug_shape = drug_shape;
        this.drug_color = drug_color;
        this.drug_frontText = drug_frontText;
        this.drug_backText = drug_backText;
    }


    public String getDrug_id() { return drug_id; }
    public String getDrug_name() { return drug_name; }
    public String getDrug_image() { return drug_image; }
    public Bitmap getDrug_image_bitmap() { return drug_image_bitmap; }
    public String getDrug_type() { return drug_type; }
    public String getDrug_shape() { return drug_shape; }
    public String getDrug_color() { return drug_color; }
    public String getDrug_frontText() { return drug_frontText; }
    public String getDrug_backText() { return drug_backText; }

    public void setDrug_id( String drug_id ) { this.drug_id = drug_id; }
    public void setDrug_name( String drug_name ) { this.drug_name = drug_name; }
    public void setDrug_image( String drug_image ) { this.drug_image = drug_image; }
    public void setDrug_image_bitmap( Bitmap drug_image_bitmap ) { this.drug_image_bitmap = drug_image_bitmap; }
    public void setDrug_type( String drug_type ) { this.drug_type = drug_type; }
    public void setDrug_shape( String drug_shape ) { this.drug_shape = drug_shape;}
    public void setDrug_color( String drug_color ) { this.drug_color = drug_color; }
    public void setDrug_frontText( String drug_frontText ) { this.drug_frontText = drug_frontText; }
    public void setDrug_backText( String drug_backText ) {this.drug_backText = drug_backText; }
}
