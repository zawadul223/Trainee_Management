package com.bjit.tms.model.classroom_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {

    private String message;
    //private String file;
    //private Integer classroomId;
}
