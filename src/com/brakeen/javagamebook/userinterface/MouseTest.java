package com.brakeen.javagamebook.userinterface;

import com.brakeen.javagamebook.test.GameCore;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;


public class MouseTest extends GameCore implements KeyListener,
        MouseListener, MouseMotionListener, MouseWheelListener
{
    private static final int TRAIL_SIZE = 10;
    private static final Color[] COLORS = {
        Color.WHITE, Color.black, Color.yellow, Color.magenta
    };
    private LinkedList trailList;
    private boolean trailMode;
    private int colorIndex;
    
    
    public static void main(String[] args)
    {
        new MouseTest().run();
    }
    
    
    public void init()
    {
        super.init();
        trailList = new LinkedList();
        Window window = screen.getFullScreenWindow();
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        window.addMouseWheelListener(this);
        window.addKeyListener(this);
    }
    
    
    @Override
    public void draw(Graphics2D g) 
    {
        int count = trailList.size();
        
        if(count > 1 && !trailMode)
        {
            count = 1;
        }
        
        Window window = screen.getFullScreenWindow();
        g.setColor(window.getBackground());
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(window.getForeground());
        g.drawString("MouseTest: Press Escape to exit.", 5, FONT_SIZE);
        for(int i=0; i<count; i++)
        {
            Point p = (Point)trailList.get(i);
            g.drawString("Hello world!", p.x, p.y);
        }
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
    }
    

    public void keyReleased(KeyEvent e) 
    {
        //do nothing
    }
    

    public void mouseClicked(MouseEvent e) 
    {
        //this method is called after the mouse is released. ignore it
    }
    

    public void mousePressed(MouseEvent e) 
    {
        trailMode = !trailMode;
    }
    

    public void mouseReleased(MouseEvent e) 
    {
        //do nothinhg
    }
    

    public void mouseEntered(MouseEvent e) 
    {
        mouseMoved(e);
    }
    

    public void mouseExited(MouseEvent e) 
    {
        mouseMoved(e);
    }
    

    public void mouseDragged(MouseEvent e) 
    {
        mouseMoved(e);
    }
    

    public synchronized void mouseMoved(MouseEvent e) 
    {
        Point p = new Point(e.getX(), e.getY());
        trailList.addFirst(p);
       
        while(trailList.size() > TRAIL_SIZE)
        {
            trailList.removeLast();
        }
    }
    

    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        colorIndex = (colorIndex + e.getWheelRotation()) % COLORS.length;
        
        if(colorIndex > 0)
        {
            colorIndex += COLORS.length;
        }
        
        Window window = screen.getFullScreenWindow();
        window.setForeground(COLORS[colorIndex]);
    }
}
