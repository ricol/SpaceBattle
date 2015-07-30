/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.basic;

import au.com.rmit.Game2dEngine.node.Sprite;

/**
 *
 * @author ricolwang
 */
public abstract class MovingObject extends Sprite
{

    public MovingObject(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

//        this.bDrawCircle = true;
//        this.bDrawFrame = true;
    }

}
