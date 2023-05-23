package com.retail.rewards.RewardsAssesment.service;

import com.retail.rewards.RewardsAssesment.model.RewardSummary;
import com.retail.rewards.RewardsAssesment.model.Transaction;
import com.retail.rewards.RewardsAssesment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private TransactionRepository transactionRepository;

    public RewardSummary calculateRewards(Long customerId) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfCurrentMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime startOfLastMonth = startOfCurrentMonth.minusMonths(1);
        LocalDateTime startOfMonthBeforeLast = startOfLastMonth.minusMonths(1);

        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionTimeBetween(
                customerId, startOfMonthBeforeLast, now);

        int currentMonthPoints = 0;
        int lastMonthPoints = 0;
        int monthBeforeLastPoints = 0;

        for (Transaction transaction : transactions) {
            int points = calculateTransactionPoints(transaction.getAmount());
            if (transaction.getTransactionTime().isAfter(startOfCurrentMonth)) {
                currentMonthPoints += points;
            } else if (transaction.getTransactionTime().isAfter(startOfLastMonth)) {
                lastMonthPoints += points;
            } else if (transaction.getTransactionTime().isAfter(startOfMonthBeforeLast)) {
                monthBeforeLastPoints += points;
            }
        }

        RewardSummary rewardSummary = new RewardSummary();
        rewardSummary.setFirstMonthPoints(monthBeforeLastPoints);
        rewardSummary.setSecondMonthPoints(lastMonthPoints);
        rewardSummary.setThirdMonthPoints(currentMonthPoints);
        rewardSummary.setTotalPoints(monthBeforeLastPoints + lastMonthPoints + currentMonthPoints);

        return rewardSummary;
    }

    private int calculateTransactionPoints(Double amount) {
        if (amount > 100) {
            return 2 * (amount.intValue() - 100) + 50;
        } else if (amount > 50) {
            return amount.intValue() - 50;
        } else {
            return 0;
        }
    }
}
