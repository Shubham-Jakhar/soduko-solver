package com.example.Soduko_solver.service;

import org.springframework.stereotype.Service;

@Service
public class SolverService {

    private static final int SIZE = 9;

    public boolean solve(int[][] board) {
        boolean[][] rows = new boolean[SIZE][SIZE + 1];
        boolean[][] cols = new boolean[SIZE][SIZE + 1];
        boolean[][] boxes = new boolean[SIZE][SIZE + 1];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int val = board[r][c];
                if (val != 0) {
                    if (!isValidPlacement(rows, cols, boxes, r, c, val)) return false;
                    place(rows, cols, boxes, r, c, val, true);
                }
            }
        }
        return backtrack(board, rows, cols, boxes);
    }

    private boolean backtrack(int[][] board, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        int[] next = findNextCell(board, rows, cols, boxes);
        if (next == null) return true;
        int r = next[0], c = next[1];

        for (int num = 1; num <= 9; num++) {
            if (canPlace(rows, cols, boxes, r, c, num)) {
                board[r][c] = num;
                place(rows, cols, boxes, r, c, num, true);
                if (backtrack(board, rows, cols, boxes)) return true;
                place(rows, cols, boxes, r, c, num, false);
                board[r][c] = 0;
            }
        }
        return false;
    }

    private int[] findNextCell(int[][] board, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        int bestR = -1, bestC = -1, bestCount = Integer.MAX_VALUE;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] != 0) continue;
                int count = 0;
                for (int num = 1; num <= 9; num++)
                    if (canPlace(rows, cols, boxes, r, c, num)) count++;
                if (count == 0) return new int[]{r, c};
                if (count < bestCount) {
                    bestCount = count;
                    bestR = r;
                    bestC = c;
                }
            }
        }
        return bestR == -1 ? null : new int[]{bestR, bestC};
    }

    private boolean canPlace(boolean[][] rows, boolean[][] cols, boolean[][] boxes, int r, int c, int num) {
        int b = (r / 3) * 3 + (c / 3);
        return !rows[r][num] && !cols[c][num] && !boxes[b][num];
    }

    private void place(boolean[][] rows, boolean[][] cols, boolean[][] boxes, int r, int c, int num, boolean set) {
        int b = (r / 3) * 3 + (c / 3);
        rows[r][num] = set;
        cols[c][num] = set;
        boxes[b][num] = set;
    }

    private boolean isValidPlacement(boolean[][] rows, boolean[][] cols, boolean[][] boxes, int r, int c, int val) {
        int b = (r / 3) * 3 + (c / 3);
        return !rows[r][val] && !cols[c][val] && !boxes[b][val];
    }
}
