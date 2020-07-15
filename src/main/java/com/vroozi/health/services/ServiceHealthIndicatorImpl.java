package com.vroozi.health.services;

import com.vroozi.health.model.Health;
import com.vroozi.health.model.HealthCheck;
import com.vroozi.health.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static com.vroozi.health.model.AvailabilityStatus.AVAILABLE;
import static com.vroozi.health.model.AvailabilityStatus.UNAVAILABLE;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SEE_OTHER;

@Service
public class ServiceHealthIndicatorImpl implements ServiceHealthIndicator {

  private static Logger LOGGER = LoggerFactory.getLogger(ServiceHealthIndicatorImpl.class);

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void servicesHealth(List<Health> doHealthCheck, HealthCheck healthCheck) {
    doHealthCheck.forEach(health ->
        healthCheck.getServices().add(doHealthCheck(health.getUrl(), health.getName())));
  }

  private Health doHealthCheck(String url, Source source) {
    try {
      ResponseEntity responseEntity =
          restTemplate.exchange(url, HttpMethod.HEAD, null,
              new ParameterizedTypeReference<String>() {
              });
      LOGGER.debug("Checking Health of Url {}, and Got status {}", url,
          responseEntity.getStatusCode());
      if (asList(OK.value(), NO_CONTENT.value(), SEE_OTHER.value())
          .contains(responseEntity.getStatusCode().value())) {
        return Health.builder()
            .source(source)
            .url(url)
            .status(AVAILABLE)
            .build();
      } else {
        return Health.builder()
            .source(source)
            .url(url)
            .status(UNAVAILABLE)
            .build();
      }
    } catch (Exception e) {
      LOGGER.error("Exception while Checking the Health of Url {}, source {}", url, source, e);
      return Health.builder()
          .source(source)
          .url(url)
          .status(UNAVAILABLE)
          .build();
    }
  }
}
