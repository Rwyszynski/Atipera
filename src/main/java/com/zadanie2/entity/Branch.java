package com.zadanie2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zadanie2.dto.Commit;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Branch(Commit commit) { }
