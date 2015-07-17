/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.scene;

import au.com.rmit.Game2dEngine.action.MoveXYByAction;
import au.com.rmit.Game2dEngine.action.MoveXYToAction;
import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.spacebattle.sprite.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class SpaceShipScene extends Scene
{

    Gravity g = new Gravity(0, 0);
    float delta = 0;
    float GRAVITY_VALUE = 500;

    public SpaceShipScene()
    {
        Timer theTimerForGravity = new Timer(10, new ActionListener()
        {

            @Override
            public synchronized void actionPerformed(ActionEvent e)
            {
                g.GX = (float) sin(delta) * GRAVITY_VALUE;
                g.GY = (float) cos(delta) * GRAVITY_VALUE;
                delta += 0.1;
            }

        });

        theTimerForGravity.start();

        this.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                MySpaceship aObject;

                int width = 100;
                int height = 100;
                aObject = new MySpaceship(e.getX() - width / 2, e.getY() - height / 2, width, height, 0, 0, 0);
                addSprite(aObject);
                aObject.lifetime = Sprite.EVER;
                aObject.bDeadIfNoActions = true;
//                aObject.applyGravity(g);

                for (int i = 0; i < 10; i++)
                {
                    {
                        MoveXYByAction aMoveXYByAction = new MoveXYByAction();

                        int indexX = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
                        int indexY = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
                        int randomX = indexX * (int) (abs(theRandom.nextInt()) % 400);
                        int randomY = indexY * (int) (abs(theRandom.nextInt()) % 400);
                        int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
                        int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);

                        System.out.println("randomX: " + randomX + " in " + timeX + "seconds");
                        System.out.println("randomY: " + randomY + " in " + timeY + "seconds");

                        aMoveXYByAction.moveXBy(randomX, timeX);
                        aMoveXYByAction.moveYBy(randomY, timeY);

                        aObject.addAction(aMoveXYByAction);
                    }

                    {
                        MoveXYToAction aMoveXYToAction = new MoveXYToAction(aObject);

                        int randomX = (int) (abs(theRandom.nextInt()) % getWidth());
                        int randomY = (int) (abs(theRandom.nextInt()) % getHeight());
                        int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
                        int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);

                        System.out.println("MoveXTo: " + randomX + " in " + timeX + "seconds");
                        System.out.println("MoveYTo: " + randomY + " in " + timeY + "seconds");

                        aMoveXYToAction.moveXTo(randomX, timeX);
                        aMoveXYToAction.moveYTo(randomY, timeY);

                        aObject.addAction(aMoveXYToAction);
                    }
                }
//                for (int i = 0; i < 10; i++)
//                {
//                    ScaleWidthHeightByAction aScaleWidthHeightByAction = new ScaleWidthHeightByAction();
//
//                    int indexX = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
//                    int indexY = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
//                    int randomX = indexX * (int) (abs(theRandom.nextInt()) % 300);
//                    int randomY = indexY * (int) (abs(theRandom.nextInt()) % 300);
//                    int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                    int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);
//
//                    System.out.println("Scale Width By: " + randomX + " in " + timeX + "seconds");
//                    System.out.println("Scale Height By: " + randomY + " in " + timeY + "seconds");
//
//                    aScaleWidthHeightByAction.scaleWidthBy(randomX, timeX);
//                    aScaleWidthHeightByAction.scaleHeightBy(randomY, timeY);
//
//                    aObject.addAction(aScaleWidthHeightByAction);
//                }

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }
        });

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

}
