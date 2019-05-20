import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TronPanel extends JPanel implements FocusListener, KeyListener, ActionListener, MouseListener 
{

	private MosaicPanel arena;
	
	private JLabel message;

	private Timer timer;
	
	private final static int ROWS = 60;   
	private final static int COLUMNS = 80;
	private final static int BLOCKSIZE = 10;
	private final static int BORDER_WIDTH = 5;
	
	private final static int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3, NOT_MOVING = 4;
	private int direction;

	private int currentColumn, currentRow;
