/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.scene;

import au.com.rmit.Game2dEngine.scene.Scene;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ricolwang
 */
public class SpaceShipScene extends Scene implements KeyListener
{

    public SpaceShipScene()
    {
//        Timer aTimer;
//        aTimer = new Timer(100, new ActionListener()
//        {
//
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                EnemySpaceship aObject;
//
//                int width = getWidth();
//                int heigth = getHeight() / 10;
//
//                int x = abs(theRandom.nextInt()) % width;
//                int y = abs(theRandom.nextInt()) % heigth;
//                double velocityX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 50.0f;
//                double velocityY = theRandom.nextFloat() * 50.0f + 100.0f;
//
//                aObject = new EnemySpaceship(x, y, 50, 50, 0, velocityX, velocityY);
//
//                addSprite(aObject);
//            }
//        });
//        aTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.println("KeyTyped...");
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
