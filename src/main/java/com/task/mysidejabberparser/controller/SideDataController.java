package com.task.mysidejabberparser.controller;

import com.task.mysidejabberparser.Ultils.SideModelUtil;
import com.task.mysidejabberparser.entity.SideData;
import com.task.mysidejabberparser.model.SideModel;
import com.task.mysidejabberparser.repository.SideDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class SideDataController {

    private final SideDataRepository repository;

    @GetMapping
    Flux<SideModel> getAllSideModels() {
        return SideModelUtil.toModel(repository.findAll());
    }

    @GetMapping("{domain}")
    public Mono<SideModel> getSideModelByDomain(@PathVariable String domain) {
        return SideModelUtil.toModel(repository.findById(domain));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SideModel> saveProduct(@RequestBody SideData data) {
        return SideModelUtil.toModel(repository.save(data));
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
    public Mono<Void> deleteAllSideData() {
        return repository.deleteAll();
    }
}
