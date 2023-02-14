package com.task.mysidejabberparser.controller;

import com.task.mysidejabberparser.Ultils.SideModelUtil;
import com.task.mysidejabberparser.entity.SideData;
import com.task.mysidejabberparser.model.SideModel;
import com.task.mysidejabberparser.repository.SideDataRepository;
import com.task.mysidejabberparser.service.SideDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class SideDataController {

    private final SideDataRepository repository;
    private final SideDataService service;

    @GetMapping
    Flux<SideModel> getAllSideModels() {
        return SideModelUtil.toModel(repository.findAll());
    }

    @GetMapping("{domain}")
    public Mono<ResponseEntity<SideModel>> getSideModelByDomain(@PathVariable String domain) {
        if (!domain.isBlank()) {
            return repository.findById(domain)
                    .defaultIfEmpty(service.getSideData(domain))
                    .map(SideModelUtil::toModel)
                    .map(ResponseEntity::ok);
        }
        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<SideModel>> saveProduct(@RequestBody SideData data) {
        if(!Objects.isNull(data)) {
            repository.findSideDataByName(data.getName()).map(sideData -> {
                if (sideData.equals(data)) {
                    return new ResponseEntity<>(data, HttpStatus.ALREADY_REPORTED);
                } else {
                    return repository.save(data).map(ResponseEntity::ok);
                }
            });
        }
        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{domain}")
    public Mono<ResponseEntity<SideModel>> updateProduct(@PathVariable String domain, @RequestBody SideData data) {
        return repository.findById(domain)
                .flatMap(existingData -> {
                    existingData.setName(data.getName());
                    existingData.setUrl(data.getUrl());
                    existingData.setRating(data.getRating());
                    existingData.setReviewsCount(data.getReviewsCount());
                    return SideModelUtil.toModel(repository.save(existingData));
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{domain}")
    public Mono<ResponseEntity<Void>> deleteSideDataByDomain(@PathVariable String domain) {
        return repository.findById(domain)
                .flatMap(existingData -> repository.delete(existingData)
                        .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteAllSideData() {
        return repository.deleteAll().map(ResponseEntity::ok);
    }
}
