package scheduler;

import graph.Graph;
import graph.Vertex;
import graph.Edge;
import graph.VertexComparator;

import java.util.*;

/**
 * Class to represent a schedule
 */
public class State {
    List<Processor> processors;
    int currentCost;
    int currentLevel;
    int costToBottomLevel;
    HashMap<Vertex, Integer> prevVertexEndTimeHashMap;
    HashMap<Vertex,Vertex> vertexPrevVertexHashMap;
    Graph g;

    public List<Processor> getProcessors() {
        return processors;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCostToBottomLevel() {
        return costToBottomLevel;
    }

    public Graph getG() {
        return g;
    }

    List<Vertex> traversed;
    PriorityQueue<Vertex> toTraverse;
    int lastProcessorVertexAddedTo;
    private int prevProcessNum = -1;


    public State(int numProcessors, Graph g) {
        traversed = new ArrayList<>();
        processors = new ArrayList<>();
        this.g = g;
        for (int i = 0; i < numProcessors; i++) {
            processors.add(new Processor(i + 1));
        }
        toTraverse = new PriorityQueue<>(new VertexComparator());
        toTraverse.addAll(g.getRoots());
        currentLevel = 0;
        costToBottomLevel = g.calculateBottomLevel();
        currentCost = 0;
        prevVertexEndTimeHashMap = new HashMap<>();
    }

    /**
     * Create a copy a given state
     * @param copyState
     */
    private State(State copyState) {
        traversed = new ArrayList<>();
        traversed.addAll(copyState.traversed);
        processors = new ArrayList<>();
        this.g = copyState.g;
            processors.add(new Processor(copyState.processors.get(0), 0));

        toTraverse = new PriorityQueue<>(new VertexComparator());
        toTraverse.addAll(copyState.toTraverse);
        currentLevel = copyState.currentLevel;
        costToBottomLevel = copyState.costToBottomLevel;
        prevVertexEndTimeHashMap = new HashMap<>(copyState.prevVertexEndTimeHashMap);
    }

    public State addVertex(int processorNum, Vertex v) {
        // Clone state then add the new vertex. Will also have to clone the processor list and processor block
        // list within it -> reference disappears once u clone so must use int
        lastProcessorVertexAddedTo = processorNum;
        traversed.add(v);
        toTraverse.remove(v);

        //System.out.println(Arrays.toString(hasBlock.toArray()));
        // Add the vertex to processor x, at the earliest possible time.
        int boundCost = processors.get(processorNum).addVertex(v, traversed, prevVertexEndTimeHashMap);
        costToBottomLevel = Math.max(costToBottomLevel, boundCost);

        // Set the new currentCost && current level
        currentLevel = currentLevel + 1;

        int stateEndTime = 0;

        for (Processor p : processors) {

            if (stateEndTime < p.getEndTime()) {
                stateEndTime = p.getEndTime();
            }

        }

        currentCost = stateEndTime;

        // Update the toTraverseList with new vertexes to travers
        for (Edge e : v.getOutgoingEdges()) {
            Vertex toAdd = e.getToVertex();
            if (!toTraverse.contains(toAdd) && !traversed.contains(toAdd)) {
                toTraverse.add(toAdd);
            }
        }

        // Required to check for duplicates later.
        Collections.sort(processors);
        prevVertexEndTimeHashMap.putIfAbsent(v,currentCost);
        prevVertexEndTimeHashMap.put(v,Math.max(prevVertexEndTimeHashMap.get(v),currentCost));

        return this;
    }

    public boolean canVisit(Vertex v) {
        //Vertex / Edges to be update to have the from vertices f
        //TODO
        return v.canVisit(traversed);
    }

    public boolean allVisited() {
        //Checks if any more vertexes exist to expand
        return toTraverse.isEmpty();
    }

    public HashSet<State> generatePossibilities() {
        //Generates a list of possible states to visit
        HashSet<State> possibleStates = new HashSet<>();
        if (!allVisited()) {
            List<Vertex> toAddList = new ArrayList<>();
            for (Vertex v : toTraverse) {
                if (canVisit(v)) {
                    toAddList.add(v);
                    HashSet<Processor> checkedProcessors = new HashSet<>();
                        State copy = new State(this);
                        Processor p = processors.get(0);
                        if(!checkedProcessors.contains(p)) {
                            checkedProcessors.add(p);
                            copy.addVertex(0, v);
                            possibleStates.add(copy);
                        }

                }
            }
            toTraverse.addAll(toAddList);
            toTraverse.removeAll(toAddList);
        }

        return possibleStates;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(processors, state.processors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processors);
    }

    //TODO return a copy of State, fpr a;; addVertex here.
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("\nCurrent Level: " + currentLevel + " Bottom Level: " + costToBottomLevel + " Current Cost: " + currentCost);
        for (int i = 0; i < processors.size(); i++) {
            Processor p = processors.get(i);
            sb.append("\nProcessor " + i + ":" + p.toString());
        }
        return sb.toString() + "\nVerticies Left:" + toTraverse;
    }
    public void outputFormat() {
        for (Processor p : processors) {
            p.outputFormat();
        }

    }

    /**
     * Ensures that a given schedule is valid by ensuring all end time for all vertices of a given
     * vertex are lower than the scheduled start time of that vertex
     * @return
     */
    public boolean isValid() {
        PriorityQueue<ProcessorBlock> pbs = new PriorityQueue<>();
        HashMap<Vertex,ProcessorBlock> vertexProcessorBlockHashMap = new HashMap<>();
        for (Processor p : processors) {
            for(ProcessorBlock pb:p.getProcessorBlockList()){
                pbs.add(pb);
                vertexProcessorBlockHashMap.put(pb.getV(),pb);
            }
        }
        for(ProcessorBlock pb: pbs){
            Vertex v = pb.getV();
            int starttime = pb.getStartTime();
            for(Vertex v1: v.getIncomingVerticies()){
                if(starttime < vertexProcessorBlockHashMap.get(v1).getEndTime()){
                    return false;
                };
            }
        }
        return true;
    }
}
