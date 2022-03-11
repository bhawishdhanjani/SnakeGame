package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    static JButton button = new JButton("Try Again");
    GamePanel gamePanel = new GamePanel();
    public GameFrame(){
        this.add(gamePanel);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            this.remove(gamePanel);
            this.setVisible(false);
            gamePanel = new GamePanel();
            this.add(gamePanel);
            this.setVisible(true);
            SwingUtilities.updateComponentTreeUI(this);

        }

    }
}
