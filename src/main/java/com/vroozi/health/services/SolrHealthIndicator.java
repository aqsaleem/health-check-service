package com.vroozi.health.services;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;

public class SolrHealthIndicator extends AbstractHealthIndicator {
  private final SolrServer solrServer;

  public SolrHealthIndicator(SolrServer solrServer) {
    this.solrServer= solrServer;
  }

  protected void doHealthCheck(Builder builder) throws Exception {
    CoreAdminRequest request = new CoreAdminRequest();
    request.setAction(CoreAdminAction.STATUS);
    CoreAdminResponse response = request.process(this.solrServer);
    int statusCode = response.getStatus();
    Status status = statusCode != 0 ? Status.DOWN : Status.UP;
    builder.status(status).withDetail("status", statusCode);
  }
}
