import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;

class TicTacToe extends JFrame {

    static Cell[] cells = new Cell[9];
    static Boolean Mode = true;
    JPanel btnPanel;
    static Handler handler;
    menuHandler menuh;
    JMenuBar menuBar;
    JMenu menuFunc;
    JMenuItem menuClear;
    JMenuItem menuMode;


    public TicTacToe() {
        super("Tic-Tac-Toe");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        btnPanel = new JPanel(new GridLayout(3, 3));
        handler = new Handler();

        // add cells to panel
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(i);
            btnPanel.add(cells[i]);
            cells[i].addActionListener(handler);
            cells[i].setFocusable(false);
        }

        //menuBar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuFunc = new JMenu("Options");
        menuClear = new JMenuItem("Reset Game");
        menuMode = new JMenuItem("Change Symbol");
        menuClear.addActionListener(new ActionListener(){
             @Override public void actionPerformed(ActionEvent e) {
                if(e.getSource() == menuClear){
                    TicTacToe.resetBtns();
                }
              } });
        menuMode.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e) {
               if(e.getSource() == menuMode){
                   Mode = !Mode;
                }
             } });
        menuFunc.add(menuClear);
        menuFunc.add(menuMode);
        menuBar.add(menuFunc);
        
        //player changes symbol

        this.add(btnPanel);

    }
    

    public static void main(String[] args) {
        TicTacToe frame = new TicTacToe();
        frame.setVisible(true);
    }

    public static void resetBtns() {
        for (int i = 0; i < 9; i++) {
            cells[i].setText("");
            cells[i].cellState = 0;
        }
    }

    public static void disbleBtns() {
        for (int i = 0; i < 9; i++) {
            cells[i].setEnabled(false);
        }
    }

    public static void enableBtns() {
        for (int i = 0; i < 9; i++) {
            cells[i].setEnabled(true);
        }
    }

    public static Boolean currentMode(){
        return Mode;
    }
    

}

class Cell extends JButton {
    int index;
    // 0 : blank cell, 1 : O cell, 2 : X cell
    int cellState;

    public Cell(int index) {
        super();
        this.index = index;
        // initialize cell state 0(blank cell)
        this.cellState = 0;
    }

    public int getIndex() {
        return index;
    }
}
class menuHandler implements ActionListener{
    JMenuItem item = new JMenuItem();
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == item){
            TicTacToe.resetBtns();
        }
    }
}

class Handler implements ActionListener {
    // true stands for player1(O), while false stands for player2(X)
    private static Boolean player1 = true;
    // 0 : blank cell, 1 : O cell, 2 : X cell
    // to track winning conditions
    private int[] trackCellState;
    // -1 : continue, 0 : tie, 1 : O wins, 2 : X wins
    public int GameState;

    public Handler() {
        trackCellState = new int[9];
        for (int i = 0; i < 9; i++) {
            // initialize cell state 0(blank cell)
            trackCellState[i] = 0;
        }

    }

    public int evaluate() {
        GameState = -1;

        // check winning conditions
        // row
        for (int i = 0; i < 9; i += 3) {
            if ((trackCellState[i] == trackCellState[i + 1] && trackCellState[i] == trackCellState[i + 2])
                    && trackCellState[i] != 0) {
                GameState = trackCellState[i];
            }
        }

        // col
        for (int i = 0; i < 3; i++) {
            if ((trackCellState[i] == trackCellState[i + 3] && trackCellState[i] == trackCellState[i + 6])
                    && trackCellState[i] != 0) {
                GameState = trackCellState[i];
            }
        }

        // diagonal
        if ((trackCellState[0] == trackCellState[4] && trackCellState[0] == trackCellState[8])
                && trackCellState[0] != 0) {
            GameState = trackCellState[0];
        }

        if ((trackCellState[2] == trackCellState[4] && trackCellState[2] == trackCellState[6])
                && trackCellState[2] != 0) {
            GameState = trackCellState[2];
        }

        // if continue
        Boolean flag = false;
        if (GameState != 1 && GameState != 2) {// if having no winner, check to see if continue
            for (int i = 0; i < 9; i++) {
                if (trackCellState[i] == 0) {
                    GameState = -1; // there are still empty spaces, game continue
                    flag = true;
                }
            }
        }
        // it's a tie
        if (flag == false && (GameState != 1 && GameState != 2))
            GameState = 0;

        //if not continue, disable all the buttons
        if(GameState != -1) TicTacToe.disbleBtns();
        return GameState;

    }

    public void actionPerformed(ActionEvent e) {
        //what if the source is from menuBar
        String[] S1 = new String[] {"O","><"};
        String[] S2 = new String[]{"X", "=="};
        Boolean Mode = true;
        Cell clickedCell = (Cell) e.getSource();
        int index = clickedCell.getIndex();
        JOptionPane notice;

        
        if (clickedCell.cellState == 0) {
            Mode = TicTacToe.currentMode();
            if (player1) {
                if(Mode){
                    clickedCell.setText(S1[0]); // present it on swing
                }else{
                    clickedCell.setText(S1[1]);
                }
                clickedCell.cellState = 1;
                trackCellState[index] = 1; // set cell to O
                player1 = false; // player2's turn
            } else if (player1 == false) {
                if(Mode){
                    clickedCell.setText(S2[0]); // present it on swing
                }else{
                    clickedCell.setText(S2[1]);
                }
                clickedCell.cellState = 2;
                trackCellState[index] = 2; // set cell to O
                player1 = true; // player1's turn
            }
        }

        int currentGameState = this.evaluate();
        System.out.println("GameState: " + currentGameState);
        if (currentGameState == 1) {
            System.out.println("The winner is O");
            // JoptionPane reset the game
            notice = new JOptionPane();
            String winner;
            if(Mode){
                winner = S1[0];
            }else{
                winner = S1[1];
            }
            int input = notice.showOptionDialog(null, winner+" wins!\nClick ok to RESET the game\nClick cancel to CLOSE the window", "Gameover",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

            if (input == JOptionPane.OK_OPTION) {
                for (int i = 0; i < 9; i++) {
                    trackCellState[i] = 0;
                }
                GameState = -1;
                player1 = true;
                TicTacToe.resetBtns();
                TicTacToe.enableBtns();
            }else if(input == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }
            
        }
        
        if (currentGameState == 2) {
            System.out.println("The winner is X");
            // JoptionPane reset the game
            notice = new JOptionPane();
            String winner;
            if(Mode){
                winner = S2[0];
            }else{
                winner = S2[1];
            }
            int input = notice.showOptionDialog(null, winner+" wins!\nClick ok to RESET the game\nClick cancel to CLOSE the window", "Gameover",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            
            if (input == JOptionPane.OK_OPTION) {
                for (int i = 0; i < 9; i++) {
                    trackCellState[i] = 0;
                }
                GameState = -1;
                player1 = true;
                TicTacToe.resetBtns();
                TicTacToe.enableBtns();
            }else if(input == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }
            
        }
        
        if (currentGameState == 0) {
            System.out.println("It's a tied game");
            // JoptionPane reset the game
            notice = new JOptionPane();
            int input = notice.showOptionDialog(null, "It's a tie game!\nClick ok to RESET the game\nClick cancel to CLOSE the window", "Gameover",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            
            if (input == JOptionPane.OK_OPTION) {
                for (int i = 0; i < 9; i++) {
                    trackCellState[i] = 0;
                }
                GameState = -1;
                player1 = true;
                TicTacToe.resetBtns();
                TicTacToe.enableBtns();
            }else if(input == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }

        }

    }

}