package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import common.Sprite;
import javax.swing.BoxLayout;
import java.awt.Component;

public class SpriteCreator extends JFrame
{
	private SpriteDisplay m_spriteDisplay;
	private JComboBox<String> cbxColors;
	private JComboBox<String> cbxSymbol;
	private JButton setDimensions;
	private JButton save;
	private JButton load;
	private JButton fill;
	private JButton clear;
	private JPanel buttonPanel;
	
	private enum InputAction {
		UP_ARW
		, DOWN_ARW
		, LEFT_ARW
		, RIGHT_ARW
		, ENTER
	}
	
	public static void main(String args[])
	{
		SpriteCreator sc = new SpriteCreator();
	}
	
	public SpriteCreator()
	{
		setTitle("Sprite Creator");
		InitComponents();
        InitInput();
        
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        m_spriteDisplay.setOffset((int)(m_spriteDisplay.getLocationOnScreen().getX() - this.getLocationOnScreen().getX()), (int)(m_spriteDisplay.getLocationOnScreen().getY() - this.getLocationOnScreen().getY()));
        m_spriteDisplay.repaint();
    }
	
	private void InitComponents()
	{
		m_spriteDisplay = new SpriteDisplay();

		JPanel menuPanel = new JPanel();
		
		cbxColors = new JComboBox<String>();
		cbxColors.setFocusable(false);
		cbxColors.addItem("BLACK");
		cbxColors.addItem("WHITE");
		cbxColors.addItem("DARK_DARK_GRAY");
		cbxColors.addItem("DARK_GRAY");
		cbxColors.addItem("GRAY");
		cbxColors.addItem("LIGHT_GRAY");
		cbxColors.addItem("RED");
		cbxColors.addItem("BRICK");
		cbxColors.addItem("BLUE");
		cbxColors.addItem("GREEN");
		cbxColors.addItem("CYAN");
		cbxColors.addItem("YELLOW");
		cbxColors.addItem("GOLD");
		cbxColors.addItem("MAGENTA");
		cbxColors.addItem("ORANGE");
		cbxColors.addItem("PINK");

		cbxSymbol = new JComboBox<String>();
		cbxSymbol.setFocusable(false);
		cbxSymbol.addItem(""+(char)0x2588);
		cbxSymbol.addItem(""+(char)0x2587);
		cbxSymbol.addItem(""+(char)0x2586);
		cbxSymbol.addItem(""+(char)0x2585);
		cbxSymbol.addItem(""+(char)0x2584);
		cbxSymbol.addItem(""+(char)0x2583);
		cbxSymbol.addItem(""+(char)0x3011);
		cbxSymbol.addItem(""+(char)0x3010);
		cbxSymbol.addItem("#");
		cbxSymbol.addItem("X");
		cbxSymbol.addItem("x");
		cbxSymbol.addItem("-");
		cbxSymbol.addItem(".");
		cbxSymbol.addItem("O");
		cbxSymbol.addItem("o");
		cbxSymbol.addItem("0");
		cbxSymbol.addItem(" ");
        
        buttonPanel = new JPanel();
		
        GroupLayout gl_menuPanel = new GroupLayout(menuPanel);
        gl_menuPanel.setHorizontalGroup(
        	gl_menuPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_menuPanel.createSequentialGroup()
        			.addGroup(gl_menuPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_menuPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(cbxColors, 0, 187, Short.MAX_VALUE))
        				.addGroup(gl_menuPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(cbxSymbol, 0, 187, Short.MAX_VALUE))
        				.addGroup(gl_menuPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        gl_menuPanel.setVerticalGroup(
        	gl_menuPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_menuPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cbxColors, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbxSymbol, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
        			.addGap(187))
        );
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        setDimensions = new JButton("Set Dim");
        setDimensions.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(setDimensions);
        setDimensions.setFocusable(false);
        fill = new JButton("Fill");
        fill.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(fill);
        fill.setFocusable(false);
        clear = new JButton("Clear");
        clear.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(clear);
        clear.setFocusable(false);
        save = new JButton("Save");
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(save);
        save.setFocusable(false);
        load = new JButton("Load");
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(load);
        load.setFocusable(false);
        menuPanel.setLayout(gl_menuPanel);
        
        JPanel drawPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        			.addComponent(drawPanel, GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        			.addGap(11))
        		.addComponent(drawPanel, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        getContentPane().setLayout(groupLayout);

        drawPanel.setLayout(new BorderLayout(0, 0));
        drawPanel.add(m_spriteDisplay, BorderLayout.CENTER);
	}
	
	private void InitInput()
	{
		InputMap im = m_spriteDisplay.getInputMap();
		ActionMap am = m_spriteDisplay.getActionMap();
		
		// Keys pressed down
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,    0, false), InputAction.UP_ARW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,  0, false), InputAction.DOWN_ARW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  0, false),	InputAction.LEFT_ARW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),	InputAction.RIGHT_ARW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),	InputAction.ENTER);
		
		am.put(InputAction.UP_ARW, 		new InputActionHandler(InputAction.UP_ARW));
		am.put(InputAction.DOWN_ARW, 	new InputActionHandler(InputAction.DOWN_ARW));
		am.put(InputAction.LEFT_ARW, 	new InputActionHandler(InputAction.LEFT_ARW));
		am.put(InputAction.RIGHT_ARW, 	new InputActionHandler(InputAction.RIGHT_ARW));
		am.put(InputAction.ENTER, 		new InputActionHandler(InputAction.ENTER));
		
		// JButtons
		setDimensions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				DimensionPrompt dp = new DimensionPrompt();
			}
		});
		save.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_spriteDisplay.saveSprite();
			}
		});
		load.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_spriteDisplay.loadSprite();
			}
		});
		fill.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_spriteDisplay.fillSprite();
			}
		});
		clear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_spriteDisplay.clearSprite();
			}
		});
	}
	
	private class InputActionHandler extends AbstractAction 
	{
		InputAction action;

		InputActionHandler(InputAction action) 
        {
            this.action = action;
        }

        public void actionPerformed(ActionEvent e) 
        {
        	switch (action)
        	{
        		case UP_ARW:
        			m_spriteDisplay.moveCursorY(true);
        			break;
        		case DOWN_ARW:
        			m_spriteDisplay.moveCursorY(false);
        			break;
        		case LEFT_ARW:
        			m_spriteDisplay.moveCursorX(true);
        			break;
        		case RIGHT_ARW:
        			m_spriteDisplay.moveCursorX(false);
        			break;
        		case ENTER:
        			m_spriteDisplay.enterPressed();
        			break;
        	};
        }
	}
	
	private class SpriteDisplay extends JPanel
	{
		private int m_iDrawWidth;
		private int m_iDrawHeight;
		private int m_iOffsetX;
		private int m_iOffsetY;
		private int m_iCursorX;
		private int m_iCursorY;
		private int m_iSpriteDimX;
		private int m_iSpriteDimY;
		
		private final String SPRITE_PATH = System.getProperty("user.dir") + "\\src\\resources\\sprites";
		
		Font font;
		
		Sprite sprite;
		
		public SpriteDisplay()
		{
			setOpaque(true);
	        setFocusable(true);
			setBackground(Color.BLACK);
			
			m_iSpriteDimX = 32;
			m_iSpriteDimY = 32;
			sprite = new Sprite(m_iSpriteDimX, m_iSpriteDimY);
			font = new Font("Courier", Font.PLAIN, 20);
	        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
			m_iDrawWidth = (int)font.getStringBounds(""+(char)0x2588, frc).getWidth();
			m_iDrawHeight = (int)font.getStringBounds(""+(char)0x2588, frc).getHeight();
			
			System.out.println(String.format("Width: %d Height: %d (%3.2f:1)", m_iDrawWidth, m_iDrawHeight, (float)m_iDrawWidth / m_iDrawHeight));
			
			addMouseListener(new MouseAdapter() 
			{
		        public void mousePressed(MouseEvent e) 
		        {
		        	if (e.getButton() == 1)
		        	{
			        	int xPos = Math.round(((e.getX() - m_iOffsetX) * sprite.getWidth() / ((float)m_iDrawWidth * sprite.getWidth())));
			        	int yPos = Math.round(((e.getY() - m_iOffsetY) * sprite.getHeight() / ((float)m_iDrawWidth * sprite.getHeight())));

			            sprite.setSymbol(xPos, yPos, ((String)cbxSymbol.getSelectedItem()).charAt(0));
			            sprite.setColor(xPos, yPos, getColor(cbxColors.getSelectedIndex()));
			            repaint();
		        	}
		        	else if (e.getButton() == 3)
		        	{
		        		int xPos = Math.round(((e.getX() - m_iOffsetX) * sprite.getWidth() / ((float)m_iDrawWidth * sprite.getWidth())));
			        	int yPos = Math.round(((e.getY() - m_iOffsetY) * sprite.getHeight() / ((float)m_iDrawWidth * sprite.getHeight())));

			            sprite.setSymbol(xPos, yPos, '\0');
			            sprite.setColor(xPos, yPos, Color.BLACK);
			            repaint();
		        	}
		        }
	        });
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			for (int i = 0; i < sprite.getWidth(); i++)
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					char symbol = '-';
					int drawPosX = m_iOffsetX + (i * m_iDrawWidth);
					int drawPosY = m_iOffsetY + (j * m_iDrawWidth);

					g.setColor(Color.WHITE);
					g.setFont(font);
					
					if (sprite.getSymbol(i, j) != '\0')
					{
						symbol = sprite.getSymbol(i, j);
						g.setColor(sprite.getColor(i, j));
					}
					
					g.drawString(""+symbol, drawPosX, drawPosY);
				}
			
			// Draw cursor
			g.setColor(Color.WHITE);
			g.drawString(""+(char)0x25CB, m_iCursorX, m_iCursorY);
		}
		
		private void setOffset(int x, int y)
		{
			m_iOffsetX = x;
			m_iOffsetY = y;
			
			m_iCursorX = x;
			m_iCursorY = y;
		}
		
		public void setDimensions(int x, int y)
		{
			sprite = new Sprite(x, y);
			repaint();
		}
		
		private void moveCursorX(boolean left)
		{
			if (left)
			{
				if (m_iCursorX > m_iOffsetX)
					m_iCursorX -= m_iDrawWidth;
				else
					m_iCursorX = m_iOffsetX + (m_iDrawWidth * m_iSpriteDimX);
			}
			else
			{
				if (m_iCursorX < m_iOffsetX + (m_iDrawWidth * m_iSpriteDimX))
					m_iCursorX += m_iDrawWidth;
				else
					m_iCursorX = m_iOffsetX;
			}
			
            repaint();
		}
		
		private void moveCursorY(boolean up)
		{
			if (up)
			{
				if (m_iCursorY > m_iOffsetY)
					m_iCursorY -= m_iDrawWidth;
				else
					m_iCursorY = m_iOffsetY + (m_iDrawWidth * m_iSpriteDimY);
			}
			else
			{
				if (m_iCursorY < m_iOffsetY + (m_iDrawWidth * m_iSpriteDimY))
					m_iCursorY += m_iDrawWidth;
				else
					m_iCursorY = m_iOffsetY;
			}
			
            repaint();
		}
		
		private void enterPressed()
		{
			int xPos = Math.round(((m_iCursorX - m_iOffsetX) * sprite.getWidth() / ((float)m_iDrawWidth * sprite.getWidth())));
        	int yPos = Math.round(((m_iCursorY - m_iOffsetY) * sprite.getHeight() / ((float)m_iDrawWidth * sprite.getHeight())));
        	
        	if (xPos == sprite.getWidth())
        		for (int x = 0; x < sprite.getWidth(); x++)
        			//if (sprite.getSymbol(x, yPos) == '\0')
	        		{
	        			sprite.setSymbol(x, yPos, ((String)cbxSymbol.getSelectedItem()).charAt(0));
	                    sprite.setColor(x, yPos, getColor(cbxColors.getSelectedIndex()));
	        		}
        	else if (yPos == sprite.getHeight())
        		for (int y = 0; y < sprite.getHeight(); y++)
        			//if (sprite.getSymbol(xPos, y) == '\0')
	        		{
	        			sprite.setSymbol(xPos, y, ((String)cbxSymbol.getSelectedItem()).charAt(0));
	                    sprite.setColor(xPos, y, getColor(cbxColors.getSelectedIndex()));
	        		}
        	else
        	{
	        	sprite.setSymbol(xPos, yPos, ((String)cbxSymbol.getSelectedItem()).charAt(0));
	            sprite.setColor(xPos, yPos, getColor(cbxColors.getSelectedIndex()));
        	}
        	
            repaint();
		}
		
		private Color getColor(int name)
		{
			switch (name)
			{
				case 0:
					return Color.BLACK;
				case 1:
					return Color.WHITE;
				case 2:
					return new Color(32, 32, 32);
				case 3:
					return Color.DARK_GRAY;
				case 4:
					return Color.GRAY;
				case 5:
					return Color.LIGHT_GRAY;
				case 6:
					return Color.RED;
				case 7:
					return new Color(178, 34, 34);
				case 8:
					return Color.BLUE;
				case 9:
					return Color.GREEN;
				case 10:
					return Color.CYAN;
				case 11:
					return Color.YELLOW;
				case 12:
					return new Color(212, 175, 55);
				case 13:
					return Color.MAGENTA;
				case 14:
					return Color.ORANGE;
				case 15:
					return Color.PINK;
			}
			
			return Color.BLACK;
		}
		
		private void saveSprite()
		{
			FileOutputStream fout = null;
			ObjectOutputStream oos = null;

			try 
			{
				JFileChooser fc = new JFileChooser();
				
				fc.setCurrentDirectory(new File(SPRITE_PATH));
				fc.setDialogTitle("Save your sprite");
				
				if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					String path = fc.getSelectedFile().getPath();
					
					if (!path.endsWith(".ser"))
						path += ".ser";
					
					fout = new FileOutputStream(path);
					oos = new ObjectOutputStream(fout);
					oos.writeObject(sprite);
	
					System.out.println("Saved sprite to " + path);
				}
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			} finally 
			{
				if (fout != null) 
				{
					try 
					{
						fout.close();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}

				if (oos != null) 
				{
					try 
					{
						oos.close();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		private void loadSprite()
		{
			FileInputStream streamIn = null;
		    ObjectInputStream objectinputstream = null;
		    
			try 
			{
				JFileChooser fc = new JFileChooser();
				
				fc.setCurrentDirectory(new File(SPRITE_PATH));
				fc.setDialogTitle("Load saved sprite");
				
				if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					String path = fc.getSelectedFile().getPath();
					
					if (!path.endsWith(".ser"))
						path += ".ser";
					
					streamIn = new FileInputStream(path);
				    objectinputstream = new ObjectInputStream(streamIn);
				    sprite = (Sprite) objectinputstream.readObject();
				    
				    m_iSpriteDimX = sprite.getWidth();
				    m_iSpriteDimY = sprite.getHeight();
				    
				    repaint();
				    
					System.out.println("Loaded sprite " + path);
				}
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			} finally 
			{
				if (streamIn != null) 
				{
					try 
					{
						streamIn.close();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}

				if (objectinputstream != null) 
				{
					try 
					{
						objectinputstream.close();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		private void fillSprite()
		{
			for (int i = 0; i < sprite.getWidth(); i++)
				for (int j = 0; j < sprite.getHeight(); j++)
					if (sprite.getSymbol(i, j) == '\0')
					{
						sprite.setSymbol(i, j, ((String)cbxSymbol.getSelectedItem()).charAt(0));
			            sprite.setColor(i, j, getColor(cbxColors.getSelectedIndex()));
					}
			
			repaint();
		}
		
		private void clearSprite()
		{
			sprite = new Sprite(m_iSpriteDimX, m_iSpriteDimY);
			repaint();
		}
	}
	
	private class DimensionPrompt extends JDialog 
	{
		private final JPanel contentPanel = new JPanel();
		
		private JButton btnOkay;
		private JButton btnCancel;
		private JTextField txtX;
		private JTextField txtY;
		private JLabel lblNewX;
		private JLabel lblNewY;

		public DimensionPrompt() 
		{
			setBounds(100, 100, 250, 150);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			setupObjects();
			setupInput();
			
			setVisible(true);
		}
		
		private void setupObjects()
		{
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			{
				lblNewX = new JLabel("New X:");
			}
			{
				txtX = new JTextField();
				txtX.setColumns(10);
			}
			{
				lblNewY = new JLabel("New Y:");
			}
			{
				txtY = new JTextField();
				txtY.setColumns(10);
			}
			GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
			gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(30)
								.addComponent(lblNewX)
								.addGap(5)
								.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(31)
								.addComponent(lblNewY)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(69))
			);
			gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewX))
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewY)))
			);
			contentPanel.setLayout(gl_contentPanel);
			
			JPanel buttonPane = new JPanel();
			
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOkay = new JButton("OK");
				buttonPane.add(btnOkay);
				getRootPane().setDefaultButton(btnOkay);
			}
			{
				btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		
		private void setupInput()
		{
			btnOkay.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					m_spriteDisplay.setDimensions(Integer.parseInt(txtX.getText()), Integer.parseInt(txtY.getText()));
					dispose();
				}
			});
			btnCancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
		}
	}
}
