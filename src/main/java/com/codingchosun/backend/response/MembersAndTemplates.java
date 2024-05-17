package com.codingchosun.backend.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class MembersAndTemplates {
    String writer;
    List<String> members;
    List<String> templateNames;

}
