package br.com.engcon.algorithm;

import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Import;

public class A extends SearchIA {

	public static void main(String[] args) throws Exception {
		Graph graph = Import.loadGraph("caxeiro");
		
		A bl = new A();
		
		System.out.println("cost = "+bl.search(graph.getNode("arad"), graph.getNode("bucharest")));
		
	}

	public float search(Node o, Node t) {
		return 0;
	}

}
