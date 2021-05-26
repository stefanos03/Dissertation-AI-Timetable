package solver;

import java.io.FileWriter;
import java.util.Arrays;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class Scheduling {

    private int size;
    private int[] timeslot;
    private boolean addSolution;
    private int[][] schedule;
    private int totalTimeslot;
    private String solution = "";

    public Scheduling() {
        this.size = 0;
    }

    public Scheduling(int size) {
        this.size = size;
    }

    public int[][] getSchedule() {
        return this.schedule;
    }

    public String getSolution() {
        return this.solution;
    }

    public int[] getTimeslot() {
        return this.timeslot;
    }
    
    public void setTotalTimeslot(int TotalTimeslot) {
    	this.totalTimeslot = TotalTimeslot;
    }
    
    public int getTotalTimeslot() {
    	return this.totalTimeslot;
    } 


    public boolean getAddSolution() {
        return this.addSolution;
    }

    private void setAddSolution(boolean status) {
        this.addSolution = status;
    }

    private boolean checkTimeslot(int exam, int[][] matrix, int[] timeslot, int t) {
        for (int i = 0; i < size; i++) {
            if (matrix[exam][i] != 0 && t == timeslot[i]) {
                return false;
            }
        }
        return true;
    }

//is it feasible
    private boolean isFeasible(int[][] matrix, int total_timeslot, int[] timeslot, int course) {
        if (course == size) {
            return true;
        }

        for (int i = 1; i <= total_timeslot; i++) {
            if (checkTimeslot(course, matrix, timeslot, i)) {
                timeslot[course] = i;

                if (isFeasible(matrix, total_timeslot, timeslot, course + 1)) {
                    return true;
                }

                timeslot[course] = 0;
            }
        }

        return false;
    }

    public void timesloting(int[][] matrix, int total_timeslot) {
        timeslot = new int[size];
        for (int i = 0; i < size; i++) {
            timeslot[i] = 0;
        }

        if (isFeasible(matrix, total_timeslot, timeslot, 0)) {
            setAddSolution(true);
        } else {
            setAddSolution(false);
        }
    }

//print the results
    public void printSchedule(int[][] degree) {
        if (!addSolution) {
            System.out.println("Null");
        } else {
            schedule = new int[size][2];
            for (int i = 0; i < size; i++) {

                solution += degree[i][0] + " " + timeslot[i] + "\n";
                schedule[i][0] = degree[i][0];
                schedule[i][1] = timeslot[i];
            }
        }
    }
  
    //print the solutions
    public void exportSchedule(String filename) {
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\stefanoschatz\\Desktop\\java code\\SolverETP\\Solutions\\" + filename + ".sol");
            fileWriter.write(this.getSolution());
            fileWriter.close();
            System.out.println("File "+filename+" file saved successfully"); 
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
   
    }
}
