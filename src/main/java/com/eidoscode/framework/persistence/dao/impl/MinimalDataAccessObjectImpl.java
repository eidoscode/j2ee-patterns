package com.eidoscode.framework.persistence.dao.impl;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Minimal implementation of the Data Access Object.
 * 
 * @version 1.0
 * @since 1.0
 * @author eantonini
 * 
 */
public abstract class MinimalDataAccessObjectImpl {

  protected final Logger logger;

  /**
   * Main constructor of the Data Access Object.
   * 
   * @since 1.0
   */
  public MinimalDataAccessObjectImpl() {
    logger = Logger.getLogger(getClass());
  }

  /**
   * Brings the {@link EntityManager}.
   * 
   * @since 1.0
   * @return {@link EntityManager}.
   */
  public abstract EntityManager getEntityManager();

  /**
   * Brings the {@link Logger}.
   * 
   * @since 1.0
   * @return {@link Logger}.
   */
  public Logger getLogger() {
    return logger;
  }

}
