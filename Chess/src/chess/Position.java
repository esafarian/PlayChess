package chess;

class Position {

	private ReturnPiece.PieceFile file;
    private int rank; 

    public Position(ReturnPiece.PieceFile file, int rank) {
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank must be between 1 and 8.");
        }
        this.file = file;
        this.rank = rank;
    }

    public ReturnPiece.PieceFile getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public void setFile(ReturnPiece.PieceFile file) {
        this.file = file;
    }

    public void setRank(int rank) {
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank must be between 1 and 8.");
        }
        this.rank = rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + rank;
        return result;
    }

    @Override
    public String toString() {
        return "" + file + rank;
    }
    
 
}

