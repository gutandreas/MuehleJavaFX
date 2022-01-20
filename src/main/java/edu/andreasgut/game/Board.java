package edu.andreasgut.game;

public interface Board {

	void putStone(Position position, int playerIndex);

	void move(Move move, int playerIndex);

	boolean checkPut(Position position);

	boolean checkMove(Move move, boolean allowedToJump);

	boolean checkKill(Position position, int otherPlayerIndex);

	void clearStone(Position position);

	boolean checkMorris(Position position);

	boolean checkIfAbleToMove(int playerIndex);

	boolean isThereStoneToKill(int otherPlayerIndex);

	int countPlayersStones(int playerIndex);

	boolean isFieldFree(Position position);

	boolean isFieldOccupied(Position position);

	boolean isThisMyStone(Position position, int ownPlayerIndex);

	boolean isThisMyEnemysStone(Position position, int ownPlayerIndex);

	int getNumberOnPosition(int ring, int field);

	String toString();

	Board clone();

}