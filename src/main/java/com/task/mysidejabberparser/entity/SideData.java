package com.task.mysidejabberparser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class SideData {

    @Id
    String domain;
    String name;
    String rating;
    String url;
    String reviewsCount;
}
