package poo1labo8;

import java.awt.Container;
import javax.swing.JFrame;

public class POO1Labo8 {
/*
    private static final String TITLEGAMEMAINFRAME = "Jeu de hasard";
    private static JFrame gameMainFraime;
*/
    public static void main(String[] args) {
        //gameMainFraime = gameMainFrameFactory();
       // gameMainFraime.setVisible(true);
       GameMainFraime gmf = new GameMainFraime();
       gmf.setVisible(true);
    }
/*
    private static JFrame gameMainFrameFactory() {
        JFrame gameMainFrame = new JFrame();
        gameMainFrame.setSize(300, 200);
        gameMainFrame.setTitle(TITLEGAMEMAINFRAME);
        gameMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return gameMainFrame;
    }

    private static Container gameContainerMainFrameFactory() {
        Container container = getContentPane();
        return container;
    }*/

}
