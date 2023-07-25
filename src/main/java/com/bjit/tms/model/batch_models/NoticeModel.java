package com.bjit.tms.model.batch_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NoticeModel {

    private Integer batchId;
    private String notice;

}
