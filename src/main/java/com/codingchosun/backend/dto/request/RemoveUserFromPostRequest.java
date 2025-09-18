package com.codingchosun.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RemoveUserFromPostRequest {
    @NotEmpty
    Long removeId;  //userId들어갈거임
}
