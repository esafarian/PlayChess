package chess;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

	// moves in an L-shape. can jump over other pieces.
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
	    // Get the differences between the starting and ending positions
	    int dx = Math.abs(destination.getFile().ordinal() - position.getFile().ordinal());
	    int dy = Math.abs(destination.getRank() - position.getRank());

	    // Check for the L-shape move
	    if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
	        // Make sure the destination square isn't occupied by a piece of the same color
	        ReturnPiece pieceAtDestination = landsOnAPiece(currentGame, destination);
	        if (pieceAtDestination == null || !sameColor(pieceAtDestination)) {
	            return true;
	        }
	    }

	    return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}

}