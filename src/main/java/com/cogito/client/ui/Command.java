package com.cogito.client.ui;

class Command {
	private String id;
	private String description;
	private String params;
	private String template;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	@Override
	public String toString() {
		return "Command [description=" + description + ", params=" + params
				+ ", template=" + template + "]";
	}
	
}