package com.zadanie2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubResult(String name, boolean fork, Owner owner) {
}
