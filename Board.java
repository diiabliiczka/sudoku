import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFrame;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.GraphicsConfiguration;

public class Board extends JFrame
{
    private ArrayList<JPanel> UIElements;
    public JFrame frame;
    public Board(){ };

    public void init() {
        this.bootstrapSudoku();
    }

    public void resetUI(JPanel p1) {
        removeAll();
        validate();
        repaint();
        setVisible(true);
    }
    public void bootstrapSudoku() {

        JFrame frame = new JFrame("Poop");

        this.frame = frame;

        setSize(1100,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        Border lineBorder = new LineBorder(Color.PINK,2);
        String[] levels = { "Easy", "Medium", "Hard" };
        JComboBox difficultyBox = new JComboBox(levels);
        difficultyBox.setSelectedIndex(0);

        // Generate Sudoku Board
        Sudoku sudoku = this.generateSudokuBoard();

        // Create parent element for all UI
        JPanel p1 = new JPanel(new GridLayout(3,3));

        // Map Sudoku mat values to board
        this.populateSudokuBoard(lineBorder, sudoku, p1);

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
        JButton solve = new JButton("Solve");

        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> values = new ArrayList<>();
                for (JPanel panel : self.UIElements) {
                    Component[] components = panel.getComponents();
                    for (Component comp : components) {
                        // Cast comp to JComboBox / JTextField to get the values
                        if (comp instanceof JTextField) {
                            JTextField textField = (JTextField) comp;
                            values.add(textField.getText());
                        }
                    }
                }
            }
        });

        Buttons.add(new JButton("Hint"));
        Buttons.add(solve);
        Buttons.add(new JButton("Reset"));
        Buttons.add(difficultyBox);

        add(p1);
        add(Buttons);
        setVisible(true);
    }

    public void populateSudokuBoard(Border lineBorder, Sudoku sudoku, JPanel p1) {
        JPanel p2 = new JPanel(new GridLayout(3,3));

        p2.setBorder(lineBorder);

        int counter = 0;
        int[][] mat = sudoku.getMat();
        ArrayList<JPanel> UIElements = new ArrayList<>();

        int xOffset = 0, yOffset = 0;
        while (counter < 9) {
            p2 = new JPanel(new GridLayout(3,3));
            p2.setBorder(lineBorder);
            ArrayList<Integer> block = this.getBlock(mat, xOffset, yOffset, 3);
            for (int i = 0; i <= 8; i++) {
                String number = Integer.toString(block.get(i));
                p2.add(new JTextField(number.equals("0") ? "" : number));
                p1.add(p2);
            }
            if (xOffset == 6) {
                xOffset = 0;
                yOffset = yOffset + 3;
            } else {
                xOffset = xOffset + 3;
            }
            counter++;
            UIElements.add(p2);
        }

        this.UIElements = UIElements;
    }

    public ArrayList<Integer> getBlock(int[][] data, int xOffset, int yOffset, int size) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int[] copy = data [i + yOffset];
            for (int j = xOffset; j < xOffset + size; j++) {
                result.add(copy[j]);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        Board sud = new Board();
        sud.init();
    }
}