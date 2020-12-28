/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.other;

import com.wang.Game2dEngine.action.ExpandByAction;
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Sprite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;

/**
 *
 * @author ricolwang
 */
public class FriendFire extends Sprite
{

    public FriendFire(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

        this.bCustomDrawing = true;
        this.setBlue(255);
        this.bDeadIfNoActions = true;

        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(10, 0.05f);
        this.addAction(aAction);
    }

    public FriendFire()
    {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void onCustomDraw(IEngineGraphics theEngineGraphics)
    {
        super.onCustomDraw(theEngineGraphics); //To change body of generated methods, choose Tools | Templates.

        Color blackTransparent = new Color(0, 0, 0, 0);
        theEngineGraphics.setColor(blackTransparent);
        theEngineGraphics.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        theEngineGraphics.setColor(this.getColor());
        theEngineGraphics.setStroke(new BasicStroke(3, 2, 1));

        Polygon p = new Polygon(new int[]
        {
            (int) getWidth() / 2, 0, (int) getWidth()
        }, new int[]
        {
            0, (int) getHeight() - 1, (int) getHeight() - 2
        }, 3);

        theEngineGraphics.drawPolygon(p);
    }
}
