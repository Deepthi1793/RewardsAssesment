package com.retail.rewards.RewardsAssesment.repository;

import com.retail.rewards.RewardsAssesment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
