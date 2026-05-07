package com.example;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TicTacToe {
    public static final int LISTENING_PORT = 9876;

    public String[][] board = new String[3][3];
    // true = X, and False = O
    public boolean currentPlayer = true;

    public boolean makeMove(Boolean player, int row, int col) {
        if (board[row][col] == null && board[row][col] != "X" && board[row][col] != "O") {
            if (player == true) {
                board[row][col] = "X";
            } else {
                board[row][col] = "O";
            }
           
            return !currentPlayer;
        }

        return currentPlayer;
    }

    public boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != null && board[0][j].equals(board[1][j]) && board[0][j].equals(board[2][j])) {
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return true;
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ServerSocket listener; // Listens for incoming connections.
        Socket connection;
        int count =0;
        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            while (true) {
                connection = listener.accept();
                ConnectionHandler h = new ConnectionHandler(connection, count);
                count++;
                h.start();
                // Accept next connection request and handle it.
            }
        } catch (Exception e) {
            System.out.println("Sorry, the server has shut down.");
            System.out.println("Error:  " + e);
            return;
        }
    }

    private static class ConnectionHandler extends Thread {
        private static ArrayList<ConnectionHandler> handlers;
        int count;
        Socket client;
        String clientAddress;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        ConnectionHandler(Socket socket, int count) {
            client = socket;
            this.count = count;
            if (handlers == null) {
                handlers = new ArrayList();
            }
            handlers.add(this);
        }

        public void run() {

            try {
                ois = new ObjectInputStream(client.getInputStream());
                oos = new ObjectOutputStream(client.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            while (true) {
                try {
                    String message = (String) ois.readObject();
                    if (message.equals("disconnect")) {
                        System.out.println(message + " Closing Connection");
                        break;
                    } else {
                        // loop through all the handlers and tell their output streams the message
                        for (int i = 0; i < handlers.size(); i++) {
                            handlers.get(i).oos.writeObject(count+message);
                        }
                        System.out.println(message);
                    }
                    // your code to send messages goes here.
                } catch (EOFException e) {
                    System.out.println("the client disconnected, bye!!!");
                    handlers.remove(this);
                    break;
                } catch (Exception e) {
                    System.out.println("Error on connection with: " + clientAddress + ": " + e);
                }
            }
        }
    }
}
