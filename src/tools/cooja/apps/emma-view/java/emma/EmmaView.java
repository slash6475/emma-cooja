/*
 * Copyright (c) 2009, Swedish Institute of Computer Science.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * $Id: EmmaView.java,v 1.4 2009/11/05 11:54:59 fros4943 Exp $
 */

package emma;

import emma.model.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.sics.cooja.ClassDescription;
import se.sics.cooja.Mote;
import se.sics.cooja.Simulation;
import se.sics.cooja.SimEventCentral.MoteCountListener;
import se.sics.cooja.dialogs.MessageList.MessageContainer;
import se.sics.cooja.interfaces.IPAddress;
import se.sics.cooja.interfaces.Position;
import se.sics.cooja.interfaces.RimeAddress;
import se.sics.cooja.plugins.Visualizer;
import se.sics.cooja.plugins.VisualizerSkin;
import se.sics.cooja.plugins.Visualizer.MoteMenuAction;

import se.sics.cooja.MoteMemory;

import java.util.Arrays;
import java.awt.Font;
/**
 * Visualizer skin for mote addresses.
 *
 * Paints the address below each mote.
 *
 * @author Fredrik Osterlind
 */
@ClassDescription("EmmaView")
public class EmmaView implements VisualizerSkin {
  private static Logger logger = Logger.getLogger(EmmaView.class);

  private Simulation simulation = null;
  private Visualizer visualizer = null;

  final int RESOURCE_NAME_LENGTH_MAX = 10;
  final int RESOURCE_DESC_LENGTH_MAX = 50;
  final int EMMA_RESOURCE_NAME_SIZE  = 16;

  private Observer addrObserver = new Observer() {
    public void update(Observable obs, Object obj) {
      visualizer.repaint();
    }
  };
  private MoteCountListener newMotesListener = new MoteCountListener() {

   public void moteWasAdded(Mote mote) {
    /*
      IPAddress ipAddr = mote.getInterfaces().getIPAddress();
      if (ipAddr != null) {
        ipAddr.addObserver(addrObserver);
      }
      RimeAddress rimeAddr = mote.getInterfaces().getRimeAddress();
      if (rimeAddr != null) {
        rimeAddr.addObserver(addrObserver);
      }*/
    };

    public void moteWasRemoved(Mote mote) {
/*      IPAddress ipAddr = mote.getInterfaces().getIPAddress();
      if (ipAddr != null) {
        ipAddr.deleteObserver(addrObserver);
      }
      RimeAddress rimeAddr = mote.getInterfaces().getRimeAddress();
      if (rimeAddr != null) {
        rimeAddr.deleteObserver(addrObserver);
      }*/
    };
  };

  public void setActive(Simulation simulation, Visualizer vis) {
    this.simulation = simulation;
    this.visualizer = vis;

    simulation.getEventCentral().addMoteCountListener(newMotesListener);
    for (Mote m: simulation.getMotes()) {
      newMotesListener.moteWasAdded(m);
    }
    
    /* Register menu actions */
//    visualizer.registerMoteMenuAction(CopyAddressAction.class);
  }

  public void setInactive() {
    simulation.getEventCentral().removeMoteCountListener(newMotesListener);
    for (Mote m: simulation.getMotes()) {
      newMotesListener.moteWasRemoved(m);
    }

    /* Unregister menu actions */
  //  visualizer.unregisterMoteMenuAction(CopyAddressAction.class);
  }

  public Color[] getColorOf(Mote mote) {
    return null;
  }


  // FOR EACH ROOT RESOURCE
  public Resource generateInfo(Mote mote){
    Resource rmote               = new Resource("/");
    MoteMemory moteMem          = mote.getMemory();
    String[] variablesName      = moteMem.getVariableNames();
    byte[] buff2_b = {};

    for(String var : variablesName){
      if(var.contains("root_") && var.indexOf('_', 6) == -1){

        // GET NAME
        //char* name;
        int i;
        int addr        = moteMem.getVariableAddress(var) + 1;
        byte[] buff_b   =  moteMem.getMemorySegment(addr, 2);
        buff_b   =  moteMem.getMemorySegment(moteMem.parseInt(buff_b) , RESOURCE_NAME_LENGTH_MAX);
        for(i=0; i < buff_b.length; i++)
          if(buff_b[i] == 0)
            break;
          byte[] dst = Arrays.copyOf(buff_b, i);
          String Name = new String(dst);

        // GET DESCRIPTION
        // char* description;
          addr        = moteMem.getVariableAddress(var) + 1 + 2;
          buff_b     =  moteMem.getMemorySegment(addr, 2);
          buff_b     =  moteMem.getMemorySegment(moteMem.parseInt(buff_b) , RESOURCE_DESC_LENGTH_MAX);
          for(i=0; i < buff_b.length; i++)
            if(buff_b[i] == 0)
              break;
            dst = Arrays.copyOf(buff_b, i);
            String Desc = new String(dst);


            Resource root = new Resource(Name, Desc);
            rmote.addResource(root);


        // GET LIST ADDRESS
        //emma_resource_t* list;
            addr        = moteMem.getVariableAddress(var) + 23;
            buff_b     =  moteMem.getMemorySegment(addr, 2);
        // GET name
            while(moteMem.parseInt(buff_b) != 0){

              // GET NAME OF RESOURCE
              buff2_b     =  moteMem.getMemorySegment(moteMem.parseInt(buff_b)+2 , EMMA_RESOURCE_NAME_SIZE);
              for(i=0; i < buff2_b.length; i++)
                if(buff2_b[i] == 0)
                  break;
                dst = Arrays.copyOf(buff2_b, i);

                Resource r = new Resource(new String(dst));

              // GET DATA
                if(root.getName().equals("L")){
                  buff2_b     =  moteMem.getMemorySegment(moteMem.parseInt(buff_b)+ 2 + EMMA_RESOURCE_NAME_SIZE, 2);
                  buff2_b     =  moteMem.getMemorySegment(moteMem.parseInt(buff2_b), 2);

                  System.out.println("plop : " + moteMem.parseInt(buff2_b));
                  r.setValue(moteMem.parseInt(buff2_b));
                }


              // GET NEXT RESOURCE
                buff_b     =  moteMem.getMemorySegment(moteMem.parseInt(buff_b)+ 2 + EMMA_RESOURCE_NAME_SIZE + 6, 2);

                root.addResource(r);
              }
            }
          }

          return rmote;
        }

        public void paintBeforeMotes(Graphics g) {
        }

        public void paintAfterMotes(Graphics g) {
          FontMetrics fm = g.getFontMetrics();
          g.setColor(Color.BLUE);
          g.setFont(new Font("",0,10));

          /* Paint last output below motes */
          Mote[] allMotes = simulation.getMotes();
          for (Mote mote: allMotes) {
            Resource rmote = generateInfo(mote);

            String msg = "";
            Position pos = mote.getInterfaces().getPosition();
            Point pixel = visualizer.transformPositionToPixel(pos);
            int msgWidth = fm.stringWidth(msg);

            for (int i = 0; i < rmote.getResources().size(); i++){
              ArrayList<Resource> childs = rmote.getResources().get(i).getResources();
              for(int j=0; j < childs.size(); j ++)
                g.drawString("/" + rmote.getResources().get(i).getName() + "/" + childs.get(j).getName() + " : " + childs.get(j).getValue(), pixel.x + 15, pixel.y + 5 - j*10);
            }

          }
        }
/*
  public static class CopyAddressAction implements MoteMenuAction {
    public boolean isEnabled(Visualizer visualizer, Mote mote) {
      return true;
    }

    public String getDescription(Visualizer visualizer, Mote mote) {
      return "Copy address to clipboard: \"" + getMoteString(mote) + "\"";
    }

    public void doAction(Visualizer visualizer, Mote mote) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      StringSelection stringSelection = new StringSelection(getMoteString(mote));
      clipboard.setContents(stringSelection, null);
    }
  };*/
  public Visualizer getVisualizer() {
    return visualizer;
  }
}