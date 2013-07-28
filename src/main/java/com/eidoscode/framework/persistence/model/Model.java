package com.eidoscode.framework.persistence.model;

import java.io.Serializable;

/**
 * Main interface of the model that would be persisted.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.1
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
	Id getId();

	/**
	 * Sets the Id value.
	 * 
	 * @param id
	 *            Id value
	 * @since 1.0
	 */
	void setId(Id id);

	/**
	 * Brings the Version of the bean.
	 * 
	 * @since 1.1
	 * @return Version of the bean.
	 */
	int getVersion();

	/**
	 * Sets the version of the bean.
	 * 
	 * @since 1.1
	 * @param version
	 *            Version of the bean.
	 */
	void setVersion(final int version);

}