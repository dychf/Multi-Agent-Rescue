package com.dychf.mas.behaviours;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;
import java.util.Random;

/**
 * @author ilyas Aroui
 */
public class ReceiveBlock extends SimpleBehaviour {

    private static final long serialVersionUID = -2058134622075555998L;

    private String envoyeur = "";
    private boolean finished = false;
    private int exitValue;

    public ReceiveBlock(final Agent myAgent) {
        super(myAgent);
    }

    public void randomWalk(int iterations) {
        int i = 0;
        while (i < iterations) {
            List<Couple<String, List<Couple<Observation, Integer>>>> lobs = ((AbstractDedaleAgent) this.myAgent).observe();
            Random rand = new Random();
            int posAleat = rand.nextInt(lobs.size());
            String nextNode = lobs.get(posAleat).getLeft();
            ((AbstractDedaleAgent) this.myAgent).moveTo(nextNode);
            i++;
        }
    }

    @Override
    public void action() {
        exitValue = 1;

        final MessageTemplate msgTemplate = MessageTemplate.and(
                MessageTemplate.MatchProtocol("Block_Protocol"),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        final ACLMessage msg = this.myAgent.receive(msgTemplate);

        if (msg != null) {
            this.randomWalk(3);
        }

        finished = true;
    }

    public int onEnd() {
        return exitValue;
    }


    @Override
    public boolean done() {
        return finished;
    }
}
	