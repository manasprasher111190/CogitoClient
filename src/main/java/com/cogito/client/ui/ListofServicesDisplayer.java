package com.cogito.client.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	
	public ListofServicesDisplayer(Rmodule rmodule, Rmodule gnumodule) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		Optional<RRoot> rmoduleServerObject = InitialLoadUp.getRmoduleServerObject();
		Optional<RRoot> gnumoduleServerObject = InitialLoadUp.getGnuModuleServerObject();
		if(rmoduleServerObject.isPresent() && gnumoduleServerObject.isPresent()){
			packAllServiceNodesAndShow(rmodule,gnumodule,rmoduleServerObject.get(),gnumoduleServerObject.get());	
		}else{
			new Alert(AlertType.ERROR, "SERVER OBJECT FAILED TO LOAD", ButtonType.OK);
		}
		
	}
	
	private Node getOneDClientServices(Rmodule rClientObject,
			RRoot rServerObject, Rmodule gnuClientObject, RRoot gnuServerObject) {
		ObservableList<String> listofOneDServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						joinList(getListFromObject(rClientObject
								.getOneD().getCommand()),
								getListFromObject(gnuClientObject
										.getOneD().getCommand())),
						joinList(getListFromObject(rServerObject
								.getList().getOneD().getCommand()),
								getListFromObject(gnuServerObject
										.getList().getOneD().getCommand()))));
		listof1DServices.setItems(listofOneDServices);
		listof1DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						joinList(getListFromObject(rClientObject
								.getOneD().getCommand()),
								getListFromObject(gnuClientObject
										.getOneD().getCommand())),
						joinList(getListFromObject(rServerObject
								.getList().getOneD().getCommand()),
								getListFromObject(gnuServerObject
										.getList().getOneD().getCommand())))));
		return makeSection(listof1DServices, listof1DServerServices,
				SERVER_SERVICES_SUPPORTED_TITLE, MORE_SERVICES_SUPPORTED_TITLE);
	}

	
		
	private Node getTwoDClientServices(Rmodule rClientObject,
			RRoot rServerObject, RRoot gnuServerObject, Rmodule gnuClientObject) {
		ObservableList<String> listof2DClientServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						joinList(getListFromObject(rClientObject.getTwoD()
								.getCommand()),
								getListFromObject(gnuClientObject.getTwoD()
										.getCommand())),
						joinList(getListFromObject(rServerObject.getList()
								.getTwoD().getCommand()),
								getListFromObject(gnuServerObject.getList()
										.getTwoD().getCommand()))));
		listof2DServices.setItems(listof2DClientServices);
		listof2DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						joinList(getListFromObject(rClientObject.getTwoD()
								.getCommand()),
								getListFromObject(gnuClientObject.getTwoD()
										.getCommand())),
						joinList(getListFromObject(rServerObject.getList()
								.getTwoD().getCommand()),
								getListFromObject(gnuServerObject.getList()
										.getTwoD().getCommand())))));
		return makeSection(listof2DServices, listof2DServerServices,
				SERVER_SERVICES_SUPPORTED_TITLE, MORE_SERVICES_SUPPORTED_TITLE);
	}
	
	private  Node getThreeDClientServices(Rmodule rClientObject, RRoot rServerObject, Rmodule gnuClientObject, RRoot gnuServerObject){
		ObservableList<String> listofThreeDClientServices = FXCollections
				.observableArrayList(servicesSupportedByServer(
						joinList(getListFromObject(rClientObject.getThreeD()
								.getCommand()),
								getListFromObject(gnuClientObject.getThreeD()
										.getCommand())),
						joinList(getListFromObject(rServerObject.getList()
								.getThreeD().getCommand()),
								getListFromObject(gnuServerObject.getList()
										.getThreeD().getCommand()))));
		listof3DServices.setItems(listofThreeDClientServices);
		listof3DServerServices.setItems(FXCollections
				.observableArrayList(moreServicesSupportedByServer(
						joinList(getListFromObject(rClientObject.getThreeD()
								.getCommand()),
								getListFromObject(gnuClientObject.getThreeD()
										.getCommand())),
						joinList(getListFromObject(rServerObject.getList()
								.getThreeD().getCommand()),
								getListFromObject(gnuServerObject.getList()
										.getThreeD().getCommand())))));
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

	private void packAllServiceNodesAndShow(Rmodule rClientObject, Rmodule gnuClientObject, RRoot rServerObject, RRoot gnuServerObject) {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(getOneDClientServices(rClientObject,rServerObject,gnuClientObject,gnuServerObject),getTwoDClientServices(rClientObject,rServerObject,gnuServerObject,gnuClientObject),getThreeDClientServices(rClientObject,rServerObject,gnuClientObject,gnuServerObject));
		VBox vbox = new VBox(hbox);
		vbox.getChildren().add(showDataButton);
		ScrollPane scroll = new ScrollPane(vbox);
		Stage stage = new Stage();
		Scene scene = new Scene(scroll);
		stage.setScene(scene);
		stage.show();
		setUpdateButtonEventHandler(rClientObject,rServerObject,gnuServerObject);
	}
	
	private void setUpdateButtonEventHandler(Rmodule rmodule, RRoot rServerObject, RRoot gnuServerObject) {
		showDataButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				makeCommandList(
						listof1DServices.getSelectionModel().getSelectedItems(),
						listof1DServerServices.getSelectionModel()
								.getSelectedItems(),
						listof2DServices.getSelectionModel().getSelectedItems(),
						listof2DServerServices.getSelectionModel()
								.getSelectedItems(), listof3DServices
								.getSelectionModel().getSelectedItems(),
						listof3DServerServices.getSelectionModel()
								.getSelectedItems(),rmodule,rServerObject,gnuServerObject);
			}
		});
	}
	
	private <T> List<T> joinList(List<T> list1, List<T> list2) {
		return Stream.of(list1, list2).flatMap(x -> x.stream())
				.collect(Collectors.toList());
	}	
	
	private List<String> getListFromObject(List<Command> commandList) {
		return commandList.stream()
				.map(c -> c.getDescription())
				.collect(Collectors.toList());
	}

	private void makeCommandList(ObservableList<String> observableList,
			ObservableList<String> observableList2,
			ObservableList<String> observableList3,
			ObservableList<String> observableList4,
			ObservableList<String> observableList5,
			ObservableList<String> observableList6,
			Rmodule rmodule, RRoot rServerObject, RRoot gnuServerObject) {
		List<String> collect = Stream
				.of(observableList, observableList2, observableList3,
						observableList4, observableList5, observableList6)
				.flatMap(x -> x.stream()).collect(Collectors.toList());

		try {
            new JSONtoGuiGenerator(create(collect, rServerObject, gnuServerObject));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private List<Command> create(List<String> strings,RRoot rserverObject,RRoot gnuserverObject){
		List<Command> oneDCommands = joinList(rserverObject.getList().getOneD().getCommand(), gnuserverObject.getList().getOneD().getCommand()).stream().filter(m -> makePredicate(m,strings)).collect(Collectors.toList());
		List<Command> twoDCommands = joinList(rserverObject.getList().getTwoD().getCommand(), gnuserverObject.getList().getTwoD().getCommand()).stream().filter(m -> makePredicate(m,strings)).collect(Collectors.toList());
		List<Command> threeDCommands = joinList(rserverObject.getList().getThreeD().getCommand(), gnuserverObject.getList().getThreeD().getCommand()).stream().filter(m -> makePredicate(m,strings)).collect(Collectors.toList());
		return Stream.of(oneDCommands,twoDCommands,threeDCommands).flatMap(x -> x.stream()).collect(Collectors.toList());
	}

	private boolean makePredicate(Command m,List<String> strings) {
		return strings.contains(m.getDescription());
	}
	
	
}
