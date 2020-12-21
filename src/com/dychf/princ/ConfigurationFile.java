package com.dychf.princ;

import eu.su.mas.dedale.env.EnvironmentType;

/**
 * Configuration file for a Dedale instance
 *
 * @author hc
 */
public final class ConfigurationFile {
    public static final String[] entities = {"hunter1", "hunter1", "hunter1", "prey"};

    public static final Integer topologySize = 10;
    public static final Integer exploNum = 2;

    public static String topology_path = "resources/dychf/test1/myTopology";
    public static String element_path = "resources/dychf/test1/myElements";
    public static String entity_path = "resources/dychf/test1/myEntities";

    /******************************
     *
     * PLATEFORM configuration
     *
     *****************************/

    public static boolean PLATFORMisDISTRIBUTED = false;
    public static boolean COMPUTERisMAIN = true;

    public static String PLATFORM_HOSTNAME = "127.0.0.1";
    public static String PLATFORM_ID = "dychf";
    public static Integer PLATFORM_PORT = 8888;

    public static String LOCAL_CONTAINER_NAME = PLATFORM_ID + "_" + "container1";
    public static String LOCAL_CONTAINER2_NAME = PLATFORM_ID + "_" + "container2";
    public static String LOCAL_CONTAINER3_NAME = PLATFORM_ID + "_" + "container3";
    public static String LOCAL_CONTAINER4_NAME = PLATFORM_ID + "_" + "container4";


    /**
     * Required by the environment class to be able to load it. Currently, only one type of environment is available
     */
    public static EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.GS;

    /**
     * Name of the agent in charge of the environment E/S
     */
    public static String DEFAULT_GATEKEEPER_NAME = "GK";


    /******************************
     *
     * ENVIRONMENT configuration
     *
     * When the environment is loaded we need :
     *  - a topology,
     *  - the configuration of the elements on the map,
     *  - and the characteristics of the agents.
     *
     *  The two first parameters must be null if the environment is generated or already online
     *
     *****************************/


    /**
     * Not null when the environment is loaded; should be null if the environment is generated or already online
     */
    public static String INSTANCE_TOPOLOGY = topology_path;

    /**
     * Not null when the environment is loaded; should be null if the environment is generated or already online
     */
    public static String INSTANCE_CONFIGURATION_ELEMENTS = element_path;

    /**
     * Must'nt be null as it describes the agents capabilities on the map
     */
    public static String INSTANCE_CONFIGURATION_ENTITIES = entity_path;

    /************************************
     *
     * When the environment is generated
     *
     ***********************************/

    /**
     * Parameter used to generate the environment
     */
    public static Integer ENVIRONMENT_SIZE = 10;

    /**
     * Parameter used to perceive the wumpus trough its smell
     */
    public static final Integer DEFAULT_DETECTION_RADIUS = 1;

    /**
     * true if a grid environment should be generated, false otherwise (A dogoronev env is generated)
     **/
    public static boolean ENVIRONMENTisGRID = true;
    public static boolean ACTIVE_WELL = false;
    public static boolean ACTIVE_GOLD = true;
    public static boolean ACTIVE_DIAMOND = true;

}