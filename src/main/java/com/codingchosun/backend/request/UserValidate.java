package com.codingchosun.backend.request;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class UserValidate {
    private Long userId;
    private String templateName;
}
