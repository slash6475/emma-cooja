<?xml version="1.0" encoding="UTF-8"?>
<simconf>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mrm</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mspsim</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/avrora</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/serial_socket</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/collect-view</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/powertracker</project>
  <simulation>
    <title>emma-test</title>
    <randomseed>123456</randomseed>
    <motedelay_us>1000000</motedelay_us>
    <radiomedium>
      se.sics.cooja.radiomediums.UDGM
      <transmitting_range>50.0</transmitting_range>
      <interference_range>100.0</interference_range>
      <success_ratio_tx>1.0</success_ratio_tx>
      <success_ratio_rx>1.0</success_ratio_rx>
    </radiomedium>
    <events>
      <logoutput>40000</logoutput>
    </events>
    <motetype>
      se.sics.cooja.avrmote.RavenMoteType
      <identifier>Raven1</identifier>
      <description>EMMA border router</description>
      <source EXPORT="discard">[CONFIG_DIR]/emma-node/examples/ipv6/rpl-border-router/border-router.c</source>
      <commands EXPORT="discard">make border-router.avr-raven TARGET=avr-raven</commands>
      <firmware EXPORT="copy">[CONFIG_DIR]/emma-node/examples/ipv6/rpl-border-router/border-router.avr-raven</firmware>
      <moteinterface>se.sics.cooja.interfaces.Position</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraMoteID</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraLED</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.RavenRadio</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraClock</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraUsart0</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraUsart1</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvrDebugger</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraADC</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.Mote2MoteRelations</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.MoteAttributes</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.RimeAddress</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.IPAddress</moteinterface>
    </motetype>
    <motetype>
      se.sics.cooja.avrmote.RavenMoteType
      <identifier>Raven2</identifier>
      <description>EMMA node</description>
      <source EXPORT="discard">[CONFIG_DIR]/emma-node/examples/emma-node-example/emma-node-example.c</source>
      <commands EXPORT="discard">make emma-node-example.avr-raven TARGET=avr-raven</commands>
      <firmware EXPORT="copy">[CONFIG_DIR]/emma-node/examples/emma-node-example/emma-node-example.avr-raven</firmware>
      <moteinterface>se.sics.cooja.interfaces.Position</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraMoteID</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraLED</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.RavenRadio</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraClock</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraUsart0</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraUsart1</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvrDebugger</moteinterface>
      <moteinterface>se.sics.cooja.avrmote.interfaces.AvroraADC</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.Mote2MoteRelations</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.MoteAttributes</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.RimeAddress</moteinterface>
      <moteinterface>se.sics.cooja.interfaces.IPAddress</moteinterface>
    </motetype>
    <mote>
      <breakpoints />
      <interface_config>
        se.sics.cooja.interfaces.Position
        <x>28.202461783602647</x>
        <y>19.968067220514293</y>
        <z>0.0</z>
      </interface_config>
      <interface_config>
        se.sics.cooja.avrmote.interfaces.AvroraMoteID
        <id>1</id>
      </interface_config>
      <motetype_identifier>Raven1</motetype_identifier>
    </mote>
    <mote>
      <breakpoints />
      <interface_config>
        se.sics.cooja.interfaces.Position
        <x>19.604143751750726</x>
        <y>28.30583016049191</y>
        <z>0.0</z>
      </interface_config>
      <interface_config>
        se.sics.cooja.avrmote.interfaces.AvroraMoteID
        <id>2</id>
      </interface_config>
      <motetype_identifier>Raven2</motetype_identifier>
    </mote>
    <mote>
      <breakpoints />
      <interface_config>
        se.sics.cooja.interfaces.Position
        <x>39.797163372009024</x>
        <y>34.689429911412276</y>
        <z>0.0</z>
      </interface_config>
      <interface_config>
        se.sics.cooja.avrmote.interfaces.AvroraMoteID
        <id>3</id>
      </interface_config>
      <motetype_identifier>Raven2</motetype_identifier>
    </mote>
  </simulation>
  <plugin>
    se.sics.cooja.plugins.SimControl
    <width>287</width>
    <z>0</z>
    <height>104</height>
    <location_x>270</location_x>
    <location_y>1</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.Visualizer
    <plugin_config>
      <skin>se.sics.cooja.plugins.skins.IDVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.GridVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.TrafficVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.UDGMVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.AddressVisualizerSkin</skin>
      <viewport>7.675919843335313 0.0 0.0 7.675919843335313 -102.47983603566138 -120.27328341099906</viewport>
    </plugin_config>
    <width>271</width>
    <z>1</z>
    <height>290</height>
    <location_x>1</location_x>
    <location_y>1</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.LogListener
    <plugin_config>
      <filter />
    </plugin_config>
    <width>739</width>
    <z>6</z>
    <height>186</height>
    <location_x>272</location_x>
    <location_y>106</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.RadioLogger
    <plugin_config>
      <split>150</split>
    </plugin_config>
    <width>1009</width>
    <z>5</z>
    <height>243</height>
    <location_x>2</location_x>
    <location_y>759</location_y>
  </plugin>
  <plugin>
    SerialSocketServer
    <mote_arg>0</mote_arg>
    <width>455</width>
    <z>2</z>
    <height>105</height>
    <location_x>555</location_x>
    <location_y>1</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.MoteInterfaceViewer
    <mote_arg>1</mote_arg>
    <plugin_config>
      <interface>Usart 1</interface>
      <scrollpos>0,0</scrollpos>
    </plugin_config>
    <width>502</width>
    <z>3</z>
    <height>465</height>
    <location_x>2</location_x>
    <location_y>293</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.MoteInterfaceViewer
    <mote_arg>2</mote_arg>
    <plugin_config>
      <interface>Usart 1</interface>
      <scrollpos>0,0</scrollpos>
    </plugin_config>
    <width>509</width>
    <z>4</z>
    <height>466</height>
    <location_x>502</location_x>
    <location_y>293</location_y>
  </plugin>
</simconf>

