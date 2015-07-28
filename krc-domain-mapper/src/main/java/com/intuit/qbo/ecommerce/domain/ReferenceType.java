package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;

/**
 * @author bgopinath
 *
 */
public class ReferenceType implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String value;
    protected String name;
    protected String type;

    public ReferenceType() {
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public void setName(String value) {
	name = value;
    }

    public String getType() {
	return type;
    }

    public void setType(String value) {
	type = value;
    }

}