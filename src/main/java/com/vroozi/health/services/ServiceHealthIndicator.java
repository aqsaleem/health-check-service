package com.vroozi.health.services;


import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;

import java.util.List;

public interface ServiceHealthIndicator {

  /**
   * checks services health
   */
  void servicesHealth(List<Health> doHealthCheck, HealthCheck healthCheck);
}
