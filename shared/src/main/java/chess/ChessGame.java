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

    private void switchTeams(TeamColor currentTurn) {
        if (currentTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> possibleMoves = possibleMoves(teamTurn);
        ChessPiece piece = board.getPiece(startPosition);
        //Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        if (piece.getTeamColor() != teamTurn) {
            return validMoves;
        }

        //implement some more checks to determine if the move is actually valid

        for (ChessMove move : possibleMoves) {
            if (!movePutsInCheck(piece.getTeamColor(), move)) {
                validMoves.add(move);
            }
        }
        return validMoves;
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
            board.addPiece(move.getEndPosition(), new ChessPiece(this.teamTurn,move.getPromotionPiece()));
            board.addPiece(start, null);
            board.addPiece(move.getEndPosition(), myPiece);
            switchTeams(teamTurn);
        } else {
            throw new InvalidMoveException();
        }
    }

    private void unsafeMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start) != null) {
            ChessPiece myPiece = board.getPiece(start);
            board.addPiece(move.getEndPosition(), myPiece);
            board.addPiece(start, null);
        }
    }

    public Collection<ChessMove> possibleMoves(TeamColor color) {
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
            ChessPiece piece = board.getPiece(move.getEndPosition());
            if (board.getPiece(move.getEndPosition()) != null &&
                    board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING &&
                    board.getPiece(move.getEndPosition()).getTeamColor() == teamColor) {
                return true;
            }
        }
        return false;
    }

    private boolean movePutsInCheck(TeamColor teamColor, ChessMove move) {
        ChessPiece destPiece = board.getPiece(move.getEndPosition());
        unsafeMove(move);
        ChessMove undo = new ChessMove(move.getEndPosition(), move.getStartPosition(), null);
        if (isInCheck(teamColor)) {
            board.addPiece(move.getEndPosition(), destPiece);
            board.addPiece(move.getStartPosition(),board.getPiece(move.getEndPosition()));
            return true;
        } else {
            board.addPiece(move.getEndPosition(), destPiece);
            board.addPiece(move.getStartPosition(),board.getPiece(move.getEndPosition()));
            return false;
        }
    }

    //make move but without the check to see if the move is valid, for use in checking if future moves put me in check

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(isInCheck(teamColor)){return false;}
        Collection<ChessMove> possibleMoves = possibleMoves(teamColor);
        for (ChessMove move : possibleMoves) {
            if (movePutsInCheck(teamColor, move)) {
                possibleMoves.remove(possibleMoves);
            }
        }
        return possibleMoves.isEmpty();
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
        return this.board;
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
