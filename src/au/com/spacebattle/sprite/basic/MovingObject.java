/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.basic;

import au.com.rmit.Game2dEngine.sprite.Sprite;

/**
 *
 * @author ricolwang
 */
public abstract class MovingObject extends Sprite
{

    public MovingObject(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

//        this.bDrawShape = true;
//        this.bDrawFrame = true;
        
//        this.bDrawVelocityVector = true;
//        this.DrawVelocityBase = 0.2;
    }

}
