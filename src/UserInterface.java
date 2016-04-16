import JavaBean.ARPBean;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Yasic on 2016/4/14.
 */
public class UserInterface extends JFrame{
    private JLabel jTargetIPText;
    private JTextField jTargetIPField;
    private JLabel jGateWayText;
    private JTextField jGateWayField;
    private JLabel jLocalMacText;
    private JTextField jLocalMacField;
    private JLabel jTargetMacText;
    private JTextField jTargetMacField;
    private JButton jButton;
    private JButton jLocalNetworkAttackButton;
    private JButton jSingleTargetAttackButton;
    private JButton jCancelAttackButton;
    private JButton jExitButton;
    private Thread thread;
    private JLabel jTipText;

    private UserInterface(){
        super();
        this.setSize(400, 450);
        this.getContentPane().setLayout(null);
        //this.add(getJLabel(), null);
        //this.add(getJTextField(), null);
        this.add(getJTargetIPText(),null);
        this.add(getJTargetIPTextField(),null);
        this.add(getJGateWayText(),null);
        this.add(getJGateWayTextField(),null);
        this.add(getjTargetMacText(),null);
        this.add(getjTargetMcField(),null);
        this.add(getjLocalMacField(),null);
        this.add(getjLocalMacText(),null);
        this.add(getLocalNetworkAttackButton(), null);
        this.add(getjSingleTargetAttackButton(), null);
        this.add(getjCancelAttackButton(), null);
        this.add(getjExitButton(), null);
        this.add(getjTipText(), null);
        this.setTitle("ARP Attack");

        jLocalNetworkAttackButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (thread != null){
                        thread.stop();
                    }
                    jTipText.setText("正在进行全局域网攻击");
                    thread = Main.localNetworkAttack(new ARPBean(
                            jTargetIPField.getText(),
                            jGateWayField.getText(),
                            jTargetMacField.getText(),
                            jLocalMacField.getText(),false));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        jSingleTargetAttackButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (thread != null){
                        thread.stop();
                    }
                    jTipText.setText("正在对目标进行攻击");
                    thread = Main.localNetworkAttack(new ARPBean(
                            jTargetIPField.getText(),
                            jGateWayField.getText(),
                            jTargetMacField.getText(),
                            jLocalMacField.getText(),true));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        jCancelAttackButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (thread != null){
                    thread.stop();
                }
                jTipText.setText("等待指令");
            }
        });

        jExitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });
    }

    private javax.swing.JLabel getjTipText() {
        if (jTipText == null) {
            jTipText = new javax.swing.JLabel();
            jTipText.setBounds(20, 235, 190, 60);
            jTipText.setText("等待指令");
        }
        return jTipText;
    }

    private javax.swing.JLabel getJTargetIPText() {
        if (jTargetIPText == null) {
            jTargetIPText = new javax.swing.JLabel();
            jTargetIPText.setBounds(20, 30, 80, 40);
            jTargetIPText.setText("Target Ip:");
        }
        return jTargetIPText;
    }

    private javax.swing.JTextField getJTargetIPTextField() {
        if (jTargetIPField == null) {
            jTargetIPField = new javax.swing.JTextField();
            jTargetIPField.setBounds(100, 30, 200, 40);
        }
        return jTargetIPField;
    }

    private javax.swing.JLabel getJGateWayText() {
        if (jGateWayText == null) {
            jGateWayText = new javax.swing.JLabel();
            jGateWayText.setBounds(20, 90, 80, 40);
            jGateWayText.setText("GateWay:");
        }
        return jGateWayText;
    }

    private javax.swing.JTextField getJGateWayTextField() {
        if (jGateWayField == null) {
            jGateWayField = new javax.swing.JTextField();
            jGateWayField.setBounds(100, 90, 200, 40);
        }
        return jGateWayField;
    }

    private javax.swing.JLabel getjTargetMacText() {
        if (jTargetMacText == null) {
            jTargetMacText = new javax.swing.JLabel();
            jTargetMacText.setBounds(20, 150, 80, 40);
            jTargetMacText.setText("Target Mac:");
        }
        return jTargetMacText;
    }

    private javax.swing.JTextField getjTargetMcField() {
        if (jTargetMacField == null) {
            jTargetMacField = new javax.swing.JTextField();
            jTargetMacField.setBounds(100, 150, 200, 40);
        }
        return jTargetMacField;
    }

    private javax.swing.JLabel getjLocalMacText() {
        if(jLocalMacText == null) {
            jLocalMacText = new javax.swing.JLabel();
            jLocalMacText.setBounds(20, 210, 80, 40);
            jLocalMacText.setText("Local Mac:");
        }
        return jLocalMacText;
    }

    private javax.swing.JTextField getjLocalMacField() {
        if(jLocalMacField == null) {
            jLocalMacField = new javax.swing.JTextField();
            jLocalMacField.setBounds(100, 210, 200, 40);
        }
        return jLocalMacField;
    }

    private javax.swing.JButton getJButton() {
        if(jButton == null) {
            jButton = new javax.swing.JButton();
            jButton.setBounds(20, 20, 250, 50);
            jButton.setText("OK");
        }
        return jButton;
    }

    private JButton getLocalNetworkAttackButton(){
        if (jLocalNetworkAttackButton == null){
            jLocalNetworkAttackButton = new JButton();
            jLocalNetworkAttackButton.setBounds(20, 280, 160, 50);
            jLocalNetworkAttackButton.setText("Local Network Attack");
        }
        return jLocalNetworkAttackButton;
    }

    private JButton getjSingleTargetAttackButton(){
        if (jSingleTargetAttackButton == null){
            jSingleTargetAttackButton = new JButton();
            jSingleTargetAttackButton.setBounds(200, 280, 160, 50);
            jSingleTargetAttackButton.setText("Single Target Attack");
        }
        return jSingleTargetAttackButton;
    }

    private JButton getjCancelAttackButton(){
        if (jCancelAttackButton == null){
            jCancelAttackButton = new JButton();
            jCancelAttackButton.setBounds(20, 340, 160, 50);
            jCancelAttackButton.setText("Cancel Attack");
        }
        return jCancelAttackButton;
    }

    private JButton getjExitButton(){
        if (jExitButton == null){
            jExitButton = new JButton();
            jExitButton.setBounds(200, 340, 160, 50);
            jExitButton.setText("EXIT");
        }
        return jExitButton;
    }

    public static void main(String[] args)
    {
        UserInterface w = new UserInterface();
        w.setVisible(true);

    }
}
