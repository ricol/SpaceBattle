package au.com.spacebattle.main;

import au.com.rmit.Game2dEngine.director.Director;
import au.com.spacebattle.scene.SpaceShipScene;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.System.exit;
import java.util.Random;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ricolwang
 */
public class FrameMain extends javax.swing.JFrame implements MouseListener, MouseMotionListener, ActionListener, KeyListener

{

    boolean bAlreadyRun;
    MouseEvent mouseEvent;
    Random theRandom = new Random();
    SpaceShipScene theScene;
    Timer theTimerForMovingSpaceShip = new Timer(200, this);

    public FrameMain()
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panelGame = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });
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
            .addGap(0, 532, Short.MAX_VALUE)
        );

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnStartActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnStart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Director.getSharedInstance().setParent(this.panelGame);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowActivated
    {//GEN-HEADEREND:event_formWindowActivated
        this.addKeyListener(this);
        this.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        if (theScene == null)
        {
            this.launchGame();
        } else
        {
            theScene.pause();
        }

        if (theScene.isScenePaused())
        {
            btnStart.setText("Continue");
        } else
        {
            btnStart.setText("Pause");
        }

        this.requestFocus();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        exit(0);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        if (theScene != null)
        {
            theScene.adjustLabelPos();
        }
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnStart;
    private javax.swing.JPanel panelGame;
    // End of variables declaration//GEN-END:variables

    void launchGame()
    {
        this.panelGame.addMouseListener(this);
        this.panelGame.addMouseMotionListener(this);

        this.panelGame.setFocusable(true);
        this.panelGame.requestFocusInWindow();

        this.theScene = new SpaceShipScene();
        Director.getSharedInstance().setParent(this.panelGame);
        Director.getSharedInstance().showScene(theScene);
        this.theScene.gameStart();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            if (this.theScene.bGameRunning)
            {
                theScene.theShip.bAutoshot = true;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (this.theScene.bGameRunning)
            {
                theScene.theShip.openLaser();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            if (this.theScene.bGameRunning)
            {
                theScene.theShip.bAutoshot = false;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.mouseEvent = e;

        if (this.theScene.bGameRunning)
        {
            if (theScene.theShip != null)
            {
                if (this.mouseEvent != null)
                {
                    theScene.theShip.moveToXYInSequence(this.mouseEvent.getX(), this.mouseEvent.getY(), 0.06f);
//                    theScene.theShip.moveToXY(this.mouseEvent.getX(), this.mouseEvent.getY());
                }
            }
        }
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
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.theTimerForMovingSpaceShip))
        {
            if (this.theScene.bGameRunning)
            {
                if (theScene.theShip != null)
                {
                    if (this.mouseEvent != null)
                    {
                        theScene.theShip.moveToXYInSequence(this.mouseEvent.getX(), this.mouseEvent.getY(), 0.1f);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if (theScene.bGameRunning)
            {
                theScene.gameEnd();
            } else
            {
                theScene.gameStart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
