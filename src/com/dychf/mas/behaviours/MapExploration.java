package com.dychf.mas.behaviours;

import com.dychf.mas.agents.AgentInterface;
import com.dychf.mas.agents.ExploreAgent;
import com.dychf.mas.knowledge.MapRepresentation;
import com.dychf.princ.ConfigurationFile;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.*;


/**
 * @author ilyas Aroui
 */
public class MapExploration extends SimpleBehaviour {

    private static final long serialVersionUID = 8567689731496787661L;

    private boolean finished = false;
    private int exitValue;
    /**
     * 智能体对当前环境的了解
     */
    private MapRepresentation myMap;
    /**
     * 智能体可达但是未访问的节点
     */
    private List<String> openNodes;
    /**
     * 智能体访问过的节点
     */
    private Set<String> closedNodes;

    public MapExploration(final ExploreAgent myAgent) {
        super(myAgent);
        this.openNodes = new ArrayList<String>();
        this.closedNodes = new HashSet<String>();
    }

    @Override
    public void action() {
        exitValue = 1;

        this.myMap = ((AgentInterface) this.myAgent).getMap();
        if (this.myMap == null)
            this.myMap = new MapRepresentation();

        //0) Retrieve the current position
        String myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();

        if (myPosition != null) {
            //List of observable from the agent's current position
            List<Couple<String, List<Couple<Observation, Integer>>>> lobs = ((AbstractDedaleAgent) this.myAgent).observe();//myPosition

            /**
             * Just added here to let you see what the agent is doing, otherwise he will be too quick
             */
            try {
                this.myAgent.doWait(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //1) remove the current node from openlist and add it to closedNodes.
            this.closedNodes.add(myPosition);
            this.openNodes.remove(myPosition);

            this.myMap.addNode(myPosition, MapRepresentation.MapAttribute.closed);

            //2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
            String nextNode = null;
            Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter = lobs.iterator();
            while (iter.hasNext()) {
                String nodeId = iter.next().getLeft();
                if (!this.closedNodes.contains(nodeId)) {
                    if (!this.openNodes.contains(nodeId)) {
                        this.openNodes.add(nodeId);
                        this.myMap.addNode(nodeId, MapRepresentation.MapAttribute.open);
                        this.myMap.addEdge(myPosition, nodeId);
                    } else {
                        //the node exist, but not necessarily the edge
                        this.myMap.addEdge(myPosition, nodeId);
                    }
                    if (nextNode == null) nextNode = nodeId;
                }
            }

            //3) while openNodes is not empty, continues.
            if (this.openNodes.isEmpty()) {
                //Explo finished
                finished = true;
                exitValue = 3;
                System.out.println("Exploration successufully done, behaviour removed.");
            } else {
                //4) select next move.
                //4.1 If there exist one open node directly reachable, go for it,
                //	 otherwise choose one from the openNode list, compute the shortestPath and go for it
                if (nextNode == null) {
                    //no directly accessible openNode
                    //chose one, compute the path and take the first step.
                    nextNode = this.myMap.getShortestPath(myPosition, this.openNodes.get(0)).get(0);
                }
                if (!((AbstractDedaleAgent) this.myAgent).moveTo(nextNode)) {
                    exitValue = 2;
                    finished = true;
                }
            }
        }
        finished = true;
    }

    @Override
    public boolean done() {
        ((AgentInterface) this.myAgent).setMap(this.myMap);
        return finished;
    }

    public int onEnd() {
        return exitValue;
    }
}
