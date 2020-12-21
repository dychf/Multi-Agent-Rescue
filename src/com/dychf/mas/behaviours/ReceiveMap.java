package com.dychf.mas.behaviours;

import com.dychf.mas.agents.AgentInterface;
import com.dychf.mas.knowledge.MapRepresentation;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author ilyas Aroui
 */
public class ReceiveMap extends SimpleBehaviour {


    private static final long serialVersionUID = -2058134622078551998L;

    private Map<String[], LinkedList<String>> sgSender;
    private MapRepresentation mapReceiver;
    private boolean finished = false;
    private int exitValue;

    public ReceiveMap(final Agent myAgent) {
        super(myAgent);
    }

    @Override
    public void action() {
        exitValue = 1;
        this.mapReceiver = ((AgentInterface) this.myAgent).getMap();
        final MessageTemplate msgTemplate_map = MessageTemplate.and(
                MessageTemplate.MatchProtocol("map_explo"),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        final ACLMessage msg_map = this.myAgent.receive(msgTemplate_map);

        if (msg_map != null) {

            try {
                this.sgSender = (Map<String[], LinkedList<String>>) msg_map.getContentObject();
            } catch (UnreadableException e) {
                e.printStackTrace();
            }

            for (String key[] : this.sgSender.keySet()) {
                if (key[1] != null) {
                    switch (key[1]) {
                        case "open":
                            //别人的open状态的节点也放到自己的open列表中
                            if (this.mapReceiver.getGraph().getNode(this.sgSender.get(key).get(0)) == null) {
                                this.mapReceiver.addNode(key[0], MapRepresentation.MapAttribute.open);
                            }
                            break;
                    }
                } else {
                    //由别人探索到了
                    this.mapReceiver.addNode(key[0]);
                }

                for (int i = 0; i < this.sgSender.get(key).size(); i++) {
                    if (this.mapReceiver.getGraph().getNode(this.sgSender.get(key).get(i)) == null) {
                        this.mapReceiver.addNode(this.sgSender.get(key).get(i));
                    }
                    this.mapReceiver.addEdge(key[0], this.sgSender.get(key).get(i));
                }
            }
            ((AgentInterface) this.myAgent).setMap(this.mapReceiver);
        }
        finished = true;
    }

    @Override
    public boolean done() {
        return finished;
    }

    public int onEnd() {
        return exitValue;
    }
}
	