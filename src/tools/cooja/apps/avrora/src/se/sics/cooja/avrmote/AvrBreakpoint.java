/*
 * Copyright (c) 2012, Swedish Institute of Computer Science.
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

package se.sics.cooja.avrmote;

import java.io.File;

import org.apache.log4j.Logger;

import se.sics.cooja.Watchpoint;
import se.sics.cooja.WatchpointMote.WatchpointListener;
import avrora.sim.Simulator;
import avrora.sim.Simulator.Probe;
import avrora.sim.State;

/**
 * Avrora watchpoint.
 *
 * @author Fredrik Osterlind
 */
public class AvrBreakpoint extends Watchpoint<AvroraMote> {
  private static Logger logger = Logger.getLogger(AvrBreakpoint.class);

  private Probe probe = null;

  public AvrBreakpoint(AvroraMote mote) {
    super(mote);
    /* expects setConfigXML(..) */
  }

  public AvrBreakpoint(AvroraMote mote, Integer address, File codeFile, Integer lineNr) {
    super(mote, address, codeFile, lineNr);
  }

  public void registerBreakpoint() {
    probe = new Simulator.Probe.Empty() {
      public void fireBefore(State s, int pc) {
        /* Avrora will return immediately */

        /* Request Cooja to stop executing */
        if (stopsSimulation()) {
          getMote().stopNextInstruction();
        }

        /* Notify listeners */
        WatchpointListener[] listeners = getMote().getWatchpointListeners();
        for (WatchpointListener listener: listeners) {
          listener.watchpointTriggered(AvrBreakpoint.this);
        }
      }
    };
    getMote().sim.insertProbe(probe, getExecutableAddress());
  }

  public void unregisterBreakpoint() {
    getMote().sim.removeProbe(probe, getExecutableAddress());
  }
}
