/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite;

import au.com.rmit.Game2dEngine.node.MovingSprite;

/**
 *
 * @author ricolwang
 */
public class Spaceship extends MovingSprite
{

    public Spaceship(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }
    
}
