import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class MosaicPanel extends JPanel 
{
	private int rows;
	private int columns;
	private Color defaultColor;
	private Color groutingColor;
	private boolean alwaysDrawGrouting;
	private Color[][] grid;
	private BufferedImage OSI;
	private boolean needsRedraw;

	public MosaicPanel() 
	{
		this(42,42,16,16);
	}
	
	public MosaicPanel(int rows, int columns) 
	{
		this(rows,columns,16,16);
	}
	
	public MosaicPanel(int rows, int columns, int preferredBlockWidth, int preferredBlockHeight) 
	{
		this(rows, columns, preferredBlockWidth, preferredBlockHeight, null, 0);
	}
	
	public MosaicPanel(int rows, int columns, int preferredBlockWidth, int preferredBlockHeight, Color borderColor, int borderWidth) 
	{
		this.rows = rows;
		this.columns = columns;
		grid = new Color[rows][columns];
		defaultColor = Color.black;
		groutingColor = Color.gray;
		alwaysDrawGrouting = false;
		setBackground(defaultColor);
		setOpaque(true);
		setDoubleBuffered(false);
		if (borderColor != null) 
		{
			if (borderWidth < 1)
				borderWidth = 1;
			setBorder(BorderFactory.createLineBorder(borderColor,borderWidth));
		}
		else
			borderWidth = 0;
		if (preferredBlockWidth > 0 && preferredBlockHeight > 0)
			setPreferredSize(new Dimension(preferredBlockWidth*columns + 2*borderWidth, preferredBlockHeight*rows + 2*borderWidth));
	}
	
	public void setDefaultColor(Color c) 
	{
		if (c == null)
			c = Color.black;
		if (! c.equals(defaultColor)) 
		{
			defaultColor = c;
			setBackground(c);
			redrawMosaic();
		}
	}
	
	public Color getDefaultColor() 
	{
		return defaultColor;
	}
	
	public void setGroutingColor(Color c) 
	{
		if (c == null || ! c.equals(groutingColor)) 
		{
			groutingColor = c;
			redrawMosaic();
		}
	}
