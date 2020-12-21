package com.dychf.mas.behaviours;

import com.dychf.mas.agents.AgentInterface;
import com.dychf.mas.knowledge.MapRepresentation;
import com.dychf.princ.ConfigurationFile;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author ilyas Aroui
 */
public class SendMap extends SimpleBehaviour {

    private static final long serialVersionUID = -2058134622078521998L;

    private MapRepresentation myMap;
    private boolean finished = false;
    private int exitValue;

    public SendMap(final Agent myagent) {
        super(myagent);
    }

    @Override
    public void action() {
        exitValue = 1;
        this.myMap = ((AgentInterface) this.myAgent).getMap();

        ACLMessage msg_map = new ACLMessage(ACLMessage.INFORM);
        msg_map.setSender(this.myAgent.getAID());
        msg_map.setProtocol("map_explo");

        if (this.myMap != null) {
            try {
                this.myMap.serializeMap();
                msg_map.setContentObject((Serializable) this.myMap.sg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < ConfigurationFile.exploNum; i++) {
                msg_map.addReceiver(new AID("Explo" + i, AID.ISLOCALNAME));
            }

            ((AbstractDedaleAgent) this.myAgent).sendMessage(msg_map);

        } else {
            System.out.println("map_null before sending");
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