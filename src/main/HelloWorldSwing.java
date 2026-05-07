package com.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HelloWorldSwing extends Thread {

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static BufferedImage spaceBackground;
    private static BufferedImage purplePlanet;
    private static BufferedImage mintPlanet;

    public static void main(String[] args) {
        HelloWorldSwing one = new HelloWorldSwing();
        HelloWorldSwing two = new HelloWorldSwing();
        one.start();
        two.start();
    }

    public void run() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 9876);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            TicTacToe game = new TicTacToe();

            // Create and set up the window.
            JFrame frame = new JFrame("Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout()); // Use GridBagLayout to center the label
            // Add the ubiquitous "Hello World" label.
            try {
                spaceBackground = ImageIO.read(new File("/workspaces/CP2Template/src/images/spaceBackground.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mintPlanet = ImageIO.read(new File("/workspaces/CP2Template/src/images/mintPlanet.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                purplePlanet = ImageIO.read(new File("/workspaces/CP2Template/src/images/purplePlanet.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JPanel spacePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(spaceBackground, 0, 0, this);
                }
            };
            GridLayout gl = new GridLayout(3, 3);
            spacePanel.setLayout(gl);

           

            JPanel homePanel = new JPanel();
            homePanel.setBackground(Color.WHITE);
            JLabel homeLabel = new JLabel("Out of this World Tic-Tac-Toe!");
            homeLabel.setFont(new Font("Arial", Font.BOLD, 25));
            homePanel.add(homeLabel);
            JButton startButton = new JButton("Start Game");

            JPanel winPanel = new JPanel();
            // homePanel.
            startButton.addActionListener(e -> {
                // Code to start the game goes here
                frame.setContentPane(spacePanel);
                frame.repaint();
                frame.setVisible(true);
                System.out.println("Start Game button clicked!");
            });
            homePanel.add(startButton);
            frame.setContentPane(homePanel);

            // Display the window.
            frame.setSize(540, 540);
            frame.repaint();
            frame.setVisible(true);
            System.out.println("before while true");
            while (true) {
                System.out.println("top of while true");
                String message = (String) ois.readObject();
                System.out.println("mes " + message);
                //player 1
                for (int i = 0; i < 9; i++) {
                System.out.println("making button number " + i);
                JButton button = new JButton();
                button.setName(Integer.toString(i) + "button");
                button.setContentAreaFilled(false);
                button.setPreferredSize(new Dimension(150, 150));
                button.addActionListener(new ActionListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // TODO Auto-generated method stub
                        try {

                            if (button.getName().equals("0button")) {
                                
                                button.setEnabled(false);
                                if (message.substring(0,1).equals("0")) {
                                    button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    game.makeMove(true, 0, 0);
                                } else {
                                    button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    game.makeMove(false, 0, 0);
                                }
                            }

                            if (button.getName().equals("1button")) {
                                
                                button.setEnabled(false);
                                if (message.substring(0,1).equals("0")) {
                                    button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    game.makeMove(true, 0, 1);
                                } else {
                                    button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    game.makeMove(false, 0, 1);
                                }
                            }

                            if (button.getName().equals("2button")) {
                                
                                button.setEnabled(false);
                                if (message.substring(0,1).equals("0")) {
                                    button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    game.makeMove(true, 0, 2);
                                } else {
                                    button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    game.makeMove(false, 0, 2);
                                }
                            }

                            if (button.getName().equals("3button")) {
                                
                                button.setEnabled(false);
                                if (message.substring(0,1).equals("0")) {
                                    button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    game.makeMove(true, 1, 0);
                                } else {
                                    button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    game.makeMove(false, 1, 0);
                                }
                            }

                           if (button.getName().equals("4button")) {
                                
                                button.setEnabled(false);
                                if (message.substring(0,1).equals("0")) {
                                    button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    game.makeMove(true, 1, 0);
                                } else {
                                    button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    game.makeMove(false, 0, 0);
                                }
                            }

                            if (button.getName().equals("5button")) {
                                game.makeMove(game.currentPlayer, 1, 2);
                                button.setEnabled(false);
                                    if(game.currentPlayer){
                                        button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    }
                                  else{
                                        button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    } 
                                    oos.writeObject(button.getName());
                            oos.flush();
                            }
                            
                            if (button.getName().equals("6button")) {
                                game.makeMove(game.currentPlayer, 2, 0);
                                button.setEnabled(false);
                                    if(game.currentPlayer){
                                        button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    }
                                  else{
                                        button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    } 
                                    oos.writeObject(button.getName());
                            oos.flush();
                            }
                            
                            if (button.getName().equals("7button")) {
                                game.makeMove(game.currentPlayer, 2, 1);
                                button.setEnabled(false);
                                    if(game.currentPlayer){
                                        button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    }
                                  else{
                                        button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    } 
                            }
                            
                            if (button.getName().equals("8button")) {
                                game.makeMove(game.currentPlayer, 2, 2);
                                button.setEnabled(false);
                                    if(game.currentPlayer){
                                        button.setDisabledIcon(new ImageIcon(mintPlanet));
                                    }
                                  else{
                                        button.setDisabledIcon(new ImageIcon(purplePlanet));
                                    } 
                                    oos.writeObject(button.getName());
                            oos.flush();
                            }

                            frame.repaint();
                            
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            System.out.println("Not working down here");
                            e1.printStackTrace();
                        }

                    }

                });
                spacePanel.add(button);
            }
                    }
                //player2
                // recieve info and write to screen.
            

        } catch (IOException e) {
            System.out.println("Something went wrong with sending stuff " + e);
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}