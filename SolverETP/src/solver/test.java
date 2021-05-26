package solver;

import java.util.*;

/**
 *
 * @author Stefanos Chatzileftheris
 */
public class test {

    static final String directory = "C:\\Users\\Dell\\Documents\\GitHub\\Dissertation-AI-Timetable\\SolverETP\\carter_datasets\\";

    public static void main(String[] args) {
        String dir_crs="";
        String dir_stu="";
        
        String dir_carf92_stu = directory + "car-f-92.stu";
        String dir_carf92_crs = directory + "car-f-92.crs";

        String dir_cars91_stu = directory + "car-s-91.stu";
        String dir_cars91_crs = directory + "car-s-91.crs";

        String dir_earf83_stu = directory + "ear-f-83.stu";
        String dir_earf83_crs = directory + "ear-f-83.crs";

        String dir_hecs92_stu = directory + "hec-s-92.stu";
        String dir_hecs92_crs = directory + "hec-s-92.crs";

        String dir_kfus93_stu = directory + "kfu-s-93.stu";
        String dir_kfus93_crs = directory + "kfu-s-93.crs";

        String dir_lsef91_stu = directory + "lse-f-91.stu";
        String dir_lsef91_crs = directory + "lse-f-91.crs";

        String dir_purs93_stu = directory + "pur-s-93.stu";
        String dir_purs93_crs = directory + "pur-s-93.crs";

        String dir_ryes93_stu = directory + "rye-s-93.stu";
        String dir_ryes93_crs = directory + "rye-s-93.crs";

        String dir_staf83_stu = directory + "sta-f-83.stu";
        String dir_staf83_crs = directory + "sta-f-83.crs";

        String dir_tres92_stu = directory + "tre-s-92.stu";
        String dir_tres92_crs = directory + "tre-s-92.crs";

        String dir_utas92_stu = directory + "uta-s-92.stu";
        String dir_utas92_crs = directory + "uta-s-92.crs";

        String dir_utes92_stu = directory + "ute-s-92.stu";
        String dir_utes92_crs = directory + "ute-s-92.crs";

        String dir_yorf83_stu = directory + "yor-f-83.stu";
        String dir_yorf83_crs = directory + "yor-f-83.crs";
        //test file
        String dir_test_stu = directory + "test.stu";
        String dir_test_crs = directory + "test.crs";

        Scanner input = new Scanner(System.in);

        int timeslot = 0;

        System.out.println("=== WELCOME TO THE TIMETABLE-SOLVER=== \n"
                + "\nCHOOSE ONE DATASET OF  TORONTO BENCHMARK:");
        System.out.println("1. Car-f-92 (Carleton91)");
        System.out.println("2. Car-s-91 (Carleton92)");
        System.out.println("3. Ear-f-83 (EarlHaig83)");
        System.out.println("4. Hec-s-92 (EdHEC92)");
        System.out.println("5. Kfu-s-93 (KingFahd93)");
        System.out.println("6. Lse-f-91 (LSE91)");
        System.out.println("7. Pur-s-93 (Pur93)");
        System.out.println("8. Rye-s-93 (Rye92)");
        System.out.println("9. Sta-f-83(St.Andrews83)");
        System.out.println("10. Tre-s-92(Trent92)");
        System.out.println("11. Uta-s-92(TorontoAS92)");
        System.out.println("12. Ute-s-92(TorontoE92)");
        System.out.println("13. yor-f-83(YorkMills83)");
        System.out.println("14. Test");
        System.out.println("0. EXIT");

        System.out.print("\nSelect a File : ");
         int data =input.nextInt();
               
                switch(data) {
			case 1:
				dir_stu = dir_carf92_stu;
				dir_crs = dir_carf92_crs;
				break;
			case 2:
				dir_stu = dir_cars91_stu;
				dir_crs = dir_cars91_crs;
				break;
			case 3:
				dir_stu = dir_earf83_stu;
				dir_crs = dir_earf83_crs;
				break;
			case 4:
				dir_stu = dir_hecs92_stu;
				dir_crs = dir_hecs92_crs;
				break;
			case 5:
				dir_stu = dir_kfus93_stu;
				dir_crs = dir_kfus93_crs;
				break;
			case 6:
				dir_stu = dir_lsef91_stu;
				dir_crs = dir_lsef91_crs;
				break;
			case 7:
				dir_stu = dir_purs93_stu;
				dir_crs = dir_purs93_crs;
				break;
			case 8:
				dir_stu = dir_ryes93_stu;
				dir_crs = dir_ryes93_crs;
				break;
			case 9:
				dir_stu = dir_staf83_stu;
				dir_crs = dir_staf83_crs;
				break;
			case 10:
				dir_stu = dir_tres92_stu;
				dir_crs = dir_tres92_crs;
				break;
			case 11:
				dir_stu = dir_utas92_stu;
				dir_crs = dir_utas92_crs;
				break;
			case 12:
				dir_stu = dir_utes92_stu;
				dir_crs = dir_utes92_crs;
				break;
			case 13:
				dir_stu = dir_yorf83_stu;
				dir_crs = dir_yorf83_crs;
				break;
			case 0:
                            System.out.println("Thank you for using the Solver");
				System.exit(0);
		}
                
         System.out.println("===Solver Technique===");       
        System.out.println("1.Constructor");
        System.out.println("2.Simulated Annealing");
        System.out.println("3.Hill Climbing");
        System.out.println("4.Genetic Algorithm");
        System.out.println("5. Tabu Search");
        System.out.print("\nSelect a Technique : ");
        int techniques = input.nextInt();
      int iteration;
        switch (techniques) {
            
            //Initial Solution
            case 1:
                long startTime = System.nanoTime();
                Constructor.executeConstructor(dir_stu, dir_crs, timeslot);
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
               System.out.println("Total time : " + (double) totalTime / 100000000 + " Seconds"); //for stopping the program running
                break;
                
               //Simulated Annealing Algorithm 
            case 2:
                System.out.print("add the iterations : ");
                iteration = input.nextInt();
                long startTime1 = System.nanoTime();
                Optimizer.SimulatedAnnealing(dir_stu, dir_crs, 1000, iteration);
                long endTime1 = System.nanoTime();
                long totalTime1 = endTime1 - startTime1;
                Constructor.executeConstructor(dir_stu, dir_crs, timeslot);
                System.out.println("Total time : " + (double) totalTime1 / 1000000000 + "seconds");
                break;
                
                //Hill Climbing
            case 3:
               System.out.print("add the iterations : ");
                iteration = input.nextInt();
                long startTime2 = System.nanoTime();
                Optimizer.hillClimbing(dir_stu, dir_crs, 100, iteration);
                //save the results;
                long endTime2 = System.nanoTime();
                long totalTime2 = endTime2 - startTime2;
                Constructor.executeConstructor(dir_stu, dir_crs, timeslot);
                System.out.println("Total time : " + (double) totalTime2 / 1000000000 + " seconds");
                break;

                //Genetic Algorithm
//            case 4:
//          
//                  System.out.print("Total iterations : ");
//                iteration = input.nextInt();
//                long startTime3 = System.nanoTime();
//                Optimizer.GeneticAlgorithm(dir_stu, dir_crs, 10, iteration);
//                 Constructor.executeConstructor(dir_stu, dir_crs, timeslot);
//                long endTime3 = System.nanoTime();
//                long totalTime3 = endTime3 - startTime3;
//                System.out.println("Total time : " + (double) totalTime3 / 1000000000 + " seconds");
//                break;
                
                //Tabu Search List
            case 4:
                  System.out.print("Total iterations : ");
                iteration = input.nextInt();
                long startTime4=System.nanoTime();
                Optimizer.TabuSearch(dir_crs, dir_stu, dir_stu,iteration);
                long endTime4=System.nanoTime();
                long totalTime4=endTime4-startTime4;
                Constructor.executeConstructor(dir_stu, dir_crs, timeslot);
                System.out.println("Total time :"+(double)totalTime4/1000000000+"seconds");
                break;
            case 0:
                System.out.println("Thank you for using the Solver");
                System.exit(0);
            default:
                System.exit(0);
        }
        input.close();
    }
}