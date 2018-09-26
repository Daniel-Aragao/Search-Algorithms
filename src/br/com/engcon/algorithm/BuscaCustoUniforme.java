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
//		String graphName = "caxeiro";
		String graphName = "bus-network";

		Graph graph = Import.loadGraph(graphName);
		
		BuscaCustoUniforme bl = new BuscaCustoUniforme();
		
		if(graphName.equals("caxeiro")) {
			System.out.println("cost = "+bl.search(graph.getNode("arad"), graph.getNode("bucharest")));			
		}else {
			
			System.out.println("cost = "+bl.search(graph.getNode("p_5729"), graph.getNode("p_2500")));
		}
		
	}

	public float search(Node o, Node t) {
//		Collections.sort(list, c);
		List<Node> border = new ArrayList<Node>();
		List<Node> path = new ArrayList<Node>();
		
		float weight = 0;
		
		Node current = o;
		
		current.getOtherAttributes().put("BCUWeight", 0F);
		
		border.add(current);		
		
		while(border.size() > 0 && current != t){
			Collections.sort(border, getComparator());
			
			current = border.remove(0);
			path.add(current);
			
			weight = (Float) current.getOtherAttributes().get("BCUWeight");
			
			List<Node> childrens = current.getNeighborsOut();
			
			for(Node children : childrens) {
				Edge edge = current.getEdgeWith(children);
				
				children.getOtherAttributes().put("BCUWeight", weight + edge.getWeight());
				
				if(!path.contains(children) && !border.contains(children)){
					border.add(children);
				}
			}
		}
		
		if(current == t) {
			for(Node n : path) {
				System.out.println(n.getId());
			}
			
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
	
	
//	public Comparator

}
