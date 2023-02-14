package com.task.mysidejabberparser.repository;

import com.task.mysidejabberparser.entity.SideData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SideDataRepository extends ReactiveMongoRepository<SideData, String> {
    Mono<SideData> findSideDataByName(String name);
}
