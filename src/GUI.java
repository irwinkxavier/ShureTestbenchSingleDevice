import com.formdev.flatlaf.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
public class GUI {
    private static JFrame frame;
    private JPanel Discover;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton discoverButton;
    private JList IPList;
    private DefaultListModel<String> IPListModel;
    private JButton resetButton;
    private JButton configureButton;
    private JPanel Main;
    private JPanel Configure;
    private JPanel Device4Ch;
    private JLabel Device4Ch_RFBandLabel;
    private JComboBox Device4Ch_RFBandSelect;
    private JLabel Device4Ch_Channel1;
    private JLabel Device4Ch_1FreqLabel;
    private JTextField Device4Ch_1FreqInput;
    private JLabel Device4Ch_1NameLabel;
    private JTextField Device4Ch_1NameInput;
    private JLabel Device4Ch_Channel2;
    private JLabel Device4Ch_2NameLabel;
    private JLabel Device4Ch_2FreqLabel;
    private JTextField Device4Ch_2NameInput;
    private JTextField Device4Ch_2FreqInput;
    private JTextField Device4Ch_3NameInput;
    private JTextField Device4Ch_3FreqInput;
    private JTextField Device4Ch_4NameInput;
    private JTextField Device4Ch_4FreqInput;
    private JLabel Device4Ch_Channel3;
    private JLabel Device4Ch_3NameLabel;
    private JLabel Device4Ch_3FreqLabel;
    private JLabel Device4Ch_Channel4;
    private JLabel Device4Ch_4NameLabel;
    private JLabel Device4Ch_4FreqLabel;
    private JLabel Device4Ch_Label;
    private JPanel Device2Ch;
    private JLabel Device2Ch_Label;
    private JLabel Device2Ch_RFBandLabel;
    private JComboBox Device2Ch_RFBandSelect;
    private JLabel Device2Ch_Channel1;
    private JLabel Device2Ch_1FreqLabel;
    private JTextField Device2Ch_1FreqInput;
    private JLabel Device2Ch_1NameLabel;
    private JTextField Device2Ch_1NameInput;
    private JLabel Device2Ch_Channel2;
    private JLabel Device2Ch_2NameLabel;
    private JLabel Device2Ch_2FreqLabel;
    private JTextField Device2Ch_2NameInput;
    private JTextField Device2Ch_2FreqInput;
    private JButton configureDevicesButton;

    private Discover deviceDiscovery = new Discover();
    private Commands command = new Commands();

    public GUI() {
        Discover.setVisible(true);
        Configure.setVisible(false);
//        resetButton.setEnabled(false);
//        configureButton.setEnabled(false);
        IPListModel = new DefaultListModel<String>();
        IPList.setModel(IPListModel);

//        //***Temporarily adding devices manually onto the list***
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("AD4D");
        devices.add("AD4Q");

        discoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deviceDiscovery.discover();
                if (deviceDiscovery.timeout){
                    JOptionPane.showMessageDialog(null, "No Devices Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
                IPListModel.addAll(devices);
            }
        });
        configureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(IPList.getSelectedValue() == "AD4Q") {
                    Discover.setVisible(false);
                    Device4Ch.setVisible(true);
                    Device2Ch.setVisible(false);
                    Configure.setVisible(true);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
//                    frame.setDefaultCloseOperation();
                }
                else if(IPList.getSelectedValue() == "AD4D"){
                    Discover.setVisible(false);
                    Device2Ch.setVisible(true);
                    Device4Ch.setVisible(false);
                    Configure.setVisible(true);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                }
                else if(IPList.isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Select a Device First", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        configureDevicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Device2Ch.isVisible()){
                    String status = command.sendAD4D(deviceDiscovery.discoveredIP, (String) Device2Ch_RFBandSelect.getSelectedItem(),Device2Ch_1NameInput.getText(),Device2Ch_1FreqInput.getText(),Device2Ch_2NameInput.getText(),Device2Ch_2FreqInput.getText());
                    if(status.equals("Device Configured Successfully")){
                        JOptionPane.showMessageDialog(null, status, "Success", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, status, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (Device4Ch.isVisible()) {
                    String status = command.sendAD4Q(deviceDiscovery.discoveredIP, (String) Device4Ch_RFBandSelect.getSelectedItem(),Device4Ch_1NameInput.getText(),Device4Ch_1FreqInput.getText(),Device4Ch_2NameInput.getText(),Device4Ch_2FreqInput.getText() ,Device4Ch_3NameInput.getText(),Device4Ch_3FreqInput.getText(),Device4Ch_4NameInput.getText(),Device4Ch_4FreqInput.getText());
                    if(status.equals("Device Configured Successfully")){
                        JOptionPane.showMessageDialog(null, status, "Success", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, status, "Error", JOptionPane.ERROR_MESSAGE);
//                        IPList.
                    }
                }
            }
        });
    }

    public static void main(String[] args) {

        GUI app = new GUI();


        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Shure Testbench");
        frame.setContentPane(new GUI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
