package com.dychf.mas.behaviours;

import com.dychf.princ.ConfigurationFile;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author ilyas Aroui
 */
public class SayImBlock extends SimpleBehaviour {

    private static final long serialVersionUID = -2055554622078521998L;

    private boolean finished = false;
    private int exitValue;

    public SayImBlock(final Agent myAgent) {
        super(myAgent);
    }

    @Override
    public void action() {
        exitValue = 1;

        String myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setSender(this.myAgent.getAID());
        msg.setProtocol("Block_Protocol");

        if (myPosition != "") {
            msg.setContent(this.myAgent.getName() + " ; " + myPosition);
            for (int i = 0; i < ConfigurationFile.exploNum; i++) {
                msg.addReceiver(new AID("Explo" + i, AID.ISLOCALNAME));
            }

            ((AbstractDedaleAgent) this.myAgent).sendMessage(msg);
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
