/*
 * Class GraphCreater is the main class for Project GraphCreater
 * Renders and manages the JFrame, responds to clicks, 
 * and performs the traveling salesman calculations
 * 
 * Created with help from Jason Galbraith's GraphCreater videos for Java class
 * Author: Grace Hunter
 * Date: 27 April 2018
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreater implements ActionListener, MouseListener{
	//initialize all Objects for JFrame
	JFrame frame = new JFrame("Graph Creater");
	GraphPanel panel = new GraphPanel();
	JButton nodeBN  = new JButton("New Node");
	JButton edgeBN = new JButton("New Edge");
	JTextField nameTF = new JTextField("name");
	Container south = new Container();
	
	JTextField node1TF = new JTextField("node 1");
	JTextField node2TF = new JTextField("node 2");
	JButton testBN = new JButton("Check Path");
	JTextField startTF = new JTextField("start");
	JButton travellingBN = new JButton("Travelling Salesman");
	Container north = new Container();
	
	//states
	final int CREATE_NODE = 0;
	final int FIRST_EDGE = 2;
	final int SECOND_EDGE = 3;
	int lastClicked = 0;
	Node firstNode;
	
	//ArrayLists for travellingSalesman()
	ArrayList<ArrayList<Node>> correctPaths = new ArrayList<ArrayList<Node>>();
	ArrayList<Integer> totals = new ArrayList<Integer>();
	
	public GraphCreater() {
		//set size and layout of JPanel, add components
		frame.setSize(1000, 1000);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		
		south.setLayout(new GridLayout(1, 3));
		south.add(nodeBN);
		south.add(nameTF);
		south.add(edgeBN);
		frame.add(south, BorderLayout.NORTH);
		
		north.setLayout(new GridLayout(1, 5));
		north.add(node1TF);
		north.add(node2TF);
		north.add(testBN);
		north.add(startTF);
		north.add(travellingBN);
		frame.add(north, BorderLayout.SOUTH);
		
		//add listeners
		nodeBN.addActionListener(this);
		edgeBN.addActionListener(this);
		panel.addMouseListener(this);
		testBN.addActionListener(this);
		travellingBN.addActionListener(this);
		
		//set colors
		nodeBN.setBackground(Color.ORANGE);
		edgeBN.setBackground(Color.LIGHT_GRAY);
		travellingBN.setBackground(Color.LIGHT_GRAY);
		testBN.setBackground(Color.LIGHT_GRAY);
		
		//show panel
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void travellingSalesman(Node start, ArrayList<Node> path, int total) {
		//add the starting node if isn't already in the path
		if(!path.contains(start)) {
			path.add(start);
		}
		//if the number of nodes in the path is the same length as the number of nodes
		ArrayList<Node> nodes = panel.getNodes();
		if(path.size() == nodes.size()) {
			//store path and total
			totals.add(total);
			ArrayList<Node> correctPath = new ArrayList<Node>();
			for (int i = 0; i < path.size(); i++) {
				correctPath.add(path.get(i));
			}
			correctPaths.add(correctPath);
			//back up one node before the program continues
			path.remove(path.size() - 1);
			return;
		}
		else {
			//for each connected edge, get the connected node, and then run travellingSalesman() again with the new path
			ArrayList<Edge> edges = panel.getEdges();
			for (int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				Node connected = edge.getConnected(start);
				if(connected != null) {
					//if the node is not already in the path
					if(path.contains(connected) == false) {
						path.add(connected);
						travellingSalesman(connected, path, total + Integer.parseInt(edge.getLabel()));
					}
				}
			}
		}
		//back up one node
		path.remove(path.size() - 1);
	}
	
	public static void main(String[] args) {
		new GraphCreater();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(lastClicked == CREATE_NODE) {
			panel.addNode(e.getX(), e.getY(), nameTF.getText());
		}
		else if(lastClicked == FIRST_EDGE) {
			Node node = panel.getNode(e.getX(), e.getY());
			if(node != null) {
				firstNode = node;
				lastClicked = SECOND_EDGE;
				//highlight
				node.highlight = true;
			}
		}
		else if(lastClicked == SECOND_EDGE) {
			Node node = panel.getNode(e.getX(), e.getY());
			//check to make sure edge label is a number
			String label = nameTF.getText();
			boolean valid = false;
			for (int i = 0; i < label.length(); i++) {
				if(Character.isDigit(label.charAt(i))) {
					valid = true;
				}
				else {
					valid = false;
					break;
				}
			}
			if(!valid) {
				JOptionPane.showMessageDialog(frame, "Edge name must contain only numbers");
				return;
			}
			
			if(node != null && !firstNode.equals(node)) {
				firstNode.highlight = false;
				//add an edge
				panel.addEdge(firstNode, node, nameTF.getText());
				firstNode = null;
				lastClicked = FIRST_EDGE;
			}
		}
		frame.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nodeBN)) {
			//change state and colors
			lastClicked = CREATE_NODE;
			nodeBN.setBackground(Color.ORANGE);
			edgeBN.setBackground(Color.LIGHT_GRAY);
		}
		else if(e.getSource().equals(edgeBN)) {
			//change state and color
			lastClicked = FIRST_EDGE;
			edgeBN.setBackground(Color.ORANGE);
			nodeBN.setBackground(Color.LIGHT_GRAY);
		}
		else if(e.getSource().equals(testBN)) {
			//get nodes
			Node node1 = panel.getNode(node1TF.getText());
			Node node2 = panel.getNode(node2TF.getText());
			
			//check to make sure both nodes exist before proceeding
			if(node1 == null) {
				JOptionPane.showMessageDialog(frame, "There is no node with name " + node1TF.getText());
			}
			else if(node2 == null) {
				JOptionPane.showMessageDialog(frame, "There is no node with name " + node2TF.getText());
			}
			else {
				Queue queue = new Queue();
				ArrayList<Node> allConnected = new ArrayList<Node>();
				allConnected.add(node1);
				ArrayList<Node> connections = panel.getConnected(node1);
				
				//add each in connections to queue
				for (int i = 0; i < connections.size(); i++) {
					queue.enqueue(connections.get(i));
				}
				
				//while connections have not all been found
				while(!queue.isEmpty()) {
					//remove one from queue and add it to allConnections
					Node node = queue.dequeue();
					if(!allConnected.contains(node)) {
						allConnected.add(node);
					}
					//add all connected nodes to the queue if not already in connected list
					connections = panel.getConnected(node);
					for (int i = 0; i < connections.size(); i++) {
						if(!allConnected.contains(connections.get(i))){
							queue.enqueue(connections.get(i));
						}
					}
				}
				
				//check if second node is in connected list
				if(allConnected.contains(node2)) {
					JOptionPane.showMessageDialog(frame, node1TF.getText() + " and " + node2TF.getText() + " are connected.");
				}
				else {
					JOptionPane.showMessageDialog(frame, node1TF.getText() + " and " + node2TF.getText() + " are not connected.");
				}
			}
		}
		else if(e.getSource().equals(travellingBN)) {
			
			//get the starting node, check to make sure it exists
			Node start = panel.getNode(startTF.getText());
			if(start == null) {
				JOptionPane.showMessageDialog(frame, "There is no node with name " + startTF.getText());
			}
			else {
				travellingSalesman(start, new ArrayList<Node>(), 0);
				
				if(correctPaths.size() > 0) {
					int min = totals.get(0);
					int minIndex = 0;
					//find the smallest total
					for (int i = 0; i < totals.size(); i++) {
						if (totals.get(i) <= min) {
							min = totals.get(i);
							minIndex = i;
						}
					}
					//get the corresponding path
					ArrayList<Node> path = correctPaths.get(minIndex);
					//get the labels from each node in the path and add to an output strand
					String out = "";
					for (int i = 0; i < path.size() - 1; i++) {
						out += path.get(i).getLabel();
						out += ", ";
					}
					out += path.get(path.size() - 1).getLabel();
					out += ". ";
					//display best path
					JOptionPane.showMessageDialog(frame, "The shortest path is " + out 
							+ "The cost to travel this path is " + min + ".");
				}
				//if there are no paths...let user know
				else {
					JOptionPane.showMessageDialog(frame, "There are no paths that go through all nodes once from the starting node " + startTF.getText() + ".");
				}
				//clear correctPaths and totals
				correctPaths = new ArrayList<ArrayList<Node>>();
				totals = new ArrayList<Integer>();
			}
		}
	}
}
