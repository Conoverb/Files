package snakegame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;




public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    
    private int[] enemyXPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750};
    private int[] enemyYPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    //declaring movement keywords
    
    Random random = new Random();
    
    private int XPos = random.nextInt(32);
    private int YPos = random.nextInt(23);
    private int score = 0;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean dead = false;
    
    //declaring image keywords
    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private ImageIcon titleImage;
    private ImageIcon snakeImage;
    private ImageIcon enemyImage;
    
    
    
    private Timer timer;
    private int delay = 100;
    private int snakeLength = 1;
    private int move = 0;
    
    public GamePlay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paint(Graphics g)
    {
        if(move == 0)
        {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;
            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }
        //draw title image border
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        
        //draw the title image
        titleImage = new ImageIcon("titleImage.jpg");
        titleImage.paintIcon(this, g, 25, 11);
        
        //draw border for gameplay
        g.setColor(Color.RED);
        g.drawRect(74,74,751,577);
        
        //draw background for the gameplay
        g.setColor(Color.pink);
        g.fillRect(75, 75, 750, 575);
        
        //draw score
        g.setColor(Color.RED);
        g.setFont(new Font("Calibri", Font.BOLD,20));
        g.drawString("Scores: "+ score, 780,30);
        
        //draw length
        g.setColor(Color.RED);
        g.setFont(new Font("Calibri", Font.BOLD, 20));
        g.drawString("Length: " + snakeLength, 780, 50);
        
        rightMouth = new ImageIcon("rightmouth.png");
        rightMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        for(int a = 0; a < snakeLength; a++)
        {
            //moves right
            if(a==0 && right)
            {
                rightMouth = new ImageIcon("rightmouth.png");
                rightMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            //moves left
            if(a==0 && left)
            {
                leftMouth = new ImageIcon("leftmouth.png");
                leftMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            //moves up
            if(a==0 && up)
            {
                upMouth = new ImageIcon("upmouth.png");
                upMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            //moves down
            if(a==0 && down)
            {
                downMouth = new ImageIcon("downmouth.png");
                downMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            
            if(a != 0)
            {
                snakeImage = new ImageIcon("snakeimage.png");
                snakeImage.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
        }
        enemyImage = new ImageIcon("enemy.png");
        
        if((enemyXPos[XPos] == snakeXLength[0]) && enemyYPos[YPos] == snakeYLength[0])
        {
            score++;
            snakeLength++;
            XPos = random.nextInt(34);
            YPos = random.nextInt(23);
        }
        enemyImage.paintIcon(this, g, enemyXPos[XPos], enemyYPos[YPos]);
        for(int b = 1; b < snakeLength; b++)
        {
            if(snakeXLength[b] == snakeXLength[0] && snakeYLength[b] == snakeYLength[0])
            {
            dead = true;
            right = false;
            left = false;
            up = false;
            down = false;
            
            g.setColor(Color.black);
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("Game Over", 300, 300);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press Space to Restart", 325, 340);
            }
        }

          
        g.dispose();     
    }
   public void actionPerformed(ActionEvent e){
       timer.start();
       if(right)
       {
           for(int r = snakeLength-1; r >= 0; r--)
           {
           snakeYLength[r+1] = snakeYLength[r];
           }
           for(int r = snakeLength; r >= 0; r--)
           {
               if(r == 0)
               {
               snakeXLength[r] = snakeXLength[r]+25;
               }
               else
               {
                   snakeXLength[r] = snakeXLength[r-1];
               }
               if(snakeXLength[r] > 800)
               {
                   snakeXLength[r] = 75;
               }
           }
           repaint();
       }
       if(left)
       {
           for(int r = snakeLength-1; r >= 0; r--)
           {
           snakeYLength[r+1] = snakeYLength[r];
           }
           for(int r = snakeLength; r >= 0; r--)
           {
               if(r == 0)
               {
               snakeXLength[r] = snakeXLength[r]-25;
               }
               else
               {
                   snakeXLength[r] = snakeXLength[r-1];
               }
               if(snakeXLength[r] < 75)
               {
                   snakeXLength[r] = 800;
               }
           }
           repaint(); 
           
       }
       if(up)
       {
           for(int r = snakeLength-1; r >= 0; r--)
           {
           snakeXLength[r+1] = snakeXLength[r];
           }
           for(int r = snakeLength; r >= 0; r--)
           {
               if(r == 0)
               {
               snakeYLength[r] = snakeYLength[r]-25;
               }
               else
               {
                   snakeYLength[r] = snakeYLength[r-1];
               }
               if(snakeYLength[r] < 75)
               {
                   snakeYLength[r] = 625;
               }
           }
           repaint(); 
       }
       if(down)
       {
           for(int r = snakeLength-1; r >= 0; r--)
           {
           snakeXLength[r+1] = snakeXLength[r];
           }
           for(int r = snakeLength; r >= 0; r--)
           {
               if(r == 0)
               {
               snakeYLength[r] = snakeYLength[r]+25;
               }
               else
               {
                   snakeYLength[r] = snakeYLength[r-1];
               }
               if(snakeYLength[r] > 625)
               {
                   snakeYLength[r] = 75;
               }
           }
           repaint();   
       }
   }
   public void keyTyped(KeyEvent e){
       
   }

   public void keyPressed(KeyEvent e){
       if(e.getKeyCode() == KeyEvent.VK_SPACE && dead == true)
       {
           move = 0;
           score = 0;
           snakeLength = 3;
           dead = false;
           repaint();
       }
       if(e.getKeyCode() == KeyEvent.VK_RIGHT && dead == false)
       {
           move++;
           right = true;
           if(!left)
           {
               right = true;
           }
           else
           {
               right = false;
               left = true;
           }
           up = false;
           down = false;
       }
       if(e.getKeyCode() == KeyEvent.VK_LEFT && dead == false)
       {
           move++;
           left = true;
           if(!right)
           {
               left = true;
           }
           else
           {
               right = true;
               left = false;
           }
           up = false;
           down = false;
       }
       if(e.getKeyCode() == KeyEvent.VK_UP && dead == false)
       {
           move++;
           up = true;
            if(!down)
           {
               up = true;
           }
           else
           {
               up = false;
               down = true;
           }
           right = false;
           left = false;
       }
       if(e.getKeyCode() == KeyEvent.VK_DOWN && dead == false)
       {
           move++;
           down = true;
            if(!up)
           {
               down = true;
           }
           else
           {
               up = true;
               down = false;
           }
           right = false;
           left = false;
       }
   }
   public void keyReleased(KeyEvent e){
       
   }
}
