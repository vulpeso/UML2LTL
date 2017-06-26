package pl.edu.agh.umldiagrams.controllers;

import java.util.ArrayList;

import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode.NodeType;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;

public class ParsedDiagramController {

	private ArrayList<ModelDiagramNode> models = new ArrayList<ModelDiagramNode>();
	private ArrayList<ActivityDiagram> diagrams = new ArrayList<ActivityDiagram>();
	
	
	public void addModelNode(final ModelDiagramNode node) {
		models.add(node);
	}
	
	public void addActivityDiagram(final ActivityDiagram node) {
		diagrams.add(node);
	}
	
	
	public void clear() {
		models.clear();
		diagrams.clear();
	}
	
	
	public ModelDiagramNode getNodeById(final String id) {
		for(ModelDiagramNode node: models) {
			if(node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}
	
	public void findRelationships() {
		for(ModelDiagramNode modelDiagramNode1: models) {
			for(String id1: modelDiagramNode1.getOutRelationships()) {
				for(ModelDiagramNode modelDiagramNode2: models) {
					for(String id2: modelDiagramNode2.getInRelationships()) {
						if(id1.equals(id2)) {
							modelDiagramNode1.addOutRelationshipNodes(modelDiagramNode2);
							if(modelDiagramNode1.getNodeType() == NodeType.DECISION_NODE) {
								if(modelDiagramNode1.getOutTrueRelationship()!= null && modelDiagramNode1.getOutTrueRelationship().equals(id1)) {
									modelDiagramNode1.setOutTrueRelationshipNodeId(modelDiagramNode2.getId());
								}
							}
							modelDiagramNode2.addInRelationshipNodes(modelDiagramNode1);
						}
					}
				}
			}
		}
	}
	
	public void print() {
		for(ActivityDiagram activityDiagram: diagrams) {
			System.out.println("ActivityDiagram: " + activityDiagram.getName());
			for(ModelDiagramNode modelDiagramNode: activityDiagram.getModelDiagramNodesList()) {
				System.out.println("Node: " + modelDiagramNode.getName() + " id=" + modelDiagramNode.getId());
				System.out.println("\tOut relationships: ");
				for(ModelDiagramNode modelRelationship: modelDiagramNode.getOutRelationshipNodes()) {
					System.out.println("\t\t"+modelRelationship.getName() + " ("+modelRelationship.getId()+ ") ");
				}
				System.out.println("\tIn relationships:");
				for(ModelDiagramNode modelRelationship: modelDiagramNode.getInRelationshipNodes()) {
					System.out.println("\t\t"+modelRelationship.getName() + " ("+modelRelationship.getId()+ ") ");
				}
			}
		}
	}

	public ArrayList<ActivityDiagram> getDiagrams() {
		return diagrams;
	}
		
}
