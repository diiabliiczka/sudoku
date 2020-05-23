import javax.swing.JFrame;
import java.lang.*;
import java.util.Arrays;

// Sudoku generator    
public class Sudoku extends JFrame
{ 
    int[] mat[]; 
    int N; // number of columns/rows. 
    int SRN; // square root of
    int K; // Numm issing digits 
  
    // Constructor 
    Sudoku(int N, int K) 
    { 
        this.N = N; 
        this.K = K; 
  
        // Compute square root of N 
        Double SRNd = Math.sqrt(N); 
        SRN = SRNd.intValue(); 
  
        mat = new int[N][N]; 
    }
    
    public int[][] getMat()
    {
      return this.mat;
    } 
  
    // Sudoku Generator 
    public void fillValues() 
    { 
        // Fill the diagonal of SRN x SRN matrices 
        fillDiagonal(); 
  
        // Fill remaining blocks 
        fillRemaining(0, SRN); 
  
        // Remove Randomly K digits to make game 
        removeKDigits(); 
       
    } 
  
    // Fill the diagonal SRN number of SRN x SRN matrices 
    void fillDiagonal() 
    { 
  
        for (int i = 0; i<N; i=i+SRN) 
  
            // for diagonal box, start coordinates->i==j 
            fillBox(i, i); 
    } 
  
    // Returns false if given 3 x 3 block contains num. 
    boolean unUsedInBox(int rowStart, int colStart, int num) 
    { 
        for (int i = 0; i<SRN; i++) 
            for (int j = 0; j<SRN; j++) 
                if (mat[rowStart+i][colStart+j]==num) 
                    return false; 
  
        return true; 
    } 
  
    // Fill a 3 x 3 matrix. 
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
  
    // Random generator 
    int randomGenerator(int num) 
    { 
        return (int) Math.floor((Math.random()*num+1)); 
    } 
  
    // Check if safe to put in cell 
    boolean CheckIfSafe(int i,int j,int num) 
    { 
        return (unUsedInRow(i, num) && 
                unUsedInCol(j, num) && 
                unUsedInBox(i-i%SRN, j-j%SRN, num)); 
    } 
  
    // check in the row for existence 
    boolean unUsedInRow(int i,int num) 
    { 
        for (int j = 0; j<N; j++) 
           if (mat[i][j] == num) 
                return false; 
        return true; 
    } 
  
    // check in the row for existence 
    boolean unUsedInCol(int j,int num) 
    { 
        for (int i = 0; i<N; i++) 
            if (mat[i][j] == num) 
                return false; 
        return true; 
    } 
  
    /* A recursive function to fill remaining  
       matrix */
    boolean fillRemaining(int i, int j) 
    { 
        //  System.out.println(i+" "+j); 
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
  
    /* Remove the K num of digits to 
       complete game */
    public void removeKDigits() 
    { 
        int count = K; 
        while (count != 0) 
        { 
            int cellId = randomGenerator(N * N); 
  
            // System.out.println(cellId); 
            // extract coordinates i  and j 
            int i = (cellId/N); 
            int j = cellId%9; 
            if (j != 0) 
                j = j - 1; 
  
             //System.out.println(i+" "+j); 
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
      // naming JFrame window (if easy print easy if medium print medium if hard print hard)
      super("Sudoku Puzzle");
      // create 700 x 700 window frame
      setSize(1000, 600);
      // do not let resizing
      setResizable(false);
      // exit on close
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
    
    // Print sudoku 
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
   
  
    // Driver code 
    public static void main(String[] args) 
    { 
       
           
           //for (int l = 0; l < value.length; l++) {
           //   System.out.println(value[l]);
           //}
           

           //sudoku.printSudoku(); 
        }
     }
     
   /*  for (int j = 0; j < result[j][j].length; j++) {

        int fal = result[j][j].length;

        int sal = value[j][j].length;

        int[] result = new int[fal + sal];

        System.arraycopy(firstArray, 0, result, 0, fal); 

        System.arraycopy(secondArray, 0, result, fal, sal); */