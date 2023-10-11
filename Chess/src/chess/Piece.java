package chess;

abstract class Piece {
    protected ReturnPiece returnPiece;

    public Piece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank) {
        this.returnPiece = new ReturnPiece();
        this.returnPiece.pieceType = pieceType;
        this.returnPiece.pieceFile = pieceFile;
        this.returnPiece.pieceRank = pieceRank;
    }

    public ReturnPiece getReturnPiece() {
        return returnPiece;
    }

    public abstract boolean isValidMove(ReturnPiece.PieceFile destinationFile, int destinationRank);
}
