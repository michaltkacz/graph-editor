import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

enum EdgeType {
	BASIC_EDGE("KrawêdŸ zwyk³a"),
	SPECIAL_EDGE("KrawêdŸ specjalna");
	
	String edgeType;
	
	EdgeType(String edgeType) {
		this.edgeType = edgeType;
	}
	
	@Override
	public String toString() {
		return edgeType;
	}
}

public class Edge implements Serializable{

	private static final long serialVersionUID = -6972652167790425200L;

	protected Node nodeA;
	protected Node nodeB;
	
	protected EdgeType edgeType;

	public Edge(Node a, Node b) {
		nodeA = a;
		nodeB = b;
		edgeType = EdgeType.BASIC_EDGE;
	}
	
	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}
	
	public void draw(Graphics g) {
		int xa = nodeA.getX();
		int ya = nodeA.getY();
		int xb = nodeB.getX();
		int yb = nodeB.getY();
		
		g.setColor(Color.BLACK);
		g.drawLine(xa, ya, xb, yb);
	}
	
	public boolean isUnderCursor(int mx, int my) {
		
		if( mx < Math.min(nodeA.getX(), nodeB.getX()) ||
			mx > Math.max(nodeA.getX(), nodeB.getX()) ||
			my < Math.min(nodeA.getY(), nodeB.getY()) ||
			my > Math.max(nodeA.getY(), nodeB.getY()) ) {
			return false;
		}
		
		
		int A = nodeB.getY() - nodeA.getY();
		int B = nodeB.getX() - nodeA.getX();
		
		double distance = Math.abs(A*mx - B*my + nodeB.getX()*nodeA.getY() - nodeB.getY()*nodeA.getX())/Math.sqrt(A*A+B*B);
		return distance <= 5;
	}
	
	public void move(int dx, int dy) {
		nodeA.move(dx, dy);
		nodeB.move(dx, dy);
	}
	
	@Override
	public String toString() {
		return "[" +edgeType.toString() + "]: (" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
	}
}