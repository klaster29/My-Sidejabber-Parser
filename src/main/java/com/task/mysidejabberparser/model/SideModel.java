package com.task.mysidejabberparser.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SideModel {
    String name;
    Double rating;
    String url;
    Integer reviewsCount;

    public SideModel(String name, String rating, String url, String reviewsCount) {
        this.name = name;
        this.rating = Double.valueOf(rating);
        this.url = "https://" + url;
        this.reviewsCount = Integer.valueOf(reviewsCount);
    }

    public SideModel() {

    }
}
