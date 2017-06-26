package pl.edu.agh.umldiagrams.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import pl.edu.agh.umldiagrams.config.NodeConfig.NodeType;
import pl.edu.agh.umldiagrams.controllers.ParsedDiagramController;
import pl.edu.agh.umldiagrams.exceptions.ConfigException;

public class Config {

	private NodeConfig seqConfig;
	private NodeConfig seqSeqConfig;
	private NodeConfig loopConfig;
	private NodeConfig decConfig;
	private NodeConfig parConfig;
	private NodeConfig mergConfig;
	private NodeConfig excConfig;
	private NodeConfig synchConfig;
	
	private TreeSet<NodeConfig> configPriorities = new TreeSet<NodeConfig>();
	private List<String> trueArrowNames = new ArrayList<String>();
	
	public Config(final String configFilePath) throws ConfigException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(configFilePath);
			
			Document document = (Document) builder.build(xmlFile);
			// node description:
			Element rootNode = document.getRootElement();
			if(rootNode.getName() != "Config") {
				throw new ConfigException("Config exception: root node should be named \"Config\".");
			}
			
			
			List nodesChildren = ((Element)(rootNode.getChildren().get(0))).getChildren();
			
			for (int i = 0; i < nodesChildren.size(); i++) {
				Element child = (Element) nodesChildren.get(i);
				if(child.getName().equals("Seq")) {
					seqConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.SEQ);
				} else if(child.getName().equals("SeqSeq")) {
					seqSeqConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.SEQSEQ);
				} else if(child.getName().equals("Par")) {
					parConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.PAR);
				} else if(child.getName().equals("Dec")) {
					decConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.DEC);
				} else if(child.getName().equals("Loop")) {
					loopConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.LOOP);
				} else if(child.getName().equals("Synch")) {
					synchConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.SYNCH);
				} else if(child.getName().equals("Exc")) {
					excConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.EXC);
				} else if(child.getName().equals("Merg")) {
					mergConfig = new NodeConfig(child.getAttributeValue("name"), Integer.valueOf(child.getAttributeValue("priority")), NodeType.MERG);
				}
			}
			
			List namesChildren = ((Element)(rootNode.getChildren().get(1))).getChildren();
			
			for (int i = 0; i < namesChildren.size(); i++) {
				Element child = (Element) namesChildren.get(i);
				if(child.getName().equals("DecisionName")) {
					trueArrowNames.add(child.getAttributeValue("name"));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ConfigException("No config file found in location: "+configFilePath);
		}
		
		if(seqConfig == null) {
			seqConfig = new NodeConfig("seq", 4, NodeType.SEQ);
		}
		if(seqSeqConfig == null) {
			seqSeqConfig = new NodeConfig("seqseq", 3, NodeType.SEQSEQ);
		}
		if(loopConfig == null) {
			loopConfig = new NodeConfig("loop", 1, NodeType.LOOP);
		}
		if(parConfig == null) {
			parConfig = new NodeConfig("par", 2, NodeType.PAR);
		}
		if(decConfig == null) {
			decConfig = new NodeConfig("dec", 2, NodeType.LOOP);
		}
		if(excConfig == null) {
			excConfig = new NodeConfig("exc", 2, NodeType.EXC);
		}
		if(synchConfig == null) {
			synchConfig = new NodeConfig("synch", 2, NodeType.SYNCH);
		}
		if(mergConfig == null) {
			mergConfig = new NodeConfig("merg", 2, NodeType.MERG);
		}
		
		configPriorities.add(seqConfig);
		configPriorities.add(seqSeqConfig);
		configPriorities.add(parConfig);
		configPriorities.add(decConfig);
		configPriorities.add(loopConfig);
		configPriorities.add(excConfig);
		configPriorities.add(mergConfig);
		configPriorities.add(synchConfig);
		
	}

	public TreeSet<NodeConfig> getConfigPriorities() {
		return configPriorities;
	}
	
	public NodeConfig getSeqConfig() {
		return seqConfig;
	}

	public NodeConfig getSeqSeqConfig() {
		return seqSeqConfig;
	}

	public NodeConfig getLoopConfig() {
		return loopConfig;
	}

	public NodeConfig getDecConfig() {
		return decConfig;
	}

	public NodeConfig getParConfig() {
		return parConfig;
	}

	public NodeConfig getMergConfig() {
		return mergConfig;
	}

	public NodeConfig getExcConfig() {
		return excConfig;
	}

	public NodeConfig getSynchConfig() {
		return synchConfig;
	}

	public List<String> getTrueArrowNames() {
		return trueArrowNames;
	}
	
}
