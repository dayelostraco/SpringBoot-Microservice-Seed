package com.dayelostraco.microservice.repository;

import com.dayelostraco.microservice.model.dto.SampleModelDto;
import com.dayelostraco.microservice.model.entity.SampleModel;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@CacheConfig(cacheNames = "sampleModels")
@RepositoryRestResource(
        collectionResourceRel = "sampleModel",
        path = "sampleModel",
        excerptProjection = SampleModelDto.class)
public interface SampleModelRepository extends JpaRepository<SampleModel, Long> {

    @Override
    @CachePut("sampleModels")
    <S extends SampleModel> S save(S s);

    @Override
    @Cacheable("sampleModels")
    SampleModel findOne(Long aLong);
}
