package views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import common.Engine;

public class MainWindow extends JFrame
{
	private int iFontPixelWidth;
	private int iFontPixelHeight;
	
	private DrawingRegion	drDrawingRegion;
	
	private Engine engine;
	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	
	public void CreateWindow(int width, int height, String title, int fontSize, Engine engine)
	{
		drDrawingRegion = new DrawingRegion();
        this.engine = engine;
        
        setLayout(new BorderLayout());
        add(drDrawingRegion, BorderLayout.CENTER);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        addWindowListener(new WindowListener() 
        {
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) 
			{
				CloseCalled();
			}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0){}
        });
	}
	
	public void CloseCalled()
	{
		Dispose();
	}
	
	public void Dispose()
	{
		engine.StopEngine();
	}
	
	public void SetBackFont(Font font, int width, int height)
	{
		drDrawingRegion.setBackFont(font, width, height);
	}
	
	public void SetForeFont(Font font, int width, int height)
	{
		drDrawingRegion.setForeFont(font, width, height);
	}
	
	public InputMap GetInputMap()
	{
		return drDrawingRegion.getInputMap(IFW);
	}
	
	public ActionMap GetActionMap()
	{
		return drDrawingRegion.getActionMap();
	}
	
	public DrawingRegion GetDrawingRegion()
	{
		return drDrawingRegion;
	}
}
