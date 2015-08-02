package com.cogito.client.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.DocFlavor.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class JSONtoGuiGenerator {
	
	private final Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
	private final Joiner join = Joiner.on(',').skipNulls();
	
	public JSONtoGuiGenerator(List<Command> commandList) throws IOException {
//		byte[] rModulejsonData = Files.readAllBytes(Paths.get(this
//				.getClass().getClassLoader().getResource("config.txt")
//				.getPath()));
		
//		byte[] gnuModulejsonData = Files.readAllBytes(Paths.get(this.getClass()
//				.getClassLoader().getResource("gnumodule.txt").getPath()));
//		ObjectMapper objectMapper = new ObjectMapper();
//		RRoot rmodule = objectMapper.readValue(rModulejsonData, RRoot.class);
//		GnuRoot gnumodule = objectMapper.readValue(gnuModulejsonData, GnuRoot.class);
		
//		createGnuPanel(gnumodule.getGnumodule().getCommand());
		createRPanel(commandList);
	}
	
	
	private void createRPanel(List<Command> commandList) {
		NodeBuilder nodebuilder = new NodeBuilder();
		for(Command command:commandList){
			nodebuilder.injectRmoduleLabelOnly(command.getDescription());
     		nodebuilder.injectRmoduleCommandNode(command,command.getId());
		}
		
		nodebuilder.showPanel();
		onSubmitClick(nodebuilder,commandList);
	}
	
	private void createGnuPanel(List<Command> commandList){
		NodeBuilder nodeBuilder = new NodeBuilder();
		for(Command command:commandList){
			nodeBuilder.injectRmoduleLabelOnly(command.getDescription());
     		nodeBuilder.injectRmoduleCommandNode(command,command.getId());
		}
		nodeBuilder.showPanel();
	}
	
	
	
	private void onSubmitClick(NodeBuilder nodeBuilder,
			List<Command> commandList) {
		nodeBuilder.getButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				List<String> templates = new ArrayList<>();
				for (int i = 0; i < commandList.size(); i++) {
					GUIValueHolder holder = nodeBuilder.getChildrenValues(String.valueOf(i));
					List<String> listofValues = holder.getListofValues();
					templates.add(String.format(commandList.get(i).getTemplate(), listofValues.toArray()));
					List<String> alternatives = holder.getAlternatives();
					String replace = alternatives.toString().replace("[", "").replace("]", "");
					String[] split = replace.split("&");
					for(int j =0 ; j< split.length; j++ ){
						String template = commandList.get(i).getAlternative().getCommand().get(j).getTemplate();
						String[] split2 = cleanUpCommas(split[j]).split(",");
						String format = String.format(template, split2);
						System.out.println("FORMAT: "+format);
						templates.add(format);
					}
				 
				}
				CogitoClientUIGridHelper.showCogitoGrid();
				for (String t : templates) {
					new CogitoClientUI(t.replace(" ", ""),
							UrlUtilities.RSERVICE_URL + "/data?template=");
				}
			}
		});
	}
	
	private String cleanUpCommas(String string) {
        return join.join(splitter.split(string));
    }
	
}

