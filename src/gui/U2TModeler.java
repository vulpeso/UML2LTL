package gui;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class U2TModeler extends mxGraphComponent {
	
	private static final long serialVersionUID = -3423258904388587768L;
	JFrame parent;
	
	public U2TModeler(JFrame parent){
		super(initGraph());
		this.parent = parent;
	}
	
	private static mxGraph initGraph(){
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 80, 80, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		return graph;
	}
}
