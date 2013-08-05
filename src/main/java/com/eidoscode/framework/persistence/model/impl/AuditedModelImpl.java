package com.eidoscode.framework.persistence.model.impl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eidoscode.framework.persistence.model.AuditedModel;
import com.eidoscode.framework.persistence.model.Model;
import com.eidoscode.framework.persistence.model.listener.CreationDateListener;
import com.eidoscode.framework.persistence.model.listener.ModifiedDateListener;

/**
 * Main implementation of the {@link Model} interface with the created and
 * modified date. <br/>
 * This information will be stored automatically.
 * 
 * @author eantonini
 * @since 1.3
 * @version 1.0
 * @param <Id>
 *          The type of the Id of the model. If it`s a relational database, it
 *          probably will be something such as a {@link Long} or {@link Integer}
 *          .
 */
@MappedSuperclass
@EntityListeners({ CreationDateListener.class, ModifiedDateListener.class })
public abstract class AuditedModelImpl<Id extends Serializable> extends
    ModelImpl<Id> implements Serializable, Model<Id>, AuditedModel<Id> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1207430387524378060L;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_ON")
  private Date createdOn;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIED_ON")
  private Date modifiedOn;

  @Override
  public Date getCreatedOn() {
    return createdOn;
  }

  @Override
  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  @Override
  public Date getModifiedOn() {
    return modifiedOn;
  }

  @Override
  public void setModifiedOn(Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  // @PrePersist
  // @PreUpdate
  // private <Key extends Serializable, E extends AuditedModel<Key>> void
  // fillDefaultInformation(
  // E bean) {
  // System.out.println("FILL CREATION DATE");
  // if (bean.getId() == null) {
  // bean.setCreatedOn(new Date());
  // }
  // }

}
