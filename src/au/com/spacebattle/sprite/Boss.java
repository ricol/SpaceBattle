/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite;

import au.com.rmit.Game2dEngine.action.ExpandByAction;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Boss extends Enemy
{

    public Boss(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public Boss()
    {
        super("boss-enemy.png");

        this.lifetime = abs(theRandom.nextInt()) % 5 + 5;

        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 3 + 2;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        this.addAction(aAction);
    }
}
