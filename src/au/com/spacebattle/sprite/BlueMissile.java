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
public class BlueMissile extends Missile
{

    public BlueMissile(double x, double y, double mass, double velocityX, double velocityY)
    {
        super(x, y, 24, 62, mass, velocityX, velocityY);
        
        this.setImage("blue-missile.png");
        this.lifetime = 5;
    }

}
