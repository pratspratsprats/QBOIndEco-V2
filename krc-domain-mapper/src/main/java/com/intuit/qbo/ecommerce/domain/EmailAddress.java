package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;

/**
 * @author bgopinath
 *
 */
public class EmailAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;
    protected String Address;
    protected Boolean _default;
    protected String tag;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getAddress() {
	return Address;
    }

    public void setAddress(String address) {
	this.Address = address;
    }

    public Boolean get_default() {
	return _default;
    }

    public void set_default(Boolean _default) {
	this._default = _default;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

}
