package com.cogito.client.ui;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class InitialLoadUp {

	private static RRoot rmoduleServerObject;
	private static RRoot gnumoduleServerObject;
	private static RRoot rmoduleClientObject;
	private static RRoot gnumoduleClientObject;
	
	private InitialLoadUp() {
		throw new UnsupportedOperationException();
	}
	
	public static void loadRmoduleServerObject() {
		ObjectMapper serverObject = new ObjectMapper();
		try {
			rmoduleServerObject = serverObject.readValue(new URL(
					UrlUtilities.GETLIST_OF_R_SERVER_SERVICES), RRoot.class);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	public static void loadGnuModuleServerObject() {
		ObjectMapper serverObject = new ObjectMapper();
		try{
			gnumoduleServerObject = serverObject.readValue(new URL(UrlUtilities.GETLIST_OF_GNU_SERVER_SERVICES), RRoot.class);	
		}catch(IOException e){
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	public static void loadRmoduleClientObject() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			byte[] rModulejsonData = Files.readAllBytes(Paths.get(InitialLoadUp
					.class.getClassLoader().getResource("config.txt")
					.getPath()));
			rmoduleClientObject = objectMapper.readValue(rModulejsonData, RRoot.class);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	public static void loadgnumoduleClientObject() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			byte[] gnuModulejsonData = Files.readAllBytes(Paths.get(InitialLoadUp
					.class.getClassLoader().getResource("gnumodule.txt")
					.getPath()));
			gnumoduleClientObject = objectMapper.readValue(gnuModulejsonData, RRoot.class);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	public static Optional<RRoot> getRmoduleServerObject(){
	   return Optional.of(rmoduleServerObject);	
	}
	
	public static Optional<RRoot> getGnuModuleServerObject(){
		return Optional.of(gnumoduleServerObject);
	}
	
	public static Optional<RRoot> getRmoduleClientObject() {
		return Optional.of(rmoduleClientObject);
	}
	
	public static Optional<RRoot> getgnumoduleClientObject() {
		return Optional.of(gnumoduleClientObject);
	}
	
}
