package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        
        // Retrieve the graph.
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of distances.
        double[] distances = new double[nbNodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[data.getOrigin().getId()] = 0;

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Initialize array of label
        Label[] labels = new Label[nbNodes];
        for (int i = 0; i < nbNodes; i++) {
        	Node nod = graph.getNodes().get(i);
        	labels[i] = new Label(nod.getId(), false, Double.POSITIVE_INFINITY, null);
        }
        labels[data.getOrigin().getId()].setCost(0);

        // Initialize the Binary Heap
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        tas.insert(labels[data.getOrigin().getId()]);
        
        // Actual algorithm
        while (!tas.isEmpty()) {
        	
        	//Extract the minimum node
        	Label labelorigin = tas.findMin();
        	Node node = graph.getNodes().get(labelorigin.getSommet());
            
            //Mark the node
            labels[node.getId()].setMarque(true);
            for (Arc arc: node.getSuccessors()) {
                
                // Small test to check allowed roads and if the destination road isn't marked
                if (!data.isAllowed(arc) || labels[arc.getDestination().getId()].getMarque()) {
                    continue;
                }

                // Retrieve weight of the arc.
                double w = data.getCost(arc);
                double oldCost = labels[arc.getDestination().getId()].getCost();
                double newCost = labels[node.getId()].getCost() + w;

                if (Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
                    notifyNodeReached(arc.getDestination());
                    tas.insert(labels[arc.getDestination().getId()]);
                }

                // Check if new distances would be better, if so update...
                if (newCost < oldCost) {
                    labels[arc.getDestination().getId()].setCost(newCost);
                    labels[arc.getDestination().getId()].setPere(arc);
                }
                
            }
            
            //Remove the marked node out of the binary heap
            tas.remove(labelorigin);
        }


        // Destination has no predecessor, the solution is infeasible...
        if (labels[data.getDestination().getId()].getPere() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[data.getDestination().getId()].getPere();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        
        return solution;
    }

}
