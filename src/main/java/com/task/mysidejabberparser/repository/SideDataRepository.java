package com.task.mysidejabberparser.repository;

import com.task.mysidejabberparser.entity.SideData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SideDataRepository extends ReactiveMongoRepository<SideData, String> {
}
