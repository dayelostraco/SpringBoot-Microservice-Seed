package com.dayelostraco.microservice.model.dto;

import com.dayelostraco.microservice.model.entity.SampleModel;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "sampleModelDto", types = SampleModel.class)
public interface SampleModelDto {

    Long getId();

    String getName();
}