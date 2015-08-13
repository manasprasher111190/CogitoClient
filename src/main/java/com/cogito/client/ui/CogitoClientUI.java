package com.cogito.client.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.google.common.io.ByteStreams;

public class CogitoClientUI {

	private final String template;
	private final String serviceUrl;
 
	public CogitoClientUI(String template, String url) {
		this.template = template;
		this.serviceUrl = url;
		init();
	}

	private void init() {
		final GetTestPlotTask task = new GetTestPlotTask();
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent arg0) {
				byte[] array = task.getValue();
				CogitoClientUIGridHelper.addGraphToCogitoGrid(array);
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

	private class GetTestPlotTask extends Task<byte[]> {
		@Override
		protected byte[] call() throws Exception {
			
			byte[] plotImage = getPlotImage(template);
			System.out.println("BYTE ARRAY:"+Arrays.toString(plotImage));
			return plotImage;
		}

	}

	private byte[] getPlotImage(String template) {
		try {
			String decode = URLEncoder.encode(template, "UTF-8");
			URL url = new URL(serviceUrl+decode);
			System.out.println(url.toString());
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
