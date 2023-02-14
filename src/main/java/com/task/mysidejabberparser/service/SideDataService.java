package com.task.mysidejabberparser.service;

import com.google.gson.Gson;
import com.task.mysidejabberparser.entity.SideData;
import com.task.mysidejabberparser.model.TempDataModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class SideDataService {

    public static final String CLASS_NAME_FOR_RATING_AND_REVIEWS_COUNT = "liftigniter-metadata";
    public static final String CLASS_NAME_FOR_NAME = "dialog__title";
    public static final String CLASS_NAME_FOR_URL = "url-header__external-link";
    public static final String KEY_TO_GET_ATTRIBUTES_TO_TEMP_MODEL = "#data";
    public static final String FIRST_PART_OF_PATH = "https://www.sitejabber.com/reviews/";

    public SideData getSideData(String domain) {
        SideData resultData;
        try {
            Document document = Jsoup.connect(FIRST_PART_OF_PATH + domain).get();
            String url = getUrl(document);
            String name = getName(document);
            Gson gson = new Gson();
            TempDataModel ratingAndReviews = getRatingAndReviewsCount(document, gson);
            resultData = parseAndGetSideData(domain, url, name, ratingAndReviews);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultData;
    }

    private SideData parseAndGetSideData(String path, String url, String name, TempDataModel ratingAndReviews) {
        return new SideData(path, name, ratingAndReviews.getRating(), url, ratingAndReviews.getReviewCount());

    }

    private TempDataModel getRatingAndReviewsCount(Document document, Gson gson) throws IOException {
        return gson.fromJson(Objects.requireNonNull(document.getElementById(CLASS_NAME_FOR_RATING_AND_REVIEWS_COUNT))
                .childNode(0)
                .attributes()
                .get(KEY_TO_GET_ATTRIBUTES_TO_TEMP_MODEL), TempDataModel.class);
    }

    private String getUrl(Document document) {
        return document.getElementsByClass(CLASS_NAME_FOR_URL).text();
    }

    private String getName(Document document) {
        return document.getElementsByClass(CLASS_NAME_FOR_NAME).text();
    }
}
