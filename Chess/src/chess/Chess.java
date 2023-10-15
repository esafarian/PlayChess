// Eleeza Safarian
// Alyssa Fermin

package chess;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

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
	// hashmap to map Pieces to corresponding ReturnPiece objects
	private static Map<ReturnPiece, Piece> pieceMap = new HashMap<>();


	public static ReturnPlay play(String move) {

		// set currentGame msg to null at start of each move
		currentGame.message = null;

		// is move = "resign"?
		if (move.equalsIgnoreCase("resign")) {
			if (currPlayer == Player.white) {
				currentGame.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			} else {
				currentGame.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			}

			return currentGame;
		}

		// is the move in correct FileRank A, FileRank B notation?
		// not case-sensitive. also ensures that the move is on the board
		move = move.toLowerCase(Locale.ROOT);
		if ( isBadNotation(move) ){
			currentGame.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return currentGame;
		}

		// parse move after verifying notation
		Position start = new Position(null, 1);
		Position end = new Position(null, 1);
		Position[] startAndEnd = parseMove(start, end, move);
		start = startAndEnd[0];
		end = startAndEnd[1];

		// is it a valid move?
		// first: does the piece the player wants to move belong to them?
		if ( !hasValidPiece(start) ){
			currentGame.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return currentGame;
		}

//		Position start = new Position(null, 1);
//		Position end = new Position(null, 1);
//		Position[] startAndEnd = parseMove(start, end, move);
//		start = startAndEnd[0];
//		end = startAndEnd[1];

		// store the piece the player wants to move
		ReturnPiece currReturnPiece = new ReturnPiece();
		currReturnPiece.pieceType = getPieceAt(start);
		currReturnPiece.pieceRank = start.getRank();
		currReturnPiece.pieceFile = start.getFile();

		Piece currPiece = returnPiece(currReturnPiece.pieceType, start.getFile(), start.getRank());

		// second: can the piece they want to move move like that? / is there a piece in the way?
		if ( !currPiece.isValidMove(currentGame, end) ){
			currentGame.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return currentGame;
		}

		// third: will the move put its own king in check?

		// try to carry out move, including special moves
		currPiece.executeMove(end);
		currentGame = executeMove(currentGame, end, currReturnPiece);

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
		currPlayer = Player.white;
		currentGame.piecesOnBoard = new ArrayList<>();
		fillBoard();


	}

	/*
	 * USED IN: start()
	 * FUNCTION: instantiates pieces on board
	 * OUTPUT: when PlayChess is run, this method is called and PlayChess outputs the starting board
	 */
	private static void fillBoard() {

		// for each ReturnPiece object in the arraylist,
		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) {

			// white pawns
			ReturnPiece whitePawn = new ReturnPiece();
			whitePawn.pieceType = ReturnPiece.PieceType.WP;
			whitePawn.pieceFile = file;
			whitePawn.pieceRank = 2;
			Piece wp = new Pawn(whitePawn.pieceType, new Position(whitePawn.pieceFile, whitePawn.pieceRank));
			pieceMap.put(whitePawn, wp);
			currentGame.piecesOnBoard.add(whitePawn);

			// black pawns
			ReturnPiece blackPawn = new ReturnPiece();
			blackPawn.pieceType = ReturnPiece.PieceType.BP;
			blackPawn.pieceFile = file;
			blackPawn.pieceRank = 7;
			Piece bp = new Pawn(blackPawn.pieceType, new Position(blackPawn.pieceFile, blackPawn.pieceRank));
			pieceMap.put(blackPawn, bp);
			currentGame.piecesOnBoard.add(blackPawn);

			// other pieces
			if (file == ReturnPiece.PieceFile.a || file == ReturnPiece.PieceFile.h) {
				// Rooks
				ReturnPiece wrPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WR, file, 1);
				ReturnPiece brPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BR, file, 8);
				pieceMap.put(wrPiece, new Rook(wrPiece.pieceType, new Position(wrPiece.pieceFile, wrPiece.pieceRank)));
				pieceMap.put(brPiece, new Rook(brPiece.pieceType, new Position(brPiece.pieceFile, brPiece.pieceRank)));

			} else if (file == ReturnPiece.PieceFile.b || file == ReturnPiece.PieceFile.g) {
				// Knights
				ReturnPiece wnPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WN, file, 1);
				ReturnPiece bnPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BN, file, 8);
				pieceMap.put(wnPiece, new Knight(wnPiece.pieceType, new Position(wnPiece.pieceFile, wnPiece.pieceRank)));
				pieceMap.put(bnPiece, new Knight(bnPiece.pieceType, new Position(bnPiece.pieceFile, bnPiece.pieceRank)));

			} else if (file == ReturnPiece.PieceFile.c || file == ReturnPiece.PieceFile.f) {
				// Bishops
				ReturnPiece wbPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WB, file, 1);
				ReturnPiece bbPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BB, file, 8);
				pieceMap.put(wbPiece, new Bishop(wbPiece.pieceType, new Position(wbPiece.pieceFile, wbPiece.pieceRank)));
				pieceMap.put(bbPiece, new Bishop(bbPiece.pieceType, new Position(bbPiece.pieceFile, bbPiece.pieceRank)));

			} else if (file == ReturnPiece.PieceFile.d) {
				// Queens
				ReturnPiece wqPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WQ, file, 1);
				ReturnPiece bqPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BQ, file, 8);
				pieceMap.put(wqPiece, new Queen(wqPiece.pieceType, new Position(wqPiece.pieceFile, wqPiece.pieceRank)));
				pieceMap.put(bqPiece, new Queen(bqPiece.pieceType, new Position(bqPiece.pieceFile, bqPiece.pieceRank)));

			} else if (file == ReturnPiece.PieceFile.e) {
				// Kings
				ReturnPiece wkPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.WK, file, 1);
				ReturnPiece bkPiece = addPiece(currentGame.piecesOnBoard, ReturnPiece.PieceType.BK, file, 8);
				pieceMap.put(wkPiece, new King(wkPiece.pieceType, new Position(wkPiece.pieceFile, wkPiece.pieceRank)));
				pieceMap.put(bkPiece, new King(bkPiece.pieceType, new Position(bkPiece.pieceFile, bkPiece.pieceRank)));
			}
		}

	}

	private static ReturnPiece addPiece(ArrayList<ReturnPiece> piecesOnBoard, ReturnPiece.PieceType type, ReturnPiece.PieceFile file, int rank) {
		ReturnPiece piece = new ReturnPiece();
		piece.pieceType = type;
		piece.pieceFile = file;
		piece.pieceRank = rank;
		piecesOnBoard.add(piece);
		return piece;  // Return the piece so that it can be used for mapping
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

		// trim leading/trailing whitespaces: "  a2  a3  " --> "a2  a3"
		move = move.trim();

		char file1 = move.charAt(0);
		char rank1 = move.charAt(1);
		char file2 = move.charAt(3);
		char rank2 = move.charAt(4);

		// move cannot be stay in same place
		if (file1 == file2 && rank1 == rank2){
			return true;
		}
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
	 * USED IN: play()
	 * FUNCTION: checks does the piece the player wants to move belong to them?
	 * RETURNS: TRUE if the piece is found and belongs to player, FALSE if piece not found or doesn't belong to player
	 */
	public static boolean hasValidPiece(Position start){
		char file1 = start.getFileChar();
		int rank1 = start.getRank();

		// does the piece on FileRankA belong to the current player?
		for (int i = 0; i<currentGame.piecesOnBoard.size(); i++){
			ReturnPiece piece = currentGame.piecesOnBoard.get(i);

			// if piece is found check if it belongs to the player
			if ( piece.pieceFile.name().charAt(0) == file1
					&& piece.pieceRank == rank1 ){

				// belongs to the player
				if (piece.pieceType.name().toLowerCase().charAt(0) == currPlayer.name().charAt(0)){
					return true;
				}

				// does not belong to the player
				return false;
			}
		}
		return false;
	}

	/* USED IN: play()
	 * FUNCTION: parses String input and returns
	 *
	 *
	 */
	private static Position[] parseMove(Position start, Position end, String move) {

		// trim any trailing or leading white spaces
		move = move.trim();

		// split the move based on spaces
		String[] splitMove = move.split(" ");

		// parsing the starting position
		ReturnPiece.PieceFile startFile = ReturnPiece.PieceFile.valueOf(splitMove[0].substring(0, 1));
		int startRank = Integer.parseInt(splitMove[0].substring(1, 2));

		// parsing the ending position
		ReturnPiece.PieceFile destFile = ReturnPiece.PieceFile.valueOf(splitMove[1].substring(0, 1));
		int destRank = Integer.parseInt(splitMove[1].substring(1, 2));

		// set the parsed positions to the given Position objects
		start.setFile(startFile);
		start.setRank(startRank);
		end.setFile(destFile);
		end.setRank(destRank);

		// default promotion is to Queen
		if(currPlayer == Player.white) {
			ReturnPiece.PieceType promotionPiece = ReturnPiece.PieceType.WQ;
		} else {
			ReturnPiece.PieceType promotionPiece = ReturnPiece.PieceType.BQ;
		}

		// if there is a pawn promotion
		if (splitMove.length > 2) {
			char promotionChar = Character.toUpperCase(splitMove[2].charAt(0));
			pawnPromotion(promotionChar, getPieceAt(start));
		}


		// Check for special commands
		if ("resign".equalsIgnoreCase(move)) {
			currentGame.message = (currPlayer == Player.white) ? ReturnPlay.Message.RESIGN_WHITE_WINS : ReturnPlay.Message.RESIGN_BLACK_WINS;
		} else if (move.endsWith("draw?")) {
			currentGame.message = ReturnPlay.Message.DRAW;
		}

		Position[] startAndEnd = {start, end};
		return startAndEnd;
	}

	private static void pawnPromotion(char promotionChar, ReturnPiece.PieceType promoted) {
		ReturnPiece.PieceType promotionPiece = null;

		switch (promotionChar) {
			case 'N':
				promotionPiece = (currPlayer == Player.white) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
				break;
			case 'B':
				promotionPiece = (currPlayer == Player.white) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
				break;
			case 'R':
				promotionPiece = (currPlayer == Player.white) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
				break;
			case 'Q':
				promotionPiece = (currPlayer == Player.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
				break;
		}

		promoted = promotionPiece;


	}

	/*
	 * USED IN: play()
	 * FUNCTION: gets the piece at the square in question
	 *
	 */
	private static ReturnPiece.PieceType getPieceAt(Position position) {

		for (ReturnPiece piece : currentGame.piecesOnBoard) {
			if (piece.pieceFile == position.getFile() && piece.pieceRank == position.getRank()) {
				return piece.pieceType;
			}
		}

		return null;

	}

	/*
	 * FUNCTION: takes ReturnPiece info and returns corresponding piece obj
	 */
	private static Piece returnPiece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile file, int rank){
		// pawns
		if (pieceType == ReturnPiece.PieceType.BP || pieceType == ReturnPiece.PieceType.WP){
			return new Pawn(pieceType, new Position(file, rank));
		}

		// rooks
		else if (pieceType == ReturnPiece.PieceType.BR || pieceType == ReturnPiece.PieceType.WR){
			return new Rook(pieceType, new Position(file, rank));
		}

		// bishops
		else if (pieceType == ReturnPiece.PieceType.BB || pieceType == ReturnPiece.PieceType.WB){
			return new Bishop(pieceType, new Position(file, rank));
		}

		// knights
		else if (pieceType == ReturnPiece.PieceType.BN || pieceType == ReturnPiece.PieceType.WN){
			return new Knight(pieceType, new Position(file, rank));
		}

		// queens
		else if (pieceType == ReturnPiece.PieceType.BQ || pieceType == ReturnPiece.PieceType.WQ){
			return new Queen(pieceType, new Position(file, rank));
		}

		// kings
		else if (pieceType == ReturnPiece.PieceType.BK || pieceType == ReturnPiece.PieceType.WK){
			return new King(pieceType, new Position(file, rank));
		}

		return null;
	}

	public static ReturnPlay executeMove(ReturnPlay currentGame, Position destination, ReturnPiece currReturnPiece) {
		int i = currentGame.piecesOnBoard.indexOf(currReturnPiece);
		currentGame.piecesOnBoard.get(i).pieceFile = destination.getFile();
		currentGame.piecesOnBoard.get(i).pieceRank = destination.getRank();

		// check for take
		// check for special move
		// check for check

		return currentGame;
	}


}


