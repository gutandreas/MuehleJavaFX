package edu.andreasgut.game;

public final class Position implements Comparable<Position> {

	public final int ring, field;

	public Position(int ring, int field) {
		this.ring = ring;
		this.field = field;
	}

	public int getRing() {
		return ring;
	}

	public int getField() {
		return field;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Position
				&& ring == ((Position) other).ring 
				&& field == ((Position) other).field;
	}

	@Override
	public int compareTo(Position position) {
		if (ring == position.ring && field == position.field)
			return 0;
		if (ring == position.ring && field > position.field)
			return 1;
		if (ring > position.ring)
			return 1;
		return -1;
	}

	@Override
	public String toString() {
		return "Position " + getRing() + "/" + getField();
	}
}
