package com.cogito.client.ui;

class Command {
	private String id;
	private String description;
	private String service;
	private String params;
	private String template;
	private Alternative alternative;

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
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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
	public Alternative getAlternative() {
		return alternative;
	}
	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}
	
	@Override
	public String toString() {
		return "Command [id=" + id + ", description=" + description
				+ ", params=" + params + ", template=" + template
				+ ", alternative=" + alternative + "]";
	}
	
	
}