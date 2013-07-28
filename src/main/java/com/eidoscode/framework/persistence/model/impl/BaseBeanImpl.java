package com.eidoscode.framework.persistence.model.impl;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.eidoscode.framework.persistence.model.Model;

/**
 * Main implementation of the {@link Model} interface.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.0
 * @param <Id>
 *            The type of the Id of the model. If it`s a relational database, it
 *            probably will be something such as a {@link Long} or
 *            {@link Integer}.
 */
@MappedSuperclass
public abstract class BaseBeanImpl<Id extends Serializable> implements
		Serializable, Model<Id> {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -2078833131112166110L;

}
