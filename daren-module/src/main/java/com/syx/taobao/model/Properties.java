package com.syx.taobao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_properties")
public class Properties {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="PROP_KEY")
	private String key;
	
	@Column(name="PROP_VALUE")
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Properties [id=" + id + ", key=" + key + ", value=" + value + "]";
	}

	public Properties(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Properties() {
		super();
	}
	
	
}
