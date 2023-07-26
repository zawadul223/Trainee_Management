package com.bjit.tms.model.classroom_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListModel {

    private String trainerName;
    private String message;
    //private String file;
    private Date date;

}
