package chess;

import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

	/*  combines rook and bishop
	*	can move any number horizontally, vertically, or diagonally
	*	cannot leap over other pieces
	*/
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		// queen can either act as a bishop or rook
		Piece queenActsBishop = new Bishop(ReturnPiece.PieceType.BB, position);
		Piece queenActsRook = new Rook(ReturnPiece.PieceType.BR, position);

		if (queenActsBishop.isValidMove(currentGame, destination) ||
			queenActsRook.isValidMove(currentGame, destination)){
			return true;
		}

		return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}

}