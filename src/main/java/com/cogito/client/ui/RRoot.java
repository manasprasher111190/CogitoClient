package com.cogito.client.ui;


class RRoot{
	private Rmodule list;

	public Rmodule getList() {
		return list;
	}

	public void setList(Rmodule rmodule) {
		this.list = rmodule;
	}

	@Override
	public String toString() {
		return "RRoot [Rmodule=" + list + "]";
	}
	
}