package chess;

import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }


	// can move any number of squares diagonally. cannot jump over other pieces
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		Position source = position;

		// is the move a diagonal move?
		// change in rank = change in file
		if ( Math.abs(source.getFile().name().charAt(0) - destination.getFile().name().charAt(0))
				!= Math.abs(source.getRank() - destination.getRank()) ){
			return false;
		}

		// is there a piece in between start and destination? bishop can't jump.
		ArrayList<Position> spotsOnPath = getSpotsOnPath(source, destination);
		for (Position spot : spotsOnPath){
			// check if any of the pieces block the move
			for (ReturnPiece pieceOnBoard : currentGame.piecesOnBoard){
				if (pieceOnBoard.pieceRank == spot.getRank()
					&& pieceOnBoard.pieceFile == spot.getFile()){
					return false;
				}
			}
		}

		return true;
	}

	// bishop moves diagonally. find the spots in between source and destination
	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		int numSpots = Math.abs(source.getRank() - destination.getRank()) - 1;

		// no spots between, return NULL
		if (numSpots == 0) return null;

		// figure out which way file and rank changes
		char sourceFile = source.getFileChar();
		int sourceRank = source.getRank();
		char destinationFile = destination.getFileChar();
		int destinationRank = destination.getRank();

		int fileDirection = destinationFile - sourceFile;
		if (fileDirection > 0) fileDirection = 1;
		else fileDirection = -1;

		int rankDirection = destinationRank - sourceRank;
		if (rankDirection > 0) rankDirection = 1;
		else rankDirection = -1;


		// get all spots between
		ArrayList<Position> spotsOnPath = new ArrayList<>();
		for (int i = 1; i <= numSpots; i++){
			Position currSpot = new Position(null, 1);

			// currSpot's file
			currSpot.setFile(source.convertIntToFile(sourceFile + (fileDirection * i)));

			// currSpot's rank
			currSpot.setRank(sourceRank + (rankDirection * i));

			spotsOnPath.add(currSpot);
		}

		return spotsOnPath;
	}

	@Override
	public void executeMove(Position destination) {

	}

}
