// Eleeza Safarian
// Alyssa Fermin

package chess;

import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	private static ReturnPlay currentGame = new ReturnPlay();
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		start();
		
		return currentGame;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		//instantiate arraylist to be filled with all the pieces *currently* on the board
		currentGame.piecesOnBoard = new ArrayList<>();
		        
		        
		//for each ReturnPiece object in the arraylist,
		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) {
			
			//create a new ReturnPiece Object
			ReturnPiece whitePawn = new ReturnPiece();
			
			//for each piece on the board, establish its location by setting the file and rank
			// as well as the given possible piecetypes (see ReturnPiece class fields)
			
			whitePawn.pieceType = ReturnPiece.PieceType.WP;
			whitePawn.pieceFile = file;
			whitePawn.pieceRank = 2;
	            			
			//once the ReturnPiece object for a piece is created, it can be added to arraylist
			currentGame.piecesOnBoard.add(whitePawn);
				    
			//do the same for black pawns and rest of the pieces
			ReturnPiece blackPawn = new ReturnPiece();
            blackPawn.pieceType = ReturnPiece.PieceType.BP;
            blackPawn.pieceFile = file;
            blackPawn.pieceRank = 7;
            currentGame.piecesOnBoard.add(blackPawn);
            
            //manually set each of black
            if (file == ReturnPiece.PieceFile.a || file == ReturnPiece.PieceFile.h) {
                // Rooks
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WR, file, 1);
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BR, file, 8);
            } else if (file == ReturnPiece.PieceFile.b || file == ReturnPiece.PieceFile.g) {
                // Knights
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WN, file, 1);
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BN, file, 8);
            } else if (file == ReturnPiece.PieceFile.c || file == ReturnPiece.PieceFile.f) {
                // Bishops
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WB, file, 1);
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BB, file, 8);
            } else if (file == ReturnPiece.PieceFile.d) {
                // Queens
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WQ, file, 1);
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BQ, file, 8);
            } else if (file == ReturnPiece.PieceFile.e) {
                // Kings
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WK, file, 1);
                addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BK, file, 8);
            }
        }
				    
			//this arraylist holds all the pieces that have been "placed" on the board
			// each object's file and rank fields hold their default position on the board
			//this arraylist is then used in the PlayChess class to print the board out to the user
			//the objects in this arraylist can also be updated by setting the file and rank after a move is played
				
		
}
	//helper method
	//literally just adds the pieces to the arraylist in start()
	private static void addPiece(ArrayList<ReturnPiece> piecesOnBoard, ReturnPiece.PieceType type, ReturnPiece.PieceFile file, int rank) {
		ReturnPiece piece = new ReturnPiece();
        piece.pieceType = type;
        piece.pieceFile = file;
        piece.pieceRank = rank;
        piecesOnBoard.add(piece);
		}
		       
		
		
	}


