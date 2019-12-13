import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*
 * 	Program: Edytor grafów.
 * 
 *    Pliki: GraphEditor.java, 
 *    		 GraphPanel.java
 *    		 Grid.java,
 *    		 Node.java,
 *    		 SpecialNode.java,
 *    		 Edge.java,
 *    		 SpecialEdge.java,
 *    		 Graph.java,
 *    
 *     Plik: Grid.java
 *	  		 definicja klasy Grid
 *
 *    Autor: Micha³ Tkacz 248869
 *	   Data: 22.11.2019r.
 *	Zajêcia: Pi¹tek TN 11:15
 */

public class Grid implements Serializable{

	private static final long serialVersionUID = 7269963525433830278L;
	
	private Dimension dimension;
	private int spacing;


	private Color color;

	private List<Rectangle2D.Double> rects;

	
	public Grid(Dimension dimension, int spacing) {
		color = new Color(0,0,0,0.1f);
		scaleGrid(dimension, spacing);
		
	}
	
	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		if(dimension == null)
			this.dimension = new Dimension(0, 0);
		else
			this.dimension = dimension;
	}

	public int getSpacing() {
		return spacing;
	}

	
	public void setSpacing(int spacing) {
		if(spacing <= 0)
			this.spacing = 20;
		else
			this.spacing = spacing;
	}
	
	public void scaleGrid(Dimension dimension, int spacing) {
		setSpacing(spacing);
		scaleGrid(dimension);
	}

	public void scaleGrid(Dimension dimension) {
		setDimension(dimension);
		createGrid();
	}
	
	private void createGrid() {		
		rects = new ArrayList<Rectangle2D.Double>();
		for(int x = spacing; x <= dimension.getWidth(); x += 2*spacing) {
			rects.add(new Rectangle2D.Double(x, -1, spacing, (int) dimension.getHeight()));
		}
	
		for(int y = spacing; y < dimension.getHeight(); y += 2*spacing) {
			rects.add(new Rectangle2D.Double(0, y, (int)dimension.getWidth(), spacing));

		}
		
	}
	
	public void draw (Graphics g) {
		g.setColor(color);	
		for(Rectangle2D.Double rect : rects) {
			g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
		}
		
	}
	
}
