package au.com.spacebattle.main;

import au.com.rmit.Game2dEngine.action.MoveCentreXToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreYToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.director.Director;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.scene.TestScene;
import au.com.spacebattle.sprite.Boss;
import au.com.spacebattle.sprite.Enemy;
import au.com.spacebattle.sprite.Missile;
import au.com.spacebattle.sprite.Sheld;
import au.com.spacebattle.sprite.Spaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.Math.abs;
import java.util.Random;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Philology
 */
public class FrameMain extends javax.swing.JFrame implements KeyListener, ActionListener, MouseListener, MouseMotionListener
{

    Random theRandom = new Random();
    SpaceShipScene theScene;
    Spaceship theShip;

    char c;
    Timer keyTimer = new Timer(200, this);
    Timer timerForEnemy = new Timer(100, this);
    Timer timerForFire = new Timer(10, this);
    Timer timerForFirMainWeapon = new Timer(100, this);

    public FrameMain()
    {
        initComponents();

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent evt)
            {
                int x = 10, y = 10;
                panelGame.setLocation(x, y);
                panelGame.setSize(new Dimension(getWidth() - 2 * x, getHeight() - 4 * y));
                Director.getSharedInstance().updatePosition(0, 0, panelGame.getWidth(), panelGame.getHeight());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panelGame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt)
            {
                formWindowActivated(evt);
            }
        });

        panelGame.setBackground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout panelGameLayout = new javax.swing.GroupLayout(panelGame);
        panelGame.setLayout(panelGameLayout);
        panelGameLayout.setHorizontalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        panelGameLayout.setVerticalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Director.getSharedInstance().setParent(this.panelGame);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowActivated
    {//GEN-HEADEREND:event_formWindowActivated
        // TODO add your handling code here:
//        this.launchTest();
        this.launchGame();

    }//GEN-LAST:event_formWindowActivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelGame;
    // End of variables declaration//GEN-END:variables

    void launchGame()
    {
        this.panelGame.addKeyListener(this);
        this.panelGame.addMouseListener(this);
        this.panelGame.addMouseMotionListener(this);

        this.panelGame.setFocusable(true);
        this.panelGame.requestFocusInWindow();

        this.theScene = new SpaceShipScene();
        Director.getSharedInstance().setParent(this.panelGame);
        Director.getSharedInstance().showScene(theScene);

        this.theShip = new Spaceship("my-spaceship.png");
        this.theShip.lifetime = Sprite.EVER;

        this.theShip.setX(this.getWidth() / 2.0);
        this.theShip.setY(this.getHeight() * (3 / 4.0));

        this.theScene.addSprite(theShip);

        this.timerForEnemy.start();
        this.timerForFire.start();
        this.timerForFirMainWeapon.start();
    }

    void launchTest()
    {
        Director.getSharedInstance().setParent(this.panelGame);
        Director.getSharedInstance().showScene(new TestScene());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        c = e.getKeyChar();
        this.keyTimer.start();
        this.fireKey(c);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        this.keyTimer.stop();
    }

    void fireKey(char e)
    {
        float value = 50;
        float duration = 0.2f;

        if (e == 'a')
        {
            //move left
            MoveXByAction aAction = new MoveXByAction();
            aAction.moveXBy(-value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Left Action");
        } else if (e == 'd')
        {
            //move right
            MoveXByAction aAction = new MoveXByAction();
            aAction.moveXBy(value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Right Action");
        } else if (e == 'w')
        {
            //move up
            MoveYByAction aAction = new MoveYByAction();
            aAction.moveYBy(-value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Up Action");
        } else if (e == 's')
        {
            //move down
            MoveYByAction aAction = new MoveYByAction();
            aAction.moveYBy(value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Down Action");
        } else if (e == 'f')
        {
            //fire
            float delta = 15;
            float speed = 1500;
            float lifetime = 0.5f;
            Missile aMissile = new Missile("red-missile.png");
            aMissile.setX(theShip.getX() - delta + 20);
            aMissile.setY(theShip.getY() + 70);
            aMissile.setVelocityY(-speed);
            aMissile.lifetime = lifetime;
            this.theScene.addSprite(aMissile);

            aMissile = new Missile("red-missile.png");
            aMissile.setX(theShip.getX() + theShip.getWidth() - delta - 10);
            aMissile.setY(theShip.getY() + 70);
            aMissile.setVelocityY(-speed);
            aMissile.lifetime = lifetime;
            this.theScene.addSprite(aMissile);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.keyTimer))
        {
            this.fireKey(c);
        } else if (e.getSource().equals(this.timerForEnemy))
        {
            if (abs(theRandom.nextInt()) % 100 > 95)
            {
                Boss aBoss = new Boss();
                boolean b = theRandom.nextBoolean();
                int index = b ? 1 : 0;
                index = (int) power(-1, index);
                int size = (int) (this.getWidth() * (1 / 4.0));

                aBoss.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
                aBoss.setY(-100);

                b = theRandom.nextBoolean();
                index = b ? 1 : 0;
                index = (int) power(-1, index);

                float velocityX = index * abs(theRandom.nextInt()) % 100 + 50;
                float velocttyY = abs(theRandom.nextInt()) % 300 + 200;

                aBoss.setVelocityX(velocityX / 2);
                aBoss.setVelocityY(velocttyY / 2);

                this.theScene.addSprite(aBoss, 5);
            } else
            {
                this.theScene.addSprite(this.createAEnemy());
            }
        } else if (e.getSource().equals(this.timerForFire))
        {
            this.fireKey('f');
        } else if (e.getSource().equals(this.timerForFirMainWeapon))
        {
            float delta = 15;
            float speed = 500;
            float lifetime = 1;
            Missile aMissile = new Missile("nuclear.png");
            aMissile.setX(theShip.getX() + theShip.getWidth() / 2 - delta);
            aMissile.setY(theShip.getY() - delta * 3);
            aMissile.setVelocityY(-speed * 1.5);
            aMissile.lifetime = lifetime;
            this.theScene.addSprite(aMissile);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
//        c = 'f';
//        this.keyTimer.start();
//        this.fireKey(c);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
//            //Blast
//            float velocity = 500;
//            float delta = 0;
//
//            while (delta < (Math.PI * 2))
//            {
//                float velocityX = (float) sin(delta) * velocity;
//                float velocityY = (float) cos(delta) * velocity;
//
//                delta += 0.1;
//
//                Missile aMissile = new Missile("red-missile.png");
//                aMissile.setX(this.theShip.getCentreX());
//                aMissile.setY(this.theShip.getCentreY());
//
//                aMissile.lifetime = 0.5;
//                aMissile.setVelocityX(velocityX);
//                aMissile.setVelocityY(velocityY);
//
//                this.theScene.addSprite(aMissile);
//            }

            //sheld up
            Sheld aSheld = new Sheld(theShip.getCentreX(), theShip.getCentreY(), 0, 0, 0, 0, 0);
            aSheld.lifetime = 1;

            theScene.addSprite(aSheld);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.keyTimer.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        double theShipCentreX = this.theShip.getCentreX();
        double theShipCentreY = this.theShip.getCentreY();

        MoveCentreXToAction aCentreXAction = new MoveCentreXToAction(theShip);
        aCentreXAction.MoveCentreXTo(x, 0);
        theShip.addAction(aCentreXAction);

        MoveCentreYToAction aCentreYAction = new MoveCentreYToAction(theShip);
        aCentreYAction.MoveCentreYTo(y, 0);
        theShip.addAction(aCentreYAction);
    }

    Enemy createAEnemy()
    {
        String[] data = new String[]
        {
            "blue-enemy.png", "purple-enemy.png", "green-enemy.png"
        };
        int index = abs(theRandom.nextInt()) % data.length;
        Enemy aEnemy = new Enemy(data[index]);

        boolean b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) power(-1, index);
        int size = (int) (this.getWidth() * (1 / 4.0));

        aEnemy.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
        aEnemy.setY(-100);

        b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) power(-1, index);

        float velocityX = index * abs(theRandom.nextInt()) % 50 + 20;
        float velocttyY = abs(theRandom.nextInt()) % 100 + 100;

        aEnemy.setVelocityX(velocityX);
        aEnemy.setVelocityY(velocttyY);
        aEnemy.lifetime = 5;

        return aEnemy;
    }

    Missile createAFriendlyMissile()
    {
        String[] data = new String[]
        {
            "blue-missile.png", "green-missile.png", "nuclear.png", "red-missile.png"
        };
        int index = abs(theRandom.nextInt()) % data.length;

        Missile aMissile = new Missile(data[index]);

        return aMissile;
    }

    Missile createARocketMissile()
    {
        Missile aMissile = new Missile("rocket.png");
        return aMissile;
    }

    Missile createAEnemyMissile()
    {
        String[] data = new String[]
        {
            "red-enemy-missile.png"
        };
        int index = abs(theRandom.nextInt()) % data.length;

        Missile aMissile = new Missile(data[index]);

        return aMissile;
    }

}
