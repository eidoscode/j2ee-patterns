package com.eidoscode.framework.persistence.model.listener;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.eidoscode.framework.persistence.model.AuditedModel;

/**
 * Creation date listener. <br/>
 * This listener will set the creation date of the bean.
 * 
 * @author eantonini
 * 
 * @version 1.0
 * @since 1.3
 */
public class CreationDateListener {

  /**
   * Fill the creation date information.
   * 
   * @param model
   *          Model class that implements the interface {@link AuditedModel}.
   * @since 1.0
   */
  @PrePersist
  @PreUpdate
  public <Key extends Serializable, E extends AuditedModel<Key>> void fillInformation(
      E model) {
    if (model.getId() == null) {
      model.setCreatedOn(new Date());
    }
  }
}
