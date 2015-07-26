/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.other;

import au.com.rmit.Game2dEngine.action.ExpandByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class Sheld extends Sprite
{

    public Sheld(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

        this.bCustomDrawing = true;
        this.setRed(255);
        this.bDeadIfNoActions = true;

        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(300, 0.5f);
        this.addAction(aAction);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        Color blackTransparent = new Color(0, 0, 0, 0);
        theGraphics2D.setColor(blackTransparent);
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        theGraphics2D.setColor(this.getColor());
        theGraphics2D.setStroke(new BasicStroke(5, 2, 1));
        theGraphics2D.drawArc(0, 0, (int) getWidth(), (int) getHeight(), 0, 360);
    }

}
