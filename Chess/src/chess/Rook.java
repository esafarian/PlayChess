package chess;

import java.util.ArrayList;

public class Rook extends Piece{

	public Rook(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		return null;
	}
}
