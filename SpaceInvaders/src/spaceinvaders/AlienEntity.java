package spaceinvaders;
public class AlienEntity extends Entity{
    private double moveSpeed = 95;
    private SpaceInvaders game;
    
    public AlienEntity(SpaceInvaders game,String ref, int x, int y){
        super(ref,x,y);
        dx = -moveSpeed;
    }
    public void move(long delta){
        if((dx < 0) && (x < 10)){
        game.updateLogic();
    }
        
        if((dx > 0) && (x > 750)){
        game.updateLogic();
        
    }
        super.move(delta);
}
    public void doLogic(){
        dx = -dx;
        y += 10;
        if (y > 570){
            game.notifyDeath();
        }
    }
    
    public void collidedWith(Entity other){   
    }
}
