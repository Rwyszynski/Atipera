package com.zadanie2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zadanie2.entity.Branch;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FullObject(String name, Owner owner, List<Branch> branch) {
}
