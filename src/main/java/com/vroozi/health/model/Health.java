package com.vroozi.health.model;

public class Health {

  private Source name;
  private String url;
  private AvailabilityStatus status = AvailabilityStatus.UNAVAILABLE;

  private Health() {
  }

  public Source getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public AvailabilityStatus getStatus() {
    return status;
  }

  public static HealthBuilder builder() {
    return new HealthBuilder();
  }

  public static class HealthBuilder {

    private Health health = new Health();

    public HealthBuilder source(Source source) {
      health.name = source;
      return this;
    }

    public HealthBuilder url(String url) {
      health.url = url;
      return this;
    }

    public HealthBuilder status(AvailabilityStatus status) {
      health.status = status;
      return this;
    }

    public Health build() {
      return health;
    }
  }
}
