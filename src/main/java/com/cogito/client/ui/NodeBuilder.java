package com.cogito.client.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodeBuilder {
	
	private VBox node = new VBox();

	public void injectLabelOnly(String labelDescription){
		node.getChildren().add(new Label(labelDescription));
	}
	
	public void injectLabelAndDescriptiveTextField(String labelDescription,String id){
		HBox hbox = new HBox();
		hbox.setId(id);
		hbox.getChildren().add(new Label(labelDescription));
		hbox.getChildren().add(new TextField());
		node.getChildren().add(hbox);
	}
	
	public Node getNode(){
		return node;
	}
}
