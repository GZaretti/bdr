/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo1labo8;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author ZEED
 */
public class GameMainFraime extends JFrame {

    private static final String TITLEGAMEMAINFRAME = "Jeu de hasard";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
   
    
    public GameMainFraime() {
        setSize(WIDTH, HEIGHT);
        setTitle(TITLEGAMEMAINFRAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Container c = getContentPane();
        c.setLayout(new GridLayout(4, 3, 50, 20));
   
       PaTirageUn = new Panneau(c);
       PaTirageDeux = new Panneau(c);
       PaTirageTrois = new Panneau(c);
       
        
    }
    
    private JButton btTirageUn;
    private JButton btTirageDeux;
    private JButton btTirageTrois;
    private JButton btStart;
    
    
    private JLabel lblEssai;
    private JLabel lblResultat;

    
            
    
    private Panneau PaTirageUn;
    private Panneau PaTirageDeux;
    private Panneau PaTirageTrois;
    

}
