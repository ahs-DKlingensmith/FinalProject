
/**
 * Dylan Klingensmith
 * Period 2
 * 5/22/19
 * Bingo Class
 *
 */
import java.util.*;
import java.io.*;

public class Bingo {

    private Random rand = new Random();
    private int[][] card;       //Bingo card configuration
    private int[] stream;       //list of 75 integers
    private boolean[][] marks;  //simulates placing chips on a Bingo card

    public Bingo() {
        card = new int[5][5];
        stream = new int[75];
        marks = new boolean[5][5];
    }

    /**
     * This method writes a random Bingo card configuration and a stream of
     * random number between 1 and 75 to the output file.
     *
     * The first column in the table contains only integers between 1 and 15,
     * the second column numbers are all between 16 and 30, the third are 31 to
     * 45, the fourth 46-60, and the fifth 61-75.
     *
     * There are no duplicate numbers on a Bingo card.
     */
    public void write(String outputFile) throws IOException {

        int[][] printCard = new int[5][5];
        int range = 1;

        ArrayList<Integer> printStream = new ArrayList<Integer>();

        FileWriter fwriter = new FileWriter(outputFile, true);
        PrintWriter printFile = new PrintWriter(fwriter);

        for (int c = 0; c < printCard[0].length; c++) {
            for (int r = 0; r < printCard.length; r++) {

                printCard[r][c] = rand.nextInt(15) + range;

                for (int compare = 0; compare < printCard[0].length; compare++) {

                    if (r != compare
                            && printCard[r][c] == printCard[compare][c]) {

                        printCard[r][c] = rand.nextInt(15) + range;
                        compare = 0;

                    }
                }
            }
            range += 15;
        }

        for (int printRow = 0; printRow < printCard.length; printRow++) {
            for (int printCol = 0; printCol < printCard[0].length; printCol++) {

                if (printRow == 2 && printCol == 2) {
                    printFile.print("0\t");
                } else {
                    printFile.print(printCard[printRow][printCol] + "\t");
                }
            }
            printFile.println("\n");
        }

        for (int assign = 0; assign < 75; assign++) {
            printStream.add(assign + 1);
        }

        for (int print = 0; print < 75; print++) {
            int randomIndex = rand.nextInt(printStream.size());
            int randomNumber = printStream.get(randomIndex);

            printFile.print(randomNumber + " ");
            printStream.remove(randomIndex);
        }

        printFile.close();

    }

    /**
     * Shuffles the list of numbers
     */
    public void shuffle(ArrayList<Integer> list) {

        int randomIndex;
        int holdValue;

        for (int k = 0; k < list.size(); k++) {

            randomIndex = rand.nextInt(list.size());

            holdValue = list.get(k);
            list.set(k, list.get(randomIndex));
            list.set(randomIndex, holdValue);

        }
    }

    /**
     * This method reads a given inputFile that contains a Bingo card
     * configuration and a stream of numbers between 1 and 75. . A Bingo card
     * configuration is stored in the card array. A list of 75 integers is
     * stored in the stream array.
     */
    public void read(String inputFile) throws IOException {

        File file = new File(inputFile);
        Scanner readFile = new Scanner(file);

        for (int readRows = 0; readRows < card.length; readRows++) {

            for (int readCols = 0; readCols < card[0].length; readCols++) {

                card[readRows][readCols] = readFile.nextInt();

            }
        }

        for (int readStream = 0; readStream < stream.length; readStream++) {

            stream[readStream] = readFile.nextInt();

        }

        readFile.close();
        

    }

    /**
     * This method returns the first integer from the stream array that gives
     * you the earliest winning condition.
     *
     * - all the spots in a column are marked - all the spots in a row are
     * marked - all the spots in either of the two diagonals are marked - all
     * four corner squares are marked
     */
    public int playGame() {

        int winningNumber = 0;
        int drawnNumber = 0;

        boolean gameIsWon = false;

        for (int falseRow = 0; falseRow < card.length; falseRow++) {
            for (int falseCol = 0; falseCol < card[0].length; falseCol++) {

                marks[falseRow][falseCol] = false;

            }
        }
        marks[2][2] = true;

        while (gameIsWon == false) {

            winningNumber = stream[drawnNumber];

            for (int markRow = 0; markRow < card.length; markRow++) {
                for (int markCol = 0; markCol < card[0].length; markCol++) {

                    if (card[markRow][markCol] == winningNumber) {
                        marks[markRow][markCol] = true;
                    }

                }
            }

            for (int checkRow = 0; checkRow < card.length; checkRow++) {

                int rowScore = 0;
                for (int checkCol = 0; checkCol < card[0].length; checkCol++) {

                    if (marks[checkRow][checkCol] = true) {
                        rowScore++;
                    }

                    if (rowScore == 5) {
                        gameIsWon = true;
                    }

                }

            }

            for (int checkCol = 0; checkCol < card[0].length; checkCol++) {

                int colScore = 0;
                for (int checkRow = 0; checkRow < card.length; checkRow++) {

                    if (marks[checkRow][checkCol] == true) {
                        colScore++;
                    }

                    if (colScore == 5) {
                        gameIsWon = true;
                    }

                }

            }

            int fDiagnolScore = 0;
            for (int forwardDiagnol = 0; forwardDiagnol < card.length; forwardDiagnol++) {

                if (marks[forwardDiagnol][forwardDiagnol] == true) {
                    fDiagnolScore++;
                }

                if (fDiagnolScore == 5) {
                    gameIsWon = true;
                }

            }

            int bDiagnolScore = 0;
            for (int backwardDiagnol = 0; backwardDiagnol < card.length; backwardDiagnol++) {

                if (marks[backwardDiagnol][4 - backwardDiagnol] == true) {
                    bDiagnolScore++;
                }

                if (bDiagnolScore == 5) {
                    gameIsWon = true;
                }

            }

            if (marks[0][0] && marks[0][4] && marks[4][0] && marks[4][4]) {
                gameIsWon = true;
            }
            
            drawnNumber++;
        }

        return winningNumber;
    }

}
