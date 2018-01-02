package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardBuilder {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    public FlashCardBuilder(){
//        the User interface
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Create a Jpanel to hold everything
        JPanel mainPanel = new JPanel();

//        Create Font and format text
        Font greatFont = new Font("Helvetica Neue", Font.BOLD, 41);



//        Question area
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(greatFont);
        //        JscrollPane
        JScrollPane qJScrollPane = new JScrollPane(question);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

//        Answer area
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(greatFont);

        //        JscrollPane
        JScrollPane aJScrollPane = new JScrollPane(answer);
        aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

//        new card list ready to be used

        cardList = new ArrayList<FlashCard>();




//        JButton
        JButton nextButton = new JButton ("Next Card");
        nextButton.setFont(new Font("Serif", Font.PLAIN, 40));

//        labels
        JLabel qJLabel = new JLabel("Question");
        qJLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        JLabel aJLabel = new JLabel("Answer");
        aJLabel.setFont(new Font("Serif", Font.PLAIN, 40));

//        menuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.setFont(new Font("Serif", Font.BOLD, 36));
        newMenuItem.setFont(new Font("Serif", Font.BOLD, 36));
        saveMenuItem.setFont(new Font("Serif", Font.BOLD, 36));

// add items of file menu to file menu
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
//added file menu to menu bar
        menuBar.add(fileMenu);


//        eventListeners to file menu
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener( new SaveMenuItemListener());


//        add components to mainpanel
        mainPanel.add(qJLabel);
        mainPanel.add(qJScrollPane);
        mainPanel.add(aJLabel);
        mainPanel.add(aJScrollPane);
        mainPanel.add(nextButton);

        nextButton.addActionListener(new NextCardListener());

//        add mainPanel to the frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(950, 1000);
        frame.setVisible(true);






    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });

    }

    class NextCardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();

//            System.out.println("Size of ArrayList " + cardList.size());


        }


    }

    class NewMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
//            create a card and save into cardList Array and then clear card




        }
    }

    class SaveMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

//            create a file with file chooser

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());

        }
    }



    //method to reset form and puts cursor in the question box
    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private void saveFile(File selectedFile) {

        try{
//            chaining items together. creates filewriter that passes selectedfile and then the bufferedwritter is created.
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

//            loops through cardList Array instead of using i=0 i>array.length... hasNext evaluates true or false
            Iterator<FlashCard> cardIterator = cardList.iterator();
            while (cardIterator.hasNext()){
                FlashCard card = (FlashCard)cardIterator.next();
//                the Format that it writes to a file to be like this: How are you today/Good\n
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
//            because of memory always close the writer
            writer.close();
//            other way of doing it is
//            for (FlashCard : cardList){
//                writer.write(card.getQuestion() + "/");
//                writer.write(card.getAnswer() + "\n");
//            }

        }catch (Exception e){

            System.out.println("there is an Exception " );
            e.printStackTrace();


        }
    }
}
