/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.other;

import au.com.rmit.Game2dEngine.node.MovingSprite;

/**
 *
 * @author ricolwang
 */
public class ExpodeParticle extends MovingSprite
{

    public ExpodeParticle(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        
    }
    
    public ExpodeParticle()
    {
        this(0, 0, 7, 7, 0, 0, 0);
    }

}
