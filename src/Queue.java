/*
 * Class Queue of project GraphCreater
 * Manages an ArrayList as a queue and contains the methods enqueue(), dequeue(), and isEmpty()
 * 
 * Created with help from Jason Galbraith's GraphCreater videos for Java class
 * Author: Grace Hunter
 * Date: 27 April 2018
 */
import java.util.ArrayList;

public class Queue {
	ArrayList<Node> queue = new ArrayList<Node>();
	
	public void enqueue(Node node) {
		queue.add(node);
	}
	public Node dequeue() {
		Node node = queue.get(0);
		queue.remove(0);
		return node;
	}
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
