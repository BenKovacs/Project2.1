package oth.model;


public class BoardSquare {
	int x;
	int y;
	private boolean occupied;
	private double weight;

	public BoardSquare(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}


	public double getWeight() {
		return weight;
	}
}
