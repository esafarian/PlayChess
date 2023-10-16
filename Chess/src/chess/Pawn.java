package chess;

import java.util.ArrayList;

class Pawn extends Piece {

    public Pawn(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

    @Override
    public boolean isValidMove(ReturnPlay currentGame, Position destination) {
        // for white pawn
        if (returnPiece.pieceType == ReturnPiece.PieceType.WP 
                && returnPiece.pieceFile == destination.getFile() 
                && returnPiece.pieceRank + 1 == destination.getRank()) {
            return true;
        }

        // for black pawn
        if (returnPiece.pieceType == ReturnPiece.PieceType.BP 
                && returnPiece.pieceFile == destination.getFile() 
                && returnPiece.pieceRank - 1 == destination.getRank()) {
            return true;
        }

        return false;
    }


    @Override
    public void executeMove(Position destination) {
        // Update the pawn's position to the destination
        this.position = destination;
        
       
    }

    public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
        return null;
    }
}