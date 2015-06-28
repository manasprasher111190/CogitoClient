package com.cogito.client.ui;

import java.util.List;


class Rmodule{
	private List<Command> command;

	public List<Command> getCommand() {
		return command;
	}

	public void setCommand(List<Command> command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "Rmodule [command=" + command + "]";
	}
	
}