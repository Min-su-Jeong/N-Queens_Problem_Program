package n_Queens;

public class Queen {
	private int row, col;
	
	// »ý¼ºÀÚ
	public Queen(int r, int c) {
		this.row = r;
		this.col = c;
	}
	
	// setter
	public void setRow(int row) {
		row = this.row;
	}
	public void setCol(int col) {
		col = this.col;
	}
	
	// getter
	public int getRow() {
		return this.row;
	}
	public int getCol() {
		return this.col;
	}
}
