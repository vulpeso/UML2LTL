package pl.edu.agh.umldiagrams.config;

public class NodeConfig implements Comparable<NodeConfig> {

	public enum NodeType {
		SEQ, SEQSEQ, PAR, DEC, LOOP, MERG, EXC, SYNCH
	}
	private final String name;
	private final int priority;
	private final NodeType nodeType;
	
	public NodeConfig(final String name, final int priority, final NodeType nodeType) {
		this.name = name;
		this.priority = priority;
		this.nodeType = nodeType;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priority;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	@Override
	public int compareTo(NodeConfig o) {
		return priority - o.priority;
	}

}
