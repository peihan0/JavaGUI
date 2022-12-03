//package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument.Content;

public class TextFrame extends JFrame implements ActionListener{
    JTextArea textArea; //show entered text
    JTextField textField; //for user to enter text
    JScrollPane scrollPane;
    JButton clearbtn;
    JButton addbtn;
    JMenuBar menuBar;
    JMenu menuFunc;
    JMenuItem menuClear;


    public TextFrame(String title){
        super(title);
        this.setSize(400, 350);
        this.setResizable(false);
        this.setLocation(800,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //textField
        textField = new JTextField();
        textField.addActionListener(this);
        textField.setBounds(50, 220, 300, 30);
        panel.add(textField);

        //bottons
        addbtn = new JButton("add");
        addbtn.setBounds(100,255, 100,40);
        addbtn.addActionListener(this);
        panel.add(addbtn);

        clearbtn = new JButton("clear");
        clearbtn.setBounds(200,255,100,40);
        clearbtn.addActionListener(this);
        panel.add(clearbtn);

        //textArea
        textArea = new JTextArea();
        //for read purpose only
        textArea.setEditable(false);
        //automatically change line
        //textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setSize(300, 200);
        scrollPane.setLocation(50, 10);
       
        panel.add(scrollPane);

        //Menu
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuFunc = new JMenu("Options");
        menuClear = new JMenuItem("clear");
        menuClear.addActionListener(this);
        menuFunc.add(menuClear);
        menuBar.add(menuFunc);


        this.add(panel);


    }

    public static void main(String[] args){
        TextFrame demo = new TextFrame("A TxtFrame");
        demo.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == addbtn){
            System.out.println("user intput:" + textField.getText());
            textArea.setText(textArea.getText()+textField.getText()+"\n");
            textField.setText("");

        }else if(e.getSource() == clearbtn){
            System.out.println("Clear the text area");
            textArea.setText("");
            
        }else if(e.getSource() == menuClear){
            textArea.setText("");

        }else if(e.getSource() == textField){
            System.out.println("user intput:" + textField.getText());
            textArea.setText(textArea.getText()+textField.getText()+"\n");
            textField.setText("");
        }
        
        
    }
}

