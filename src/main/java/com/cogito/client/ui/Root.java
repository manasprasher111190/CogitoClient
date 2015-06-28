package com.cogito.client.ui;


class Root{
	private Rmodule rmodule;

	public Rmodule getRmodule() {
		return rmodule;
	}

	public void setRmodule(Rmodule rmodule) {
		this.rmodule = rmodule;
	}

	@Override
	public String toString() {
		return "Root [Rmodule=" + rmodule + "]";
	}
	
}