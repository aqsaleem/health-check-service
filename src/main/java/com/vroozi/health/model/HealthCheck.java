package com.vroozi.health.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthCheck {

  private boolean health;
  private List<Health> dataSources = new ArrayList<>();
  private List<Health> services = new ArrayList<>();

  public boolean isHealth() {
    return health;
  }

  public void setHealth(boolean health) {
    this.health = health;
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
