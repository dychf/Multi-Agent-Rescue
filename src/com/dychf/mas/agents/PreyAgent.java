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
public class PreyAgent extends AbstractDedaleAgent implements AgentInterface {

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
