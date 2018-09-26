package br.com.engcon.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Import;

public class BuscaCustoUniforme extends SearchIA {

	public static void main(String[] args) throws Exception {
		Graph graph = Import.loadGraph("caxeiro");
		
		BuscaCustoUniforme bl = new BuscaCustoUniforme();
		
		System.out.println("cost = "+bl.search(graph.getNode("arad"), graph.getNode("bucharest")));
		
	}

	public float search(Node o, Node t) {
		return 0;
	}

}
