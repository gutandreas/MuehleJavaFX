package edu.andreasgut.game;

public interface Board {
	boolean isValidPut(Position position);
	boolean isValidMove(Position from, Position to, boolean allowedToJump);
	boolean isKillPossibleAt(Position position, int otherPlayerIndex);
	boolean isMorrisAt(Position position);
	
	boolean canPlayerMove(int playerIndex);
	boolean canPlayerKill(int otherPlayerIndex); // wenn man es jetzt so liest dann erscheinen die
	                                             // erscheinen die beiden Methoden als unsymmetrisch.
	                                             // Beim einen wird player als Parameter angegeben,
	                                             // bei der anderen otherPlayer.

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