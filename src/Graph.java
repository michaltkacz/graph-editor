import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*
 * 	Program: Edytor grafów.
 * 
 *    Pliki: GraphEditor.java, 
 *    		 GraphPanel.java
 *    		 Grid.java,
 *    		 Node.java,
 *    		 SpecialNode.java,
 *    		 Edge.java,
 *    		 SpecialEdge.java,
 *    		 Graph.java,
 *    
 *     Plik: Graph.java
 *	         definicja klasy Graph
 *
 *    Autor: Micha³ Tkacz 248869
 *	   Data: 22.11.2019r.
 *	Zajêcia: Pi¹tek TN 11:15
 */

class GraphException extends Exception{
	private static final long serialVersionUID = 5280421833743690760L;
	
	public GraphException(String message) {
		super(message);
	}
	
}

public class Graph implements Serializable{

	private static final long serialVersionUID = 5673009196816218789L;

	private String graphTitle;
	private List<Node> nodes;
	private List<Edge> edges;

	
	public Graph(String title) {
		setGraphTitle(title);
		setNodes(new ArrayList<Node>());
		setEdges(new ArrayList<Edge>());
		
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		if(graphTitle == null)
			graphTitle = "";
		else
			this.graphTitle = graphTitle;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public void draw(Graphics g) {
		for (Edge edge : getEdges()) {
			edge.draw(g);
		}
		
		for (Node node : getNodes()) {
			node.draw(g);
		}
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void addEdge(Edge e) {
		for (Edge edge : edges) {
			if(e.equals(edge))
				return;
		}
		edges.add(e);
	}

	public Node findNodeUnderCursor(int mx, int my) {
		for (Node node : nodes) {
			if(node.isUnderCursor(mx, my)) {
				return node;
			}
		}
		return null;
	}
	
	public Edge findEdgeUnderCursor(int mx, int my) {
		for (Edge edge : edges) {
			if(edge.isUnderCursor(mx, my)) {
				return edge;
			}
		}
		return null;
	}

	public void removeNode(Node nodeUnderCursor) {
		removeAttachedEdges(nodeUnderCursor);
		nodes.remove(nodeUnderCursor);		
	}
	
	protected void removeAttachedEdges(Node nodeUnderCursor) {
		edges.removeIf(e -> {
			return e.getNodeA().equals(nodeUnderCursor) 
				|| e.getNodeB().equals(nodeUnderCursor);
		});
	}
	
	public void removeEdge(Edge edgeUnderCursor) {
		edges.remove(edgeUnderCursor);
	}
	
	public void moveGraph(int dx, int dy) {
		for (Node node : nodes) {
			node.move(dx, dy);
		}
	}
	
	public static void serializeGraph(String fileName, Graph graph) throws GraphException {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
			out.writeObject(graph);
		}catch(IOException e) {
			throw new GraphException("B³¹d serializacji!");
		}
	}
	
	public static void serializeGraph(File file, Graph graph) throws GraphException {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
			out.writeObject(graph);
		}catch(IOException e) {
			throw new GraphException("B³¹d serializacji!");
		}
	}
	
	public static Graph deserializeGraph(String fileName) throws GraphException {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
			Graph graph = (Graph) in.readObject();
			return graph;
		}catch (FileNotFoundException e) {
			throw new GraphException("Nie odnaleziono pliku z grafem do wczytania!");
		}catch(IOException e) {
			throw new GraphException("B³¹d deserializacji! Problem z plikiem.");
		} catch (ClassNotFoundException e) {
			throw new GraphException("B³¹d deserializacji! Nie znaleziono klasy pasuj¹cej do wczytanego obiektu!");
		} 
	}
	
	public static Graph deserializeGraph(File file) throws GraphException {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
			Graph graph = (Graph) in.readObject();
			return graph;
		}catch(IOException e) {
			throw new GraphException("B³¹d deserializacji! Problem z plikiem.");
		} catch (ClassNotFoundException e) {
			throw new GraphException("B³¹d deserializacji! Nie znaleziono klasy pasuj¹cej do wczytanego obiektu!");
		}
	}

	public String getListOfNodes() {
		int index = 1;
		String list = "Liczba wêz³ów: " + Integer.toString(nodes.size()) + "\n";
		list += "N. [Typ wêz³a]: (pozycja) {parametry wêz³a}\n";
		for (Node node : nodes) {
			list += Integer.toString(index++);
			list += ". ";
			list += node.toString();
			list += "\n";
		}
		return list;
	}

	public String getListOfEdges() {
		int index = 1;
		String list = "Liczba krawêdzi: " + Integer.toString(edges.size()) + "\n";
		list += "N. [Typ krawêdzi]: (Wêze³ A) ===> (Wêze³ B) {parametry krawêdzi}\n";
		for (Edge edge : edges) {
			list += Integer.toString(index++);
			list += ". ";
			list += edge.toString();
			list += "\n";
		}
		return list;
	}
	
	@Override
	public String toString() {
		return graphTitle + "("+ nodes.size() + " wierzcho³ków, " + edges.size() + " krawêdzi)";
	}
	
}
