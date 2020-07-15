package com.vroozi.health.services;

import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.vroozi.health.model.AvailabilityStatus.AVAILABLE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class HealthIndicatorImpl implements HealthIndicator {

  @Autowired
  private DataSourceHealthIndicator dataSourceHealthIndicator;

  @Autowired
  private ServiceHealthIndicator serviceHealthIndicator;

  @Override
  public HealthCheck health(List<Health> doCheckDataSourceHealth, List<Health> doCheckServiceHealth) {
    HealthCheck healthCheck = new HealthCheck();
    // data source health check
    if (isNotEmpty(doCheckDataSourceHealth)) {
      dataSourceHealthIndicator.dataSourceHealth(doCheckDataSourceHealth, healthCheck);
    }
    // services health check
    if (isNotEmpty(doCheckServiceHealth)) {
      serviceHealthIndicator.servicesHealth(doCheckServiceHealth, healthCheck);
    }
    List<Health> healthList = ListUtils
        .union(healthCheck.getDataSources(), healthCheck.getServices());
    healthCheck.setHealth(healthList.stream()
        .allMatch(health -> StringUtils.equals(AVAILABLE.name(), health.getStatus().name())));
    return healthCheck;
  }
}
