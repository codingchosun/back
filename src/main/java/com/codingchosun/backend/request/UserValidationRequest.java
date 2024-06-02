package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@NoArgsConstructor
public class UserValidationRequest {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private Long validateId;
}
