package com.codingchosun.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class ResearchRequest {
    @NotNull
    @JsonProperty("researchQuery")
    String researchQuery;
}
