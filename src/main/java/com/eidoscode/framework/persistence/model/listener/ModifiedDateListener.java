package com.eidoscode.framework.persistence.model.listener;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.eidoscode.framework.persistence.model.AuditedModel;

/**
 * Modified date listener.<br/>
 * This listener will set the modified date of the bean.
 * 
 * @author eantonini
 * 
 * @version 1.0
 * @since 1.3
 */
public class ModifiedDateListener {

  /**
   * Fill the modified date information.
   * 
   * @param model
   *          Model class that implements the interface {@link AuditedModel}.
   * @since 1.0
   */
  @PrePersist
  @PreUpdate
  public <Key extends Serializable, E extends AuditedModel<Key>> void fillInformation(
      E model) {
    model.setModifiedOn(new Date());
  }

}
