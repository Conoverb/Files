/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;
import java.awt.Image;
import java.awt.Graphics;
public class Sprite {
    private Image image;
    public Sprite(Image image){
        this.image = image;
    }
    
    public int getWidth(){
        return image.getWidth(null);
    }
    public int getHeight(){
        return image.getHeight(null);
    }
    public void draw(Graphics g, int x, int y){
        g.drawImage(image, x, y, null);
    }
}
