/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon;

import au.com.rmit.Game2dEngine.painter.interfaces.IEngineGraphics;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.spacebattle.sprite.spaceship.Spaceship;

/**
 *
 * @author ricolwang
 */
public abstract class Weapon extends Sprite
{

    Spaceship theShip;

    public Weapon(Spaceship theShip)
    {
        this.theShip = theShip;
        this.bCustomDrawing = true;

        this.setWidth(10);
        this.setHeight(10);
        this.setBlue(255);
    }

    @Override
    public void onCustomDraw(IEngineGraphics theEngineGraphics)
    {
        super.onCustomDraw(theEngineGraphics);

//        theEngineGraphics.setColor(this.getColor());
//        theEngineGraphics.fillRect(0, 0, (int) this.getWidth() - 1, (int) this.getHeight() - 1);
    }

    public void fire()
    {

    }
}
