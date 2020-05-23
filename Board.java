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

    public void resetUI(JPanel panel) {
        getRootPane().remove(panel);
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

        while (counter < (mat.length * totalRanges)) {
            for (int i = floor; i <= cap; i++) {
                result[index][i] = mat[index][i];
            }
            if (index == 8) {
                index = 0;
                floor = floor + 3;
                cap = cap + 3;
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
   
























/*
public class Sudoku extends JFrame
{ 
    int[] mat[]; 
    int N; 
    int SRN; 
    int K; 
    
    Sudoku(int N, int K) 
    { 
        this.N = N; 
        this.K = K; 
  
        Double SRNd = Math.sqrt(N); 
        SRN = SRNd.intValue(); 
        mat = new int[N][N]; 
    } 
  
    public void fillValues() 
    { 
        fillDiagonal(); 
        fillRemaining(0, SRN); 
        removeKDigits(); 
    } 

    boolean unUsedInBox(int rowStart, int colStart, int num) 
    { 
        for (int i = 0; i<SRN; i++) 
            for (int j = 0; j<SRN; j++) 
                if (mat[rowStart+i][colStart+j]==num) 
                    return false; 
        return true; 
    } 
   
    void fillBox(int row,int col) 
    { 
        int num; 
        for (int i=0; i<SRN; i++) 
        { 
            for (int j=0; j<SRN; j++) 
            { 
                do
                { 
                    num = randomGenerator(N); 
                } 
                while (!unUsedInBox(row, col, num)); 
                mat[row+i][col+j] = num; 
            } 
        } 
    } 
    
    int randomGenerator(int num) 
    { 
        return (int) Math.floor((Math.random()*num+1)); 
    } 
  
    boolean CheckIfSafe(int i,int j,int num) 
    { 
        return (unUsedInRow(i, num) && 
                unUsedInCol(j, num) && 
                unUsedInBox(i-i%SRN, j-j%SRN, num)); 
    } 

    boolean unUsedInRow(int i,int num) 
    { 
        for (int j = 0; j<N; j++) 
           if (mat[i][j] == num) 
                return false; 
        return true; 
    } 

    boolean unUsedInCol(int j,int num) 
    { 
        for (int i = 0; i<N; i++) 
            if (mat[i][j] == num) 
                return false; 
        return true; 
    } 

    boolean fillRemaining(int i, int j) 
    { 
        if (j>=N && i<N-1) 
        { 
            i = i + 1; 
            j = 0; 
        } 
        if (i>=N && j>=N) 
            return true; 
  
        if (i < SRN) 
        { 
            if (j < SRN) 
                j = SRN; 
        } 
        else if (i < N-SRN) 
        { 
            if (j==(int)(i/SRN)*SRN) 
                j =  j + SRN; 
        } 
        else
        { 
            if (j == N-SRN) 
            { 
                i = i + 1; 
                j = 0; 
                if (i>=N) 
                    return true; 
            } 
        } 
  
        for (int num = 1; num<=N; num++) 
        { 
            if (CheckIfSafe(i, j, num)) 
            { 
                mat[i][j] = num; 
                if (fillRemaining(i, j+1)) 
                    return true; 
  
                mat[i][j] = 0; 
            } 
        } 
        return false; 
    } 

    public void removeKDigits() 
    { 
        int count = K; 
        while (count != 0) 
        { 
            int cellId = randomGenerator(N * N); 
            int i = (cellId/N); 
            int j = cellId%9; 
            if (j != 0) 
                j = j - 1; 

            if (mat[i][j] != 0) 
            { 
                count--; 
                mat[i][j] = 0; 
            } 
        } 
    } 
    
   public void Board(String[] args) 
   {
      new Sudoku().setVisible(true);
   }
   
   private Sudoku()
   {
      super("Sudoku Puzzle");
      setSize(1100, 600);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
    public void printSudoku() 
    { 
        for (int i = 0; i<N; i++) 
        { 
            for (int j = 0; j<N; j++) 
                System.out.print(mat[i][j] + " "); 
            System.out.println(); 
        } 
        System.out.println(); 
    } 
  
    public static void main(String[] args) 
    { 
        int N = 9, K = 20; 
        Sudoku sudoku = new Sudoku(N, K); 
        sudoku.fillValues(); 
        sudoku.printSudoku(); 
    } 
} */

//*******************************************************//
// here I was just trying to set font and text (didnt work lol)

         /* for(int i =0; i <=8; i++)
            {
               JTextField tf = new JTextField(1);
               tf.setHorizontalAlignment(JTextField.CENTER); 
                   p2.add(tf);
            }  */
            
            
                  
       /* create new Font
        Font font = new Font("Courier", Font.BOLD,12);
        //set font for JTextField
        tf.setFont(font);*/
