package com.cogito.client.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class InitialLoadUp {

	private static RRoot rmoduleServerObject;
	
	private InitialLoadUp() {
		throw new UnsupportedOperationException();
	}
	
	public static void loadRmoduleServerObject() {
		ObjectMapper serverObject = new ObjectMapper();
		try {
			rmoduleServerObject = serverObject.readValue(new URL(
					UrlUtilities.GETLIST_OF_SERVER_SERVICES), RRoot.class);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		}
	}
	
	public static Optional<RRoot> getRmoduleServerObject(){
	   return Optional.of(rmoduleServerObject);	
	}
}
