package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;

abstract class Piece {
    protected ReturnPiece returnPiece;
    protected Position position;  // This represents the current position of the piece.

    public Piece(ReturnPiece.PieceType pieceType, Position position) {
    	
        this.returnPiece = new ReturnPiece();
        this.returnPiece.pieceType = pieceType;
        this.position = position;  
        this.returnPiece.pieceFile = position.getFile();
        this.returnPiece.pieceRank = position.getRank();
    }

    public ReturnPiece getReturnPiece() {
        return returnPiece;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.returnPiece.pieceFile = position.getFile();
        this.returnPiece.pieceRank = position.getRank();
    }

    public abstract boolean isValidMove(ReturnPlay currentGame, Position destination);
    public abstract ArrayList<Position> getSpotsOnPath(Position source, Position destination);

	public void executeMove(Position destination) {
		this.position = destination;
        // check for special move? take? check? !!!!
	}

    public boolean resultsInCheck(ReturnPlay currentGame){
        ArrayList<ReturnPiece> piecesOnBoard = currentGame.piecesOnBoard;

        // find the king
        Position kingPosition = new Position(null, 1);


        // determine who is the opponent
        boolean playerIsWhite = (returnPiece.pieceType.name().charAt(0) == 'W');

        // find opponent king
        for (ReturnPiece piece : piecesOnBoard){
            if (playerIsWhite) {
                if (piece.pieceType == ReturnPiece.PieceType.BK){
                    kingPosition.setFile(piece.pieceFile);
                    kingPosition.setRank(piece.pieceRank);
                }
            }

            else {
                if (piece.pieceType == ReturnPiece.PieceType.WK){
                    kingPosition.setFile(piece.pieceFile);
                    kingPosition.setRank(piece.pieceRank);
                }
            }
        }

        // run isValidMove() on start: pieceLocation, end: opponent king's position
        if (isValidMove(currentGame, kingPosition)){
            return true;
        }

        return false;
    }


    // will the current move put the current player in check?
    public void selfcheckCheck(ArrayList<ReturnPiece> piecesOnBoard, Position destination){
        ArrayList<ReturnPiece> hypotheticalBoard = createHypotheticalBoard(piecesOnBoard, destination);

        // run "check" check on all the opponent's pieces
        for (ReturnPiece piece : piecesOnBoard){
            // if opponent's piece
                // run "check" check


            // reset the board?
        }

        // reset the board?

    }

    public ArrayList<ReturnPiece> createHypotheticalBoard(ArrayList<ReturnPiece> piecesOnBoard, Position destination){
        // create hypothetical move
        ReturnPiece hypotheticalPiece = returnPiece;
        hypotheticalPiece.pieceRank = destination.getRank();
        hypotheticalPiece.pieceFile = destination.getFile();

        // pop original piece and replace w hypothetical
        piecesOnBoard.remove(returnPiece);
        piecesOnBoard.add(hypotheticalPiece);

        return piecesOnBoard;
    }
}
