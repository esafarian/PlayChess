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

    public char getFileChar() {
        return file.name().charAt(0);
    }

    public ReturnPiece.PieceFile convertIntToFile (int intFile) {
        switch (intFile){
            case 'a':
                return ReturnPiece.PieceFile.a;
            case 'b':
                return ReturnPiece.PieceFile.b;
            case 'c':
                return ReturnPiece.PieceFile.c;
            case 'd':
                return ReturnPiece.PieceFile.d;
            case 'e':
                return ReturnPiece.PieceFile.e;
            case 'f':
                return ReturnPiece.PieceFile.f;
            case 'g':
                return ReturnPiece.PieceFile.g;
            case 'h':
                return ReturnPiece.PieceFile.h;
        }

        return null;
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

