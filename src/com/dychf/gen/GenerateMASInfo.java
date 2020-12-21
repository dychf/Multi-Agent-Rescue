package com.dychf.gen;

import java.util.Random;

import com.dychf.princ.ConfigurationFile;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 生成各种
 *
 * @author hc
 */
public class GenerateMASInfo {

    public static void main(String[] args) {
        //genTopology(false);
        genEntities(ConfigurationFile.exploNum);
    }

    public static void genTopology(boolean full) {
        System.out.println("DGS004\n" +
                "null 0 0\n" +
                "cg  \"stylesheet\":\"node {\n" +
                "                  \tsize: 10px;\n" +
                "                  \tstroke-mode: plain;\n" +
                "                  \tstroke-color: yellow;\n" +
                "                  \tstroke-width: 0;\n" +
                "                  \ttext-background-mode: plain;\n" +
                "                  \ttext-padding: 2;\n" +
                "                  \ttext-offset: 3;\n" +
                "                    text-mode: hidden;\n" +
                "                  }\"\n" +
                "cg  \"ui.quality\":true\n" +
                "cg  \"ui.antialias\":true");
        Graph graph = generateGraph(false, ConfigurationFile.topologySize);
        Iterable<? extends Node> eachNode = graph.getEachNode();
        for (Node node : eachNode) {
            System.out.println("an \"" + node + "\" label: \"" + node + "\"");
        }
        Iterable<? extends Edge> eachEdge = graph.getEachEdge();
        for (Edge edge : eachEdge) {

            int index = edge.getIndex();
            Node node0 = edge.getNode0();
            Node node1 = edge.getNode1();
            if (full)
                System.out.println("ae \"" + index + "\" \"" + node0 + "\" \"" + node1 + "\"");
            else {
                Random random = new Random();
                int i = random.nextInt(5);
                if (i != 1)
                    System.out.println("ae \"" + index + "\" \"" + node0 + "\" \"" + node1 + "\"");
            }
        }
    }

    public static void genEntities(int exploNum) {
        //agentExplo:Explo1:4:0_0:0:0:0:2:0
        System.out.println("mapname:entities");
        Graph graph = generateGraph(false, ConfigurationFile.topologySize);
        Iterable<? extends Node> nodes = graph.getEachNode();
        int flag = 0;
        for (Node node : nodes) {
            System.out.println("agentExplo:Explo" + flag + ":4:" + node + ":0:0:0:0:2");
            flag++;
            if (flag >= exploNum) break;
        }
    }

    /**
     * @param type true creates a Dorogovtsev env, false create a grid.
     * @param size number of iteration, the greater the bigger maze.
     * @return a new graph
     */
    private static Graph generateGraph(boolean type, int size) {
        Graph g = new SingleGraph("Random graph");
        Generator gen;

        if (type) {
            //generate a DorogovtsevMendes environment
            gen = new DorogovtsevMendesGenerator();
            gen.addSink(g);
            gen.begin();
            for (int i = 0; i < size; i++) {
                gen.nextEvents();
            }
            gen.end();
        } else {
            //generate a square grid environment
            gen = new GridGenerator();
            gen.addSink(g);
            gen.begin();
            for (int i = 0; i < size; i++) {
                gen.nextEvents();
            }
            gen.end();
        }
        return g;
    }

}
