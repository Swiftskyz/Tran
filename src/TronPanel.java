import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

public class TronPanel extends JPanel implements FocusListener, KeyListener, ActionListener, MouseListener 
{

	private MosaicPanel arena;
	
	private JLabel message;

	private Timer timer;
	
	private Thread thread;
	
	private boolean running;
	
	private final static int ROWS = 60;
	private final static int COLUMNS = 80;
	private final static int BLOCKSIZE = 10;
	private final static int BORDER_WIDTH = 5;
	
	private final static int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3, NOT_MOVING = 4;
	private int direction;

	private int currentColumn, currentRow;
	
	public TronPanel() 
	{
		arena = new MosaicPanel(ROWS, COLUMNS, BLOCKSIZE, BLOCKSIZE, Color.GRAY, BORDER_WIDTH);
		message = new JLabel("To Start, Click the Arena", JLabel.CENTER);
		message.setBackground(Color.LIGHT_GRAY);
		message.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(3,3));
		bottom.add(message,BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		add(arena, BorderLayout.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		arena.setGroutingColor(null);
		arena.addFocusListener(this);
		arena.addKeyListener(this);
		arena.addMouseListener(this);
		message.addMouseListener(this);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		switch (direction) 
		{
		case UP:
			if (currentRow > 0)
				currentRow--;
			break;
		case DOWN:
			if (currentRow < ROWS-1)
				currentRow++;
			break;
		case RIGHT:
			if (currentColumn < COLUMNS-1)
				currentColumn++;
			break;
		case LEFT:
			if (currentColumn > 0)
				currentColumn--;
			break;
		}
		
		arena.setHSBColor(currentRow, currentColumn,Math.random(),0,1);
	}
	
	public class BodyPart
	{
		private int xCoor, yCoor, width, height;
		
		public BodyPart(int xCoor, int yCoor, int tileSize)
		{
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			width = tileSize;
			height = tileSize;
		}
		
		public void tick()
		{
			
		}
		
		public void draw(Graphics g)
		{
			g.setColor(Color.YELLOW);
			g.fillRect(xCoor * width, yCoor * height, width, height);
		}

		public int getxCoor()
		{
			return xCoor;
		}

		public void setxCoor(int xCoor)
		{
			this.xCoor = xCoor;
		}

		public int getyCoor()
		{
			return yCoor;
		}

		public void setyCoor(int yCoor)
		{
			this.yCoor = yCoor;
		}

	}
	
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT)
			direction = LEFT;
		else if (code == KeyEvent.VK_RIGHT)
			direction = RIGHT;
		else if (code == KeyEvent.VK_UP)
			direction = UP;
		else if (code == KeyEvent.VK_DOWN)
			direction = DOWN;
		else if (code == KeyEvent.VK_SPACE)
			message.requestFocus();
	}
	
	public void focusGained(FocusEvent e) 
	{
		arena.setBorder(BorderFactory.createLineBorder(Color.CYAN, BORDER_WIDTH));
		arena.fill(null);
		currentColumn = COLUMNS/4*3;
		currentRow = ROWS/2;
		direction = UP;
		arena.setColor(currentRow,currentColumn,255,0,0);
		direction = NOT_MOVING;
		message.setText("To PAUSE, Click this Message or Press \"SPACE\"");
		timer = new Timer(50,this);
		timer.start();
	}
	
	public void focusLost(FocusEvent e) {
		arena.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_WIDTH));
		if (timer != null)
			timer.stop();
		timer = null;
		message.setText("To START, Click the Arena");
	}
	
	public void mousePressed(MouseEvent e) 
	{
		if (e.getSource() == arena)
			arena.requestFocus();
		else
			message.requestFocus();
	}
	
	public void stop()
	{
		running = false;
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	//collision on border 
	if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49)
	{
		System.out.println("Game Over");
		stop();
	}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}

	
	
	
	
