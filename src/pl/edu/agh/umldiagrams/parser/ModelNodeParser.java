package pl.edu.agh.umldiagrams.parser;

import java.util.List;

import org.jdom2.Element;

import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.controllers.ParsedDiagramController;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode.NodeType;

public class ModelNodeParser {
	
	private ParsedDiagramController parsedDiagramController;
	private Config config;

	public ModelNodeParser(ParsedDiagramController parsedDiagramController, Config config) {
		this.parsedDiagramController = parsedDiagramController;
		this.config = config;
	}

	public void parse(final Element node) throws ParseException {

		List children = node.getChildren();

		for (int i = 0; i < children.size(); i++) {
			NodeType nodeType;
			Element child = (Element) children.get(i);
			if (child.getName().equals("ActivityAction")) {
				nodeType = NodeType.ACTIVITY_ACTION;
			} else if (child.getName().equals("DecisionNode")) {
				nodeType = NodeType.DECISION_NODE;
			} else if (child.getName().equals("Activity")) {
				nodeType = NodeType.ACTIVITY;
			} else if (child.getName().equals("InitialNode")) {
				nodeType = NodeType.INITIAL_NODE;
			} else if (child.getName().equals("ActivityFinalNode")) {
				nodeType = NodeType.ACTIVITY_FINAL_NODE;
			} else if (child.getName().equals("ForkNode")) {
				nodeType = NodeType.FORK_NODE;
			} else if (child.getName().equals("JoinNode")) {
				nodeType = NodeType.JOIN_NODE;
			} else {
				continue;
			}
			getNode(child, nodeType);
		}
	}

	private void getNode(final Element child, final NodeType nodeType) throws ParseException {
		String id = child.getAttribute("Id").getValue();
		String name = child.getAttribute("Name").getValue();

		ModelDiagramNode node = new ModelDiagramNode(nodeType);
		node.setId(id);
		node.setName(name);

		List children = child.getChildren();

		for (int i = 0; i < children.size(); i++) {
			Element childNode = (Element) children.get(i);
			if (childNode.getName().equals("FromSimpleRelationships")) {
				List fromRelationships = childNode.getChildren();
				for (int j = 0; j < fromRelationships.size(); j++) {
					Element controlFlow = (Element) fromRelationships.get(j);
					if (controlFlow.getName() == "ControlFlow") {
						if(nodeType == NodeType.DECISION_NODE) {
							node.addOutRelationship(controlFlow
									.getAttributeValue("Idref"), controlFlow
									.getAttributeValue("Name"), config.getTrueArrowNames());
						} else {
							node.addOutRelationship(controlFlow
									.getAttributeValue("Idref"));
						}
					}
				}
			}
			if (childNode.getName().equals("ToSimpleRelationships")) {
				List toRelationships = childNode.getChildren();
				for (int j = 0; j < toRelationships.size(); j++) {
					Element controlFlow = (Element) toRelationships.get(j);
					if (controlFlow.getName() == "ControlFlow") {
						node.addInRelationship(controlFlow
								.getAttributeValue("Idref"));
					}
				}
			}
		}
		parsedDiagramController.addModelNode(node);
	}
}
