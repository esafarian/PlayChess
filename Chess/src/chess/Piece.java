package chess;

abstract class Piece {
    protected ReturnPiece returnPiece;
    protected Position position; 

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

    public abstract boolean isValidMove(Position destination);

	public void executeMove(Position destination) {
		
	}
}
