package com.example.tictac;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
    private static final int BOARD_SIZE = 3;
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";
    private static final String EMPTY_CELL = "";

    private String currentPlayer;
    private Button[][] cells;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentPlayer = PLAYER_X;
        cells = new Button[BOARD_SIZE][BOARD_SIZE];

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add Tic Tac Toe text at the top
        Text ticTacToeText = new Text("TIC TAC TOE");
        ticTacToeText.setFont(Font.font("Comic Sans MS", 24));
        grid.add(ticTacToeText, 0, 0, 3, 1);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = createCell();
                grid.add(cell, col, row + 1); // Add 1 to the row to leave space for the Tic Tac Toe text
                cells[row][col] = cell;
            }
        }

        Scene scene = new Scene(grid, 340, 380);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createCell() {
        Button cell = new Button(EMPTY_CELL);
        cell.setMinSize(100, 100);
        cell.setFont(Font.font("ComicSans", 36));
        cell.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        cell.setOnAction(e -> onCellClicked(cell));
        return cell;
    }

    private void onCellClicked(Button cell) {
        if (!cell.getText().isEmpty()) {
            return;
        }

        cell.setText(currentPlayer);
        if (isGameOver()) {
            showGameOverAlert();
            resetBoard();
        } else if(catWins()) {
            resetBoard();
        }
        else {
            currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
        }
    }

    private boolean isGameOver() {
        return isAnyRowComplete() || isAnyColumnComplete() || isAnyDiagonalComplete();
    }

    private boolean isAnyRowComplete() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (cells[row][0].getText().equals(cells[row][1].getText()) &&
                    cells[row][0].getText().equals(cells[row][2].getText()) &&
                    !cells[row][0].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnyColumnComplete() {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (cells[0][col].getText().equals(cells[1][col].getText()) &&
                    cells[0][col].getText().equals(cells[2][col].getText()) &&
                    !cells[0][col].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnyDiagonalComplete() {
        return (cells[0][0].getText().equals(cells[1][1].getText()) &&
                cells[0][0].getText().equals(cells[2][2].getText()) &&
                !cells[0][0].getText().isEmpty()) ||
                (cells[0][2].getText().equals(cells[1][1].getText()) &&
                        cells[0][2].getText().equals(cells[2][0].getText()) &&
                        !cells[0][2].getText().isEmpty());
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean catWins(){
        if(isBoardFull()){
            showGameOverAlertNumber2();
        return true;
        }
        return false;
    }

    private void showGameOverAlertNumber2(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game Over! The Cat wins!");
        alert.showAndWait();
    }

    private void showGameOverAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game Over! Player " + currentPlayer + " wins!");
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cells[row][col].setText(EMPTY_CELL);
            }
        }
        currentPlayer = PLAYER_X;
    }
}

