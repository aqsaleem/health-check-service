package com.vroozi.health.model;

public enum Source {
  //data sources
  MONGO, MYSQL, SOLR, NEO4J, JMS,
  // services
  CATALOG, USER_DATA, FILE_STORAGE, PURCHASE, NOTIFICATION, CATEGORY_TREE,
  SHOPPER, SEARCH, SSO, AUTHENTICATION, GATEWAY, ADMINUI, VROOZI_BATCH, DOCUMENT_HISTORY,
  ERP
}
