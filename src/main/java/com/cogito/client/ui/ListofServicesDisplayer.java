package com.cogito.client.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ListofServicesDisplayer {
	
	private ListView<String> listof1DServices = new ListView<>();
	private ListView<String> listof1DServerServices = new ListView<>();
	private ListView<String> listof2DServices = new ListView<>();
	private ListView<String> listof2DServerServices = new ListView<>();
	private ListView<String> listof3DServices = new ListView<>();
	private ListView<String> listof3DServerServices = new ListView<>();
	private Button showDataButton = new Button("Go to Commands Definition");
	private static final String SERVER_SERVICES_SUPPORTED_TITLE="Services Supported By Server";
	private static final String MORE_SERVICES_SUPPORTED_TITLE = "More Services Supported By Server";
	
	public ListofServicesDisplayer(Rmodule rmodule) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		packAllServiceNodesAndShow(rmodule);
	}
	
	private Node getOneDClientServices(Rmodule rmodule)
			throws JsonParseException, JsonMappingException,
			MalformedURLException, IOException {
		RRoot rmod = getServerObject();
		ObservableList<String> listofOneDServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						rmodule.getOneD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getOneD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList())));
		listof1DServices.setItems(listofOneDServices);
		listof1DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						rmodule.getOneD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getOneD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()))));
		return makeSection(listof1DServices, listof1DServerServices, SERVER_SERVICES_SUPPORTED_TITLE, MORE_SERVICES_SUPPORTED_TITLE);
	}
	
	private Node getTwoDClientServices(Rmodule rmodule)
			throws JsonParseException, JsonMappingException,
			MalformedURLException, IOException {
		RRoot rmod = getServerObject();
		ObservableList<String> listof2DClientServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						rmodule.getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList())));
		listof2DServices.setItems(listof2DClientServices);
		listof2DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						rmodule.getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()))));
		return makeSection(listof2DServices, listof2DServerServices, SERVER_SERVICES_SUPPORTED_TITLE, MORE_SERVICES_SUPPORTED_TITLE);
	}
	
	private  Node getThreeDClientServices(Rmodule rmodule)
			throws JsonParseException, JsonMappingException,
			MalformedURLException, IOException {
		RRoot rmod = getServerObject();
		ObservableList<String> listofThreeDClientServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						rmodule.getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getTwoD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList())));
		listof3DServices.setItems(listofThreeDClientServices);
		listof3DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						rmodule.getThreeD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()),
						rmod.getList().getThreeD().getCommand().stream()
								.map(c -> c.getDescription())
								.collect(Collectors.toList()))));
		return makeSection(listof3DServices, listof3DServerServices, SERVER_SERVICES_SUPPORTED_TITLE, MORE_SERVICES_SUPPORTED_TITLE);
	}
	
	private Node makeSection(ListView<String> clientList,ListView<String> serverList, String clientListTitle, String serverListTitle) {
		VBox vbox = new VBox();
		TitledPane clientPane = new TitledPane(clientListTitle, clientList);
		TitledPane serverPane = new TitledPane(serverListTitle, serverList);
		vbox.getChildren().addAll(clientPane,serverPane);
		return vbox;
	}

	private List<String> servicesSupportedByServer(List<String> listofClientServices,List<String> listofServerServices) {
		listofServerServices.retainAll(listofClientServices);
		return listofServerServices;
	}

	private List<String> moreServicesSupportedByServer(List<String> listofClientServices,List<String> listofServerServices) {
		listofServerServices.removeAll(listofClientServices);
		return listofServerServices;
	}

	private static RRoot getServerObject() throws IOException, JsonParseException,
			JsonMappingException, MalformedURLException {
		ObjectMapper serverObject = new ObjectMapper();
		RRoot rmod = serverObject.readValue(new URL(
				UrlUtilities.GETLIST_OF_SERVER_SERVICES), RRoot.class);
		return rmod;
	}
	
	private void packAllServiceNodesAndShow(Rmodule rmodule) throws JsonParseException, JsonMappingException, MalformedURLException, IOException{
		HBox hbox = new HBox();
		hbox.getChildren().addAll(getOneDClientServices(rmodule),getTwoDClientServices(rmodule),getThreeDClientServices(rmodule));
		VBox vbox = new VBox(hbox);
		vbox.getChildren().add(showDataButton);
		ScrollPane scroll = new ScrollPane(vbox);
		Stage stage = new Stage();
		Scene scene = new Scene(scroll);
		stage.setScene(scene);
		stage.show();
	}
	
	private void setUpdataButtonEventHandler() {
		showDataButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
	}
	
	
}