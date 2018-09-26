package br.com.engcon.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import br.com.engcon.graph.Edge;
import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Import;
import br.com.engcon.util.Util;

public class A extends SearchIA {
	private Comparator<Node> comparator;


	public static void main(String[] args) throws Exception {
		String graphName = "bus-network";
		Graph graph = Import.loadGraph(graphName);
		
		A bl = new A();
		System.out.println("cost = "+bl.search(graph.getNode("p_5729"), graph.getNode("p_2500")));
		
	}

	public float search(Node o, Node t) {
		List<Node> border = new ArrayList<Node>();
		
		Double weight = 0D;
		
		Node current = o;
		
		current.getOtherAttributes().put("A*Weight", 0D);
		current.getOtherAttributes().put("A*Heuristic", distanceBetweenNodes(current, t));
		
		border.add(current);		
		
		while(border.size() > 0 && current != t){
			Collections.sort(border, getComparator());
			
			current = border.remove(0);
			
			weight = (Double) current.getOtherAttributes().get("A*Weight");		
			
			if(current == t) break;
			
			List<Node> childrens = current.getNeighborsOut();
			
			for(Node children : childrens) {
				Edge edge = current.getEdgeWith(children);
				
				double g =  weight +  Double.parseDouble((String) edge.getOtherAttributes().get("distance"));
				double h = distanceBetweenNodes(children, t);
				double f = g + h;
				
				Double storedG = (Double)children.getOtherAttributes().get("A*Weight");
					
				if(storedG != null && storedG <= g) {
					continue;
				}
				
				children.getOtherAttributes().put("edge_weight", edge.getWeight());
				children.getOtherAttributes().put("A*Weight", g);
				children.getOtherAttributes().put("A*Heuristic", h);
				children.getOtherAttributes().put("A*f", f);
				children.getOtherAttributes().put("parent_node", current);
				
				
				boolean isInBorder = border.contains(children);
				if(!isInBorder){
					border.add(children);
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
			
			return (float)((double) weight);			
		}
		
		return -1;
	}
	
	private Comparator<Node> getComparator() {
		if(comparator == null) {
			comparator = new Comparator<Node>() {
				
				@Override
				public int compare(Node n0, Node n1) {
					HashMap<String, Object> n0Attrs = n0.getOtherAttributes();
					HashMap<String, Object> n1Attrs = n1.getOtherAttributes();
					
					double f0 = (Double) n0Attrs.get("A*f");
					double f1 = (Double) n1Attrs.get("A*f");
					
					return f0 > f1 ? 1 : f0 < f1 ? -1 : 0;
				}
			}; 
		}
		
		return comparator; 
	}
	
	private double distanceBetweenNodes(Node n1, Node n2) {
		HashMap<String, Object> outrosAtrib1 = n1.getOtherAttributes();
		HashMap<String, Object> outrosAtrib2 = n2.getOtherAttributes();

		Double lat1 = Double.parseDouble((String) outrosAtrib1.get("latitude"));
		Double lng1 = Double.parseDouble((String) outrosAtrib1.get("longitude"));
		Double lat2 = Double.parseDouble((String) outrosAtrib2.get("latitude"));
		Double lng2 = Double.parseDouble((String) outrosAtrib2.get("longitude"));

		Double el1 = 0D;
		Double el2 = 0D;
		
		return Util.distance(lat1, lng1, lat2, lng2, el1, el2);
	}

}
