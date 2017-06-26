package pl.edu.agh.umldiagrams.patterns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.edu.agh.settings.Settings;
import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.config.NodeConfig;
import pl.edu.agh.umldiagrams.controllers.ParsedDiagramController;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode.NodeType;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart.PatternType;

public class PatternsFinder {

    static int i = 0;
    private ArrayList<PatternPart> patterns = new ArrayList<PatternPart>();
    
	public PatternPart getMainPattern() {
		if(patterns.size()==1)
			return patterns.get(0);
		else
			return null;
	}
    
	PatternPart getArgumentPattern(ModelDiagramNode node){
		PatternPart part = null;
		for(PatternPart p:patterns){
			if(p!=null &&
					p.getId()!=null &&
					p.getId().equals(node.getId())){
				part = p;
				break;
			}
		}
		if(part == null){
			part = new PatternPart(PatternType.ARG, node.getId());
			part.setName(node.getName());
			patterns.add(part);
		}
		return part;
	}
	
	void makePatternPart(PatternType type, String id, PatternPart arg1,PatternPart arg2,PatternPart arg3){
		PatternPart newPart = new PatternPart(type, id);
		newPart.getArguments().add(0, arg1);
		newPart.getArguments().add(1, arg2);
		if(arg3!=null) newPart.getArguments().add(2, arg3);
		switch(type){
		case ARG:
			break;
		case DEC:
			newPart.setName("DEC");
			break;
		case LOOP:
			newPart.setName("LOOP");
			break;
		case PAR:
			newPart.setName("PART");
			break;
		case SEQ:
			newPart.setName("SEQ");
			break;
		case SEQSEQ:
			newPart.setName("SSEQ");
			break;
		}
		patterns.remove(arg1);
		patterns.remove(arg2);
		patterns.remove(arg3);
		patterns.add(newPart);
	}
	
    public ModelDiagramNode convertActivityDiagramToComposite(
            ActivityDiagram activityDiagram, final Config config) throws ParseException {

        ModelDiagramComposite modelDiagramComposite = null;

		

		Iterator<NodeConfig> iterator = config.getConfigPriorities()
				.iterator();

		while (iterator.hasNext()) {
			NodeConfig nodeConfig = iterator.next();
			for (ModelDiagramNode node : activityDiagram.getModelDiagramNodesList()) {
				if (nodeConfig.getNodeType() == pl.edu.agh.umldiagrams.config.NodeConfig.NodeType.LOOP) {
					modelDiagramComposite = findLoop(node);
					if (modelDiagramComposite != null) {
						ActivityDiagram newActivityDiagram = makeNewActivityDiagram(
								activityDiagram, modelDiagramComposite);
						return convertActivityDiagramToComposite(
								newActivityDiagram, config);
					}
					
				} else if (nodeConfig.getNodeType() == pl.edu.agh.umldiagrams.config.NodeConfig.NodeType.PAR) {
					modelDiagramComposite = findPar(node);
					if (modelDiagramComposite != null) {
						ActivityDiagram newActivityDiagram = makeNewActivityDiagram(
								activityDiagram, modelDiagramComposite);
						return convertActivityDiagramToComposite(
								newActivityDiagram, config);
					}
				} else if (nodeConfig.getNodeType() == pl.edu.agh.umldiagrams.config.NodeConfig.NodeType.DEC) {
					modelDiagramComposite = findDec(node);
					if (modelDiagramComposite != null) {
						ActivityDiagram newActivityDiagram = makeNewActivityDiagram(
								activityDiagram, modelDiagramComposite);
						return convertActivityDiagramToComposite(
								newActivityDiagram, config);
					}
				} else if (nodeConfig.getNodeType() == pl.edu.agh.umldiagrams.config.NodeConfig.NodeType.SEQ) {
					modelDiagramComposite = findSequence(node);
					if (modelDiagramComposite != null) {
						ActivityDiagram newActivityDiagram = makeNewActivityDiagram(
								activityDiagram, modelDiagramComposite);
						return convertActivityDiagramToComposite(
								newActivityDiagram, config);
					}
				} else if (nodeConfig.getNodeType() == pl.edu.agh.umldiagrams.config.NodeConfig.NodeType.SEQSEQ) {
					modelDiagramComposite = findDoubleSequence(node);
					if (modelDiagramComposite != null) {
						ActivityDiagram newActivityDiagram = makeNewActivityDiagram(
								activityDiagram, modelDiagramComposite);
						return convertActivityDiagramToComposite(
								newActivityDiagram, config);
					}
				}
			}
		}

		return activityDiagram.getModelDiagramNodesList().size() == 1 ? activityDiagram
				.getModelDiagramNodesList().get(0) : null;

    }

    private ActivityDiagram makeNewActivityDiagram(ActivityDiagram activityDiagram, ModelDiagramComposite modelDiagramComposite) {
        ActivityDiagram newActivityDiagram = new ActivityDiagram();
        newActivityDiagram.setName(activityDiagram.getName());
        for (ModelDiagramNode child : activityDiagram.getModelDiagramNodesList()) {
            if (!modelDiagramComposite.haveNode(child.getId())) {
                newActivityDiagram.addDiagramNode(child);
            }
        }
        newActivityDiagram.addDiagramNode(modelDiagramComposite.getNode());
//        newActivityDiagram.print();
        return newActivityDiagram;
    }

    ModelDiagramComposite findSequence(final ModelDiagramNode node) {

        if (node.isActivityType() && node.outRelationshipNodesSize() == 1) {
            ModelDiagramNode nextNode = node.getOutRelationshipNodes().get(0);
            if (nextNode.isActivityType()
                    && nextNode.inRelationshipNodesSize() == 1) {
                ModelDiagramNode newNode = new ModelDiagramNode(
                        NodeType.ACTIVITY_ACTION);
                newNode.setName("seq(" + node.getName() + ","
                        + nextNode.getName() + ")");
                if(Settings.getInstance().getPrintDetails()){
                	System.out.println("Found sequence: "+newNode.getName());}
                
                newNode.setOutRelationships(nextNode.getOutRelationships());
                newNode.setOutRelationshipNodes(nextNode
                        .getOutRelationshipNodes());
                newNode.setInRelationships(node.getInRelationships());
                newNode.setInRelationshipNodes(node.getInRelationshipNodes());
                newNode.genId();
                PatternPart arg1 =  getArgumentPattern(node);
                PatternPart arg2 =  getArgumentPattern(nextNode);
                makePatternPart(PatternType.SEQ,newNode.getId(),arg1,arg2,null);
                
                ModelDiagramComposite modelDiagramComposite = new ModelDiagramComposite();
                modelDiagramComposite.setNode(newNode);
                modelDiagramComposite.addNodeInto(node);
                modelDiagramComposite.addNodeInto(nextNode);
            
                for (ModelDiagramNode n : nextNode.getOutRelationshipNodes()) {
                    n.getInRelationshipNodes().remove(nextNode);
                    n.addInRelationshipNodes(newNode);
                }

                for (ModelDiagramNode n : node.getInRelationshipNodes()) {
                	List list = n.getOutRelationshipNodes();
                	for( Iterator< ModelDiagramNode > it = list.iterator(); it.hasNext() ; ){
                		ModelDiagramNode m = it.next();
                        if (modelDiagramComposite.haveNode(m.getId())) {
                        	if(n.getNodeType() == NodeType.DECISION_NODE && n.getOutTrueRelationshipNodeId().equals(m.getId())) {
                        		n.setOutTrueRelationshipNodeId(newNode.getId());
                        	}
                        	it.remove();
                        }
                    }
                    n.addOutRelationshipNodes(newNode);
                }
                return modelDiagramComposite;
            }
        }
        return null;
    }
    
    ModelDiagramComposite findDoubleSequence(final ModelDiagramNode node) {

        if (node.isActivityType() && node.outRelationshipNodesSize() == 1) {
            ModelDiagramNode nextNode = node.getOutRelationshipNodes().get(0);
            if (nextNode.isActivityType() 
            		&& nextNode.inRelationshipNodesSize() == 1
            		&& nextNode.outRelationshipNodesSize() == 1) {
            	ModelDiagramNode nextNextNode = nextNode.getOutRelationshipNodes().get(0);
            	if (nextNextNode.isActivityType() 
                		&& nextNextNode.inRelationshipNodesSize() == 1) {
	                ModelDiagramNode newNode = new ModelDiagramNode(
	                        NodeType.ACTIVITY_ACTION);
	                newNode.setName("seqseq(" + node.getName() + ","
	                        + nextNode.getName() +","+ nextNextNode.getName() + ")");
	                if(Settings.getInstance().getPrintDetails()){
	                	System.out.println("Found sequence: "+newNode.getName());}
	               
	                newNode.setOutRelationships(nextNextNode.getOutRelationships());
	                newNode.setOutRelationshipNodes(nextNextNode
	                        .getOutRelationshipNodes());
	                newNode.setInRelationships(node.getInRelationships());
	                newNode.setInRelationshipNodes(node.getInRelationshipNodes());
	                newNode.genId();
	                PatternPart arg1 =  getArgumentPattern(node);
	                PatternPart arg2 =  getArgumentPattern(nextNode);
	                PatternPart arg3 =  getArgumentPattern(nextNextNode);
	                makePatternPart(PatternType.SEQSEQ,newNode.getId(),arg1,arg2,arg3);
	                
	                ModelDiagramComposite modelDiagramComposite = new ModelDiagramComposite();
	                modelDiagramComposite.setNode(newNode);
	                modelDiagramComposite.addNodeInto(node);
	                modelDiagramComposite.addNodeInto(nextNode);
	                modelDiagramComposite.addNodeInto(nextNextNode);
	                
	                for (ModelDiagramNode n : nextNextNode.getOutRelationshipNodes()) {
	                    n.getInRelationshipNodes().remove(nextNextNode);
	                    n.addInRelationshipNodes(newNode);
	                }
	
	                for (ModelDiagramNode n : node.getInRelationshipNodes()) {
	                	List list = n.getOutRelationshipNodes();
	                	for( Iterator< ModelDiagramNode > it = list.iterator(); it.hasNext() ; ){
	                		ModelDiagramNode m = it.next();
	                        if (modelDiagramComposite.haveNode(m.getId())) {
	                        	if(n.getNodeType() == NodeType.DECISION_NODE && n.getOutTrueRelationshipNodeId().equals(m.getId())) {
	                        		n.setOutTrueRelationshipNodeId(newNode.getId());
	                        	}
	                        	it.remove();
	                        }
	                    }
	                    n.addOutRelationshipNodes(newNode);
	                }
	                return modelDiagramComposite;
            	}
            }
        }
        return null;
    }
    
    ModelDiagramComposite findPar(final ModelDiagramNode node) {

        if (node.isActivityType() && node.outRelationshipNodesSize() == 1) {

            ModelDiagramNode forkNode = node.getOutRelationshipNodes().get(0);

            if (forkNode.getNodeType() == NodeType.FORK_NODE
                    && forkNode.outRelationshipNodesSize() == 2) {

                ModelDiagramNode parNode1 = forkNode.getOutRelationshipNodes()
                        .get(0);
                ModelDiagramNode parNode2 = forkNode.getOutRelationshipNodes()
                        .get(1);

                if (parNode1.isActivityType() && parNode2.isActivityType()
                        && parNode1.outRelationshipNodesSize() == 1
                        && parNode2.outRelationshipNodesSize() == 1
                        && parNode1.inRelationshipNodesSize() == 1
                        && parNode2.inRelationshipNodesSize() == 1) {

                    ModelDiagramNode parNode1Join = parNode1
                            .getOutRelationshipNodes().get(0);
                    ModelDiagramNode parNode2Join = parNode2
                            .getOutRelationshipNodes().get(0);

                    if (parNode1Join.getId().equals(parNode2Join.getId())
                            && parNode1Join.getNodeType() == NodeType.JOIN_NODE) {

                        ModelDiagramNode newNode = new ModelDiagramNode(
                                NodeType.ACTIVITY_ACTION);

                        newNode.setName("par(" + node.getName() + ","
                                + parNode1.getName() + "," + parNode2.getName()
                                + ")");
                        if(Settings.getInstance().getPrintDetails()){
                        		System.out.println("Found par: "+newNode.getName());}
                        
                        newNode.setOutRelationships(parNode1Join
                                .getOutRelationships());
                        newNode.setOutRelationshipNodes(parNode1Join
                                .getOutRelationshipNodes());
                        newNode.setInRelationships(node.getInRelationships());
                        newNode.setInRelationshipNodes(node
                                .getInRelationshipNodes());
                        newNode.genId();
                        PatternPart arg1 =  getArgumentPattern(node);
    	                PatternPart arg2 =  getArgumentPattern(parNode1);
    	                PatternPart arg3 =  getArgumentPattern(parNode2);
    	                makePatternPart(PatternType.PAR,newNode.getId(),arg1,arg2,arg3);	
    	                
                        ModelDiagramComposite modelDiagramComposite = new ModelDiagramComposite();
                        modelDiagramComposite.setNode(newNode);
                        modelDiagramComposite.addNodeInto(node);
                        modelDiagramComposite.addNodeInto(forkNode);
                        modelDiagramComposite.addNodeInto(parNode1);
                        modelDiagramComposite.addNodeInto(parNode2);
                        modelDiagramComposite.addNodeInto(parNode1Join);

                        for (ModelDiagramNode n : parNode1Join
                                .getOutRelationshipNodes()) {
                            n.getInRelationshipNodes().remove(parNode1Join);
                            n.addInRelationshipNodes(newNode);
                        }
                        for (ModelDiagramNode n : node.getInRelationshipNodes()) {
                            if(n.getNodeType() == NodeType.DECISION_NODE && n.getOutTrueRelationshipNodeId().equals(node.getId())) {
                        		n.setOutTrueRelationshipNodeId(newNode.getId());
                        	}

                            n.getOutRelationshipNodes().remove(node);
                            n.addOutRelationshipNodes(newNode);
                        }

                        return modelDiagramComposite;
                    }
                }
            }
        }
        return null;
    }

    ModelDiagramComposite findLoop(final ModelDiagramNode node) {

        if (node.isActivityType() && node.outRelationshipNodesSize() == 1) {

            ModelDiagramNode decisionNode = node.getOutRelationshipNodes()
                    .get(0);
            if (decisionNode.getNodeType() == NodeType.DECISION_NODE
                    && decisionNode.outRelationshipNodesSize() == 2) {

                ModelDiagramNode loopNode1 = decisionNode
                        .getOutRelationshipNodes().get(0);
                ModelDiagramNode loopNode2 = decisionNode
                        .getOutRelationshipNodes().get(1);

                ModelDiagramNode loopNode = null;
                ModelDiagramNode outOfLoopNode = null;

                if (checkLoop(loopNode1, decisionNode)) {
                    loopNode = loopNode1;
                    outOfLoopNode = loopNode2;
                } else if (checkLoop(loopNode2, decisionNode)) {
                    loopNode = loopNode2;
                    outOfLoopNode = loopNode1;
                }
                if (loopNode != null && loopNode.getId().equals(node.getId())) {
                    loopNode = null;
                }
                if (loopNode != null) {

                    ModelDiagramNode newNode = new ModelDiagramNode(
                            NodeType.ACTIVITY_ACTION);

                    newNode.setName("loop(" + node.getName() + ","
                    	+ decisionNode.getName()+ "," + loopNode.getName() + ")");
                    if(Settings.getInstance().getPrintDetails()){
                    	System.out.println("Found loop: "+newNode.getName());}
                    
                    newNode.addOutRelationshipNodes(outOfLoopNode);
                    newNode.setInRelationships(node.getInRelationships());
                    newNode.setInRelationshipNodes(node
                            .getInRelationshipNodes());
                    newNode.genId();
                    PatternPart arg1 =  getArgumentPattern(node);
	                PatternPart arg2 =  getArgumentPattern(decisionNode);
	                PatternPart arg3 =  getArgumentPattern(loopNode);
	                makePatternPart(PatternType.LOOP,newNode.getId(),arg1,arg2,arg3);
	                
                    ModelDiagramComposite modelDiagramComposite = new ModelDiagramComposite();
                    modelDiagramComposite.setNode(newNode);
                    modelDiagramComposite.addNodeInto(node);
                    modelDiagramComposite.addNodeInto(decisionNode);
                    modelDiagramComposite.addNodeInto(loopNode);

                    outOfLoopNode.getInRelationshipNodes().remove(decisionNode);
                    outOfLoopNode.addInRelationshipNodes(newNode);
                    for (ModelDiagramNode n : node.getInRelationshipNodes()) {
                    	if(n.getNodeType() == NodeType.DECISION_NODE && n.getOutTrueRelationshipNodeId().equals(node.getId())) {
                    		n.setOutTrueRelationshipNodeId(newNode.getId());
                    	}
                        n.getOutRelationshipNodes().remove(node);
                        n.addOutRelationshipNodes(newNode);
                    }
                    return modelDiagramComposite;

                }
            }
        }
        return null;
    }

    private boolean checkLoop(ModelDiagramNode loopNode,
            ModelDiagramNode decisionNode) {
        return loopNode.isActivityType()
                && loopNode.outRelationshipNodesSize() == 1
                && loopNode.getOutRelationshipNodes().get(0).getId()
                        .equals(decisionNode.getId());
    }

    ModelDiagramComposite findDec(final ModelDiagramNode node){
    	if (node.getNodeType() == NodeType.DECISION_NODE
                    && node.outRelationshipNodesSize() == 2) {
    		
    		int indexOfTrueArrow = 0;
    		
            ModelDiagramNode decNode1 = node
                    .getOutRelationshipNodes().get(0);
            ModelDiagramNode decNode2 = node
                    .getOutRelationshipNodes().get(1);
            
            if(node.getOutTrueRelationshipNodeId().equals(decNode1.getId())) {
            	indexOfTrueArrow = 0;
            } else if(node.getOutTrueRelationshipNodeId().equals(decNode2.getId())) {
            	indexOfTrueArrow = 1;
            } else {
            	return null;
            }
            
            if (decNode1.isActivityType() && decNode2.isActivityType()
                    && decNode1.outRelationshipNodesSize() == 1
                    && decNode2.outRelationshipNodesSize() == 1
                    && decNode1.inRelationshipNodesSize() == 1
                    && decNode2.inRelationshipNodesSize() == 1) {

                ModelDiagramNode decNode1Join = decNode1
                        .getOutRelationshipNodes().get(0);
                ModelDiagramNode decNode2Join = decNode2
                        .getOutRelationshipNodes().get(0);
                ModelDiagramNode trueNode, falseNode;
                if (decNode1Join.getId().equals(decNode2Join.getId())) {

                    ModelDiagramNode newNode = new ModelDiagramNode(
                            NodeType.ACTIVITY_ACTION);
                    if(indexOfTrueArrow == 0) {
                    	trueNode = decNode1;
                    	falseNode = decNode2;
                 
                    } else {
                    	trueNode = decNode2;
                    	falseNode = decNode1;	
                    }
                    newNode.setName("dec(" + node.getName()+ ","
                            + trueNode.getName() + "," + falseNode.getName() + ")");
                    if(Settings.getInstance().getPrintDetails()){
                    	System.out.println("Found dec: "+newNode.getName());}
                    
                    newNode.setOutRelationships(decNode1
                            .getOutRelationships());
                    newNode.setOutRelationshipNodes(decNode1
                            .getOutRelationshipNodes());
                    newNode.setInRelationships(node.getInRelationships());
                    newNode.setInRelationshipNodes(node
                            .getInRelationshipNodes());
                    newNode.genId();
                    PatternPart arg1 =  getArgumentPattern(node);
	                PatternPart arg2 =  getArgumentPattern(trueNode);
	                PatternPart arg3 =  getArgumentPattern(falseNode);
	                makePatternPart(PatternType.DEC,newNode.getId(),arg1,arg2,arg3);
	                
                    ModelDiagramComposite modelDiagramComposite = new ModelDiagramComposite();
                    modelDiagramComposite.setNode(newNode);
                    modelDiagramComposite.addNodeInto(node);
                    modelDiagramComposite.addNodeInto(decNode1);
                    modelDiagramComposite.addNodeInto(decNode2);

                    for (ModelDiagramNode n : decNode1.getOutRelationshipNodes()) {
                    	List list = n.getInRelationshipNodes();
                    	for( Iterator< ModelDiagramNode > it = list.iterator(); it.hasNext() ; ){
                    		ModelDiagramNode m = it.next();
                            if (modelDiagramComposite.haveNode(m.getId())) {
                            	it.remove();
                            }
                        }
                        n.addInRelationshipNodes(newNode);
                    }
                    for (ModelDiagramNode n : decNode2.getOutRelationshipNodes()) {
                    	List list = n.getInRelationshipNodes();
                    	for( Iterator< ModelDiagramNode > it = list.iterator(); it.hasNext() ; ){
                    		ModelDiagramNode m = it.next();
                            if (modelDiagramComposite.haveNode(m.getId())) {
                            	it.remove();
                            }
                        }
                    }
                    for (ModelDiagramNode n : node.getInRelationshipNodes()) {
                    	if(n.getNodeType() == NodeType.DECISION_NODE && n.getOutTrueRelationshipNodeId().equals(node.getId())) {
                    		n.setOutTrueRelationshipNodeId(newNode.getId());
                    	}

                        n.getOutRelationshipNodes().remove(node);
                        n.addOutRelationshipNodes(newNode);
                    }

                    return modelDiagramComposite;
                }
            }
        }
        return null;
    }

    private class ModelDiagramComposite {
        ModelDiagramNode node;
        List<ModelDiagramNode> nodesInto = new ArrayList<ModelDiagramNode>();

        public void addNodeInto(final ModelDiagramNode modelDiagramNode) {
            nodesInto.add(modelDiagramNode);
        }

        public ModelDiagramNode getNode() {
            return node;
        }

        public void setNode(ModelDiagramNode node) {
            this.node = node;
        }

        public boolean haveNode(String nodeId) {
            for (ModelDiagramNode node : nodesInto) {
                if (node.getId().equals(nodeId)) {
                    return true;
                }
            }
            return false;
        }
        
        public void print(){
        	System.out.println("#### Model Diagram Composite Print ####");
        	 for (ModelDiagramNode node : nodesInto) {
        		 System.out.println("\t"+node.getName() + "  id: "+node.getId());
             }
        }
    }

}