package com.cogito.client.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.google.common.io.ByteStreams;

public class CogitoClientUI extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		
		final GetTestPlotTask task = new GetTestPlotTask();
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent arg0) {
				 byte[] array = task.getValue();
                 makePlotUi(array);						
			}
		});
	}

	private void makePlotUi(byte[] array) {
		ImageView graphView = new ImageView();
		    InputStream is = new ByteArrayInputStream(array);
			graphView.setImage(new Image(is));
			HBox box = new HBox(graphView);
			Scene scene = new Scene(box);
			Stage stage = new Stage();
			stage.setTitle("Test");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	private class GetTestPlotTask extends Task<byte[]>{

		@Override
		protected byte[] call() throws Exception {
			return getPlotImage();
		}
		
	}

	private byte[] getPlotImage() {
		try {
			URL url = new URL("http://localhost:8080/plot");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			try (InputStream inputStream = conn.getInputStream()) {
				byte[] imageBytes = ByteStreams.toByteArray(inputStream);
				conn.disconnect();
				return imageBytes;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
