/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.other;

import au.com.rmit.Game2dEngine.sprite.Sprite;

/**
 *
 * @author ricolwang
 */
public class SpaceBackground extends Sprite
{

    public SpaceBackground()
    {
        super("space-background.jpg");
        this.setVelocityY(100);
        this.bDrawFrame = true;
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.theScene != null)
        {
            if (this.getY() > this.theScene.getHeight())
            {
                this.setY(-1 * (this.getHeight() + this.theScene.getHeight()));
            }
        }
    }
}
