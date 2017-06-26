package pl.edu.agh.umldiagrams.parser;

import java.util.List;

import org.jdom2.Element;

import pl.edu.agh.umldiagrams.controllers.ParsedDiagramController;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;

public class DiagramsParser {
	
	private ParsedDiagramController parsedDiagramController;

	public DiagramsParser(ParsedDiagramController parsedDiagramController) {
		this.parsedDiagramController = parsedDiagramController;
	}

	public void parse(final Element node) throws ParseException {
		List children = node.getChildren();

		for (int i = 0; i < children.size(); i++) {
			Element child = (Element) children.get(i);
			if (child.getName().equals("ActivityDiagram")) {
				ActivityDiagram activityDiagram = new ActivityDiagram();
				activityDiagram.setName(child.getAttributeValue("Name"));
				List activityDiagramChildren = child.getChildren();
				for (int j = 0; j < activityDiagramChildren.size(); j++) {
					if (((Element)(activityDiagramChildren.get(j))).getName().equals("Shapes")) {
						activityDiagram = getShapes((Element)(activityDiagramChildren.get(j)), activityDiagram);
					}
				}
				parsedDiagramController.addActivityDiagram(activityDiagram);
			} else {
				continue;
			}
		}
	}
	
	public ActivityDiagram getShapes(final Element node, ActivityDiagram activityDiagram) {
		List children = node.getChildren();

		for (int i = 0; i < children.size(); i++) {
			Element child = (Element) children.get(i);
			final String id = child.getAttributeValue("Model");
			ModelDiagramNode diagramNode = parsedDiagramController.getNodeById(id);
			if(diagramNode != null) {
				activityDiagram.addDiagramNode(diagramNode);
			}
		}
		
		return activityDiagram;
	}
}
