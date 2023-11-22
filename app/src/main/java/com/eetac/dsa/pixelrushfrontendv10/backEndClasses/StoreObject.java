package com.eetac.dsa.pixelrushfrontendv10.backEndClasses;

public class StoreObject {
    String ID;
    String articleName;
    int price;
    String description;
    String articlePhoto;

    public StoreObject(String id, String name, int price, String articleDescription, String photo){
        this.ID = id;
        this.articleName = name;
        this.price = price;
        this.description = articleDescription;
        this.articlePhoto = photo;
    }

}
