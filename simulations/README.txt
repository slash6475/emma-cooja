*******************************************************************
*******************************************************************
******************* EMMA SIMULATION TUTORIAL **********************
*******************************************************************
*******************************************************************

-----------------------
--- I. Presentation ---
-----------------------

All paths given are relative to the install directory of EMMA Framework. The EMMA Framework instaal directory is supposed to contain at least:
Install directory
   |\_emma-agent-loader
   |\_emma-cooja
   |\_emma-node
   |\_simulations
   |\_src

Content of the simulations folder:
simulation
   |\_deployment-agents
   |   |\_[AGENT]-Loop-1N.txt
   |   |\_[AGENT]-Light_deployment_and_control-3N.txt
   |   |\_[AGENT]-Light_deployment_and_control_corridor-4N.txt
   |   
   |\_EMMA-1-node.csc
   |\_EMMA-2-nodes.csc
   |\_EMMA-3-nodes.csc
   |\_EMMA-4-nodes.csc

COOJA Simulator files (.csc) are given for typical use/test/debug purposes. Simulations are given for 1 to 4 EMMA nodes. As nodes are emulated, PC performances should be adapted to use more than 5 nodes (4 nodes is the limit for a Core-2duo, 2Go RAM).

Two applications ([AGENT]-*-XN.txt) are distributed with this version of EMMA framework.
   *: The application name.
   X: The number of nodes required for deployment.

Note: Applications are written in JSON format, links to understand it are:
   - http://jsonlint.com/ : A JSON validator
   - src/emma-agent-loader/README.txt : A tutorial on how to write EMMA agents



------------------
--- II. Basics ---
------------------

*************************
** Launch a simulation **
*************************
Protocol:
   1) Execute 'emma-cooja/start.sh' script to launch COOJA
   2) File > Open simulation > Browse ...
   3) Select a .csc file according to the number of nodes wished
   4) Window 'Serial Socket(SERVER)', click 'Start' button
   5) Type in root password (this will start a TUN0 interface)
   6) Window 'Simulation Control', click 'Start' button
   7) Wait for all nodes to get the 'aaaa' prefix before anything else

The simulation is started, one can see routes creation and network prefix dissemination in the 'Network' window.
*************************



*******************
** Load an agent **
*******************
Pre-requisites:
   - COPPER plugin for Firefox installed

Protocol:
   1) Launch a 1-node simulation (see previous topic)
   2) Wait for all nodes to get the network prefix (default is aaaa)
   3) Window 'EMMA Agent Launcher', click on 'Refresh'
   4) Select node x (Address aaaa::200:x:x:x0x)
   5) Click on 'Load', select the desired agent file
   6) Click on 'Launch'
   7) Wait for the agent to be completely loaded (popup)
   8) Open Firefox, type 'coap://[aaaa::200:x:x:x0x]:5683/' in the addressbar
   9) Click on 'DISCOVER' to show node resources
   10) The loaded agent should be present under the A/ root
*******************



---------------------
--- III. Examples ---
---------------------

********************************
** Example agent: Simple loop **
********************************
Protocol:
   1) Launch a 1-node simulation (see previous topic)
   2) Load 'simulations/deployment-agents/[AGENT]-Loop-1N.txt' on node 2 (see previous topic)
   3) Open Firefox, type node 2 address (coap://[aaaa::200:2:2:202]:5683/)
   4) Click on 'DISCOVER' to show the loaded agent
   5) In addressbar, type 'coap://[aaaa::200:2:2:202]:5683/L/cnt'
   6) In Tab 'Outgoing', type 5
   7) Click on 'PUT'

The 'Loop' agent loaded on node 2 will check the value of L/cnt and increment it until it's value is equal to ten. The final value of L/cnt can be checked using the 'GET' method to retrieve it.
********************************



*************************************************
** Example agent: Light deployment and control **
*************************************************
Protocol:
   1) Launch a 1-node simulation (see previous topic)
   2) Load 'simulations/deployment-agents/[AGENT]-Light_deployment_and_control-3N.txt' on node 2 (see previous topic)
   3) After the agent has been loaded, let the application be deployed over the network (Important radio activity)
   4) Open Firefox,  execute 'DISCOVER' commands on node 3 and 4 to see the deployed agents and variables
   5) On node 3, 'PUT' the threshold value desired (e.g. 15) on resource R/S
   6) Regularly execute 'GET' command on resource R/B (located on node 3) to see its evolution

The Brightness, represented by resource R/B located on node 3 will increase to reach the value of R/S (Threshold). When getting
multiple times the resource R/B, service might be unavailable: a mutex is locking the resource to increment it safely.
*************************************************



**********************************************************
** Example agent: Light deployment and control corridor **
**********************************************************
TODO:
Agent is located in '[AGENT]-Light_deployment_and_control_corridor-4N.txt'

Problem (real node): The agent cannot write properly on himself (node 2), actually it erases AD2(himself) when writing
agent A/AS. It seems to be a problem of size in COFFEE pages (agent might be to big).

Problem2: Why in simulation COOJA authorises the agent to be written on EEPROM ?







