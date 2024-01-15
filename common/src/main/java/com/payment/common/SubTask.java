package com.payment.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubTask {

    private String membershipId;
    private String subTaskName;
    private String taskType; // banking, membership
    private String status; // success, fail, ready
}
