package com.cogito.client.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NodeBuilder {
	
	private VBox node = new VBox();
    private Button submit = new Button("Submit");
    
	public void injectRmoduleLabelOnly(String labelDescription) {
		node.getChildren().add(new Label(labelDescription));
	}
	
	public void injectRmoduleCommandNode(Command command, String id){
		String[] parameters = parseParameters(command.getParams());
		VBox vbox = new VBox();
		vbox.setId(id);
		for(String param:parameters){
			vbox.getChildren().add(injectLabelAndDescriptiveTextField(param));
		}
		List<Command> alternativesList = command.getAlternative().getCommand();
		for(Command alternatives:alternativesList){
			vbox.getChildren().add(injectAlternativeNode(alternatives));
		}
		node.getChildren().add(vbox);
	}
	
	private  String[] parseParameters(String textContent) {
		return textContent.split(",");
	}
	
	private Node injectAlternativeNode(Command command){
		TitledPane alternativesPane = new TitledPane();
		alternativesPane.setText(command.getDescription());
		String[] parameters = parseParameters(command.getParams());
		VBox vbox = new VBox();
		for(String param: parameters){
			vbox.getChildren().add(injectLabelAndDescriptiveTextField(param));
		}
		alternativesPane.setContent(vbox);
		return alternativesPane;
	}
	
	private Node injectLabelAndDescriptiveTextField(String labelDescription){
		HBox hbox = new HBox();
		hbox.getChildren().add(new Label(labelDescription));
		hbox.getChildren().add(new TextField());
		return hbox;
	}
	
	public void showPanel(){
	    node.getChildren().add(submit);
		Pane pane = new Pane(node);
		ScrollPane scrollPane = new ScrollPane(pane);
		Scene scene = new Scene(scrollPane,800,800);
		Stage stage = new Stage();
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.show();
	}
	
	public Node getNode(){
		return node;
	}
	
	public Button getButton(){
		return submit;
	}
	
	public  GUIValueHolder getChildrenValues(String id){
		VBox vbox = (VBox) node.lookup("#"+id);
		ArrayList<String> listofValues = new ArrayList<>();
		ArrayList<String> listofAlternatives = new ArrayList<>();
		GUIValueHolder holder = new GUIValueHolder();
		ObservableList<Node> childrenUnmodifiable = vbox.getChildrenUnmodifiable();
		for(Node node:childrenUnmodifiable){
			if(node instanceof HBox){
				ObservableList<Node> childrenUnmodifiable2 = ((HBox) node).getChildrenUnmodifiable();
				for(Node node2: childrenUnmodifiable2){
					if(node2 instanceof TextField){
						listofValues.add(((TextField) node2).getText());
					}		
				}
			}
			if(node instanceof TitledPane){
				Node childerUnmodifiable3 = ((TitledPane) node).contentProperty().get();
					if(childerUnmodifiable3 instanceof VBox ){
						ObservableList<Node> childrenUnmodifiable4 = ((VBox) childerUnmodifiable3).getChildren();
						for(Node node3: childrenUnmodifiable4){
							if(node3 instanceof HBox){
								ObservableList<Node> childrenUnmodifiable5 = ((HBox) node3).getChildrenUnmodifiable();
								for(Node node4: childrenUnmodifiable5){
									if(node4 instanceof TextField){
										listofAlternatives.add(((TextField) node4).getText());
									}
								}
							}
						}
						listofAlternatives.add("&");
					}		
			}
		}
		holder.setListofValues(listofValues);
		holder.setAlternatives(listofAlternatives);
		return holder;
	}
	
}
