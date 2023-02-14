package com.task.mysidejabberparser.service;

import com.google.gson.Gson;
import com.task.mysidejabberparser.model.SideModel;
import com.task.mysidejabberparser.model.TempDataModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class SideDataService {

    public static final String CLASS_NAME_FOR_RATING_AND_REVIEWS_COUNT = "liftigniter-metadata";
    public static final String CLASS_NAME_FOR_NAME = "dialog__title";
    public static final String CLASS_NAME_FOR_URL = "url-header__external-link";
    public static final String KEY_TO_GET_ATTRIBUTES_TO_TEMP_MODEL = "#data";

    public SideModel getSideModel(String path) {
        SideModel resultModel;
        try {
            Document document = Jsoup.connect(path).get();
            String url = getUrl(document);
            String name = getName(document);
            String ratingAndReviews = getRatingAndReviewsCount(document);
            Gson gson = new Gson();
            resultModel = parseAndGetModel(url, name, ratingAndReviews, gson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultModel;
    }

    private SideModel parseAndGetModel(String url, String name, String ratingAndReviews, Gson gson) {
        TempDataModel tempDataModel = gson.fromJson(ratingAndReviews, TempDataModel.class);
        return new SideModel(name, tempDataModel.getRating(), url, tempDataModel.getReviewsCount());

    }

    private String getRatingAndReviewsCount(Document document) {
        return Objects.requireNonNull(document.getElementById(CLASS_NAME_FOR_RATING_AND_REVIEWS_COUNT))
                .childNode(0)
                .attributes()
                .get(KEY_TO_GET_ATTRIBUTES_TO_TEMP_MODEL);
    }

    private String getUrl(Document document) {
        return document.getElementsByClass(CLASS_NAME_FOR_URL).text();
    }

    private String getName(Document document) {
        return document.getElementsByClass(CLASS_NAME_FOR_NAME).text();
    }


}
