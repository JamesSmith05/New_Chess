package com.company.pieces;

import com.company.board.Board;

import java.util.LinkedList;

public class pawn extends piece{

    public pawn(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        super(xp, yp, isWhite, n, ps, fileLocation);
    }

    //need to be able to only move diagonally when an enemy is there

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill){
        if(xp < 8 && xp>=0 && yp<8 && yp>=0){
            if(isWhite){
                if(willKill){ //if a piece is diagonal from the pawn it will enable the willkill function which allows the pawn to take
                    if(Board.selectedPieceOriginalYP-yp == 1 && (Board.selectedPieceOriginalXP-xp == 1 || xp-Board.selectedPieceOriginalXP == 1)){
                        return true;
                    }
                }
                else{
                    if(firstMove){ //pawn can move 2 times if the pawn hasn't been moved
                        if(Board.selectedPieceOriginalYP-yp == 2 && Board.selectedPieceOriginalXP == xp){
                            if(Board.getPieceC(Board.selectedPieceOriginalXP,Board.selectedPieceOriginalYP-1) !=null){
                                return false;
                            }
                            return true;
                        }
                    }
                    if(Board.selectedPieceOriginalYP-yp == 1 && Board.selectedPieceOriginalXP == xp){
                        return true;
                    }
                }
            }
            else{
                if(willKill){ //exact same process but for the downwards direction
                    if(yp-Board.selectedPieceOriginalYP == 1 && (Board.selectedPieceOriginalXP-xp == 1 || xp-Board.selectedPieceOriginalXP == 1)){
                        return true;
                    }
                }
                else{
                    if(firstMove){
                        if(yp-Board.selectedPieceOriginalYP == 2 && Board.selectedPieceOriginalXP == xp){
                            if(Board.getPieceC(Board.selectedPieceOriginalXP,Board.selectedPieceOriginalYP+1) !=null){
                                return false;
                            }
                            return true;
                        }
                    }
                    if(yp-Board.selectedPieceOriginalYP == 1 && Board.selectedPieceOriginalXP == xp){
                        return true;
                    }
                }
            }

        } return false;
    }
}

