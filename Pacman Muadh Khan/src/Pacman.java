import javax.swing.ImageIcon;

public class Pacman extends Tile {
	public Pacman(int row, int col, ImageIcon icon) {
		super(row, col);
		setIcon(icon);
		setBounds(col * 25, row * 25, 25, 25);
	}
	
	public void move(int dRow, int dCol) {
		setRow(getRow() + dRow);
		setCol(getCol() + dCol);
	}
}
