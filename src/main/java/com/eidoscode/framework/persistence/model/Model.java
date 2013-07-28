package com.eidoscode.framework.persistence.model;

import java.io.Serializable;

/**
 * Main interface of the model that would be persisted.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.0
 * @param <Id>
 *            The type of the Id of the model. If it`s a relational database, it
 *            probably will be something such as a {@link Long} or
 *            {@link Integer}.
 */
public interface Model<Id extends Serializable> extends Serializable {

	/**
	 * Brings the Id value.
	 * 
	 * @return Id value
	 * @since 1.0
	 */
	public abstract Id getId();

	/**
	 * Sets the Id value.
	 * 
	 * @param id
	 *            Id value
	 * @since 1.0
	 */
	public abstract void setId(Id id);

}