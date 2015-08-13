package com.cogito.client.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class DataFileDisplayer {
	
	private Button uploadButton = new Button("UPLOAD");
	private Button nextButton = new Button("NEXT");
	private ListView<String> listView = new ListView<>();
	private static final DataFileDisplayer INSTANCE = new DataFileDisplayer();
	
	public static DataFileDisplayer INSTANCE() {
		return INSTANCE;
	}
	
	private DataFileDisplayer() {
		listView.setItems(FXCollections.observableArrayList(getListViewData()));
		BorderPane pane = new BorderPane(listView);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(uploadButton,nextButton);
		pane.setBottom(hbox);
		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		setUpUploadButtonEventListeners(stage);
		setUpNextButtonEventListeners();
	}
	
	private void setUpNextButtonEventListeners() {
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Optional<RRoot> rmoduleClientObject = InitialLoadUp
						.getRmoduleClientObject();
				Optional<RRoot> gnumoduleClientObject = InitialLoadUp
						.getgnumoduleClientObject();
				if (rmoduleClientObject.isPresent()) {
					try {
						new ListofServicesDisplayer(rmoduleClientObject.get()
								.getList(),gnumoduleClientObject.get().getList());
					} catch (IOException e) {
						new Alert(AlertType.ERROR, e.getMessage(),
								ButtonType.OK);
					}
				} else {
					new Alert(AlertType.ERROR,
							"Can't Load Rmodule Client Object", ButtonType.OK);
				}
			}
		});
	}

	private void setUpUploadButtonEventListeners(Stage filestage){
		uploadButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				   FileChooser chooser = new FileChooser();
				   chooser.setTitle("Choose Data file to Upload");
				   chooser.setSelectedExtensionFilter(new ExtensionFilter("JPG files (*.jpg)", "*.JPG"));
				   File showOpenDialog = chooser.showOpenDialog(filestage);
				   if(showOpenDialog !=null){
					   deleteFile(getListViewData());
					   upload(UrlUtilities.UPLOAD_URL, showOpenDialog.getName(), showOpenDialog);
				   }else {
					   Alert errorAlert = new Alert(AlertType.ERROR);
					   errorAlert.setTitle("Error Dialog");
					   errorAlert.setHeaderText("Error Dialog");
					   errorAlert.setContentText("Please Choose a file to Upload");
					   errorAlert.showAndWait();
				   }
			}
		});
	}
	
	private void upload(String serviceUrl, String pathOfServer, File file) {
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("file",new FileBody(file));
		try {
			entity.addPart("name",new StringBody(pathOfServer, "text/plain", 
                    Charset.forName( "UTF-8" )));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost request = new HttpPost(serviceUrl);
	    request.setEntity(entity);

	    
	    try(CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpResponse response = client.execute(request);
			String r =EntityUtils.toString( response.getEntity(), "UTF-8" );
			System.out.println(r);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	private String getListViewData(){
		try {
			URL url = new URL(UrlUtilities.GETLIST_OF_FILES_URL);
			StringBuilder builder = new StringBuilder();
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
			BufferedReader buff = new BufferedReader(reader);
			String line = null;
		      while ((line = buff.readLine()) != null)
		      {
		    	  builder.append(line + "\n");
		      }
		      return builder.toString();
		} catch (MalformedURLException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		} 
	}
	
	private void deleteFile(String path){
		try {
			URL url = new URL(UrlUtilities.DELETE_FILE_URL+path);
			StringBuilder builder = new StringBuilder();
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
			BufferedReader buff = new BufferedReader(reader);
			String line;
		    do {
		      line = buff.readLine();
		      builder.append(line + "\n");
		    } while (line != null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
     
}
