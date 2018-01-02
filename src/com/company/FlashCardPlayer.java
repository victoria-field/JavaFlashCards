package com.company;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.*;

public class FlashCardPlayer {

    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator cardIterator;
    private FlashCard currentCard;
    private int currentCardIndex;
    private JButton showAnwerButton;
    private JFrame frame;
    private boolean isShowAnswer;




    public FlashCardPlayer(){

//        build UI
        frame = new JFrame("Flash Card Player");
        JPanel mainPanel = new JPanel();
        Font mFont = new Font("Helvetica Neue", Font.BOLD, 41);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        display = new JTextArea(10, 20);
        display.setFont(mFont);

        JScrollPane qJScrollPane = new JScrollPane(display);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        showAnwerButton = new JButton("Show Answer");
        showAnwerButton.setFont(new Font("Serif", Font.PLAIN, 40));

        mainPanel.add(qJScrollPane);
        mainPanel.add(showAnwerButton);
        showAnwerButton.addActionListener(new NextCardListener());

//        add menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openCardItem = new JMenuItem("Load Card Set");
        openCardItem.addActionListener(new OpenMenuListener());
        fileMenu.add(openCardItem);
        menuBar.add(fileMenu);
        fileMenu.setFont(new Font("Serif", Font.BOLD, 36));
        openCardItem.setFont(new Font("Serif", Font.BOLD, 36));


//        add to Frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(800, 800);
        frame.setVisible(true);

    }



    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardPlayer();
            }
        });
    }

    class NextCardListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            if (isShowAnswer) {
                display.setText(currentCard.getAnswer());
                showAnwerButton.setText("NextCard");
                isShowAnswer = false;
            } else{
                if(cardIterator.hasNext()) {

                    showNextCard();

                }else{
                    display.setText("That was the last card.");
                    showAnwerButton.setEnabled(false);
                }
            }


        }
    }

    class OpenMenuListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());

        }
    }

    private void loadFile(File selectedFile) {

        cardList = new ArrayList<FlashCard>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;

//            reader gets everything from the file and puts it on the line which will the the loop keep going


            while((line = reader.readLine()) != null){
                makeCard(line);
            }

        }catch (Exception e){

            System.out.println("there is an Exception " );
            e.printStackTrace();


        }
//        show the first card
        cardIterator = cardList.iterator();
        showNextCard();
    }


    private void makeCard(String lineToParse) {

//        more modern
        StringTokenizer result = new StringTokenizer(lineToParse, "/");
        if(result.hasMoreTokens()){

            FlashCard card = new FlashCard(result.nextToken(), result.nextToken());
            cardList.add(card);
            System.out.println("Made a card");

        }

//        //[question, answer] question is index 0 and answer is index 1
//        String[] result = lineToParse.split("/");
//        FlashCard card = new FlashCard(result[0], result[1]);
//        cardList.add(card);
//        System.out.println("Made a card");
    }

    private void showNextCard() {
        currentCard = (FlashCard) cardIterator.next();

        display.setText(currentCard.getQuestion());
        showAnwerButton.setText("Show Answer");
        isShowAnswer = true;

    }

}
