import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class SpecialEdge extends Edge {

	private static final long serialVersionUID = -2550804644989255961L;
	
	public static final Integer[] STROKE_VALUES = {1, 2, 3, 4, 6, 7, 8, 9, 10};
	
	protected int stroke;
	protected Color color;
	
	public SpecialEdge(Node a, Node b, Color c, int s) {
		super(a, b);
		color = c;
		stroke = s;
		edgeType = EdgeType.SPECIAL_EDGE;
	}
	
	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		if(stroke < STROKE_VALUES[0])
			this.stroke = STROKE_VALUES[0];
		else if(stroke > STROKE_VALUES[STROKE_VALUES.length-1])
			this.stroke =  STROKE_VALUES[STROKE_VALUES.length-1];
		else
			this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(color == null)
			this.color = Color.BLACK;
		else
			this.color = color;
	}

	public void changeStroke(int step) {
		setStroke(stroke+step);		
	}
	
	@Override
	public void draw(Graphics g) {
		int xa = nodeA.getX();
		int ya = nodeA.getY();
		int xb = nodeB.getX();
		int yb = nodeB.getY();
		
		Graphics2D g2 = (Graphics2D) g;
		// set stroke just for this line
		g2.setStroke(new BasicStroke(stroke));
		g2.setColor(color);
		g2.drawLine(xa, ya, xb, yb);
		// reset to default stroke value
		g2.setStroke(new BasicStroke());
	}

	@Override
	public String toString() {
		String colorHex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
		return super.toString() + "{s: " + Integer.toString(stroke) + ", c: " + colorHex + "}";
	}

}
