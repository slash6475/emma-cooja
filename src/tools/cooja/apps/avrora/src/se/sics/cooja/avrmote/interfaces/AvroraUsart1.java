/*
 * Copyright (c) 2012, The Contiki OS (www.contiki-os.org).
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
 */

package se.sics.cooja.avrmote.interfaces;

import java.util.ArrayDeque;

import org.apache.log4j.Logger;

import se.sics.cooja.Mote;
import se.sics.cooja.MoteTimeEvent;
import se.sics.cooja.Simulation;
import se.sics.cooja.avrmote.AvroraMote;
import se.sics.cooja.dialogs.SerialUI;
import avrora.sim.mcu.AtmelMicrocontroller;
import se.sics.cooja.ClassDescription;

@ClassDescription("Usart 1")
public class AvroraUsart1 extends SerialUI {
	private static final long DELAY_INCOMING_DATA = 69; /* 115200 bit/s */

  private static Logger logger = Logger.getLogger(AvroraUsart1.class);

  private AvroraMote myMote;
  private avrora.sim.mcu.USART usart;
  private MoteTimeEvent receiveNextByte;

  private ArrayDeque<Byte> rxData = new ArrayDeque<Byte>();

  public AvroraUsart1(Mote mote) {
    myMote = (AvroraMote) mote;
    receiveNextByte = new MoteTimeEvent(mote, 0) {
      public void execute(long t) {
        if (usart.receiving) {
          /* XXX TODO Postpone how long? */
          myMote.getSimulation().scheduleEvent(this, t+DELAY_INCOMING_DATA);
          return;
        }
        usart.startReceive();
      }
    };

    /* this should go into some other piece of code for serial data */
    AtmelMicrocontroller mcu = (AtmelMicrocontroller) myMote.getPlatform().getMicrocontroller().getSimulator().getMicrocontroller();
    usart = (avrora.sim.mcu.USART) mcu.getDevice(getUsart());
    if (usart != null) {
      usart.connect(new avrora.sim.mcu.USART.USARTDevice() {
        public avrora.sim.mcu.USART.Frame transmitFrame() {
          if (rxData.isEmpty()) {
            logger.warn("no data for uart");
            return new avrora.sim.mcu.USART.Frame((byte)'?', false, 8);
          }

          Byte data = rxData.pollFirst();
          if (!receiveNextByte.isScheduled() && rxData.size() > 0) {
            myMote.getSimulation().scheduleEvent(receiveNextByte, myMote.getSimulation().getSimulationTime());
          }

          return new avrora.sim.mcu.USART.Frame(data, false, 8);
        }
        public void receiveFrame(avrora.sim.mcu.USART.Frame frame) {
          dataReceived(frame.value);
        }
      });
    } else {
      System.out.println("*** Warning Avrora could not find usart1 interface...");
    }
  }

  public String getUsart() {
    return "usart1";
  }

  public Mote getMote() {
    return myMote;
  }

  public void writeArray(byte[] s) {
    for (byte b: s) {
      writeByte(b);
    }
  }

  public void writeByte(byte b) {
    if (usart == null) {
      return;
    }

    rxData.addLast(b);
    if (!receiveNextByte.isScheduled()) {
      myMote.getSimulation().scheduleEvent(receiveNextByte, myMote.getSimulation().getSimulationTime());
    }
  }

  public void writeString(String s) {
    if (usart == null) {
      return;
    }
    writeArray(s.getBytes());
    writeByte((byte)'\n');
  }
}
