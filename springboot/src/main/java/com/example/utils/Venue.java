package com.example.utils;

public class Venue {

    // define what data fields will be used to define the venue layout
    public int numRows;
    private int leftSeats;
    private int middleSeats;
    private int rightSeats;
    public char[][] seats;


    // define your constructors
    public Venue(int numRows, int leftSeats, int middleSeats, int rightSeats){
        this.numRows = numRows;
        this.leftSeats = leftSeats;
        this.middleSeats = middleSeats;
        this.rightSeats = rightSeats;
        this.seats = new char[numRows][leftSeats + middleSeats + rightSeats];
    }

    public void showSeats3(){
        System.out.println("[____ _____ ____]\n" +
                "[____ _____ ____]\n" +
                "[____ _____ ____]\n" +
                "[____ _____ ____]\n" +
                "[____ _____ ____]");
    }

    // write some methods to show the venue layout
    public void showSeats() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (i == 0 && j == 0) { // 第一行的第一个位置为 "X"
                    seats[i][j] = 'X';
                } else {
                    seats[i][j] = '_';
                }
            }
        }
        this.printSeats(1,1);
    }

    // 打印座位图
    public void printSeats(int row1,int numSeats) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (j == 4 || j ==9) { // 左中右中间用空格隔开
                    System.out.print(" ");
                }
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }
    }

    public void aaa(char input){
        int row = -1;
        int col = -1;

        // 寻找当前 "X" 的位置
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'X') {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break; // 如果找到 "X"，退出循环
        }
        // 根据输入移动 "X"
        switch (input) {
            case 'W': // 向上移动
                if (row > 0) {
                    seats[row][col] = '_';
                    seats[row - 1][col] = 'X';
                }
                break;
            case 'S': // 向下移动
                if (row < numRows - 1) {
                    seats[row][col] = '_';
                    seats[row + 1][col] = 'X';
                }
                break;
            case 'A': // 向左移动
                if (col > 0) {
                    seats[row][col] = '_';
                    seats[row][col - 1] = 'X';
                }
                break;
            case 'D': // 向右移动
                if (col < seats[row].length - 1) {
                    seats[row][col] = '_';
                    seats[row][col + 1] = 'X';
                }
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    // write some methods to select seats and move up/down/left/right
    public int[] currentBookedSeat(){
        int[] position = new int[2];
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < seats[i].length; j++){
                if(seats[i][j] == 'X'){
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

}
