package com.cogito.client.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public final class CogitoClientUIGridHelper {
	
	private static GridPane cogitoGrid = new GridPane();
	private static int columncount = 0;
	private static int rowCount = 0;
	private static ScrollPane pane = new ScrollPane(cogitoGrid);
	private static Scene scene = new Scene(pane,1000,1000);
	private CogitoClientUIGridHelper() {
		throw new UnsupportedOperationException();
	}
	
	public static void addGraphToCogitoGrid(byte[] array) {
		Platform.runLater(() -> {
			ImageView graphView = new ImageView();
			graphView.setPreserveRatio(true);
			graphView.setCache(true);
			InputStream is = new ByteArrayInputStream(array);
			graphView.setImage(new Image(is));
			cogitoGrid.add(graphView, columncount, rowCount);
			columncount++;
			if(columncount>2){
				rowCount++;
				columncount = 0;
			}
		});
	}
	
	
	public static  void showCogitoGrid() {
		cogitoGrid.setHgap(5);
		cogitoGrid.setVgap(5);
		Stage stage = new Stage();
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
                cogitoGrid.getChildren().clear();				
			}
		});
	}
}
