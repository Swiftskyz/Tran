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
	
	public Color getGroutingColor(Color c) 
	{
		return groutingColor;
	}
	
	public void setAlwaysDrawGrouting(boolean always) 
	{
		if (alwaysDrawGrouting != always) 
		{
			alwaysDrawGrouting = always;
			redrawMosaic();
		}
	}
	
	public boolean getAlwaysDrawGrouting() 
	{
		return alwaysDrawGrouting; 
	}
	
	public void setGridSize(int rows, 
			int columns, boolean preserveData) 
	{
		if (rows > 0 && columns > 0) 
		{
			Color[][] newGrid = new Color[rows][columns];
			if (preserveData) 
			{
				int rowMax = Math.min(rows,this.rows);
				int colMax = Math.min(columns,this.columns);
				for (int r = 0; r < rowMax; r++)
					for (int c = 0; c < colMax; c++)
						newGrid[r][c] = grid[r][c];
			}
			grid = newGrid;
			this.rows = rows;
			this.columns = columns;
			redrawMosaic();
		}
	}
	
	public int getRowCount() 
	{
		return rows;
	}
	
	public int getColumnCount() 
	{
		return columns;
	}
	
	public Color getColor(int row, int col) 
	{
		if (row >=0 && row < rows && col >= 0 && col < columns)
			return grid[row][col];
		else
			return null;
	}
	
	public void setColor(int row, int col, Color c) 
	{
		if (row >=0 && row < rows && col >= 0 && col < columns) 
		{
			grid[row][col] = c;
			drawSquare(row,col);
		}
	}
	
	public void setColor(int row, int col, int red, int green, int blue) 
	{
		if (row >=0 && row < rows && col >= 0 && col < columns) 
		{
			red = (red < 0)? 0 : ( (red > 255)? 255 : red);
			green = (green < 0)? 0 : ( (green > 255)? 255 : green);
			blue = (blue < 0)? 0 : ( (blue > 255)? 255 : blue);
			grid[row][col] = new Color(red,green,blue);
			drawSquare(row,col);
		}
	}
	
	public void fill(Color c) 
	{
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				grid[i][j] = c;
		redrawMosaic();      
	}
	
	public void fill(int red, int green, int blue) 
	{
		red = (red < 0)? 0 : ( (red > 255)? 255 : red);
		green = (green < 0)? 0 : ( (green > 255)? 255 : green);
		blue = (blue < 0)? 0 : ( (blue > 255)? 255 : blue);
		fill(new Color(red,green,blue));
	}
	
	public void clear() 
	{
		fill(null);
	}
	
	public Object copyColorData() 
	{
		Color[][] copy = new Color[rows][columns];
		if (alwaysDrawGrouting)
			copy[rows-1] = new Color[columns+3];
		else
			copy[rows-1] = new Color[columns+2];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < columns; c++)
				copy[r][c] = grid[r][c];
		copy[rows-1][columns] = defaultColor;
		copy[rows-1][columns+1] = groutingColor;
		return copy;
	}
	
	
