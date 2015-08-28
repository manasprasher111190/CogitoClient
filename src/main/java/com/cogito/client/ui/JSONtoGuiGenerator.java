package com.cogito.client.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;

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
		onTestClick(nodebuilder, commandList);
	}
	
	private void onSubmitClick(NodeBuilder nodeBuilder,
			List<Command> commandList) {
		nodeBuilder.getSubmitButton().setOnAction(new EventHandler<ActionEvent>() {

			

			@Override
			public void handle(ActionEvent event) {
				submitData(nodeBuilder, commandList,true);
			}

			
		});
	}
	
	private void onTestClick(NodeBuilder nodeBuilder,List<Command> commandList) {
		nodeBuilder.getTestButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				submitData(nodeBuilder, commandList, false);
			}
		});
	}
	
	private String cleanUpCommas(String string) {
        return join.join(splitter.split(string));
    }
	
	private void submitData(NodeBuilder nodeBuilder,
			List<Command> commandList, boolean isSubmit) {
		List<String> rList = new ArrayList<>();;
		List<String> gnuList = new ArrayList<>();
		for (int i = 0; i < commandList.size(); i++) {
			GUIValueHolder holder = nodeBuilder.getChildrenValues(String.valueOf(i));
			List<String> listofValues = holder.getListofValues();
			String format34 = String.format(commandList.get(i).getTemplate(), listofValues.toArray());
			if(commandList.get(i).getService().equals("R")){
				rList.add(format34);	
			}else if(commandList.get(i).getService().equals("GNU")){
				gnuList.add(format34);
			}
			
			List<String> alternatives = holder.getAlternatives();
			String replace = alternatives.toString().replace("[", "").replace("]", "");
			String[] split = replace.split("&");
			for(int j =0 ; j< split.length; j++ ){
				 if(commandList.get(i).getService().equals("R")){
					 String template = commandList.get(i).getAlternative().getCommand().get(j).getTemplate();
					String[] split2 = cleanUpCommas(split[j]).split(",");
					String format = String.format(template, split2);
					rList.add(format); 
				 }else if(commandList.get(i).equals("GNU")) {
					 String template = commandList.get(i).getAlternative().getCommand().get(j).getTemplate();
						String[] split2 = cleanUpCommas(split[j]).split(",");
						String format = String.format(template, split2);
						gnuList.add(format);
				 }
				
			}
		 
		}
		if(isSubmit){
			CogitoClientUIGridHelper.showCogitoGrid();			
		}
		for (String t : rList) {
			if(isSubmit){
				new CogitoClientUI(t.replace(" ", ""),
						UrlUtilities.RSERVICE_URL + "/data?template=");	
			}else {
				String testQueryResult = testQuery(t.replace(" ", ""), UrlUtilities.TEST_R_SERVICES);
				if(testQueryResult.equals("NONE")) {
					nodeBuilder.getTextArea().setText("TEST SUCCESSFULL");
				}
				else {
					nodeBuilder.getTextArea().setText(testQueryResult);
				}
			}
		}
		for(String t : gnuList) {
			if(isSubmit){
				new CogitoClientUI(t, UrlUtilities.GNUSERVICE_URL);	
			}else {
				//DO NOTHING FOR NOW
			}
			
		}
	}
	
	private String testQuery(String template, String serviceUrl) {
		try {
			URL url = new URL(serviceUrl+template.replaceAll(" ", ""));
			System.out.println(url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			try (InputStream inputStream = conn.getInputStream()) {
				String source = CharStreams.toString( new InputStreamReader( inputStream, "UTF-8" ) );
				return source;
			}

		} catch (MalformedURLException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
}

