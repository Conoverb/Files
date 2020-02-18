package spaceinvaders;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceInvaders extends Canvas {
    private BufferStrategy strategy;
    private boolean gameRunning = true;
    private ArrayList entities = new ArrayList();
    private ArrayList removeList = new ArrayList();
    private Entity ship;
    private double moveSpeed = 300;
    private long lastFire = 0;
    private long firingInterval = 500;
    private int alienCount;
    
    private String message = "";
    private boolean waitingForKeyPress = true;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;
    private boolean logicRequiredThisLoop = false;
    
    public SpaceInvaders(){
        JFrame container = new JFrame("Space Invaders!");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);
        
        setBounds(0,0,800,600);
        panel.add(this);
        
        setIgnoreRepaint(true);
        
        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        
        
        container.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    
    addKeyListener(new KeyInputHandler());
    requestFocus();
    createBufferStrategy(2);
    strategy = getBufferStrategy();
    initEntities();
    }
    private void startGame(){
        entities.clear();
        initEntities();
        leftPressed = false;
        rightPressed = false;
        firePressed = false;
    }
    
    private void initEntities(){
        ship = new ShotEntity(this,"ship.gif",370,550);
        entities.add(ship);
        alienCount = 0;
        for(int row = 0; row <5;row++){
            for(int x =0;x<12;x++){
                Entity alien = new AlienEntity(this, "alien.gif",100+(x*50),(50)+row*30);
                entities.add(alien);
                alienCount++;
            }
        }
    }
public void updateLogic(){
    logicRequiredThisLoop = true;
}
public void removeEntity(Entity entity){
    removeList.add(entity);
}
public void notifyDeath(){
    message = "Oh no! they got you, try again?";
    waitingForKeyPress = true;
}
public void notifyWin(){
    message = "well done you win";
    waitingForKeyPress = true;
}
public void notifyAlienKilled(){
    alienCount--;
    if(alienCount == 0){
        notifyWin();
    }
    for(int i =0;i<entities.size();i++){
        Entity entity = (Entity) entities.get(i);
        if(entity instanceof AlienEntity){
            entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
        }
    }
}
    public void tryToFire(){
        if(System.currentTimeMillis() - lastFire <firingInterval) {
            return;
        }
    
    lastFire = System.currentTimeMillis();
    ShotEntity shot = new ShotEntity(this,"shot.gif",ship.getX()+10,ship.getY()-30);
    entities.add(shot);
    }
    
    public void gameLoop(){
        long lastLoopTime = System.currentTimeMillis();
        while(gameRunning){
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,800,600);
            if(!waitingForKeyPress){
                for(int i = 0; i<entities.size();i++){
                    Entity entity = (Entity) entities.get(i);
                    entity.move(delta);
                }
            }
            for(int i = 0;i<entities.size();i++){
                Entity entity = (Entity) entities.get(i);
                entity.draw(g);
            }
            for(int p=0;p<entities.size();p++){
                for(int s=p+1;s<entities.size();s++){
                    Entity me = (Entity) entities.get(p);
                    Entity him = (Entity) entities.get(s);
                    if(me.collidesWith(him)){
                        me.collidedWith(him);
                        him.collidedWith(me);
                    }
                }
            }
            entities.removeAll(removeList);
            removeList.clear();
            if(logicRequiredThisLoop){
                for(int i =0;i<entities.size();i++){
                    Entity entity = (Entity) entities.get(i);
                    entity.doLogic();
                }
                logicRequiredThisLoop = false;
            }
        
            if(waitingForKeyPress){
                g.setColor(Color.black);
                g.drawString(message, (800-g.getFontMetrics().stringWidth(message))/2, 250);
                g.drawString("Press any key", (800-g.getFontMetrics().stringWidth("press any key"))/2,300);
            }
            
            g.dispose();
            strategy.show();
            ship.setHorizontalMovement(0);
            if((leftPressed) && (!rightPressed)){
                ship.setHorizontalMovement(-moveSpeed);               
            }else if ((rightPressed) && (!leftPressed)){
                ship.setHorizontalMovement(moveSpeed);
            }
            if(firePressed){
                tryToFire();
            }
            try{ 
                Thread.sleep(10);} catch(Exception e){
               }                
            }
        }
   

        private class KeyInputHandler extends KeyAdapter{
            private int pressCount = 1;
            public void keyPressed(KeyEvent e){
                if(waitingForKeyPress){
                    return;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    leftPressed = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    rightPressed = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    firePressed = true;
                }
            }

                public void keyReleased(KeyEvent e){
                    if(waitingForKeyPress){
                        return;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    leftPressed = false;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    rightPressed = false;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    firePressed = false;
                    }            
                }
                public void keyTyped(KeyEvent e){
                 if(waitingForKeyPress){
                     if(pressCount == 1){
                         waitingForKeyPress = false;
                         startGame();
                         pressCount = 0;
                     } else {
                         pressCount++;
                     }
                 }   
                 if(e.getKeyChar() == 27){
                     System.exit(0);
                }
            }
        }                
    public static void main(String argv[]){
        SpaceInvaders g = new SpaceInvaders();
        g.gameLoop();
    }
}

