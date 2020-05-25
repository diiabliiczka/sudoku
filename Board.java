import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFrame;
import java.lang.*;
 
public class Board extends JFrame
{

    public Board(){ };

    public void init() {
        this.bootstrapSudoku();
    }

    public void resetUI(JPanel p1) {
        removeAll();
        setContentPane(p1);
        validate();
        repaint();
        setVisible(true);
    }

    public void bootstrapSudoku() {

        setSize(1100,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        Border lineBorder = new LineBorder(Color.PINK,2);
        String[] levels = { "Easy", "Medium", "Hard" };
        JComboBox difficultyBox = new JComboBox(levels);
        difficultyBox.setSelectedIndex(0);

        // Generate Sudoku Board
        Sudoku sudoku = this.generateSudokuBoard();

        // Prepare the data transformation for Sudoku Board
        int [][] result = this.transformSudokuBoardData(sudoku);

        // Create parent element for all UI
        JPanel p1 = new JPanel(new GridLayout(3,3));

        // Map Sudoku mat values to board
        this.populateSudokuBoard(lineBorder, result, p1);

        // Build the UI for player
        this.buildPlayerUI(difficultyBox, p1);
    };

    public Sudoku generateSudokuBoard() {
        int N = 9, K = 20;
        Sudoku sudoku = new Sudoku(N, K);
        sudoku.fillValues();
        sudoku.printSudoku();

        return sudoku;
    }

//    public int[][] parseBlocks(int[][] data, int counter){
//        int [][] result = new int[9][9];
//        return (result[result.length - 1] != null) ? parseBlocks(data, counter) : data;
//    }

    public int[][] transformSudokuBoardData(Sudoku sudoku) {
        int[][] mat = sudoku.getMat();
        int[][] result = new int[9][9];

        // What row are we on
        int index = 0;
        // Beginning of range
        int floor = 0;
        // End of range
        int cap = 2;
        // Track the number of iterations
        int counter = 0;
        // How many ranges
        int totalRanges = 3;
        // Current Index
        int currIndex = 0;
        // Current index 2
        int currIndex2 = 0;

        while (counter < (mat.length * totalRanges)) {
            for (int i = floor; i <= cap; i++) {
                int number = mat[index][i];
                result[currIndex][currIndex2] = number;
                currIndex2++;
                if (currIndex2 == 8) {
                    currIndex2 = 0;
                }
            }
            if (index == 8) {
                index = 0;
                floor = floor + 3;
                cap = cap + 3;
                currIndex++;
            } else {
                index++;
            }
            counter++;
        }
        return result;
    }

    public void buildPlayerUI(JComboBox difficultyBox, JPanel p1) {
        JPanel Buttons = new JPanel(new GridLayout(5,1));
        JButton newPuzzle = new JButton("New Puzzle");
        Board self = this;
        newPuzzle.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    self.resetUI(p1);
                    self.bootstrapSudoku();
                }
            }
        );
        Buttons.add(newPuzzle);
        Buttons.add(new JButton("Hint"));
        Buttons.add(new JButton("Solve"));
        Buttons.add(new JButton("Reset"));
        Buttons.add(difficultyBox);

        add(p1);
        add(Buttons);
        setVisible(true);
    }

    public void populateSudokuBoard(Border lineBorder, int[][] data, JPanel p1) {
        JPanel p2 = new JPanel(new GridLayout(3,3));

        p2.setBorder(lineBorder);

        for (int k = 0; k <= 8; k++) {
            p2 = new JPanel(new GridLayout(3,3));
            p2.setBorder(lineBorder);
            for (int i = 0; i <= 8; i++) {
                String number = Integer.toString(data[k][i]);
                p2.add(new JTextField(number.equals("0") ? "" : number));
                p1.add(p2);
            }
        }
    }
    
    public static void main(String[] args) {
        Board sud = new Board();
        sud.init();
    }
}