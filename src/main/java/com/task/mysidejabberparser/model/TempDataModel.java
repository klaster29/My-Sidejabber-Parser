package com.task.mysidejabberparser.model;

public class TempDataModel {
    private String reviewCount;
    private String rating;
    private String adult;

    public TempDataModel(String reviewCount, String rating, String adult) {
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.adult = adult;
    }

    public TempDataModel() {
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }
}
