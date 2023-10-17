package chess;

import java.util.ArrayList;

class Pawn extends Piece {
	
	private boolean hasMoved = false; // to track if the pawn has moved before

    public Pawn(ReturnPiece.PieceType pieceType, Position position) {
        super(pieceType, position);
    }

    @Override
    public boolean isValidMove(ReturnPlay currentGame, Position destination) {
    	int direction = returnPiece.pieceType.name().charAt(0) == 'W' ? 1 : -1; // 1 for white (moving up) and -1 for black (moving down)

    	// for moving forward 1 square
    	if (position.getFile() == destination.getFile() 
    			&& position.getRank() + direction == destination.getRank() 
                && landsOnAPiece(currentGame, destination) == null) {
                hasMoved = true;
                return true;
            }

            // moving forward 2 squares
            if (!hasMoved 
                && position.getFile() == destination.getFile() 
                && position.getRank() + 2 * direction == destination.getRank() 
                && landsOnAPiece(currentGame, new Position(position.getFile(), position.getRank() + direction)) == null 
                && landsOnAPiece(currentGame, destination) == null) {
                hasMoved = true;
                return true;
            }

            //capturing diagonally
            if (position.getRank() + direction == destination.getRank() 
            		&& (position.getFile().ordinal() + 1 == destination.getFile().ordinal() 
            	        || position.getFile().ordinal() - 1 == destination.getFile().ordinal()) 
            	    && landsOnAPiece(currentGame, destination) != null 
            	    && !sameColor(landsOnAPiece(currentGame, destination))) {
            	    hasMoved = true;
            	    take(currentGame, landsOnAPiece(currentGame, destination)); // taking the opponent piece
            	    return true;
            }
            return false;
	}

        @Override
        public ArrayList<Position> getSpotsOnPath(Position source, Position destination) {
            // Fill in for pawn-specific path logic
            return new ArrayList<>();
        }

        private void take(ReturnPlay currentGame, ReturnPiece capturedPiece) {
            // removing the captured piece from the board
            currentGame.piecesOnBoard.remove(capturedPiece);
            // Replace pawn's position
            this.setPosition(new Position(capturedPiece.pieceFile, capturedPiece.pieceRank));
        }

    }
