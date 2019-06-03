/*
 * Class Edge of project GraphCreater
 * Constructs Edges using the 2 given Nodes and a label
 * Contains getters and setters for the Edges' properties
 * Also contains a method that returns the other Node the Edge is connected to
 * 
 * Created with help from Jason Galbraith's GraphCreater videos for Java class
 * Author: Grace Hunter
 * Date: 27 April 2018
 */
public class Edge {
	Node first;
	Node second;
	String label;
	//constructor
	public Edge(Node firstNode, Node secondNode, String edgeLabel) {
		first = firstNode;
		second = secondNode;
		label = edgeLabel;
		
	}
	public Node getFirst() {
		return first;
	}
	public void setFirst(Node first) {
		this.first = first;
	}
	public Node getSecond() {
		return second;
	}
	public void setSecond(Node second) {
		this.second = second;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Node getConnected(Node n) {
		if(first.equals(n)) {
			return second;
		}
		else if (second.equals(n)) {
			return first;
		}
		else {
			return null;
		}
	}
	
}
