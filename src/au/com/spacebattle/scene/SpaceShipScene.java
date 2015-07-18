/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.scene;

import au.com.rmit.Game2dEngine.action.AlphaByAction;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveXToAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.action.MoveYToAction;
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
                aObject.lifetime = Sprite.EVER;
                aObject.bDeadIfNoActions = true;
//                aObject.applyGravity(g);

                {
                    MoveXByAction aAction = new MoveXByAction();
                    int indexX = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
                    int randomX = indexX * (int) (abs(theRandom.nextInt()) % 400);
                    int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("MoveXBy: " + randomX + " in " + timeX + "seconds");
                    aAction.moveXBy(randomX, timeX);
                    aObject.addAction(aAction);
                }

                {
                    MoveYByAction aAction = new MoveYByAction();
                    int indexY = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
                    int randomY = indexY * (int) (abs(theRandom.nextInt()) % 400);
                    int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("MoveYBy: " + randomY + " in " + timeY + "seconds");
                    aAction.moveYBy(randomY, timeY);
                    aObject.addAction(aAction);
                }

                {
                    MoveXToAction aAction = new MoveXToAction(aObject);
                    int randomX = (int) (abs(theRandom.nextInt()) % getWidth());
                    int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("MoveXTo: " + randomX + " in " + timeX + "seconds");
                    aAction.moveXTo(randomX, timeX);
                    aObject.addAction(aAction);
                }

                {
                    MoveYToAction aAction = new MoveYToAction(aObject);
                    int randomY = (int) (abs(theRandom.nextInt()) % getHeight());
                    int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("MoveYTo: " + randomY + " in " + timeY + "seconds");
                    aAction.moveYTo(randomY, timeY);
                    aObject.addAction(aAction);
                }
//                {
//                    ScaleWidthByAction aAction = new ScaleWidthByAction();
//                    int indexX = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
//                    int randomX = indexX * (int) (abs(theRandom.nextInt()) % 300);
//                    int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                    System.out.println("Scale Width By: " + randomX + " in " + timeX + "seconds");
//                    aAction.scaleWidthBy(randomX, timeX);
//                    aObject.addAction(aAction);
//                }
//
//                {
//                    ScaleHeightByAction aAction = new ScaleHeightByAction();
//                    int indexY = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
//                    int randomY = indexY * (int) (abs(theRandom.nextInt()) % 300);
//                    int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                    System.out.println("Scale Height By: " + randomY + " in " + timeY + "seconds");
//                    aAction.scaleHeightBy(randomY, timeY);
//                    aObject.addAction(aAction);
//                }
//
//                {
//                    ScaleWidthToAction aAction = new ScaleWidthToAction(aObject);
//                    int randomX = (int) (abs(theRandom.nextInt()) % 300);
//                    int timeX = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                    System.out.println("Scale Width To: " + randomX + " in " + timeX + "seconds");
//                    aAction.ScaleWidthTo(randomX, timeX);
//                    aObject.addAction(aAction);
//                }
//
//                {
//                    ScaleHeightToAction aAction = new ScaleHeightToAction(aObject);
//                    int randomY = (int) (abs(theRandom.nextInt()) % 300);
//                    int timeY = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                    System.out.println("Scale Height To: " + randomY + " in " + timeY + "seconds");
//                    aAction.ScaleHeightTo(randomY, timeY);
//                    aObject.addAction(aAction);
//                }

//                    {
//                        RotateByAction aAction = new RotateByAction();
//                        int index = (int) power(-1, (int) abs(theRandom.nextInt() % 10));
//                        float angle = (float) (index * (abs(theRandom.nextFloat()) * 2 * Math.PI));
//                        int time = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                        System.out.println("Rotate By: " + angle + " in " + time + "seconds");
//                        aAction.rotateBy(angle, time);
//                        aObject.addAction(aAction);
//                    }
//                    {
//                        RotateToAction aAction = new RotateToAction(aObject);
//                        float angle = (float) (abs(theRandom.nextFloat()) * 2 * Math.PI);
//                        int time = (int) (abs(theRandom.nextInt()) % 2 + 2);
//                        System.out.println("Rotate To: " + angle + " in " + time + "seconds");
//                        aAction.rotateTo(angle, time);
//                        aObject.addAction(aAction);
//                    }
                {
                    AlphaByAction aAction = new AlphaByAction();
                    float alpha = -1 * (float) ((abs(theRandom.nextInt()) % 10) / 10.0);
                    int time = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("AlphaBy: " + alpha + " in " + time + "seconds");
                    aAction.alphaBy(alpha, time);
                    aObject.addAction(aAction);
                }

                {
                    AlphaToAction aAction = new AlphaToAction(aObject);
                    float alpha = (float) ((abs(theRandom.nextInt()) % 10) / 10.0);
                    int time = (int) (abs(theRandom.nextInt()) % 2 + 2);
                    System.out.println("AlphaTo: " + alpha + " in " + time + "seconds");
                    aAction.alphaTo(alpha, time);
                    aObject.addAction(aAction);
                }

                addSprite(aObject);
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
        }
        );

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
