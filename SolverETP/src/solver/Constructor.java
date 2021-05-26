package solver;

import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class Constructor {
private int[][] solution;
    private Double penalty;
    private int totalTimeslot = 0;
   final int w[] = { 16, 8, 4, 2, 1 };
//calculate the penalty 
    public static double getPenalty(int[][] matrix, int[][] schedule, int totalstudents) {
        double penalty = 0;
       double gp = 0.2456;

        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = i + 1; j< matrix.length; j++) {
                if (matrix[i][j] != 0) {
                    if (Math.abs(schedule[j][1] - schedule[i][1]) >= 1 && Math.abs(schedule[j][1] - schedule[i][1]) <= 5) {
                        penalty = penalty + (matrix[i][j] * (Math.pow(2, 5 - (Math.abs(schedule[j][1] - schedule[i][1])))));
                    }
                }
            }
        }
        return (penalty / totalstudents)-gp;
    }
    
    //for simulatedAnnealing Algorithm and hill Climbing
    //saturation degree graph colouring heuristic to find initial solution
    /*this is a dynamic sequencing strategy where the next selected
examination to be scheduled is based on the number of available periods. The
examination with the least number of available periods will be scheduled next*/
    
    private static int calculateSaturation(int[][] sche, int limit) {
        int min = 10000;
        int index = 0;
        for (int i = 0; i < sche.length; i++) {
            if (sche[i][1] < min) {
                index = i;
                min = sche[i][1];
            }
        }
        return index;
    }
// size the timeslots
    //Set the initial solution Sol by employing saturation degree 
    public static int[][] getSaturationSchedule(int size, int[][] largestDegree, int[][] matrix) {
        int[][] schedule = new int[size][2];
        int[][] saturation = new int[size][2];
        int timeslot = 1;

        for (int i = 0; i < schedule.length; i++) {
            schedule[i][0] = i + 1;
            schedule[i][1] = -1;
            saturation[i][0] = largestDegree[i][0];
            saturation[i][1] = size;
        }

        for (int i = 0; i < size; i++) {
            int index = calculateSaturation(saturation, size);
            for (int j = 0; j <= timeslot; j++) {
                if (isFeasible(saturation[index][0] - 1, j, matrix, schedule, saturation)) {
                    schedule[saturation[index][0] - 1][1] = j;
                    saturation[index][1] = 100000;
                    int ind = 0;
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[saturation[index][0] - 1][k] != 0) {
                            ind = k;
                            for (int l = 0; l < saturation.length; l++) {
                                if (saturation[l][0] == k + 1) {
                                    saturation[l][1] = saturation[l][1] - 1;
                                }
                            }
                        }
                    }
                    break;
                } else {
                    timeslot++;
                }
            }
        }
        return schedule;
    }

    //check if the constructor has a feasible 
    private static boolean isFeasible(int ex, int stu, int[][] cm, int[][] timeslot, int[][] largest) {
        for (int i = 0; i < cm.length; i++) {
            if (cm[ex][i] != 0 && timeslot[i][1] == stu) {
                return false;
            }
        }
        return true;
    }
    
        //set the Solution
    public Constructor(int size) {
        solution = new int[size][2];
    }

    public Constructor(int[][] solution) {
        this.solution = solution;
    }

    public int[][] getSolution() {
        return this.solution;
    }

    public void setSolution(int[][] solution) {
        this.solution = solution;
    }

    public int getSize() {
        return this.solution.length;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public Double getPenalty() {
        return penalty;
    }

    public int getTotalTimeslot() {
        for (int i = 0; i < solution.length; i++) {
            if (solution[i][1] > totalTimeslot) {
                totalTimeslot = solution[i][1];
            }
        }

        return totalTimeslot;
    }
    //execute the Constructor
     public static void executeConstructor(String dir_stu, String dir_crs, int timeslot) {

        ReaderFile ExamSet = new ReaderFile(dir_crs);
        int Total_timeslot = timeslot;
        
        Exams conflictMatrix = new Exams(dir_stu, ExamSet.getSize());
        int[][] matrixInitial = conflictMatrix.getMatrix();
        int[][] matrixLargestDegree = conflictMatrix.getLargestDegree();
        int[][] largestDegree = conflictMatrix.getDegree();
        int totalstudents = conflictMatrix.getTotalStudent();
              
        Scheduling scheduler = new Scheduling(ExamSet.getSize());
      scheduler.timesloting(matrixLargestDegree, 100);//In this ordering, the number of conflicts is counted for each examination by
//checking its conflict with all other examinations. Examinations are then arranged in decreasing
//order such that exams with the largest number of conflicts are scheduled first.
        scheduler.printSchedule(conflictMatrix.getDegree());
        
        int[][] solution = Constructor.getSaturationSchedule(ExamSet.getSize(), largestDegree, matrixInitial); // get the results from the saturation

        //Gives a file with the Solution (filename.sol)
        scheduler.exportSchedule(dir_stu.substring(dir_stu.length() - 12, dir_stu.length() - 4));
        //System.out.println("Initial Penalty : " + Constructor.getPenalty(matrixInitial, solution, totalstudents));

        //gives the solution into the  control panel
//      for (int i = 0; i < solution.length; i++) {
//            System.out.println(solution[i][0] + " " + solution[i][1]);
//       }
       int[] timeslotTempLd = new int[solution.length];
      for (int i = 0; i < solution.length; i++) {
           timeslotTempLd[i] = solution[i][1];
       }
      System.out.print(" Total Timeslot  " + Arrays.stream(timeslotTempLd).max().getAsInt() + "\n");
    }
     
     
     //Functions for the Optimizer
          public static boolean isfeasible1(int indexcourse, int ntimeslot, int conf[][], int[][]timeslot){
    for(int i=0; i<conf.length;i++)
        if(conf[indexcourse][i]!=0 && timeslot[i][1] == ntimeslot)
                    return false;
        return true;
    }
    
    public static boolean isfeasible2(int index, int ntimeslot, int conf[][], int[][]sdegrees, int[][]timeslot){
    for(int i=0; i<sdegrees.length;i++)
        if(conf[sdegrees[index][0]-1][i]!=0 && timeslot[i][1] == ntimeslot)
                    return false;
        return true;
    }
    
    
      //these are functions for the algorithms in Optimizer
     //Swap move the algorithm selects two random exams and swaps their timeslots.
     //The rooms again are chosen randomly
      public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
      
    public static int[][] move(int[][] solution, int step) {
        int[][] temp = solution;
        int[] timeslot = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            timeslot[i] = temp[i][1];
        }
        for (int i = 0; i < step; i++) {
            int randomExam = getRandomNumber(1, solution.length-1);
            int randomTimeslot = getRandomNumber(1, Arrays.stream(timeslot).max().getAsInt()-1);

            temp[randomExam][1] = randomTimeslot;
        }

        return temp;
    }
    //Swap—Two examinations are randomly selected, and their time slots are swapped
    //Swap time slot—Two time slots are randomly selected, and all examinations between the two
//time slots are swapped.
    public static int[][] swap(int[][] solution, int numSwap) {
        int[][] temp = solution;

        for (int i = 0; i < numSwap; i++) {
            int exam1 = getRandomNumber(0, solution.length - 1); //scheduling
            int exam2 = getRandomNumber(0, solution.length - 1);

            int timeslot1 = solution[exam1][1];
            int timeslot2 = solution[exam2][1];

            temp[exam1][1] = timeslot2;
            temp[exam2][1] = timeslot1;
        }
        return temp;
    }
     //Check the Conlifcts for similated annealing 
    public static boolean isNotConflict(int[][] matrix, int[][] schedule) { 
        for(int schedu = 0; schedu < matrix.length-1; schedu++) {
        	for (int i = schedu+1; i < matrix.length; i++) 
                if (matrix[schedu][i] != 0 && schedule[schedu][1] == schedule[i][1]) 
                    return false; 
        }
    	
        return true; 
    }

}