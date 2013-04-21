package com.brakeen.javagamebook.test;

import com.brakeen.javagamebook.graphics.NullRepaintManager;
import com.brakeen.javagamebook.input.GameAction;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MenuTest extends InputManagerTest implements ActionListener
{
    protected GameAction configAction;
    private JButton playButton;
    private JButton configButton;
    private JButton quitButton;
    private JButton pauseButton;
    private JPanel playButtonSpace;
    
    
    public static void main(String[] args)
    {
        new MenuTest().run();
    }
    
    
    @Override
    public void init()
    {
        super.init();
        NullRepaintManager.install();
        configAction = new GameAction("config");
        quitButton = createButton("quit", "Quit");
        playButton = createButton("play", "Continue");
        pauseButton = createButton("pause", "Pause");
        configButton = createButton("config", "Change Settings");
        
        playButtonSpace = new JPanel();
        playButtonSpace.setOpaque(false);
        playButtonSpace.add(pauseButton);
        
        JFrame frame = (JFrame)super.screen.getFullScreenWindow();
        Container contentPane = frame.getContentPane();
        
        if(contentPane instanceof JComponent)
        {
            ((JComponent)contentPane).setOpaque(false);
        }
        
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(playButtonSpace);
        contentPane.add(configButton);
        contentPane.add(quitButton);
        frame.validate();
    }
    
    
    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        JFrame frame = (JFrame)super.screen.getFullScreenWindow();
        frame.getLayeredPane().paintComponents(g);
    }
    
    
    @Override
    public void setPaused(boolean p)
    {
        super.setPaused(p);
        playButtonSpace.removeAll();
        if(isPaused())
        {
            playButtonSpace.add(playButton);
        }
        else
        {
            playButtonSpace.add(pauseButton);
        }
    }

    
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        if(src == quitButton)
        {
            super.exit.tap();
        }
        else if(src == configButton)
        {
            configAction.tap();
        }
        else if(src == playButton || src == pauseButton)
        {
            super.pause.tap();
        }
    }
    
    
    public JButton createButton(String name, String toolTip)
    {
        String imagePath = "images/menu/" + name + ".png";
        ImageIcon iconRollover = new ImageIcon(imagePath);
        int w = iconRollover.getIconWidth();
        int h = iconRollover.getIconHeight();
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        Image image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g = (Graphics2D)image.getGraphics();
        Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
        g.setComposite(alpha);
        g.drawImage(iconRollover.getImage(), 0, 0, null);
        g.dispose();
        ImageIcon iconDefault = new ImageIcon(image);
        image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        g = (Graphics2D)image.getGraphics();
        g.drawImage(iconRollover.getImage(), 2, 2, null);
        g.dispose();
        ImageIcon iconPressed = new ImageIcon(image);
        
        JButton button = new JButton();
        button.addActionListener(this);
        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setToolTipText(toolTip);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setCursor(cursor);
        button.setIcon(iconDefault);
        button.setRolloverIcon(iconRollover);
        button.setPressedIcon(iconPressed);
        return button;
    }
}
