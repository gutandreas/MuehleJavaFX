package edu.andreasgut.game;

public interface Board {
	boolean isPutPossibleAt(Position position);
	boolean isMovePossibleAt(Position from, Position to, boolean allowedToJump);
	boolean isKillPossibleAt(Position position, int enemysPlayerIndex);
	boolean isPositionPartOfMorris(Position position);
	
	boolean canPlayerMove(int playerIndex);
	boolean canPlayerKill(int playerIndex);

	void putStone(Position position, int playerIndex);
	void moveStone(Position from, Position to, int playerIndex);
	void removeStone(Position position);

	int numberOfStonesOf(int playerIndex);

	boolean isFieldFree(Position position);
	boolean isFieldOccupied(Position position);

	boolean isThisMyStone(Position position, int ownPlayerIndex);
	boolean isThisMyEnemysStone(Position position, int ownPlayerIndex);

	int getNumberOnPosition(Position position);

	String toString();
	Board clone();
}