package com.vroozi.health.services;

import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;

import java.util.List;

public interface HealthIndicator {

  /**
   * checks health of data sources and services
   */
  HealthCheck health(List<Health> doCheckDataSourceHealth, List<Health> doCheckServiceHealth);
}
