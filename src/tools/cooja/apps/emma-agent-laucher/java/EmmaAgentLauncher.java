
import java.io.FileNotFoundException;
import org.jdom.Element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFormattedTextField;
import java.text.NumberFormat;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
        


import se.sics.cooja.ClassDescription;
import se.sics.cooja.GUI;
import se.sics.cooja.Mote;
import se.sics.cooja.PluginType;
import se.sics.cooja.SimEventCentral.MoteCountListener;
import se.sics.cooja.Simulation;
import se.sics.cooja.VisPlugin;
import se.sics.cooja.interfaces.Radio;

import ch.ethz.inf.vs.californium.coap.Response;

import emma.loader.*;

@ClassDescription("EMMA Agent Launcher")
@PluginType(PluginType.SIM_PLUGIN)
public class EmmaAgentLauncher extends VisPlugin {

    private Simulation simulation;
    private MoteCountListener moteCountListener;
    protected JTextArea payload;
    protected JComboBox ip;
    protected JTextField uri;
    protected JTextField port;
    protected NumberFormat format;
    
    protected JButton refresh_btn;
    protected JButton launch_btn;
    protected JButton load_btn;
    protected JButton save_btn;

    public EmmaAgentLauncher(final Simulation simulation, final GUI gui) {
        super("EMMA Agent Launcher", gui, false);

        this.simulation = simulation;

        payload = new JTextArea(5, 20);
        payload.setEditable(true);

        ip = new JComboBox();
        port = new JTextField("5683");
        
        uri = new JTextField("/");
        JLabel label_coap = new JLabel("coap://[");
        JLabel label_coap_end = new JLabel("]:");

        JPanel jpanel_uri = new JPanel();
        GroupLayout layout = new GroupLayout(jpanel_uri);
        jpanel_uri.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_coap, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(ip, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(label_coap_end, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(port, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(uri, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label_coap)
                    .addComponent(ip)
                    .addComponent(label_coap_end)
                    .addComponent(port)
                    .addComponent(uri)
                )
        );
 
        /*Box uri_box = Box.createHorizontalBox();
        uri_box.add(Box.createHorizontalGlue());
        uri_box.add(label_coap);
        uri_box.add(ip);
        uri_box.add(label_coap_end);
        uri_box.add(port);
        uri_box.add(uri);*/

        refresh_btn = new JButton(Refresh);
        launch_btn = new JButton(Launch);
        load_btn = new JButton(Load);
        save_btn = new JButton(Save);

        Box control = Box.createHorizontalBox();
        control.add(Box.createHorizontalGlue());
        control.add(refresh_btn);
        control.add(load_btn);
        control.add(save_btn);
        control.add(launch_btn);

        //this.getContentPane().add(BorderLayout.NORTH, uri_box);
        this.getContentPane().add(BorderLayout.NORTH, jpanel_uri);
        this.getContentPane().add(BorderLayout.CENTER, new JScrollPane(payload));
        this.getContentPane().add(BorderLayout.SOUTH, control);
        setSize(400, 400);


        /*Add node listeners to generate list of node ip*/
        simulation.getEventCentral().addMoteCountListener(moteCountListener = new MoteCountListener() {

            public void moteWasAdded(Mote mote) {
                ip.addItem(mote.getInterfaces().getIPAddress().getIPString());
            }

            public void moteWasRemoved(Mote mote) {
                ip.removeItem(mote.getInterfaces().getIPAddress().getIPString());
            }
        });

        for (Mote m : simulation.getMotes()) {
            ip.addItem(m.getInterfaces().getIPAddress().getIPString());
        }

    }
    /* Refresh mote list */
    private Action Refresh = new AbstractAction("Refresh") {

        public void actionPerformed(ActionEvent e) {
            ip.removeAllItems();
            for (Mote m : simulation.getMotes()) {
                ip.addItem(m.getInterfaces().getIPAddress().getIPString());
            }
        }
    };
    /*Send an agent */
    private Thread T;
    private Action Launch = new AbstractAction("Launch") {

        public void actionPerformed(ActionEvent e) {
            launch_btn.setAction(Stop);
            T = new Thread(new Runnable() {

                public void run() {
                    String agent = payload.getText();

                    Agent eAgent = new Agent(agent);
                    if (eAgent.isValid()) {
                        String req = "coap://[" + ip.getSelectedItem().toString() + "]:" + port.getText().toString() + uri.getText();
                        System.out.println("REQ : " + req);
                        eAgent.send(req);
                        Response response = eAgent.getResponse();
                        
                        ByteArrayOutputStream rsp_data = new ByteArrayOutputStream();
                        PrintStream out = new PrintStream(rsp_data);
                        if(response != null)
                           response.prettyPrint(out);
                        
                        JOptionPane.showMessageDialog(null, rsp_data.toString());
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Agent bad written");
                    }
                    launch_btn.setAction(Launch);
                }
            });
            T.start();

        }
    };
    /* Stop sending of an agent */
    private Action Stop = new AbstractAction("Stop") {

        public void actionPerformed(ActionEvent e) {
            if (T != null) {
                if (T.isAlive()) {
                    T.stop();
                }
            }
            launch_btn.setAction(Launch);
        }
    };
    /* Save the current agent */
    private Action Save = new AbstractAction("Save") {

        public void actionPerformed(ActionEvent e) {
            String agent = payload.getText();
            Agent eAgent = new Agent(agent);

            if (!eAgent.isValid()) {
                JOptionPane.showMessageDialog(null, "Agent bad written");
                return;
            }

            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showSaveDialog(null);


            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                if (file.exists()) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "This agent already exists, do you want to erase it ?", "Warning", dialogButton);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        FileWriter fw;
                        try {
                            fw = new FileWriter(file.getAbsolutePath() + "/" + eAgent.getName() + ".emma");
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(agent);
                            bw.close();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Saving agent error : " + ex.getMessage());
                        }
                    }
                }
            }
        }
    };
    /* Load an agent */
    private Action Load = new AbstractAction("Load") {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening : " + file.getAbsolutePath());
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(file.getAbsolutePath()));

                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }

                    payload.setText(sb.toString());
                    br.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Opening agent error : " + ex.getMessage());
                }




            }
        }
    };
}
