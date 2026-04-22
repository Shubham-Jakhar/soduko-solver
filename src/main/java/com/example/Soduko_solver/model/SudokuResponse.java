package com.example.Soduko_solver.model;

public class SudokuResponse {
    private int[][] board;
    private String message;

    public SudokuResponse() {}

    public SudokuResponse(int[][] board, String message) {
        this.board = board;
        this.message = message;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
