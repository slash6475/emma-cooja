<?xml version="1.0" encoding="UTF-8"?>
<simconf>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mrm</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mspsim</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/avrora</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/serial_socket</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/collect-view</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/powertracker</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/emma-agent-laucher</project>
  <simulation>
    <title>EMMA-1-node</title>
    <speedlimit>1.0</speedlimit>
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
      <description>Raven Border Router</description>
      <source EXPORT="discard">[CONFIG_DIR]/../emma-node/examples/ipv6/rpl-border-router/border-router.c</source>
      <commands EXPORT="discard">make border-router.avr-raven TARGET=avr-raven</commands>
      <firmware EXPORT="copy">[CONFIG_DIR]/../emma-node/examples/ipv6/rpl-border-router/border-router.avr-raven</firmware>
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
      <description>Raven EMMA</description>
      <source EXPORT="discard">[CONFIG_DIR]/../emma-node/examples/emma-node-example/emma-node-example.c</source>
      <commands EXPORT="discard">make emma-node-example.avr-raven TARGET=avr-raven</commands>
      <firmware EXPORT="copy">[CONFIG_DIR]/../emma-node/examples/emma-node-example/emma-node-example.avr-raven</firmware>
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
        <x>9.76821824514001</x>
        <y>69.23601483272795</y>
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
        <x>18.858145142965004</x>
        <y>75.93014704430449</y>
        <z>0.0</z>
      </interface_config>
      <interface_config>
        se.sics.cooja.avrmote.interfaces.AvroraMoteID
        <id>2</id>
      </interface_config>
      <motetype_identifier>Raven2</motetype_identifier>
    </mote>
  </simulation>
  <plugin>
    se.sics.cooja.plugins.SimControl
    <width>280</width>
    <z>1</z>
    <height>160</height>
    <location_x>400</location_x>
    <location_y>0</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.Visualizer
    <plugin_config>
      <skin>se.sics.cooja.plugins.skins.IDVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.GridVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.TrafficVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.UDGMVisualizerSkin</skin>
      <skin>se.sics.cooja.plugins.skins.AddressVisualizerSkin</skin>
      <viewport>14.191533270841447 0.0 0.0 14.191533270841447 -41.62599422274489 -886.5652080391307</viewport>
    </plugin_config>
    <width>400</width>
    <z>4</z>
    <height>400</height>
    <location_x>1</location_x>
    <location_y>1</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.LogListener
    <plugin_config>
      <filter />
      <coloring />
    </plugin_config>
    <width>880</width>
    <z>3</z>
    <height>651</height>
    <location_x>400</location_x>
    <location_y>160</location_y>
  </plugin>
  <plugin>
    se.sics.cooja.plugins.TimeLine
    <plugin_config>
      <mote>0</mote>
      <mote>1</mote>
      <showRadioRXTX />
      <showRadioHW />
      <showLEDs />
      <split>-1</split>
      <zoomfactor>500.0</zoomfactor>
    </plugin_config>
    <width>1280</width>
    <z>5</z>
    <height>166</height>
    <location_x>0</location_x>
    <location_y>811</location_y>
  </plugin>
  <plugin>
    SerialSocketServer
    <mote_arg>0</mote_arg>
    <width>600</width>
    <z>2</z>
    <height>160</height>
    <location_x>680</location_x>
    <location_y>-1</location_y>
  </plugin>
  <plugin>
    EmmaAgentLauncher
    <width>400</width>
    <z>0</z>
    <height>410</height>
    <location_x>0</location_x>
    <location_y>401</location_y>
  </plugin>
</simconf>

