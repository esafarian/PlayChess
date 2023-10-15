package chess;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

	// moves in an L-shape. can jump over other pieces.
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		Position source = position;

		int fileChange = Math.abs(destination.getFileChar() - source.getFileChar());
		int rankChange = Math.abs(destination.getRank() - source.getRank());

		// is the move in an L-shape?
		if (fileChange == 2 && rankChange == 1 || fileChange == 1 && rankChange == 2){
			return true;
		}

		return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}

}
