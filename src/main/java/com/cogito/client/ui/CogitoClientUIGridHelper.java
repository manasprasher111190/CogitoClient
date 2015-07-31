package com.cogito.client.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public final class CogitoClientUIGridHelper {
	
	private static GridPane cogitoGrid = new GridPane();
	
	private CogitoClientUIGridHelper() {
		throw new UnsupportedOperationException();
	}
	
	public static void addGraphToCogitoGrid(byte[] array) {
		Platform.runLater(() -> {
			ImageView graphView = new ImageView();
			InputStream is = new ByteArrayInputStream(array);
			graphView.setImage(new Image(is));
			System.out.println(cogitoGrid.getChildren().size());
			cogitoGrid.add(graphView, getCogitoGridChildren(), getCogitoGridChildren()+1);
		});
	}
	
	private static int getCogitoGridChildren(){
		return cogitoGrid.getChildren().size();
	}
	
	public static  void showCogitoGrid() {
		Scene scene = new Scene(cogitoGrid);
		Stage stage = new Stage();
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
}
