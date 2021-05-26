package solver;

import java.io.*;
import java.util.*;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class Exams {

    int[][] conflict_matrix; // conflict matrix computed during problem loading
    int total_student = 0;

    
    public Exams(String dir, int size) {
        conflict_matrix = new int[size][size];
        try {
            FileReader firereader = new FileReader(dir);
            BufferedReader bufferedreader = new BufferedReader(firereader);

            buildMatrix(bufferedreader);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getTotalStudent() {
        return this.total_student;
    }

    public int[][] getMatrix() {
        return conflict_matrix;
    }

    public void buildMatrix(BufferedReader bufferedreader) {
        String examLine = null;
        try {
            while ((examLine = bufferedreader.readLine()) != null) {
                total_student++;
                String[] arr = examLine.split(" ");
                if (arr.length > 1) {
                    for (int i = 0; i < arr.length - 1; i++) {
                        for (int j = i + 1; j < arr.length; j++) {
                            int index1 = Integer.parseInt(arr[i]);
                            int index2 = Integer.parseInt(arr[j]);

                            this.conflict_matrix[index1 - 1][index2 - 1]++;
                            this.conflict_matrix[index2 - 1][index1 - 1]++;
                        }
                    }
                }
            }

        } catch (Exception e) {
        }
    }

 
    public static void sortingDegree(int array[][], int collumn) {
        Comparator<int[]> degree = Comparator.comparing(row -> row[1]);
        Comparator<int[]> course = Comparator.comparing(row -> row[0]);

        Arrays.sort(array, Collections.reverseOrder(degree.thenComparing(course.reversed())));
    }

    public int[][] getDegree() {
        int[][] temporary = Arrays.copyOf(getMatrix(), getMatrix().length);
        int[][] examDegree = new int[temporary.length][2];

        for (int i = 0; i < temporary.length; i++) {
            int count = 0;
            for (int j = 0; j < temporary.length; j++) {
                if (temporary[i][j] != 0) {
                    count++;
                }
            }
            examDegree[i][0] = i + 1;// make max array with i row 0 column.
            examDegree[i][1] = count;// make max array with i row 1 column.
        }
        sortingDegree(examDegree, 1);

        return examDegree;
    }

  /* exams are ordered, in decreasing order, by the number of
   conflicts they have with all other exams.*/
    public int[][] getLargestDegree() {
        int[][] temporary = Arrays.copyOf(getMatrix(), getMatrix().length);
        int[][] examDegree = this.getDegree();
        int[][] largestDegree = new int[temporary.length][temporary.length];
        for (int i = 0; i < temporary.length; i++) {
            for (int j = 0; j < temporary.length; j++) {
                largestDegree[i][j] = temporary[examDegree[i][0] - 1][examDegree[j][0] - 1];
            }
        }
        return largestDegree;
    }
     


}
