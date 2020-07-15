package com.vroozi.health.services;

import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;
import com.vroozi.health.model.Source;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.jms.JmsHealthIndicator;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.neo4j.Neo4jHealthIndicator;
import org.springframework.boot.actuate.solr.SolrHealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static com.vroozi.health.model.AvailabilityStatus.AVAILABLE;
import static com.vroozi.health.model.AvailabilityStatus.UNAVAILABLE;

@Service
public class DataSourceHealthIndicatorImpl implements DataSourceHealthIndicator {

  private static Logger LOGGER = LoggerFactory.getLogger(DataSourceHealthIndicatorImpl.class);

  @Autowired
  ApplicationContext applicationContext;

  @Override
  public void dataSourceHealth(List<Health> doCheckDataSourceHealth, HealthCheck healthCheck) {
    doCheckDataSourceHealth.forEach(health -> healthCheck.getDataSources().add(
        doHealthCheck(getHealthIndicatorBean(health.getName()), health.getName(),
            health.getUrl())));
  }

  Health doHealthCheck(HealthIndicator healthIndicator, Source dataSource,
      String hostAddress) {
    try {
      Status status = getHealthStatus(healthIndicator);
      LOGGER.debug("Checking Health of data source {}, And Got Status {}", dataSource, status);
      if (Status.UP == status) {
        return Health.builder()
            .source(dataSource).url(hostAddress).status(AVAILABLE)
            .build();
      } else {
        return Health.builder()
            .source(dataSource).url(hostAddress).status(UNAVAILABLE)
            .build();
      }
    } catch (Exception e) {
      LOGGER.error("Exception Occurred While Checking health Of Data Source {}", dataSource, e);
      return Health.builder()
          .source(dataSource).url(hostAddress).status(UNAVAILABLE)
          .build();
    }
  }

  Status getHealthStatus(HealthIndicator healthIndicator) {
    return healthIndicator.health().getStatus();
  }

  private HealthIndicator getHealthIndicatorBean(Source source) {
    switch (source) {
      case SOLR:
        return applicationContext.getBean(SolrHealthIndicator.class);
      case MYSQL:
        return applicationContext
            .getBean(org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator.class);
      case MONGO:
        return applicationContext.getBean(MongoHealthIndicator.class);
      case NEO4J:
        return applicationContext.getBean(Neo4jHealthIndicator.class);
      case JMS:
        return applicationContext.getBean(JmsHealthIndicator.class);
      default:
        return null;
    }
  }
}
