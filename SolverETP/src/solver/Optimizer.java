package solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class Optimizer {

    private static int[][] SolutionBest;
private int solution=0;
    public static int[][] getSoluti() {
        return SolutionBest;
    }
    public static int[][] copySolution(int[][] arr) {
        int[][] copySolution = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            copySolution[i][0] = arr[i][0];
            copySolution[i][1] = arr[i][1];
        }

        return copySolution;
    }
    public static void SimulatedAnnealing(String dir_stu, String dir_crs, double temperature, int iterations) {
        ReaderFile courseSet = new ReaderFile(dir_crs); // get the exams
        Exams cm = new Exams(dir_stu, courseSet.getSize());// get the students

        int[][] confMat = cm.getMatrix(); // get the conflict Matrix
        int totalStudents = cm.getTotalStudent();//get total students
        int[][] solution = Constructor.getSaturationSchedule(courseSet.getSize(), cm.getDegree(), confMat); //get the solution from the constructor using saturation from scheduling

    Constructor bestSolution = new Constructor(solution); // take the best solution so far from the constructor and initilize

        int[][] neighborSolution = copySolution(solution);
        int[][] solutionFinal = copySolution(neighborSolution);
        double reductionFactor = 0.001; //cooling rate
        double temperatureTemporary = temperature;

        for (int i = 0; i < iterations; i++) { // algorithm starts from here
            int moveSwapRandomizer = Constructor.getRandomNumber(1, 5);
            int[][] iterationConstructor;

            switch (moveSwapRandomizer) {
                case 1:
                    iterationConstructor = Constructor.move(copySolution(neighborSolution), 1);
                    break;
                case 2:
                    iterationConstructor = Constructor.swap(copySolution(neighborSolution), 1);
                    break;
                case 3:
                    iterationConstructor = Constructor.move(copySolution(neighborSolution), 2);
                    break;
                case 4:
                    iterationConstructor = Constructor.swap(copySolution(neighborSolution), 4);
                    break;
                case 5:
                    iterationConstructor = Constructor.move(copySolution(neighborSolution), 5);
                    break;
                default:
                    iterationConstructor = Constructor.swap(copySolution(neighborSolution), 1);
                    break;
            }

            temperatureTemporary =  (1 - reductionFactor)/temperatureTemporary ;//reducing the temperature
            if (Constructor.isNotConflict(confMat, iterationConstructor)) { // if it is not conflict from the switch function then get penelty
                if (Constructor.getPenalty(confMat, iterationConstructor, totalStudents) <= Constructor.getPenalty(confMat, neighborSolution, totalStudents)) {
                    neighborSolution = copySolution(iterationConstructor);
                    if (Constructor.getPenalty(confMat, iterationConstructor, totalStudents) <= Constructor.getPenalty(confMat, solutionFinal, totalStudents)) {
                        solutionFinal = copySolution(iterationConstructor);
                        bestSolution.setSolution(solutionFinal);
                        bestSolution.setPenalty(Constructor.getPenalty(confMat, iterationConstructor, totalStudents));
                    }
                } else if (Math.exp((Constructor.getPenalty(confMat, neighborSolution, totalStudents) - Constructor.getPenalty(confMat, iterationConstructor, totalStudents)) / temperatureTemporary) > Math.random()) {
                    neighborSolution = copySolution(iterationConstructor);
                }
            }
//every 1 iterations give me the results back
            if ((i + 1) % 10 == 0) {
               System.out.println("iterations" + (i + 1) + " " + Constructor.getPenalty(confMat, neighborSolution, totalStudents));
                //take the results
                //System.out.println(Constructor.getPenalty(confMat, neighborSolution, totalStudents));
            }

        } // algorithm  ends here
        
       // get solution
       System.out.println("==Results from Simulated Annealing==");
        System.out.println("Penalty initial solution : " + Constructor.getPenalty(confMat, Constructor.getSaturationSchedule(courseSet.getSize(), cm.getDegree(), confMat), totalStudents));// get the initial solution from the constructor
        System.out.println("Best Penalty   : " + bestSolution.getPenalty());
        System.out.println("Total timeslot : " + bestSolution.getTotalTimeslot());
        
        //get the answer
        int[][] bbest = bestSolution.getSolution();		
        for (int i = 0; i < bbest.length; i++) {
           
            System.out.println(bbest[i][0] + " " + bbest[i][1]);
        }

        SolutionBest = solutionFinal;
    }
   
    
     
   //Hill Climbing Algorithm solution starts from here
        private static int[][] bestSchedule;//start by the initial solution

    private static void setSchedule(int[][] schedule) { // it will give the best schedule
        bestSchedule = schedule;
    } 
//for hillclibming
     public static boolean checkRandomTimeslot(int randomExams, int randomTimeslot, int[][] matrix, int[][] schedule) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[randomExams][i] != 0 && schedule[i][1] == randomTimeslot) {
                return false;
            }
        }
        return true;
    }

    public static void hillClimbing(String dir_stu, String dir_crs, int timeslot, int iteration) {
        ReaderFile ExamSet = new ReaderFile(dir_crs);
        Exams cm = new Exams(dir_stu, ExamSet.getSize());//read from conflict matrix
		
        int[][] conflict_matrix = cm.getMatrix();
        int[][] schedule = Constructor.getSaturationSchedule(ExamSet.getSize(), cm.getDegree(), conflict_matrix); //get the saturation scheduling

        int TotalStudent = cm.getTotalStudent();//get the students from the conflict matrix

        int[][] scheduleNeighbor = new int[schedule.length][2];//get new schedule from neighbor
//take the neighbor into a loop
        for (int i = 0; i < scheduleNeighbor.length; i++) {
            scheduleNeighbor[i][0] = schedule[i][0];
            scheduleNeighbor[i][1] = schedule[i][1];
        }

        double penalty = Constructor.getPenalty(conflict_matrix, schedule, TotalStudent); // get the initial penalty
        System.out.println(penalty);

        Constructor bestSolution = new Constructor(schedule); //initial the best penalty
        int max_timeslot = bestSolution.getTotalTimeslot();//take the total timeslots

        
        for (int i = 0; i < iteration; i++) { //loop for how many iteration are we going to take
            int randomExamIndex = Constructor.getRandomNumber(0, conflict_matrix.length - 1); //random exams
            int randomTimeslot = Constructor.getRandomNumber(0, max_timeslot - 1); //random Timeslots

            if (checkRandomTimeslot(randomExamIndex, randomTimeslot, conflict_matrix, scheduleNeighbor)) {//check of the random timeslots are feasible
                scheduleNeighbor[randomExamIndex][1] = randomTimeslot;
                double penalty2 = Constructor.getPenalty(conflict_matrix, scheduleNeighbor, TotalStudent); //caculate the new penalty2

                if (penalty > penalty2) {// compare the new penalty with the previous to see if it s better
                    penalty = Constructor.getPenalty(conflict_matrix, scheduleNeighbor, TotalStudent);
                    schedule[randomExamIndex][1] = scheduleNeighbor[randomExamIndex][1];
                    bestSolution.setSolution(schedule);
                    bestSolution.setPenalty(penalty);
                } else {
                    scheduleNeighbor[randomExamIndex][1] = schedule[randomExamIndex][1]; //take the other penatly
                }
            }
            
            if ((i + 1) % 10 == 0) { //every 1 iteration show me the results
                System.out.println("Iterations " + (i + 1) + " - Penalty : " + penalty);
                //for results
                //System.out.println(penalty);
            }
        }

        setSchedule(schedule);//best outcome
        System.out.println("==Results from Hill Climbing==");
//show the result in the control panel
        int[][] thebest = bestSolution.getSolution();
        for (int i = 0; i < thebest.length; i++) {
            System.out.println(thebest[i][0] + " " + thebest[i][1]);
        }
       //System.out.println("Penalty initial solution : " + Constructor.getPenalty(schedule, Constructor.getSaturationSchedule(ExamSet.getSize(), cm.getDegree(), schedule), TotalStudent));
        System.out.println("Best Penalty : " + bestSolution.getPenalty());
        System.out.println("Total timeslot : " + bestSolution.getTotalTimeslot());
    }
    
    
    //Tabu Search
     public static void TabuSearch(String crs, String stu, String filename,int iterations){
        ArrayList<String> Exam; // takes the exams
        ArrayList<String> student; // students
        int[][] conflict_matrix; //put it in a conflict matrix
        try {
            
            FileReader fcr = new FileReader(crs); // read the files
            BufferedReader cr = new BufferedReader(fcr);
            
            FileReader fst = new FileReader(stu);
            BufferedReader st = new BufferedReader(fst);
            
            
            Exam = new ArrayList<String>();
            
            String examLine = null;
            while((examLine = cr.readLine()) != null) {// reading all the lines from the file
                    String[] arr = examLine.split(" ");
                    Exam.add(arr[0]);
                   
            }
            
            student = new ArrayList<String>();
            conflict_matrix = new int[Exam.size()][Exam.size()];
            
            String StudentLine = null;
            while((StudentLine = st.readLine()) != null) {// reading all the students from the file
                String[] arr = StudentLine.split(" ");
                
                if(arr.length > 0) {
                    for(int i = 0; i < arr.length-1; i++) {
                        for(int j = i+1; j < arr.length; j++) {
                            int index1 = Integer.parseInt(arr[i]);
                            int index2 = Integer.parseInt(arr[j]);
                            
                            conflict_matrix[index1-1][index2-1]++;
                            conflict_matrix[index2-1][index1-1]++;
                        }
                    }
                }
                student.add(arr[0]);
            }
            
            int[][]degrees = new int[Exam.size()][2];
            for(int i=0;i<degrees.length;i++){
                degrees[i][0]=i+1;
            }
            
            int sum = 0;
            for(int i = 0; i < conflict_matrix.length; i++) {
                for(int j = 0; j < conflict_matrix.length; j++) {
                    
                    if(conflict_matrix[i][j] != 0){
                        sum++;
                    }
                }
                degrees[i][1]=sum;
                sum=0;
            }
           
            int ts=1;
            int [][] timeslot = new int[Exam.size()][2];
            
            int[][]sdegrees = new int[Exam.size()][2];    
            sdegrees=degrees;
           
            
            Arrays.sort(sdegrees, new Comparator<int[]>() { 
        @Override
        public int compare(int[] entry1, int[] entry2) { 
                    if (entry1[1] < entry2[1]) 
                        return 1; 
                    else
                        return -1; 
                  } 
                });

            //timesloting            
            for(int i=0; i<sdegrees.length; i++){
                    for(int j=0; j<ts; j++){
                            if(Constructor.isfeasible2(i, j, conflict_matrix, sdegrees, timeslot)){
                                    timeslot[sdegrees[i][0]-1][1] = j;
                                    break;
                            }else{
                                    ts++;
                            }
                    }
            }   
            //set the penalty
            double penalty = 0;
            penalty = Constructor.getPenalty(conflict_matrix,timeslot,student.size());
            //System.out.println("penalty : " + penalty);

            int max_timeslot = 0;
            
            for(int i = 0; i<timeslot.length; i++) {// takes the timeslots from the files
                if(timeslot[i][1] > max_timeslot)
                    max_timeslot = timeslot[i][1];
            }

            System.out.println("max timeslot : " + max_timeslot);
            
            //splitting timeslot 
            int[] index = new int[timeslot.length];
            for (int i = 0; i < timeslot.length; i++) {
                for (int j = 0; j < timeslot[i].length; j++) {
                    index[i]=timeslot[i][0];

                }
            }
            
            //initiation random
            Random r = new Random();
            int rindex1,rindex2,rindex3 = 0;
            int rslot1,rslot2,rslot3 = 0;
            
            //initiation initial solution
            int[][]sbest = timeslot.clone();
            int[][]bestcandidate = timeslot.clone();
            int[][]timeslottemp = timeslot.clone();
            
            //initiation tabulist
            LinkedList<int[][]> tabulist = new LinkedList<int[][]>();
            int maxtabusize = 10;//size of the list
            tabulist.addLast(timeslot.clone());
            
            //initiation iterations
            int maxiteration =iterations; 
            int iteration=0
                    ;
            
            //initiation  the timeslots
            ArrayList<int[][]> sbest_array = new ArrayList<>();
            Double[] sbest_penalty = new Double[maxiteration];
            int[][] smallest = timeslot.clone();

            //trajectory
            double[] trajectory = new double[maxiteration/10];
            double min_traject = 99999;

            //initiation itung penalty
            double penalty1 = 0;
            double penalty2 = 0;
            double penalty3 = 0;
            
            boolean terminate = false;
            long starttimer = System.nanoTime();
            while(!terminate){
                iteration++;
                    //search candidate solution / search neighbor
                    ArrayList<int[][]> sneighborhood = new ArrayList<>();
                    
                        
                    //move 1
                    rindex1 = r.nextInt(Exam.size());//random exam
                    rslot1 = r.nextInt(max_timeslot);// random timeslot
                    if (Constructor.isfeasible1(rindex1, rslot1, conflict_matrix, timeslottemp)) {//ask if its feasible
                        timeslottemp[rindex1][1] = rslot1;
                    }
                    sneighborhood.add(timeslottemp);//add to the timeslot the exam
                    
                    //move 2
                    rindex1 = r.nextInt(Exam.size());
                    rslot1 = r.nextInt(max_timeslot);
                    rindex2 = r.nextInt(Exam.size()); // new exam
                    rslot2 = r.nextInt(max_timeslot); //new timeslot
                    if (Constructor.isfeasible1(rindex1, rslot1, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex1][1] = rslot1;
                    }
                    if (Constructor.isfeasible1(rindex2, rslot2, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex2][1] = rslot2;
                    }
                    sneighborhood.add(timeslottemp);
                    
                    //move3
                    rindex1 = r.nextInt(Exam.size());
                    rslot1 = r.nextInt(max_timeslot);
                    rindex2 = r.nextInt(Exam.size());
                    rslot2 = r.nextInt(max_timeslot);
                    rindex3 = r.nextInt(Exam.size());// new exam
                    rslot3 = r.nextInt(max_timeslot);// new timeslot
                    if (Constructor.isfeasible1(rindex1, rslot1, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex1][1] = rslot1;
                    }
                    if (Constructor.isfeasible1(rindex2, rslot2, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex2][1] = rslot2;
                    }
                    if (Constructor.isfeasible1(rindex3, rslot3, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex3][1] = rslot3;
                    }
                    sneighborhood.add(timeslottemp);   
                    
                    //swap 2
                    do{
                        rindex1 = r.nextInt(Exam.size());
                        rindex2 = r.nextInt(Exam.size());
                    }while(rindex1==rindex2);
                    rslot1 = timeslottemp[rindex1][1];
                    rslot2 = timeslottemp[rindex2][1];
                    if (Constructor.isfeasible1(rindex1, rslot2, conflict_matrix, timeslottemp)) {//swap the timeslots 
                        timeslottemp[rindex1][1]=rslot2;
                    }
                    if (Constructor.isfeasible1(rindex2, rslot1, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex2][1]=rslot1;
                    }
                    sneighborhood.add(timeslottemp);
                    
                     //swap 3
                     do{
                        rindex1 = r.nextInt(Exam.size());
                        rindex2 = r.nextInt(Exam.size());
                        rindex3 = r.nextInt(Exam.size());
                    }while(rindex1==rindex2||rindex1==rindex3||rindex2==rindex3);
                    rslot1 = timeslottemp[rindex1][1];
                    rslot2 = timeslottemp[rindex2][1];
                    rslot3 = timeslottemp[rindex3][1];
                    if (Constructor.isfeasible1(rindex1, rslot2, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex1][1]=rslot2;
                    }
                    if (Constructor.isfeasible1(rindex2, rslot3, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex2][1]=rslot3;
                    }
                    if (Constructor.isfeasible1(rindex3, rslot1, conflict_matrix, timeslottemp)) {
                        timeslottemp[rindex3][1]=rslot1;
                    }
                    sneighborhood.add(timeslottemp);
                    
                    int j = 0;
                    while (sneighborhood.size() > j) {
                        penalty2 = Constructor.getPenalty(conflict_matrix, sneighborhood.get(j), student.size());
                        penalty1 = Constructor.getPenalty(conflict_matrix, bestcandidate, student.size());
                        if(!(tabulist.contains(sneighborhood.get(j))) && penalty2<penalty1){
                            bestcandidate = sneighborhood.get(j);
                        }
                            j++;
                    }
                    
                    sneighborhood.clear();
                    
                    if(Constructor.getPenalty(conflict_matrix, bestcandidate, student.size()) < Constructor.getPenalty(conflict_matrix, sbest, student.size()) ){
                        sbest = bestcandidate;//add the best candidate
                        tabulist.addLast(bestcandidate); // added into the tabu list
                        if(tabulist.size() > maxtabusize){// if the tabulist is max remove one answer
                            tabulist.removeFirst();
                        }
                    }
                    //return sbest;
                    if ((iteration+1 ) % 10 == 0) {//every 10 iterations give me back the results
                    System.out.println("iteration "+(iteration+1)+" penalty "+ Constructor.getPenalty(conflict_matrix, sbest, student.size()));
              //System.out.println(Constructor.getPenalty(conflict_matrix, sbest, student.size()));
            }
                //System.out.println("iteration "+iteration+" penalty "+ Constructor.getPenalty(conflict_matrix, sbest, student.size()));
                
//taking the result to excel
               
                sbest_array.add(bestcandidate);
                sbest_penalty[iteration-1] = Constructor.getPenalty(conflict_matrix, sbest, student.size());

                if (iteration%100 == 0) {
                    trajectory[(iteration/10)-1] = min_traject; 
                    for(int i = 0; i < iteration; i++) {
                        if (min_traject > sbest_penalty[i]) {
                            trajectory[(iteration/10)-1] = sbest_penalty[i];
                            min_traject = trajectory[(iteration/10)-1];
                        }
                    };
                }

                if (iteration == maxiteration) {
                    terminate = true;
                }
            }//end of the while
            long endtimer   = System.nanoTime();
            long totaltimer = endtimer - starttimer;
            
            Double minPenalty = 99999.99;
            for(int i = 0; i < maxiteration; i++) {
                if (minPenalty > sbest_penalty[i]) {
                    minPenalty = sbest_penalty[i];
                    smallest = sbest_array.get(i);
                }
            }
             System.out.println("==Results from Tabu Search==");
            System.out.println("Total Time : " + (double)totaltimer/1000000000 + " Seconds");
          // System.out.println("penalty initial solution: "+penalty);
            //System.out.println("test initial pena"+Constructor.getPenalty(smallest, bestSchedule, iterations));
            System.out.println("Best penalty tabu search: "+ minPenalty);
            
         
         
        } catch(Exception e) {
            System.out.println("error: " + e);
            e.printStackTrace();
        }
    }
       //test tabu search algorithm finished

     //write the solution for from the Optimizer
    public void exportSchedule(String filename) {
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\Dell\\Documents\\GitHub\\Dissertation-AI-Timetable\\SolverETP\\" + filename + ".sol");
            fileWriter.write(this.solution);
            fileWriter.close();
            System.out.println("File "+filename+" file saved successfully"); 
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
   
    }
}
