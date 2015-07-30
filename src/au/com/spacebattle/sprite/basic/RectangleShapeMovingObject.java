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
public class RectangleShapeMovingObject extends MovingObject
{

    public RectangleShapeMovingObject(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }
    
    @Override
    public boolean collideWith(Sprite target)
    {
        return super.rectangleOverlaps(target);
    }
}
