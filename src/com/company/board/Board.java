package com.company.board;
import com.company.GUI;
import com.company.common.location;
import com.company.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Board extends JPanel {
    public static LinkedList<piece> ps = new LinkedList<>();
    public static piece selectedPiece=null;
    private static LinkedList<location> position = new LinkedList<>();
    public static int selectedPieceOriginalXP;
    public static int selectedPieceOriginalYP;
    public static boolean whiteTurn = true;
    public static boolean pieceMoved = false;
    public static boolean checkW = false;
    public static boolean checkB = false;
    public static boolean checkMateW = false;
    public static boolean checkMateB = false;


    static int timerW,timerB;

    public static boolean successfulLogin = false;

    public static void main(String[] args) {

//        GUI loginMenu = new GUI();
//        loginMenu.GUI();

//        while(!successfulLogin){

//            System.out.println(" "); // not sure why this works, but you absolutely must keep this loop in
//        }

        Board();
    }

    public static void Board(){

        timerW = 600;
        timerB = 600;
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBounds(-800, 500, 532, 582); //creates window and sets the boundaries

        for(int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
            for (int y = 0; y < 8; y++) {//to set square to black of white depending on the boolean
                //Sets the background colours of the board

                boolean piecePlaced = checkStartLocations(x, y);
                System.out.println("drawing originals");
            }
        }

        JPanel pn = new JPanel() {


            public void paint(Graphics g) {

            boolean white = true;
            for (int x = 0; x < 8; x++) { //loops through both x and y-axis and uses boolean
                for (int y = 0; y < 8; y++) {//to set square to black of white depending on the boolean
                    //Sets the background colours of the board
                    if (white) {
                        g.setColor(new Color(232, 235, 239));
                    } else {
                        g.setColor(new Color(125, 135, 150));
                    }
                    g.fillRect(x * 64, y * 64, 64, 64);
                    white = !white;

                }
                white = !white;
            }

                for (int i = 0; i < ps.size(); i++) {
                    g.drawImage(ps.get(i).image, (ps.get(i).column) * 64, ps.get(i).row * 64, null);
                }
                g.setColor(new Color(161, 161, 161));
                g.fillRect(512,0,20,512);
                g.fillRect(0,512,532,70);

                g.setFont(new Font("Arial", Font.PLAIN, 20)); //sets font for timer
                g.setColor(Color.BLACK);
                String secondValue;
                if ((timerW % 60) < 10) {
                    secondValue = "0" + (timerW % 60);
                } else {
                    secondValue = "" + (timerW % 60);
                }
                g.drawString(("White" + (timerW / 60) + ":" + secondValue), 70, 560);  //draws the exact time and uses constructor to display minutes and seconds
                if ((timerB % 60) < 10) {
                    secondValue = "0" + (timerB % 60);
                } else {
                    secondValue = "" + (timerB % 60);
                }
                g.drawString(("Black" + (timerB / 60) + ":" + secondValue), 340, 560);

                for (int i = 0; i < 8; i++) {
                    g.drawString(String.valueOf(i),517,(i*64)+38);
                }
                for (int i = 0; i < 8; i++) {
                    g.drawString(String.valueOf(i),(i*64)+28,537);
                }
                g.setFont(new Font("Arial", Font.BOLD, 70)); //sets font for timer
                if (checkMateW) {
                    g.drawString("Black Wins", 100, 256);
                }
                if (checkMateB) {
                    g.drawString("White Wins", 100, 256);
                }
        }};


        frame.add(pn);
        frame.setDefaultCloseOperation(3); //enables you to close the tab
        frame.setVisible(true);

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {if (!checkMateW && !checkMateB){
                //System.out.println((getPiece(e.getX(),e.getY()).isWhite?"White":"Black")+getPiece(e.getX(),e.getY()).name);
                selectedPiece=getPiece(e.getX(),e.getY());
                selectedPieceOriginalXP = e.getX()/64;
                selectedPieceOriginalYP = e.getY()/64;
                //System.out.println(e.getX() + " " + e.getY());
            }}
            @Override
            public void mouseReleased(MouseEvent e) {if (!checkMateW && !checkMateB){
                if(selectedPiece != null){
                    if (selectedPiece.isWhite == whiteTurn){ //checks if selected piece is white
                        selectedPiece.move(e.getX()/64,e.getY()/64,selectedPiece.isWhite);
                        if(pieceMoved){
                            whiteTurn = !whiteTurn;  //if the piece moved is white "whites turn" will be printed allowing all white pieces to move
                            if(whiteTurn){
                                //System.out.println("It is white turn");
                            } else{
                                //System.out.println("It is black turn");  //because each piece has the iswhite boolean if its true all white pieces can move and if iswhite is false black can move.
                            }
                        }
                        if(Board.checkCheck()){
                            System.out.println("check");
                        }
                        if(Board.checkmateCheck()){
                            System.out.println("checkmate");
                        }
                    }
                    frame.repaint();
                    selectedPiece = null;
                    pieceMoved = false;
                }
            }}

            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        double drawInterval = 1000000000 / 60; //0.01666 seconds

        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (true) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);  //game loop which keeps track of time
            lastTime = currentTime;
            frame.repaint();
            if (timer >= 1000000000) {
                if (!checkMateW && !checkMateB){
                    if (whiteTurn) {
                        timerW -= 1;
                    } else {
                        timerB -= 1;
                    }
                    //System.out.println("FPS:" + drawCount);
                }
                timer = 0;
            }
            if (timerW == 0) {
                timerW = 0;
                break;
            }
            if (timerB == 0) {
                timerB = 0;
                break;

                //need to implement a play again button
            }
        }
    }

    public static boolean checkCheckB(){
        boolean returnValue = false;
        checkB = false;
        int savedOriginalX = selectedPieceOriginalXP, savedOriginalY = selectedPieceOriginalYP;
        piece blackKing = null,tempPiece;
        for (int i = 0; i < ps.size(); i++) {
            tempPiece = ps.get(i);
            if(Objects.equals(tempPiece.name, "king")) {
                if (!tempPiece.isWhite) {
                    blackKing = tempPiece;
                }
            }
        }
        for (int i = 0; i < ps.size(); i++) {
            tempPiece = ps.get(i);
            selectedPieceOriginalXP = tempPiece.x/64;
            selectedPieceOriginalYP = tempPiece.y/64;
            if(tempPiece.isWhite){
                assert blackKing != null;
                if(tempPiece.validMove(blackKing.x/64,blackKing.y/64, true, true)){
                    System.out.println("this be the square of check for the king "+ blackKing.x/64 + " " + blackKing.y/64 + " " + tempPiece.name);
                    checkB = true;
                    returnValue = true;
                }
            }
        }

        selectedPieceOriginalXP = savedOriginalX;
        selectedPieceOriginalYP = savedOriginalY;

        return returnValue;
    }

    public static boolean checkCheckW(){
        boolean returnValue = false;
        checkW = false;
        int savedOriginalX = selectedPieceOriginalXP, savedOriginalY = selectedPieceOriginalYP;
        piece whiteKing = null,tempPiece;
        for (int i = 0; i < ps.size(); i++) {
            tempPiece = ps.get(i);
            if(Objects.equals(tempPiece.name, "king")) {
                if (tempPiece.isWhite) {
                    whiteKing = tempPiece;
                }
            }
        }
        for (int i = 0; i < ps.size(); i++) {
            tempPiece = ps.get(i);
            selectedPieceOriginalXP = tempPiece.x/64;
            selectedPieceOriginalYP = tempPiece.y/64;
            if(!tempPiece.isWhite){
                assert whiteKing != null;
                if(tempPiece.validMove(whiteKing.x/64,whiteKing.y/64, false, true)){
                    System.out.println(tempPiece.name + " takes white king");
                    checkW = true;
                    returnValue = true;
                }
            }
        }

        selectedPieceOriginalXP = savedOriginalX;
        selectedPieceOriginalYP = savedOriginalY;

        return returnValue;
    }

    public static boolean checkCheck(){
        boolean returnValue;
        returnValue = checkCheckB();
        if (!returnValue){
            returnValue = checkCheckW();
        }
        return returnValue;
    }

    public static boolean isPossibleMovesToPreventCheckMateW(){
        if(checkW){
            for (int i = 0; i < ps.size(); i++) {
                if (!Objects.equals(ps.get(i).name, "king")){
                    if(ps.get(i).isWhite){
                        for (int j = 0; j < 8; j++) {
                            for (int k = 0; k < 8; k++) {
                                if(ps.get(i).moveForCheckCheck(j,k,true)){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean isPossibleMovesToPreventCheckMateB(){
        if(checkB){
            for (int i = 0; i < ps.size(); i++) {
                if (!Objects.equals(ps.get(i).name, "king")){
                    if(!ps.get(i).isWhite){
                        for (int j = 0; j < 8; j++) {
                            for (int k = 0; k < 8; k++) {
                                if(ps.get(i).moveForCheckCheck(j,k,false)){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static int numberOfCheckW = 0, numberOfMovesW = 0;
    public static int numberOfMovesB = 0, numberOfCheckB = 0;

    public static boolean checkmateCheck(){
        boolean returnValue = false;
        int savedOriginalX = selectedPieceOriginalXP, savedOriginalY = selectedPieceOriginalYP;
        piece whiteKing = null,blackKing = null,tempPiece;
        int whiteKingNumber=10000, blackKingNumber=10000;
        int whiteKingX=10000,whiteKingY=10000,blackKingX=10000,blackKingY=10000;
        for (int i = 0; i < ps.size(); i++) {
            tempPiece = ps.get(i);
            if(Objects.equals(tempPiece.name, "king")) {
                if (tempPiece.isWhite) {
                    whiteKing = tempPiece;
                    whiteKingNumber = i;
                    whiteKingX = tempPiece.column;
                    whiteKingY = tempPiece.row;
                } else {
                    blackKing = tempPiece;
                    blackKingNumber = i;
                    blackKingX = tempPiece.column;
                    blackKingY = tempPiece.row;
                }
            }
        }

        ArrayList<location> possibleMovesW = new ArrayList<>();
        ArrayList<location> movesWhereCheckW = new ArrayList<>();
        ArrayList<location> possibleMovesB = new ArrayList<>();
        ArrayList<location> movesWhereCheckB = new ArrayList<>();

        numberOfCheckW = 0; numberOfMovesW = 0;
        numberOfMovesB = 0; numberOfCheckB = 0;

        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                int xp = blackKing.x/64 + j , yp = blackKing.y/64 + k;
                if ((xp) < 8 && xp >= 0 && yp < 8 && yp >= 0 && !(j==0 && k==0)) {
                    if(getPieceC(xp,yp) != null){
                        if (Objects.requireNonNull(getPieceC(xp, yp)).isWhite){
                            numberOfMovesB +=1;
                            possibleMovesB.add(new location(xp,yp));
                            ps.get(blackKingNumber).setLocation(xp,yp);
                            if(checkCheckB()){
                                numberOfCheckB +=1;
                            }
                            ps.get(blackKingNumber).setLocation(blackKingX,blackKingY);
                        }
                    }
                    if(getPieceC(xp,yp) == null){
                        numberOfMovesB +=1;
                        possibleMovesB.add(new location(xp,yp));
                        ps.get(blackKingNumber).setLocation(xp,yp);
                        if(checkCheckB()){
                            numberOfCheckB +=1;
                        }
                        ps.get(blackKingNumber).setLocation(blackKingX,blackKingY);
                    }
                }
            }
        }

        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1 ; k++) {
                int xp = whiteKing.x/64 + j , yp = whiteKing.y/64 + k;
                if ((xp) < 8 && xp >= 0 && yp < 8 && yp >= 0 && !(j==0 && k==0)) {
                    if(getPieceC(xp,yp) != null){
                        if (!Objects.requireNonNull(getPieceC(xp, yp)).isWhite){
                            numberOfMovesW +=1;
                            possibleMovesW.add(new location(xp,yp));
                            ps.get(whiteKingNumber).setLocation(xp,yp);
                            if(checkCheckW()){
                                numberOfCheckW +=1;
                            }
                            ps.get(whiteKingNumber).setLocation(whiteKingX,whiteKingY);
                        }
                    }
                    if(getPieceC(xp,yp) == null){
                        numberOfMovesW +=1;
                        possibleMovesW.add(new location(xp,yp));
                        ps.get(whiteKingNumber).setLocation(xp,yp);
                        if(checkCheckW()){
                            numberOfCheckW +=1;
                        }
                        ps.get(whiteKingNumber).setLocation(whiteKingX,whiteKingY);
                    }
                }
            }
        }

        selectedPieceOriginalXP = savedOriginalX;
        selectedPieceOriginalYP = savedOriginalY;

        System.out.println("number of B moves = " + numberOfMovesB);
        System.out.println("number of B check = " + numberOfCheckB);
        System.out.println("number of W moves = " + numberOfMovesW);
        System.out.println("number of W check = " + numberOfCheckW);

        if (numberOfCheckB>0 && numberOfCheckB == numberOfMovesB && !isPossibleMovesToPreventCheckMateB()){
            System.out.println("black in checkmate");
            checkMateB = true;
        }
        if (numberOfCheckW>0 && numberOfCheckW == numberOfMovesW && !isPossibleMovesToPreventCheckMateW()){
            System.out.println("white in checkmate");
            checkMateW = true;
        }


        return returnValue;
    }

    public static piece getPiece (int testingX, int testingY){
        int xp=testingX/64;
        int yp=testingY/64;
        for (piece p: ps){
            if (p.column==xp&&p.row==yp){
                return p;
            }
        }
        return null;
    }

    public static piece getPieceC (int testingX, int testingY){
        for (piece p: ps){
            if (p.column== testingX &&p.row== testingY){
                return p;
            }
        }
        return null;
    }



    private static boolean checkStartLocations(int x, int y) {
        //file.values()[x] this bit sets the A,B,C.... bit of the chess piece using the file enums
        location tempLocation = new location(x, y);
        boolean piecePlaced = false;

        //Checks if piece is in the right starting location, if not, will not make a new piece
        //if (x==1 y ==1){ DO a series of if statements for each of your chess pieces, if they are in the right location, set a piece and add it to list



        if (x == 0 && y == 7) { //adds all pieces on board to linked list
            piece wRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
            tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
            tempLocation.setPieces(wRook);
            piecePlaced = true;
        }
        if (x == 7 && y == 7) {
            piece wRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), true, "rook", ps, "/com/company/res/white/rook.png");
            tempLocation.setPicture(new JLabel(wRook.getFileLocation())); //CHECK
            tempLocation.setPieces(wRook);
            piecePlaced = true;
        }
        if (x == 1 && y == 7) {
            piece wKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
            tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(wKnight);
            piecePlaced = true;
        }
        if (x == 6 && y == 7) {
            piece wKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), true, "knight", ps, "/com/company/res/white/knight.png");
            tempLocation.setPicture(new JLabel(wKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(wKnight);
            piecePlaced = true;
        }
        if (x == 2 && y == 7) {
            piece wBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
            tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(wBishop);
            piecePlaced = true;
        }
        if (x == 5 && y == 7) {
            piece wBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), true, "bishop", ps, "/com/company/res/white/bishop.png");
            tempLocation.setPicture(new JLabel(wBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(wBishop);
            piecePlaced = true;
        }
        if (x == 4 && y == 7) {
            piece wKing = new king(tempLocation.getColumn(), tempLocation.getRow(), true, "king", ps, "/com/company/res/white/king.png");
            tempLocation.setPieces(wKing);
            piecePlaced = true;
        }
        if (x == 3 && y == 7) {
            piece wQueen = new queen(tempLocation.getColumn(), tempLocation.getRow(), true, "queen", ps, "/com/company/res/white/queen.png");
            tempLocation.setPieces(wQueen);
            piecePlaced = true;
        }
        if ( y == 6) {
            piece wPawn = new pawn(tempLocation.getColumn(), tempLocation.getRow(), true, "pawn", ps, "/com/company/res/white/pawn.png");
            tempLocation.setPieces(wPawn);
            piecePlaced = true;
        }




        if (x == 0 && y == 0)  {
            piece bRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
            tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
            tempLocation.setPieces(bRook);
            piecePlaced = true;
        }
        if (x == 7 && y == 0) {
            piece bRook = new rook(tempLocation.getColumn(), tempLocation.getRow(), false, "rook", ps, "/com/company/res/black/rook.png");
            tempLocation.setPicture(new JLabel(bRook.getFileLocation())); //CHECK
            tempLocation.setPieces(bRook);
            piecePlaced = true;
        }
        if (x == 1 && y == 0) {
            piece bKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
            tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(bKnight);
            piecePlaced = true;
        }
        if (x == 6 && y == 0) {
            piece bKnight = new knight(tempLocation.getColumn(), tempLocation.getRow(), false, "knight", ps, "/com/company/res/black/knight.png");
            tempLocation.setPicture(new JLabel(bKnight.getFileLocation())); //CHECK
            tempLocation.setPieces(bKnight);
            piecePlaced = true;
        }
        if (x == 2 && y == 0) {
            piece bBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
            tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(bBishop);
            piecePlaced = true;
        }
        if (x == 5 && y == 0) {
            piece bBishop = new bishop(tempLocation.getColumn(), tempLocation.getRow(), false, "bishop", ps, "/com/company/res/black/bishop.png");
            tempLocation.setPicture(new JLabel(bBishop.getFileLocation())); //CHECK
            tempLocation.setPieces(bBishop);
            piecePlaced = true;

        }
        if (x == 4 && y == 0) {
            piece bKing = new king(tempLocation.getColumn(), tempLocation.getRow(), false, "king", ps, "/com/company/res/black/king.png");
            tempLocation.setPicture(new JLabel(bKing.getFileLocation())); //CHECK
            tempLocation.setPieces(bKing);
            piecePlaced = true;
        }
        if (x == 3 && y == 0) {
            piece bQueen = new queen(tempLocation.getColumn(), tempLocation.getRow(), false, "queen", ps, "/com/company/res/black/queen.png");
            tempLocation.setPicture(new JLabel(bQueen.getFileLocation())); //CHECK
            tempLocation.setPieces(bQueen);
            piecePlaced = true;
        }
        if (y == 1) {
            piece bPawn = new pawn(tempLocation.getColumn(), tempLocation.getRow(), false, "pawn", ps, "/com/company/res/black/pawn.png");
            tempLocation.setPicture(new JLabel(bPawn.getFileLocation())); //CHECK
            tempLocation.setPieces(bPawn);
            piecePlaced = true;
        }




        position.add(tempLocation);
        return piecePlaced; //Returns true if we added a piece to the location square
    }


}
