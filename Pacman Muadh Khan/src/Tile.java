import javax.swing.JLabel;

public class Tile extends JLabel{
	private int row;
	private int col;
	
	public Tile() {
		
	}

	//constructor with parameters row and col
	public Tile(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	//getters and setters for the fields
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	//use toString method from the JLabel class
	public String toString() {
		return "Tile [row = " + row + ", col = " + col + "]";
	}
}