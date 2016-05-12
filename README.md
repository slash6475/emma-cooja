emma-cooja
==========

The package emma-cooja is based on COOJA simulator provided by the Contiki OS framework. It allows platform emulation to be executed into a simulated radio environment. Therefore the different binary programs executed on COOJA are the ones executed on real platforms. In addition, the Border Edge Router of the WSAN is connected to the hosting OS (Linux or Windows) thanks to a tunslip6 interface (Tunnel Serial Line Internet Protocol v6) such as illustrated in following Figure. Therefore external Web service based on CoAP can communicate directly with the different nodes over IPV6 connecity without additionnal drivers or plugins.

<img src="https://github.com/slash6475/emma-cooja/blob/master/img/emma-cooja-tunslip.png">

 This package provides several plugins for the analyze and the deployment of a Wireless Sensor & Actor Network based on EMMA framework. The different ressources stored in permament memory of each node can be visualized in the network view in order to facilitate realtime debugging. In addition, the plugin agent loader is a Java based application which allows to emit CoAP requests from the hosting OS . Hence the software can be used to get, create, update or delete ressource but also to send deployment agent into the WSAN.

Tutorial
=============
In this tutorial, a simulation is created with 4 EMMA nodes and one Border Edge Router. The IPV6 connectivity between the Linux host and the nodes is tested by pinging and CoAP transactions between one node and firefox. Finally, the deployment agent of service discovery mechanism is launched on one node in order to deploy on all nodes the Discover Notifier agent which will push periodically and randomly the list of neighbours, the routes, the list of ressource and the URI of a description file to a proxy (in this case emma-agent-designer). 

[![ScreenShot](https://github.com/slash6475/emma-cooja/blob/master/img/emma-cooja.png)](https://youtu.be/xfbEVCdVp5E)

HowTo
=====

Installation
------------
```
bash install $DESTINATION_FOLDER
```

Start
------------
```
ant -f tools/cooja/build.xml run_bigmem
```
An pre-configured simulation is available at: `emma-cooja/simulations/emma-helloword.csc`. This simulation is composed of a Border Edge Router and 6 EMMA nodes.
