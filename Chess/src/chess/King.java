package chess;

import java.util.ArrayList;

public class King extends Piece {

	public King(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

	// can move 1 square in any direction
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		Position source = position;

		int fileChange = Math.abs(destination.getFileChar() - source.getFileChar());
		int rankChange = Math.abs(destination.getRank() - source.getRank());

		if ((fileChange + rankChange == 1) || (fileChange == 1 && rankChange == 1)){
			return true;
		}

		return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}

}