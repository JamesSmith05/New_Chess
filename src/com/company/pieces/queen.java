package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class queen extends piece {

    public queen(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }


    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        return (validMoveRook(xp, yp, isWhite, willKill) || validMoveBishop(xp, yp, isWhite, willKill)); //combines bishop and rook moves
    }

}
