/*
 * GraphCreater project
 * Class GraphPanel draws all the nodes and edges,
 * manages the lists of edges and nodes and tracks connections
 * 
 * Created with help from Jason Galbraith's GraphCreater videos for Java class
 * Author: Grace Hunter
 * Date: 27 April 2018
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	//ArrayLists for Nodes, Edges, and storing which are connected.
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();
	final int RADIUS = 25; //radius of nodes
	
	public GraphPanel() {
		super();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draw any edges
		g.setColor(Color.BLACK);
		for(int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			g.drawLine(edge.getFirst().getX(), 
					edge.getFirst().getY(), 
					edge.getSecond().getX(), 
					edge.getSecond().getY());
			int midX = (edge.getFirst().getX() + edge.getSecond().getX()) / 2;
			int midY = (edge.getFirst().getY() + edge.getSecond().getY()) / 2;
			//add text
			g.drawString(edge.getLabel(), midX, midY);
		}
		//draw all nodes
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			//change fill color based on highlighting
			if(node.highlight) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.GRAY);
			}
			g.fillOval(node.getX() - RADIUS, node.getY() - RADIUS, RADIUS*2, RADIUS*2);
			g.setColor(Color.ORANGE);
			g.drawOval(node.getX() - RADIUS, node.getY() - RADIUS, RADIUS*2, RADIUS*2);
			g.setColor(Color.WHITE);
			//add text
			g.drawString(node.getLabel(), node.getX() - 5, node.getY() + 5);
		}
		
	}
	/*
	 * Creates a Node and adds it to the ArrayList of Nodes
	 * Takes the x, y, and label of the Node
	 * returns nothing
	 * adds a row and column to the adjacency matrix for the new node
	 */
	public void addNode(int x, int y, String label) {
		nodes.add(new Node(x, y, label));
		adjacency.add(new ArrayList<Boolean>());
		for (int i = 0; i < adjacency.size(); i++) {
			adjacency.get(i).add(false);
		}
		for (int i = 0; i < adjacency.size() - 1; i++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}
	}
	/*
	 * Add an edge to the ArrayList of Edges
	 * takes the 2 connected nodes and the label
	 * creates and Edge and adds it, then updates adjacency matrix
	 */
	public void addEdge(Node first, Node second, String label) {
		//create & add edge
		edges.add(new Edge(first, second, label));
		int firstIndex = 0;
		int secondIndex = 0;
		//get the indexes of the nodes in the nodes ArrayList and change adjacency matrix to true
		for (int i = 0; i < adjacency.size(); i++) {
				if(first.equals(nodes.get(i))) {
					firstIndex = i;
				}
				if(second.equals(nodes.get(i))) {
					secondIndex = i;
				}
		}
		adjacency.get(firstIndex).set(secondIndex, true);
		adjacency.get(secondIndex).set(firstIndex, true);
	}
	
	//return the ArrayList of edges
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	
	/*
	 * returns a node based on given x and y coordinates
	 */
	public Node getNode(int x, int y) {
		for(int i = 0; i < nodes.size(); i ++) {
			//find distance from given x & y to each node, return node if distance is less than radius
			Node node = nodes.get(i);
			int nodeX = node.getX();
			int nodeY = node.getY();
			double distance = Math.pow(Math.pow(nodeX - x, 2) + Math.pow(nodeY - y, 2), 0.5);
			if(distance <= RADIUS) {
				return node;
			}
		}
		return null;
	}
	
	/*
	 * Returns a Node based on given String name
	 * Checks if the label of each Node matches name
	 * returns a matching Node or null
	 */
	public Node getNode(String name) {
		//get each node and compare label to name
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			String label = node.getLabel();
			System.out.println();
			if(label.equals(name)) {
				return node;
			}
		}
		//if none match, return null
		return null;
	}
	/*
	 * Returns an ArrayList of connected Nodes given Node node
	 * Uses the adjacency matrix to check for any true connections to node
	 */
	public ArrayList<Node> getConnected(Node node){
		ArrayList<Node> connected = new ArrayList<Node>();
		//get the index of the current node
		int index = 0;
		for (int i = 0; i < nodes.size(); i++) {
			if(node.equals(nodes.get(i))) {
				index = i;
			}
		}
		//loop through the adjacency matrix of the appropriate row to test for connections
		for (int i = 0; i < adjacency.size(); i++) {
			if(adjacency.get(index).get(i) == true && !nodes.get(i).equals(node)) {
				connected.add(nodes.get(i));
			}
		}
		return connected;
	}
	//return the list of Nodes
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
