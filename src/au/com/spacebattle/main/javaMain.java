package au.com.spacebattle.main;

import au.com.philology.common.JavaTheme;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ricolwang
 */
public class javaMain
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JavaTheme.setLookAndFeel(JavaTheme.LOOKANDFEEL_MOTIF);
                new FrameMain().setVisible(true);
            }
        });
    }
}
