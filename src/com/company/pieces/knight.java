package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class knight extends piece{

    public knight(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }
    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {

        }//L pattern up and down                          //LEFT                                   //RIGHT         //white side
        if (Board.selectedPieceOriginalYP - yp == 2 && (Board.selectedPieceOriginalXP - xp == 1 || xp - Board.selectedPieceOriginalXP == 1)) {
            return true;
        }//L pattern sideways
        if ((Board.selectedPieceOriginalXP - xp == 2 || xp - Board.selectedPieceOriginalXP == 2) && (Board.selectedPieceOriginalYP - yp == 1 || yp - Board.selectedPieceOriginalYP == 1)) {
            return true;
        } else {
            //black side
            if (yp - Board.selectedPieceOriginalYP == 2 && (Board.selectedPieceOriginalXP - xp == 1 || xp - Board.selectedPieceOriginalXP == 1)) {
                return true;
            }
            if ((xp - Board.selectedPieceOriginalXP  == 2 ||  Board.selectedPieceOriginalXP - xp == 2) && (xp - Board.selectedPieceOriginalYP  == 1 ||  Board.selectedPieceOriginalYP - yp == 1)) {
                return true;
            }
            return false;
        }
    }

}
