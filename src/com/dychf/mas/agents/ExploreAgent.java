package com.dychf.mas.agents;

import com.dychf.mas.behaviours.*;
import com.dychf.mas.knowledge.MapRepresentation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ilyas Aroui
 */
public class ExploreAgent extends AbstractDedaleAgent implements AgentInterface {

    private static final long serialVersionUID = -6431752665590433727L;
    private MapRepresentation myMap;

    private static final String exploration = "exploration";
    private static final String sendMap = "send_map";
    private static final String receiveMap = "receive_map";
    private static final String sendBlock = "send_block";
    private static final String receiveBlock = "receive_block";
    private static final String fin = "fin";

    protected void setup() {
        super.setup();

        this.myMap = new MapRepresentation();
        List<Behaviour> lb = new ArrayList<>();

        FSMBehaviour fsm = new FSMBehaviour(this);

        fsm.registerFirstState(new MapExploration(this), exploration);
        fsm.registerState(new SendMap(this), sendMap);
        fsm.registerState(new ReceiveMap(this), receiveMap);
        fsm.registerState(new SayImBlock(this), sendBlock);
        fsm.registerState(new ReceiveBlock(this), receiveBlock);
        fsm.registerLastState(new EndProcess(), fin);

        fsm.registerDefaultTransition(exploration, sendMap);
        fsm.registerDefaultTransition(sendMap, receiveMap);
        fsm.registerDefaultTransition(receiveMap, exploration);
        fsm.registerTransition(exploration, sendBlock, 2);
        fsm.registerDefaultTransition(sendBlock, receiveBlock);
        fsm.registerDefaultTransition(receiveBlock, exploration);
        fsm.registerTransition(exploration, fin, 3);

        /*lb.add(new MapExploration(this));
        lb.add(new SendMap(this));
        lb.add(new ReceiveMap(this));
        lb.add(new ReceiveBlock(this));*/
        lb.add(fsm);
        addBehaviour(new startMyBehaviours(this, lb));
        System.out.println("the  agent " + this.getLocalName() + " is started");
    }

    @Override
    public void setMap(MapRepresentation myMap) {
        this.myMap = myMap;
    }

    @Override
    public MapRepresentation getMap() {
        return myMap;
    }
}
