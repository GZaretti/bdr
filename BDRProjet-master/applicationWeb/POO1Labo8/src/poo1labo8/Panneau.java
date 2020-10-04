/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo1labo8;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ZEED
 */
public class Panneau extends JPanel {

    private static final Color COULEURTEXTFILE = Color.yellow;
    private JLabel lbl;

    public Panneau(Container c) {
        setBackground(COULEURTEXTFILE);
        lbl = new JLabel();
        add(lbl);
        c.add(this);
    }

    public JLabel getLabel() {
        return lbl;
    }

  

}
