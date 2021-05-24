package org.insa.graphs.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlgorithmTest {
	
	//
	private static String mapdir;
	
	//
	private static GraphReader reader;
	
	//
	private static Graph g1, g2;
	
	//
	private static List<ArcInspector> filters;
	
	//
	private static ShortestPathData data;
	
	//
	private int i = 0;
	private Random rand = new Random();
	private Node s, d;
	private ShortestPathAlgorithm algo;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Arc> arcs = new ArrayList<Arc>();
	
	//Initialisation
	@BeforeClass
	public static void initAll() throws Exception {
		
		//Recuperer un graph
		mapdir =  "E:/Home/Minh/Documents/Graphes/Map/";
		reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapdir + "haute-garonne.mapgr"))));
		g1 = reader.read();
		reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapdir + "toulouse.mapgr"))));
		g2 = reader.read();
		
		//Recuperer la liste des filters
		filters = ArcInspectorFactory.getAllFilters();
		

	}
	
	@Test
	public void testValid() {
		
		//Pour different modes
		for (ArcInspector filter: filters) {
			i = 0;
			while (i < 10) {
				s = g1.getNodes().get(rand.nextInt(g1.getNodes().size()));
				d = g1.getNodes().get(rand.nextInt(g1.getNodes().size()));
				data = new ShortestPathData(g1, s, d, filter);
				algo = new DijkstraAlgorithm(data);
				System.out.println(s.getId() + " " + d.getId());
				assertTrue(algo.run().getPath().isValid());
				i++;
			}
		}
	}
	
//	@Test
//	public void testShortest() {
//		i = 0;
//		while (i < 50) {
//			s = g2.getNodes().get(rand.nextInt(g2.getNodes().size()));
//			d = g2.getNodes().get(rand.nextInt(g2.getNodes().size()));
//			data = new ShortestPathData(g1, s, d, filters.get(0));
//			algo = new DijkstraAlgorithm(data);
//			arcs = algo.run().getPath().getArcs();
//			nodes.add(s);
//			for (Arc arc : algo.run().getPath().getArcs()) {
//				if (arc.getDestination() == null || arc.getDestination() == d) {
//					break;
//				}
//				nodes.add(arc.getDestination());
//			}
//			System.out.println(s.getId() + " " +d.getId() + " " + Path.createShortestPathFromNodes(g2, nodes).getLength());
//			assertEquals(algo.run().getPath(), Path.createShortestPathFromNodes(g2,nodes));
//		}
//	}

	
//	@Test
//	public void testFastest() {
//		i = 0;
//		while (i < 50) {
//			s = g1.getNodes().get(rand.nextInt(g1.getNodes().size()));
//			d = g1.getNodes().get(rand.nextInt(g1.getNodes().size()));
//			nodes.add(s);
//			nodes.add(d);
//			data = new ShortestPathData(g1, s, d, filters.get(2));
//			algo = new DijkstraAlgorithm(data);
//			assertEquals(algo.run().getPath(), Path.createFastestPathFromNodes(g1, nodes));
//		}
//	}
}
