/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.other;

import au.com.rmit.Game2dEngine.action.ExpandByAction;
import au.com.rmit.Game2dEngine.node.MovingSprite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author ricolwang
 */
public class EnemyFire extends MovingSprite
{

    public EnemyFire(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

        this.bCustomDrawing = true;
        this.red = 255;
        this.bDeadIfNoActions = true;

        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(10, 0.05f);
        this.addAction(aAction);
    }

    public EnemyFire()
    {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        Color blackTransparent = new Color(0, 0, 0, 0);
        theGraphics2D.setColor(blackTransparent);
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        Color theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
        theGraphics2D.setColor(theColor);
        theGraphics2D.setStroke(new BasicStroke(3, 2, 1));
        Polygon p = new Polygon(new int[]
        {
            0, (int) getWidth() / 2, (int) getWidth()
        }, new int[]
        {
            0, (int) getHeight() - 2, 0
        }, 3);

        theGraphics2D.drawPolygon(p);
    }
}
