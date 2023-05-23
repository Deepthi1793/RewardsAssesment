package com.retail.rewards.RewardsAssesment.controller;

import com.retail.rewards.RewardsAssesment.model.RewardSummary;
import com.retail.rewards.RewardsAssesment.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    @Autowired
    private RewardsService rewardsService;

    @GetMapping("/{id}")
    public RewardSummary getRewardSummary(@PathVariable("id") Long customerId) {
        return rewardsService.calculateRewards(customerId);
    }

}

