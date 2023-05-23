package com.retail.rewards.RewardsAssesment.repository;

import com.retail.rewards.RewardsAssesment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerIdAndTransactionTimeBetween(Long customerId, LocalDateTime start, LocalDateTime end);}
