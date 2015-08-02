package com.cogito.client.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class CogitoApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataFileDisplayer.INSTANCE();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
