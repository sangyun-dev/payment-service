package com.payment.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyTask {

    private String taskID;
    private String taskName;

    private String membershipID;

    private List<SubTask> subTaskList;

    // 법인 계좌
    private String toBankName;

    // 법인 계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount; // only won
}
