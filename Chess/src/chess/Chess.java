// Eleeza Safarian
// Alyssa Fermin

package chess;

import java.util.ArrayList;
import java.util.Locale;

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

	enum Player {white, black}

	/**
	 * Plays the next move for whichever player has the turn.
	 *
	 * @param move String for next move, e.g. "a2 a3"
	 * @return A ReturnPlay instance that contains the result of the move.
	 * See the section "The Chess class" in the assignment description for details of
	 * the contents of the returned ReturnPlay instance.
	 */
	private static ReturnPlay currentGame = new ReturnPlay();
	private static Player currPlayer;

	public static ReturnPlay play(String move) {
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */

		// set currentGame msg to null at start of each move
		currentGame.message = null;

		// is move = "resign"?
		if (move.equals("resign")) {
			if (currPlayer == Player.white) {
				currentGame.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			} else {
				currentGame.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			}

			return currentGame;
		}

		// is the move in correct FileRank A, FileRank B notation?
		// not case-sensitive. also ensures that the move is on the board
		if ( isBadNotation(move) ){
			currentGame.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return currentGame;
		}

		// is it a valid move?
		if ( isValidMove(move) ){

		}

		// try to carry out move, including special moves

		// update whose turn it is?

		// check for "draw" after executing move

		return currentGame;
	}


	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		//instantiate arraylist to be filled with all the pieces *currently* on the board
		currentGame.piecesOnBoard = new ArrayList<>();
		currPlayer = Player.white;


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

	/*
	* USED IN: play()
	* FUNCTION: checks that a given move is in proper FileRank A, FileRank B notation
	* ex: correct: g8 h6 OR G8 H6  --  incorrect: g8h6 OR gggg
 	*/
	public static boolean isBadNotation(String move){
		//move would be greater than 5 if player tries to draw ex: a1 b2 draw?
		if (move.length() < 5) {
			return true;
		}

		move = move.toLowerCase(Locale.ROOT);
		char file1 = move.charAt(0);
		char rank1 = move.charAt(1);
		char file2 = move.charAt(3);
		char rank2 = move.charAt(4);

		// ensure that both files are valid (letters a-h). if INVALID call badNotation
		if (file1 < 97 || file1 > 104 || file2 < 97 || file2 > 104){
			return true;
		}

		// ensure that both ranks are valid (nums 1-8). if INVALID call badNotation
		if (rank1 < 49 || rank1 > 56 || rank2 < 49 || rank2 > 56){
			return true;
		}

		return false;
	}

	/*
	 * USED IN:
	 * FUNCTION:
	 * ex: correct:
	 */
	public static boolean isValidMove(String move){

		return true;
	}


}


