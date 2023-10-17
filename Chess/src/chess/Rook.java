package chess;
import java.util.ArrayList;

public class Rook extends Piece{

	private boolean hasMoved;
	
	public Rook(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
        this.hasMoved = false;
    }

	/* can move any number of squares either horizontally or vertically
	* cannot jump over other pieces
	* special moves: castling
	* */
	@Override
	public boolean isValidMove(ReturnPlay currentGame, Position destination) {
		Position source = position;

		// can only move straight vertically or horizontally
		// can only be a change in file OR rank
		int fileChange = source.getFileChar() - destination.getFileChar();
		int rankChange = source.getRank() - destination.getRank();
		if (Math.abs(fileChange + rankChange) != (Math.abs(fileChange-rankChange))){
			return false;
		}

		// is there a piece in between start and dest? rook can't jump
		ArrayList<Position> spotsOnPath = getSpotsOnPath(source, destination);
		if (spotsOnPath.size() == 0) return true;
		for (Position spot : spotsOnPath){
			// check if any pieces block the move
			for (ReturnPiece pieceOnBoard : currentGame.piecesOnBoard){
				if (pieceOnBoard.pieceRank == spot.getRank()
						&& pieceOnBoard.pieceFile == spot.getFile()){
					return false;
				}
			}
		}
		hasMoved = true;
		return true;
	}

	// rook moves horizontally or vertically. find the spots in between source and destination
	public ArrayList<Position> getSpotsOnPath(Position source, Position destination){
		// is the rook moving horizontally or vertically?

		int fileChange = destination.getFileChar() - source.getFileChar();
		int rankChange = destination.getRank() - source.getRank();
		int numSpots = 0;
		int fileDirection = 0;
		int rankDirection = 0;

		// moving horizontally
		if (Math.abs(fileChange) > 0){
			numSpots = Math.abs(fileChange)-1;

			// find fileDirection
			if (fileChange < 0){
				fileDirection = -1;
			}
			else fileDirection = 1;
		}

		// moving vertically
		else {
			numSpots = Math.abs(rankChange)-1;

			// find rankDirection
			if (rankChange < 0){
				rankDirection = -1;
			}
			else rankDirection = 1;
		}

		// get all spots between
		ArrayList<Position> spotsOnPath = new ArrayList<>();
		char sourceFile = source.getFileChar();
		int sourceRank = source.getRank();
		for (int i = 1; i <= numSpots; i++){
			Position currSpot = new Position(null, 1);

			// currSpot's file
			currSpot.setFile(source.convertIntToFile(sourceFile + (i*fileDirection)));

			// currSpot's rank
			currSpot.setRank(sourceRank + (rankDirection * i));

			spotsOnPath.add(currSpot);
		}

		return spotsOnPath;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
}