/*
 * Class Node of project GraphCreater
 * Constructs Nodes using a given x, y, and label
 * Also contains getters and setters for its properties
 * 
 * Created with help from Jason Galbraith's GraphCreater videos for Java class
 * Author: Grace Hunter
 * Date: 27 April 2018
 */
public class Node {
	int x;
	int y;
	String label;
	boolean highlight = false;
	
	//constructor
	public Node(int newX, int newY, String newLabel) {
		x = newX;
		y = newY;
		label = newLabel;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
