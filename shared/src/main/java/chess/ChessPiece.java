package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType pieceType;
    private ChessGame.TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        pieceType = type;

    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (this.pieceType) {
            case KING -> getKingMoves(board, myPosition);
            case QUEEN -> getQueenMoves(board, myPosition);
            case BISHOP -> getBishopMoves(board, myPosition);
            case KNIGHT -> getKnightMoves(board, myPosition);
            case ROOK -> getRookMoves(board, myPosition);
            case PAWN -> getPawnMoves(board, myPosition);
        };

    }

    private Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> kingMoves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).color;

        //forward-right
        if (currentRow + 1 < 9 && currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //forward left
        if (currentRow + 1 < 9 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //forward
        if (currentRow + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //left
        if (currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //right
        if (currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //backward
        if (currentRow - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //backward-left
        if (currentRow - 1 > 0 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //backward-right
        if (currentRow - 1 > 0 && currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return kingMoves;
    }

    private Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> queenMoves = new ArrayList<>();
        queenMoves.addAll(getBishopMoves(board, myPosition));
        queenMoves.addAll(getRookMoves(board, myPosition));
        return queenMoves;
    }

    private Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> bishopMoves = new ArrayList<>();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).color;
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //checks forward-right
        int r = 1;
        while (currentRow + r < 9 && currentCol + r < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + r, currentCol + r);
            if (board.getPiece(newPosition) == null) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
            r++;
        }
        //checks backward-left
        r = 1;
        while (currentRow - r > 0 && currentCol - r > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - r, currentCol - r);
            if (board.getPiece(newPosition) == null) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
            r++;
        }
        //checks forward-left
        int c = 1;
        while (c + currentRow < 9 && currentCol - c > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow + c, currentCol - c);
            if (board.getPiece(newPosition) == null) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
            c++;
        }
        //checks backward-right
        c = 1;
        while (currentRow - c > 0 && currentCol + c < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow - c, currentCol + c);
            if (board.getPiece(newPosition) == null) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                bishopMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
            c++;
        }
        return bishopMoves;
    }

    private Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> knightMoves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).color;

        //right 1 forward 2
        if (currentRow + 2 < 9 && currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //left 1 forward 2
        if (currentRow + 2 < 9 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //right 2 forward 1
        if (currentRow + 1 < 9 && currentCol + 2 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //left 2 forward 1
        if (currentCol - 2 > 0 && currentRow + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //right 2 back 1
        if (currentCol + 2 < 9 && currentRow - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //left 2 back 1
        if (currentRow - 1 > 0 && currentCol - 2 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //right 1 back 2
        if (currentRow - 2 > 0 && currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        //left 1 back 2
        if (currentRow - 2 > 0 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).color != myColor) {
                knightMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return knightMoves;
    }

    private Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> rookMoves = new ArrayList<>();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).color;
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //checks forward
        for (int r = currentRow + 1; r < 9; r++) {
            ChessPosition newPosition = new ChessPosition(r, currentCol);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
        }
        //checks backward
        for (int r = currentRow - 1; r > 0; r--) {
            ChessPosition newPosition = new ChessPosition(r, currentCol);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
        }
        //checks right
        for (int c = currentCol + 1; c < 9; c++) {
            ChessPosition newPosition = new ChessPosition(currentRow, c);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
        }
        //checks left
        for (int c = currentCol - 1; c > 0; c--) {
            ChessPosition newPosition = new ChessPosition(currentRow, c);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).color == myColor) {
                break;
            } else if (board.getPiece(newPosition).color != myColor) {
                rookMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            }
        }
        return rookMoves;
    }

    private Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> pawnMoves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).color;

        if (myColor == ChessGame.TeamColor.WHITE) {

            //handles move 2 forward
            if (currentRow == 2) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol);
                if (board.getPiece(new ChessPosition(currentRow + 1, currentCol)) == null && board.getPiece(newPosition) == null) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //move 1 forward
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (currentRow == 7 && board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
            } else if (board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(myPosition, newPosition, null));
            }

            //handles left capture
            if (currentCol - 1 > 0) {
                newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
                if (currentRow == 7 && board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                } else if (board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //handles right capture
            if (currentCol + 1 < 9) {
                newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
                if (currentRow == 7 && board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                } else if (board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

        } else {
            //Black pieces
            //move 2 backward
            if (currentRow == 7) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol);
                if (board.getPiece(new ChessPosition(currentRow - 1, currentCol)) == null && board.getPiece(newPosition) == null) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //move 1 backward
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (currentRow == 2 && board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
            } else if (board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(myPosition, newPosition, null));
            }

            //handles left capture
            if (currentCol - 1 > 0) {
                newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
                if (currentRow == 2 && board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                } else if (board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //handles right capture
            if (currentCol + 1 < 9) {
                newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
                if (currentRow == 2 && board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                } else if (board.getPiece(newPosition) != null && board.getPiece(newPosition).color != color) {
                    pawnMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }
        return pawnMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceType == that.pieceType && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, color);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "type=" + pieceType +
                ", pieceColor=" + color +
                '}';
    }
}
