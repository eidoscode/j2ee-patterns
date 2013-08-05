package com.eidoscode.framework.persistence.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface that extends {@link Model} interface with the purpose to add the
 * created and modified date to the model. <br/>
 * 
 * @author eantonini
 * @since 1.3
 * @version 1.0
 * @param <Id>
 *          The type of the Id of the model. If it`s a relational database, it
 *          probably will be something such as a {@link Long} or {@link Integer}
 *          .
 */
public interface AuditedModel<Id extends Serializable> extends Model<Id> {

  /**
   * Brings the creation date of the entity.
   * 
   * @return Creation Date
   * @since 1.3
   */
  Date getCreatedOn();

  /**
   * Sets the Creation date of the entity.
   * 
   * @param createdOn
   *          Creation Date
   * @since 1.3
   */
  void setCreatedOn(Date createdOn);

  /**
   * Brings the modified date of the entity.
   * 
   * @return Modified date.
   * @since 1.3
   */
  Date getModifiedOn();

  /**
   * Sets the Modified date of the entity.
   * 
   * @param modifiedOn
   *          Modified date.
   * @since 1.3
   */
  void setModifiedOn(Date modifiedOn);

}