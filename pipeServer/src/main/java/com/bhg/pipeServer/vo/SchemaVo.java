package com.bhg.pipeServer.vo;

import java.util.Set;

import com.bhg.pipeServer.service.IPipeEntity;

public class SchemaVo implements IPipeEntity {
	String id;
	String name;
	Set<Field> fields;

	public SchemaVo() {

	}

	public SchemaVo(String id, String name, Set<Field> fields) {
		this.id = id;
		this.name = name;
		this.fields = fields;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}
}
