package Graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private String id;
    private int cost;
    private List<Edge> outgoingEdges;
    private List<Vertex> paths;
    int bottomLevel = 0;

    public Vertex(String id, int cost){
        this.id = id;
        this.cost = cost;
        outgoingEdges = new ArrayList<>();
    }


    //TODO calculate bottom level. //DFS but prioritise most expensive
    public void calculateBottomLevel() {
        bottomLevel = -1;
        dfs(this, 0);
    }


    private void dfs(Vertex currentVertex, int currentCost){
        currentCost = currentCost + currentVertex.cost;

        if (currentVertex.outgoingEdges.size() == 0){
            if (currentCost > bottomLevel){
                bottomLevel = currentCost;
            }

        } else {

            for (int i = 0; i < currentVertex.outgoingEdges.size(); i++) {
                Vertex nextVertex = currentVertex.outgoingEdges.get(i).getToVertex();
                dfs(nextVertex, currentCost);

            }
        }
    }

    public void addOutgoingEdge(Edge edge){
        outgoingEdges.add(edge);
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(obj.toString());
    }
}
