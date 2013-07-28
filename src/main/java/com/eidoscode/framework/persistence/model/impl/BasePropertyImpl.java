package com.eidoscode.framework.persistence.model.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.eidoscode.framework.persistence.model.Model;

/**
 * Main implementation of the {@link Model} interface that is prepared to be a
 * "property" entity. <br/>
 * This entity will store values for a role different types such as String,
 * Long, Integer and etc.
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
public abstract class BasePropertyImpl<Id extends Serializable> extends
		BaseBeanImpl<Id> {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 8170977153367329761L;

	@Column(name = "PROP_NAME")
	private String propName;

	@Column(name = "BOOLEAN_NAME")
	private Boolean booleanValue;

	@Column(name = "STRING_VALUE")
	private String stringValue;

	@Lob
	@Column(name = "LARGE_STRING_VALUE")
	private String largeStringValue;

	@Column(name = "DOUBLE_VALUE")
	private Double doubleValue;

	@Column(name = "LONG_VALUE")
	private Long longValue;

	/**
	 * Returns the boolean value.
	 * 
	 * @since 1.0
	 * @return Boolean value.
	 */
	public Boolean getBooleanValue() {
		return booleanValue;
	}

	/**
	 * Sets the boolean value.
	 * 
	 * @since 1.0
	 * @param booleanValue
	 *            . Boolean value to be stored.
	 */
	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	/**
	 * Returns the property name.
	 * 
	 * @since 1.0
	 * @return property name.
	 */
	public String getPropName() {
		return propName;
	}

	/**
	 * Sets the property name.
	 * 
	 * @since 1.0
	 * @param propName
	 *            Property name.
	 */
	public void setPropName(String propName) {
		this.propName = propName;
	}

	/**
	 * Returns the string value.
	 * 
	 * @since 1.0
	 * @return string value.
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * Sets the string value.
	 * 
	 * @since 1.0
	 * @param stringValue
	 *            String value to be stored.
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * Returns the double value.
	 * 
	 * @since 1.0
	 * @return Double value
	 */
	public Double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * Sets the double value.
	 * 
	 * @since 1.0
	 * @param doubleValue
	 *            Double value to be stored.
	 */
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	/**
	 * Returns the long value.
	 * 
	 * @since 1.0
	 * @return Long value.
	 */
	public Long getLongValue() {
		return longValue;
	}

	/**
	 * Sets the long value.
	 * 
	 * @since 1.0
	 * @param longValue
	 *            Long value to be stored.
	 */
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	/**
	 * Returns a large string value.
	 * 
	 * @since 1.0
	 * @return Large string value.
	 */
	public String getLargeStringValue() {
		return largeStringValue;
	}

	/**
	 * Sets the large string value.
	 * 
	 * @since 1.0
	 * @param largeStringValue
	 *            Large string value to be stored.
	 */
	public void setLargeStringValue(String largeStringValue) {
		this.largeStringValue = largeStringValue;
	}

}
