package chess;

import java.util.ArrayList;

public class King extends Piece {

	private boolean hasMoved; // castling check
	
	public King(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
        this.hasMoved = false;
    }

	// can move 1 square in any direction
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		Position source = position;

		int fileChange = Math.abs(destination.getFileChar() - source.getFileChar());
		int rankChange = Math.abs(destination.getRank() - source.getRank());

		if ((fileChange + rankChange == 1) || (fileChange == 1 && rankChange == 1)){
			hasMoved = true;
			return true;
		}
		return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}
	
	public boolean hasMoved() {
			return hasMoved;
			
		}
	}
