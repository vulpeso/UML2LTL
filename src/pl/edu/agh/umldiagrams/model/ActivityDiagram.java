package pl.edu.agh.umldiagrams.model;

import java.util.ArrayList;
import java.util.List;

public class ActivityDiagram {
	private List<ModelDiagramNode> modelDiagramNodesList = new ArrayList<ModelDiagramNode>();
	private String name;
	
	public void addDiagramNode(final ModelDiagramNode modelDiagramNode) {
		modelDiagramNodesList.add(modelDiagramNode);
	}

	public List<ModelDiagramNode> getModelDiagramNodesList() {
		return modelDiagramNodesList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public void print(){
    	System.out.println("#### Activity Diagram Current Nodes Print ####");
    	 for (ModelDiagramNode node : modelDiagramNodesList) {
    		 System.out.println("\t"+node.getName() + "  id: "+node.getId());
         }
    }
}
