package com.company.pieces; //Contain functions to move any piece

import com.company.board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

public class piece {
    public Integer column;
    public Integer row;

    public int x,y;

    public boolean isWhite;
    public boolean firstMove = true;
    LinkedList<piece> ps;//Linked list will store a pieces functions
    public String name;
    String fileLocation;
    public BufferedImage image;

    public boolean validMove(int xp, int yp, boolean isWhite, boolean willKill){
        return true;
    }

    public boolean validMoveOutOfCheckB(){
        if (Board.checkB){
            if(Board.checkCheck()){
                return false;
            }
            else {
                return true;
            }
        }
    return true;
    }
    public boolean validMoveOutOfCheckW(){
        if (Board.checkW){
            if(Board.checkCheck()){
                return false;
            }
            else {
                return true;
            }
        }
        return true;
    }

    public boolean validMoveBishop(int xp, int yp, boolean isWhite, boolean willKill) {

        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {

            //loops diagonally by using iterator from for loop
            //checks all directions for any pieces in the way, if there is returns false

            for (int i = 0; i < 8; i++) {
                if ((Board.selectedPieceOriginalYP - yp == i && (Board.selectedPieceOriginalXP - xp == i || xp - Board.selectedPieceOriginalXP == i))
                        ||((yp - Board.selectedPieceOriginalYP == i && (Board.selectedPieceOriginalXP - xp == i || xp - Board.selectedPieceOriginalXP == i)))) {

                    if (xp > Board.selectedPieceOriginalXP)  {
                        if(yp>Board.selectedPieceOriginalYP){
                            for (int j = 1; j < xp-Board.selectedPieceOriginalXP; j++) {
                                if(Board.getPieceC(Board.selectedPieceOriginalXP + j,Board.selectedPieceOriginalYP + j) !=null){
                                    if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP + j, Board.selectedPieceOriginalYP + j).name, "king")){
                                        return false;
                                    }
                                }
                            }
                        }
                        else{
                            for (int j = 1; j < xp-Board.selectedPieceOriginalXP; j++) {
                                if(Board.getPieceC(Board.selectedPieceOriginalXP + j,Board.selectedPieceOriginalYP - j) !=null){
                                    if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP + j, Board.selectedPieceOriginalYP - j).name, "king")){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    if (xp < Board.selectedPieceOriginalXP) {
                        if(yp>Board.selectedPieceOriginalYP){
                            for (int j = 1; j < Board.selectedPieceOriginalXP-xp; j++) {
                                if(Board.getPieceC(Board.selectedPieceOriginalXP - j,Board.selectedPieceOriginalYP + j) !=null){
                                    if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP - j, Board.selectedPieceOriginalYP + j).name, "king")){
                                        return false;
                                    }
                                }
                            }
                        }
                        else{
                            for (int j = 1; j < Board.selectedPieceOriginalXP-xp; j++) {
                                if(Board.getPieceC(Board.selectedPieceOriginalXP - j,Board.selectedPieceOriginalYP - j) !=null){
                                    if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP - j, Board.selectedPieceOriginalYP - j).name, "king")){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validMoveRook(int xp, int yp, boolean isWhite, boolean willKill) {
        if (xp < 8 && xp >= 0 && yp < 8 && yp >= 0) {
            if (Board.selectedPieceOriginalYP == yp || Board.selectedPieceOriginalXP == xp) {

               //implements piece detection so castle can't jump over pieces, loops throw the row and if there is a piece on the row returns false so the rook can't jump pieces
                //checks all directions
                if (xp > Board.selectedPieceOriginalXP) {
                    for (int i = 1; i < xp-Board.selectedPieceOriginalXP; i++) {
                        if(Board.getPieceC(Board.selectedPieceOriginalXP + i,Board.selectedPieceOriginalYP) !=null){
                            if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP + i, Board.selectedPieceOriginalYP).name, "king")){
                                return false;
                            }
                        }
                    }
                }
                if (xp < Board.selectedPieceOriginalXP) {
                    for (int i = 1; i < Board.selectedPieceOriginalXP-xp; i++) {
                        if(Board.getPieceC(Board.selectedPieceOriginalXP - i,Board.selectedPieceOriginalYP) !=null){
                            if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP - i, Board.selectedPieceOriginalYP).name, "king")){
                                return false;
                            }

                        }
                    }
                }
                if (yp > Board.selectedPieceOriginalYP) {
                    for (int i = 1; i < yp-Board.selectedPieceOriginalYP; i++) {
                        if(Board.getPieceC(Board.selectedPieceOriginalXP,Board.selectedPieceOriginalYP + i) !=null){
                            if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP, Board.selectedPieceOriginalYP + i).name, "king")){
                                return false;
                            }

                        }
                    }
                }
                if (yp < Board.selectedPieceOriginalYP) {
                    for (int i = 1; i < Board.selectedPieceOriginalYP-yp; i++) {
                        if(Board.getPieceC(Board.selectedPieceOriginalXP,Board.selectedPieceOriginalYP - i) !=null){
                            if(!Objects.equals(Board.getPieceC(Board.selectedPieceOriginalXP, Board.selectedPieceOriginalYP - i).name, "king")){
                                return false;
                            }

                        }
                    }
                }
                return true;
            }
        }
        return false;
    }



    public piece(int xp, int yp, boolean isWhite, String n, LinkedList<piece> ps, String fileLocation) {
        this.column = xp; //get the x and y values of each piece. x and y-values have a boundary of 0-7 for Y and 0-7 on for X
        this.row = yp;  //defines what a piece is and what it can do eg: move
        this.x=xp*64;
        this.y=yp*64;
        this.isWhite = isWhite;
        this.ps=ps;
        this.fileLocation = fileLocation;
        name = n;
        ps.add(this);
        setImage(fileLocation);
    }
    public void check(){
    }

    public void move(int xp, int yp, boolean isWhite) {
        boolean canMove = true;
        boolean willKill = false;
        if(Board.getPiece(xp*64,yp*64) !=null){

            if(Board.getPiece(xp*64, yp*64).isWhite!=isWhite) {
                willKill = true;
            }
            else canMove = false;
        }
        if (canMove){
//            System.out.println("org "+Board.selectedPieceOriginalXP + " " + Board.selectedPieceOriginalYP);
//            System.out.println("new "+xp + " " + yp);
            canMove = this.validMove(xp,yp,isWhite,willKill);
        }

        if(canMove){
            if(willKill){
            Board.getPiece(xp * 64, yp * 64).kill(); //when a piece is onto of another piece it will call kill to remove the piece from a board
            }
            this.column=xp;
            this.row=yp;
            x=column*64;
            y=row*64;

            if(this.isWhite){
                canMove = this.validMoveOutOfCheckW();
            }else{
                canMove = this.validMoveOutOfCheckB();
            }

            if (!canMove){
                this.column=Board.selectedPieceOriginalXP;
                this.row=Board.selectedPieceOriginalYP;
                x=column*64;
                y=row*64;
            }else {
                this.firstMove = false;
                Board.pieceMoved = true;
            }



        }
    }

    public boolean moveForCheckCheck(int xp, int yp, boolean isWhite){
        boolean canMove = true;
        boolean willKill = false;
        if(Board.getPiece(xp*64,yp*64) !=null){

            if(Board.getPiece(xp*64, yp*64).isWhite!=isWhite) {
                willKill = true;
            }
            else canMove = false;
        }
        if (canMove){
            canMove = this.validMove(xp,yp,isWhite,willKill);
        }

        if(canMove){
            if(willKill){
                Board.getPiece(xp * 64, yp * 64).kill(); //when a piece is onto of another piece it will call kill to remove the piece from a board
            }
            this.column=xp;
            this.row=yp;
            x=column*64;
            y=row*64;

            if(this.isWhite){
                canMove = this.validMoveOutOfCheckW();
            }else{
                canMove = this.validMoveOutOfCheckB();
            }

            this.column=Board.selectedPieceOriginalXP;
            this.row=Board.selectedPieceOriginalYP;
            x=column*64;
            y=row*64;
        }
        return canMove;
    }

    public void setLocation(int xp, int yp){
        this.column=xp;
        this.row=yp;
        x=column*64;
        y=row*64;
    }

    public void setImage(String fileLocation){
        image=setup(fileLocation, 64,64 );
    }
    public void kill() {
        ps.remove(this); //if a piece is killed its removed off the board
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            image = scaleImage(image, width, height);

        } catch (Exception e) {
        }
        return image;
    }

    public BufferedImage scaleImage(BufferedImage original,int width,int height){

        BufferedImage scaledImage = new BufferedImage(width,height,original.getType()); //gets the buffered image and matches the type with the information in board class
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();

        return scaledImage;
    }


}
