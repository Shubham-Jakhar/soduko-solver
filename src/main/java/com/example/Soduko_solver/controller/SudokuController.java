package com.example.Soduko_solver.controller;

import com.example.Soduko_solver.model.SudokuRequest;
import com.example.Soduko_solver.model.SudokuResponse;
import com.example.Soduko_solver.service.SolverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SudokuController {

    private final SolverService solverService;

    public SudokuController(SolverService solverService) {
        this.solverService = solverService;
    }

    @PostMapping("/solve")
    public ResponseEntity<SudokuResponse> solve(@RequestBody SudokuRequest request) {
        int[][] board = request.getBoard();
        if (board == null || board.length != 9) {
            return ResponseEntity.badRequest().body(new SudokuResponse(null, "Invalid board shape"));
        }

        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(board[i], 0, copy[i], 0, 9);

        boolean solved = solverService.solve(copy);
        if (!solved) return ResponseEntity.ok(new SudokuResponse(null, "No solution found or invalid puzzle"));

        return ResponseEntity.ok(new SudokuResponse(copy, "Solved"));
    }
}
