package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;

/**
 * @author bgopinath
 *
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}
