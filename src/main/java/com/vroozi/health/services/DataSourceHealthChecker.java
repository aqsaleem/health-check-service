package com.vroozi.health.services;

import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;

import java.util.List;

public interface DataSourceHealthChecker {

  /**
   * checks Data Sources Health
   */
  void dataSourceHealth(List<Health> doCheckDataSourceHealth, HealthCheck healthCheck);
}
