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

