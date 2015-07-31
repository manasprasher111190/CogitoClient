package com.cogito.client.ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UIGridExample extends Application {

	public UIGridExample() {
		GridPane grid = new GridPane();
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		grid.getChildren().add(new TextField());
		Scene scene = new Scene(grid);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new UIGridExample();
		
	}

}
