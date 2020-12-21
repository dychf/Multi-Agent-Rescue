package com.dychf.mas.behaviours;

import com.dychf.princ.ConfigurationFile;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author ilyas Aroui
 */
public class EndProcess extends SimpleBehaviour {
    private boolean finished = false;

    @Override
    public void action() {
        // TODO Auto-generated method stub
        ACLMessage msg_finish = new ACLMessage(ACLMessage.INFORM);
        msg_finish.setSender(this.myAgent.getAID());
        msg_finish.setProtocol("finish_protocol");

        for (int i = 0; i < ConfigurationFile.exploNum; i++) {
            if (!(this.myAgent.getLocalName()).equals("Explo" + i)) {
                msg_finish.addReceiver(new AID("Explo" + i, AID.ISLOCALNAME));
            }
        }
        ((AbstractDedaleAgent) this.myAgent).sendMessage(msg_finish);
        System.out.println("_____Mission finished._____________________________________________________________");
        finished = true;
    }

    @Override
    public boolean done() {
        // TODO Auto-generated method stub
        return finished;
    }
}
