/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite;

/**
 *
 * @author ricolwang
 */
public class RedMissile extends Missile
{

    public RedMissile(double x, double y, double mass, double velocityX, double velocityY)
    {
        super(x, y, 32, 32, mass, velocityX, velocityY);
        
        this.setImage("red-missile.png");
        this.lifetime = 5;
    }
    
}
