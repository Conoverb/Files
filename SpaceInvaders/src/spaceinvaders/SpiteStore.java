
package spaceinvaders;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpiteStore {
    private static SpiteStore single = new SpiteStore();
    public static SpiteStore get(){
        return single;
    }
    private HashMap sprites = new HashMap();
    public Sprite getSprite(String ref){
        if(sprites.get(ref) != null){
            return (Sprite)sprites.get(ref);
        }
        BufferedImage sourceImage = null;
        try{
            URL url = this.getClass().getClassLoader().getResource(ref);
            if(url == null){
                fail("Can't find ref: " + ref);
            }
            sourceImage = ImageIO.read(url);
        }
        catch(IOException e){
            fail("Failed to load: " + ref);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage,0,0,null);
        Sprite sprite = new Sprite(image);
        sprites.put(ref, sprite);
        return sprite;
    }
    public void fail(String message){
        System.err.println(message);
        System.exit(0);
    }
}