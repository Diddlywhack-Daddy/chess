package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    private void switchTeams(TeamColor currentTurn) {
        if (currentTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> validMoves = piece.pieceMoves(board, startPosition);

        //implement some more checks to determine if the move is actually valid
        //checks if the move puts the king in check NOT WORKING RN
        for (ChessMove move : validMoves) {
            if (movePutsInCheck(piece.getTeamColor(), move)) {
                validMoves.remove(move);
            }
        }

        return validMoves;
    }

    private boolean movePutsInCheck(TeamColor teamColor, ChessMove move) {
        ChessPiece destPiece = board.getPiece(move.getEndPosition());

        unsafeMove(move);
        ChessMove undo = new ChessMove(move.getEndPosition(), move.getStartPosition(), null);
        if (!isInCheck(teamColor)) {
            board.addPiece(move.getEndPosition(), destPiece);
            unsafeMove(undo);
            return false;
        } else {
            board.addPiece(move.getEndPosition(), destPiece);
            unsafeMove(undo);
            return true;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start) != null &&
                validMoves(start).contains(move)) {
            ChessPiece myPiece = board.getPiece(start);
            board.addPiece(move.getEndPosition(), null);
            board.addPiece(start, null);
            board.addPiece(move.getEndPosition(), myPiece);
        } else {
            throw new InvalidMoveException();
        }
    }

    //make move but without the check to see if the move is valid, for use in checking if future moves put me in check
    private void unsafeMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start) != null) {
            ChessPiece myPiece = board.getPiece(start);
            board.addPiece(move.getEndPosition(), null);
            board.addPiece(start, null);
            board.addPiece(move.getEndPosition(), myPiece);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessGame.TeamColor otherColor;
        if (teamColor == TeamColor.WHITE) {
            otherColor = TeamColor.BLACK;
        } else {
            otherColor = TeamColor.WHITE;
        }

        Collection<ChessMove> otherMoves = possibleMoves(otherColor);

        for (ChessMove move : otherMoves) {
            if (board.getPiece(move.getEndPosition()) != null &&
                    board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING &&
                    board.getPiece(move.getEndPosition()).getTeamColor() == teamColor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> possibleMoves = possibleMoves(teamColor);
        possibleMoves.removeIf(move -> movePutsInCheck(teamColor, move));
        return possibleMoves.isEmpty();
    }

    public Collection<ChessMove> possibleMoves(ChessGame.TeamColor color) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition boardPosition = new ChessPosition(i, j);
                //safety check for a piece on the square
                if (board.getPiece(boardPosition) != null) {
                    //if the piece is my color, save all their possible moves
                    if (board.getPiece(boardPosition).getTeamColor() == color) {
                        possibleMoves.addAll(board.getPiece(boardPosition).pieceMoves(board, boardPosition));
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
