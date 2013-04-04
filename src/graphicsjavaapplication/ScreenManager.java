package graphicsjavaapplication;


import java.awt.*;
import javax.swing.JFrame;
import java.awt.image.*;


public class ScreenManager
{
    private GraphicsDevice device;


    public ScreenManager()
    {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }


    public DisplayMode[] getCompatibleDisplayModes()
    {
        return device.getDisplayModes();
    }


    public DisplayMode findFirstCompatibleMode(DisplayMode modes[])
    {
        DisplayMode goodModes[] = device.getDisplayModes();

        for(int i=0; i<modes.length; i++)
        {
            for(int j=0;j<goodModes.length;j++)
                if(displayModesMatch(modes[i], goodModes[j]))
                    return modes[i];
        }

        return null;
    }


    public DisplayMode getCurrentDisplayMode()
    {
        return device.getDisplayMode();
    }


    public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2)
    {
        if(mode1.getWidth() != mode2.getWidth() ||
                mode1.getHeight() != mode2.getHeight())
            return false;

        if(mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                mode1.getBitDepth() != mode2.getBitDepth())
            return false;

        if(mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                mode1.getRefreshRate() != mode2.getRefreshRate())
            return false;

        return true;
    }


    public void setFullScreen(DisplayMode displayMode)
    {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        device.setFullScreenWindow(frame);

        if(displayMode != null && device.isDisplayChangeSupported())
        {
            try
            {
                device.setDisplayMode(displayMode);
            }
            catch(IllegalArgumentException ex) { /* do nothing */ }
        }

        frame.createBufferStrategy(2);
    }


    public Graphics2D getGraphics()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
        {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D)strategy.getDrawGraphics();
        }

        return null;
    }


    public void update()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
        {
            BufferStrategy strategy = window.getBufferStrategy();
            if(!strategy.contentsLost())
                strategy.show();
        }

        Toolkit.getDefaultToolkit().sync();
    }


    public Window getFullScreenWindow()
    {
        return device.getFullScreenWindow();
    }


    public int getWidth()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
            return window.getWidth();

        return 0;
    }


    public int getHeight()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
            return window.getHeight();

        return 0;
    }


    public void restoreScreen()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
            window.dispose();

        device.setFullScreenWindow(null);
    }


    public BufferedImage createCompatibleImage(int w, int h, int transparency)
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
        {
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparency);
        }

        return null;
    }
}
