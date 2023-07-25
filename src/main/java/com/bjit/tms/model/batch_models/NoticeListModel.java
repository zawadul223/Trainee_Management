package com.bjit.tms.model.batch_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeListModel {

    private String notice;
    private Date time;
    private String trainerName;
}
