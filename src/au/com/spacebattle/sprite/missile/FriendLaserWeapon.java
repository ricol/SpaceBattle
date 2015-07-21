/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class FriendLaserWeapon extends FriendMissile
{

    public static final int HEIGHT = 1000;
    public static final int WIDTH = 20;

    MySpaceship theShip;

    public FriendLaserWeapon(MySpaceship ship)
    {
        super(0, 0, 0, HEIGHT, 0, 0, -10000);
        this.setRed(abs(theRandom.nextInt()) % 255);
        this.setGreen(abs(theRandom.nextInt()) % 255);
        this.setBlue(abs(theRandom.nextInt()) % 255);
        this.setWidth(WIDTH * abs(theRandom.nextFloat()));
        this.setCentreX(ship.getCentreX());
        this.setCentreY(ship.getCentreY() - this.getHeight() / 2 - ship.getHeight() / 2);
        theShip = ship;

        this.layer = ship.layer;
        this.lifetime = 0.1f;

        this.bCustomDrawing = true;
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        Color blackTransparent = new Color(0, 0, 0, 0);
        theGraphics2D.setColor(blackTransparent);
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        theGraphics2D.setColor(getColor());
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
    }

    @Override
    public void onCollideWith(Sprite target)
    {
    }

}
