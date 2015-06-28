package com.cogito.client.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

public class XmltoGuiGenerator extends Application {
	
	private ImmutableMap<String, String> xmlToGuiMap = ImmutableMap
			.<String, String> builder().put("Description", "Label")
			.put("params", "Label").build();

	public XmltoGuiGenerator() throws IOException {
		byte[] jsonData = Files.readAllBytes(Paths.get(this
				.getClass().getClassLoader().getResource("config.txt")
				.getPath()));
		
		ObjectMapper objectMapper = new ObjectMapper();
		Root rmodule = objectMapper.readValue(jsonData, Root.class);
		
		createPanel(rmodule.getRmodule().getCommand());
	}
	
	
	
	private  String[] parseParameters(String textContent) {
		return textContent.split(",");
	}
	
	private void createPanel(List<Command> commandList) {
		NodeBuilder nodebuilder = new NodeBuilder();
		for(Command command:commandList){
			nodebuilder.injectLabelOnly(command.getDescription());
			String[] parseParameters = parseParameters(command.getParams());
			for(String param:parseParameters){
				nodebuilder.injectLabelAndDescriptiveTextField(param,command.getId());
			}
		}
		Pane pane = new Pane(nodebuilder.getNode());
		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					new XmltoGuiGenerator();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 				
			}
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	   
	}

 
}

