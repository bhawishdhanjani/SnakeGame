package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 125;
    int x[] = new int[GAME_UNIT];
    int y[] = new int[GAME_UNIT];
    int bodyPart = 6;
    int appleX;
    int appleY;
    int appleEaten = 0;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random = new Random();
    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyPart; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(random.nextInt(250),random.nextInt(250),random.nextInt(250)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD , 35));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score : "+appleEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score : "+appleEaten))/2,g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

    }

    public void move(){
        for (int i = bodyPart ; i>0 ;  i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
               break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            appleEaten++;
            bodyPart++;
            newApple();
        }

    }
    public void checkCollision(){
        for (int i = bodyPart ; i>0 ; i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
            if(x[0]<0 || x[0]>(SCREEN_WIDTH-UNIT_SIZE)){
                running = false;
            }
            if(y[0]<0 || y[0]>(SCREEN_HEIGHT-UNIT_SIZE)){
                running = false;
            }
        }

    }
    public void gameOver(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD , 35));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Score : "+appleEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score : "+appleEaten))/2,g.getFont().getSize());

        //Game Over
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD , 70));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-fontMetrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
        this.add(GameFrame.button);
        GameFrame.button.setBounds(250,330,100,40);
        GameFrame.button.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                        direction = 'L' ;
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                        direction = 'R' ;
                break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                        direction = 'D' ;
                break;
                case KeyEvent.VK_UP:
                    if(direction!='D')
                        direction = 'U' ;
                    break;

            }
        }
    }









}
