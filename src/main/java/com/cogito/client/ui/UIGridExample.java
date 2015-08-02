package com.cogito.client.ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class UIGridExample extends Application {

	public UIGridExample() {
		GridPane grid = new GridPane();
		Button b1 = new Button("ONe oneoneoneoneonoenoe");
		Button b2 = new Button("ONe");
		Button b3 = new Button("Threesss");
		grid.setStyle("-fx-padding: 10;"+
		                 "-fx-border-style: solid inside;"+
				"-fx-border-width: 10;"+
		                 "-fx-border-insets: 5;"+
				"-fx-border-radius: 5;"+
		                 "-fx-border-color: blue;");
		
		grid.getChildren().add(b1);
		grid.getChildren().add(b2);
		grid.getChildren().add(b3);
		grid.getColumnConstraints().add(new ColumnConstraints(100));
		grid.getRowConstraints().add(new RowConstraints(50));
		
//		grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());
//		grid.getChildren().add(new TextField());
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
//		new UIGridExample();
		
	}

}
