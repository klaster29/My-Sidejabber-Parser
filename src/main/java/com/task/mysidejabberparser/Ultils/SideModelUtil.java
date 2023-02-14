package com.task.mysidejabberparser.Ultils;

import com.task.mysidejabberparser.entity.SideData;
import com.task.mysidejabberparser.model.SideModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SideModelUtil {
    public static Flux<SideModel> toModel(Flux<SideData> data) {
        return data.flatMap(data1 -> Flux.just(new SideModel(data1)));
    }

    public static Mono<SideModel> toModel(Mono<SideData> data) {
        return data.map(SideModel::new);
    }

    public static SideModel toModel(SideData data) {
        return new SideModel(data.getName(), data.getRating(), data.getUrl(), data.getReviewsCount());
    }
}
