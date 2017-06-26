package pl.edu.agh.umldiagrams.model;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.umldiagrams.exceptions.ParseException;

public class ModelDiagramNode {

	public enum NodeType {
		ACTIVITY, ACTIVITY_ACTION, ACTIVITY_FINAL_NODE, DECISION_NODE, INITIAL_NODE, FORK_NODE, JOIN_NODE
	}
	private static long idBase = 1;
	private List<String> outRelationships = new ArrayList<String>();
	private List<String> inRelationships = new ArrayList<String>();
	private List<ModelDiagramNode> outRelationshipNodes = new ArrayList<ModelDiagramNode>();
	private List<ModelDiagramNode> inRelationshipNodes = new ArrayList<ModelDiagramNode>();
	private String name;
	private String  id;
	private final NodeType nodeType;
	
	private String outTrueRelationship = null;
	private String outTrueRelationshipNodeId = null;
	
	public ModelDiagramNode(final NodeType nodeType) {
		this.nodeType = nodeType;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public void genId() {
		this.id = "" + (System.currentTimeMillis() + ((long)100000 * (long)100000000 * (++idBase)));
	}
	
	public void addOutRelationship(final String flow) {
		outRelationships.add(flow);
	}
	
	public void addOutRelationship(final String flow, final String flowName, final List<String> trueNames) throws ParseException {
		outRelationships.add(flow);
		
		if(isTrueArrow(trueNames, flowName)) {
			if(outTrueRelationship != null) {
				throw new ParseException("Decision node should have one true out arrow, find two: " + flow + ", " + outTrueRelationship);
			} else {
				outTrueRelationship = flow;
			}
		}
	}
	
	private boolean isTrueArrow(List<String> trueNames, String flowName) {

		for(String s: trueNames) {
			if(s.toUpperCase().equals(flowName.toUpperCase())) {
				return true;
			}
		}

		return false;
	}
	public int outRelationshipSize() {
		return outRelationships.size();
	}
	
	public void addInRelationship(final String flow) {
		inRelationships.add(flow);
	}
	
	public int inRelationshipSize() {
		return inRelationships.size();
	}
	public void addOutRelationshipNodes(ModelDiagramNode node) {
		outRelationshipNodes.add(node);
	}
	
	public int outRelationshipNodesSize() {
		return outRelationshipNodes.size();
	}
	
	public int inRelationshipNodesSize() {
		return inRelationshipNodes.size();
	}
	
	public int outRelationshipSizeNodes() {
		return outRelationshipNodes.size();
	}
	
	public void addInRelationshipNodes(final ModelDiagramNode node) {
		inRelationshipNodes.add(node);
	}
	
	public int inRelationshipSizeNodes() {
		return inRelationshipNodes.size();
	}
	public List<String> getOutRelationships() {
		return outRelationships;
	}
	public List<String> getInRelationships() {
		return inRelationships;
	}
	public List<ModelDiagramNode> getOutRelationshipNodes() {
		return outRelationshipNodes;
	}
	public List<ModelDiagramNode> getInRelationshipNodes() {
		return inRelationshipNodes;
	}
	public NodeType getNodeType() {
		return nodeType;
	}
	
	public boolean isActivityType() {
		if(nodeType == NodeType.ACTIVITY || nodeType == NodeType.ACTIVITY_ACTION || nodeType == NodeType.ACTIVITY_FINAL_NODE || nodeType == NodeType.INITIAL_NODE) {
			return true;
		}
		return false;
	}
	public void setOutRelationships(List<String> outRelationships) {
		this.outRelationships = outRelationships;
	}
	public void setInRelationships(List<String> inRelationships) {
		this.inRelationships = inRelationships;
	}
	public void setOutRelationshipNodes(
			List<ModelDiagramNode> outRelationshipNodes) {
		this.outRelationshipNodes = outRelationshipNodes;
	}
	public void setInRelationshipNodes(List<ModelDiagramNode> inRelationshipNodes) {
		this.inRelationshipNodes = inRelationshipNodes;
	}
	public String getOutTrueRelationship() {
		return outTrueRelationship;
	}
	public String getOutTrueRelationshipNodeId() {
		return outTrueRelationshipNodeId;
	}
	public void setOutTrueRelationshipNodeId(String outTrueRelationshipNodeId) {
		this.outTrueRelationshipNodeId = outTrueRelationshipNodeId;
	}
	
}
