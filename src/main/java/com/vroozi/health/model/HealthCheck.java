package com.vroozi.health.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthCheck {

  private boolean healthy;
  private List<Health> dataSources = new ArrayList<>();
  private List<Health> services = new ArrayList<>();

  public boolean isHealthy() {
    return healthy;
  }

  public void setHealthy(boolean healthy) {
    this.healthy = healthy;
  }

  public List<Health> getDataSources() {
    return dataSources;
  }

  public void setDataSources(List<Health> dataSources) {
    this.dataSources = dataSources;
  }

  public List<Health> getServices() {
    return services;
  }

  public void setServices(List<Health> services) {
    this.services = services;
  }
}
