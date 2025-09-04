package com.example.utils;

/**
 * @author gaohong
 * @title SeatMap
 * @date 2024/4/5 22:09
 * @description TODO
 */
import java.util.Scanner;

public class SeatMap {
    private char[][] seats;
    private int numRows;

    public SeatMap(int numRows) {
        this.numRows = numRows;
        seats = new char[numRows][15]; // 每一行共 15 列
        initializeSeats();
    }

    // 初始化座位，第一行的第一个位置为 "X"，其余位置为 "_"
    private void initializeSeats() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (i == 0 && j == 0) { // 第一行的第一个位置为 "X"
                    seats[i][j] = 'X';
                } else {
                    seats[i][j] = '_';
                }
            }
        }
    }

    // 打印座位图
    public void printSeatMap() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (j == 5 || j == 10) { // 左中右中间用空格隔开
                    System.out.print(" ");
                }
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SeatMap seatMap = new SeatMap(5); // 创建一个座位图，5行

        System.out.println("Initial seating layout:");
        seatMap.printSeatMap();

        System.out.println("Use 'w' (up), 'a' (left), 's' (down), 'd' (right) to move 'X'. Enter 'q' to quit.");
        while (true) {
            System.out.print("Enter movement (wasd): ");
            char direction = scanner.next().charAt(0);
            if (direction == 'q') {
                System.out.println("Exiting program.");
                break;
            }
            seatMap.moveSeat(direction);
            seatMap.printSeatMap();
        }
    }

    // 移动座位
    public void moveSeat(char direction) {
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
        switch (direction) {
            case 'w': // 向上移动
                if (row > 0) {
                    seats[row][col] = '_';
                    seats[row - 1][col] = 'X';
                }
                break;
            case 's': // 向下移动
                if (row < numRows - 1) {
                    seats[row][col] = '_';
                    seats[row + 1][col] = 'X';
                }
                break;
            case 'a': // 向左移动
                if (col > 0) {
                    seats[row][col] = '_';
                    seats[row][col - 1] = 'X';
                }
                break;
            case 'd': // 向右移动
                if (col < seats[row].length - 1) {
                    seats[row][col] = '_';
                    seats[row][col + 1] = 'X';
                }
                break;
            default:
                System.out.println("Invalid direction!");
        }
    }
}
