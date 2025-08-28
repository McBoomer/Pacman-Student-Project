import javax.swing.ImageIcon;

public class Ghost extends Tile {
	public Ghost (int row, int col, ImageIcon icon) {
		super(row, col);
		setIcon(icon);
		setBounds(col * 25 , row * 25, 25, 25);
	}
	
	public void move(int dRow, int dCol) {
		setRow(getRow() + dRow);
		setCol(getCol() + dCol);
	}
	
	public void autoMove() {
		int direction = (int)(Math.random() * 4);
		int dRow = 0, dCol = 0;
		
		switch(direction) {
			case 0:
				dRow = -1;
				break;
			case 1:
				dRow = 1;
				break;
			case 2:
				dCol = -1;
				break;
			case 3:
				dCol = 1;
				break;
		}
	}

    public boolean isInSquareMovement() {
        return false;
    }

    public void setInSquareMovement(boolean b) {
    }
}
