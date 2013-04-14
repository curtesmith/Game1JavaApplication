package com.brakeen.javagamebook.userinterface;

import com.brakeen.javagamebook.test.GameCore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;


public class MouseLookTest extends GameCore 
    implements MouseMotionListener, KeyListener 
{
    private Image bgImage;
    private Robot robot;
    private Point mouseLocation;
    private Point centerLocation;
    private Point imageLocation;
    private boolean relativeMouseMode;
    private boolean isRecentering;
    
    
    public static void main(String[] args)
    {
        new MouseLookTest().run();
    }
    
    
    public void init()
    {
        super.init();
        mouseLocation = new Point();
        centerLocation = new Point();
        imageLocation = new Point();
        relativeMouseMode = true;
        isRecentering = false;
        
        try
        {
            robot = new Robot();
            recenterMouse();
            mouseLocation.x = centerLocation.x;
            mouseLocation.y = centerLocation.y;
        }
        catch(AWTException ex)
        {
            System.out.println("Couldn't create robot!");
        }
        
        Window window = screen.getFullScreenWindow();
        window.addMouseMotionListener(this);
        window.addKeyListener(this);
        bgImage = loadImage("images/background.jpg");
    }
    
    
    @Override
    public void draw(Graphics2D g) 
    {
        int w = screen.getWidth();
        int h = screen.getHeight();
        imageLocation.x %= w;
        imageLocation.y %= screen.getHeight();
        
        if(imageLocation.x < 0)
        {
            imageLocation.x += w;
        }
        
        if(imageLocation.y < 0)
        {
            imageLocation.y += screen.getHeight();
        }
        
        int x = imageLocation.x;
        int y = imageLocation.y;
        g.drawImage(bgImage, x, y, null);
        g.drawImage(bgImage, x-w, y, null);
        g.drawImage(bgImage, x, y-h, null);
        g.drawImage(bgImage, x-w, y-h, null);        
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Press space to change mouse modes.", 5, FONT_SIZE);
        g.drawString("Press Escape to exit.", 5, FONT_SIZE);
    }

    
    public synchronized void recenterMouse()
    {
        Window window = screen.getFullScreenWindow();
        
        if(robot != null && window.isShowing())
        {
            centerLocation.x = window.getWidth() / 2;
            centerLocation.y = window.getHeight() / 2;
            SwingUtilities.convertPointToScreen(centerLocation, window);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }
    
    
    public void mouseDragged(MouseEvent e) 
    {
        mouseMoved(e);
    }

    
    public synchronized void mouseMoved(MouseEvent e) 
    {
        //this event is from recentering the mouse. ignore it
        if(isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY())
        {
            isRecentering = false;
        }
        else
        {
            int dx = e.getX() - mouseLocation.x;
            int dy = e.getY() - mouseLocation.y;
            imageLocation.x += dx;
            imageLocation.y += dy;
            //recenter the mouse
            if(relativeMouseMode)
            {
                recenterMouse();
            }
        }
        
        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
    }

    
    public void keyTyped(KeyEvent e) 
    {
        //do nothing
    }

    
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            stop();
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            relativeMouseMode = !relativeMouseMode;
        }
    }

    
    public void keyReleased(KeyEvent e) 
    {
        //do nothing
    }
}
