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
    public boolean willCheckSelf(ReturnPlay currentGame, Position destination){
        ArrayList<ReturnPiece> piecesOnBoard = currentGame.piecesOnBoard;

        // save piece's OG position, hypothesize board, run checks, then return board to OG state
        Position pieceOGPosition = new Position(returnPiece.pieceFile, returnPiece.pieceRank);
        ArrayList<ReturnPiece> hypotheticalBoard = createHypotheticalBoard(piecesOnBoard, destination);


        char playerColor = returnPiece.pieceType.name().charAt(0);

        // run "check" check on all the opponent's pieces
        for (ReturnPiece retPiece : piecesOnBoard){
            // if opponent's piece
            if (retPiece.pieceType.name().charAt(0) != playerColor){
                // create a piece obj of this ReturnPiece
                Piece piece = Chess.returnPiece(retPiece.pieceType, retPiece.pieceFile, retPiece.pieceRank);

                // check if any move this piece can make will result in check
                if (piece.resultsInCheck(currentGame)){
                    unhypothesizeBoard(hypotheticalBoard, destination);
                    return true;
                }
            }
        }

        unhypothesizeBoard(hypotheticalBoard, destination);
        return false;
    }

    // returns the piece on the landing spot
    public ReturnPiece landsOnAPiece(ReturnPlay currentGame, Position destination){
        char playerColor = returnPiece.pieceType.name().charAt(0);

        // find if there is a piece on the destination
        for (ReturnPiece piece : currentGame.piecesOnBoard) {
            char currColor = piece.pieceType.name().charAt(0);

            if (piece.pieceFile == destination.getFile() && piece.pieceRank == destination.getRank()) {
                    return piece;
            }
        }

        return null;
    }

    public boolean sameColor(ReturnPiece compareTo){
        char thisColor = returnPiece.pieceType.name().charAt(0);
        char thatColor = compareTo.pieceType.name().charAt(0);

        if (thisColor == thatColor) return true;
        return false;
    }

    public ArrayList<ReturnPiece> createHypotheticalBoard(ArrayList<ReturnPiece> piecesOnBoard, Position destination){
        // create hypothetical move
        ReturnPiece hypotheticalPiece = new ReturnPiece();
        hypotheticalPiece.pieceType = returnPiece.pieceType;

        hypotheticalPiece.pieceRank = destination.getRank();
        hypotheticalPiece.pieceFile = destination.getFile();

        // pop original piece and replace w hypothetical
        piecesOnBoard.remove(returnPiece);
        piecesOnBoard.add(hypotheticalPiece);

        return piecesOnBoard;
    }

    public ArrayList<ReturnPiece> unhypothesizeBoard(ArrayList<ReturnPiece> hypotheticalBoard, Position destination){
        // create hypothetical move
        ReturnPiece hypotheticalPiece = new ReturnPiece();
        hypotheticalPiece.pieceType = returnPiece.pieceType;

        hypotheticalPiece.pieceRank = destination.getRank();
        hypotheticalPiece.pieceFile = destination.getFile();

        // pop hypothetical and replace with OG piece
        hypotheticalBoard.remove(hypotheticalPiece);
        hypotheticalBoard.add(returnPiece);

        return hypotheticalBoard;
    }
}