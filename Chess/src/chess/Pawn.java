package chess;

class Pawn extends Piece {

	public Pawn(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank) {
        super(pieceType, pieceFile, pieceRank);
    }

	@Override
    public boolean isValidMove(ReturnPiece.PieceFile destinationFile, int destinationRank) {
		if (returnPiece.pieceType == ReturnPiece.PieceType.WP && returnPiece.pieceFile == destinationFile && returnPiece.pieceRank + 1 == destinationRank) {
			return true;
		}

        if (returnPiece.pieceType == ReturnPiece.PieceType.BP && returnPiece.pieceFile == destinationFile && returnPiece.pieceRank - 1 == destinationRank) {
            return true;
        }


        return false;
    }
}
