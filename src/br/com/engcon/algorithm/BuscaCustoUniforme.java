package br.com.engcon.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.engcon.graph.Edge;
import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Import;

public class BuscaCustoUniforme extends SearchIA {
	private Comparator<Node> comparator;

	public static void main(String[] args) throws Exception {
		String graphName = "caxeiro";
		graphName = "bus-network";

		Graph graph = Import.loadGraph(graphName);
		
		BuscaCustoUniforme bl = new BuscaCustoUniforme();
		
		if(graphName.equals("caxeiro")) {
			System.out.println("cost = "+bl.search(graph.getNode("arad"), graph.getNode("bucharest")));			
		}else {			
			System.out.println("cost = "+bl.search(graph.getNode("p_5729"), graph.getNode("p_2500")));
		}
	}

	public float search(Node o, Node t) {
		List<Node> border = new ArrayList<Node>();		
		float weight = 0;		
		Node current = o;
		
		current.getOtherAttributes().put("BCUWeight", 0F);
		
		border.add(current);		
		
		while(border.size() > 0 && current != t){
			Collections.sort(border, getComparator());
			current = border.remove(0);			
			
			weight = (Float) current.getOtherAttributes().get("BCUWeight");
			
			List<Node> childrens = current.getNeighborsOut();
			
			for(Node children : childrens) {
				Edge edge = current.getEdgeWith(children);
				
				Float childrenWeight = (Float) children.getOtherAttributes().get("BCUWeight");
				Float newWeight = weight + Float.parseFloat((String) edge.getOtherAttributes().get("distance"));
				
				if(childrenWeight == null || childrenWeight > newWeight){
					
					children.getOtherAttributes().put("BCUWeight", newWeight);
					children.getOtherAttributes().put("parent_node", current);
					
					if(childrenWeight == null) {
						border.add(children);						
					}
				}
			}
		}
		
		if(current == t) {
			Node printCurrent = current;

			int count = 0;
			
			while(printCurrent != null) {
				System.out.println(printCurrent);
				printCurrent = (Node) printCurrent.getOtherAttributes().get("parent_node");
				count++;
			}
			System.out.println("Nós no caminho: " + count);
			
			return weight;			
		}
		
		return -1;
	}
	
	private Comparator<Node> getComparator() {
		if(comparator == null) {
			comparator = new Comparator<Node>() {				
				@Override
				public int compare(Node n0, Node n1) {
					float f0 = (Float) n0.getOtherAttributes().get("BCUWeight");
					float f1 = (Float) n1.getOtherAttributes().get("BCUWeight");
					
					return f0 > f1 ? 1 : f0 < f1 ? -1 : 0;
				}
			}; 
		}
		
		return comparator; 
	}
}