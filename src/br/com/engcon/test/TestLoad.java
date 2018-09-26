package br.com.engcon.test;

import java.util.List;

import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Import;

public class TestLoad {
	
	public static void main(String[] args) throws Exception {
		
		Graph graph = Import.loadGraph("bus-network");
		
		List<Node> nodes = graph.getNodeList();
		
		for (Node node : nodes) {
			
			System.out.println(node.getId());
		}
		
	}

}
