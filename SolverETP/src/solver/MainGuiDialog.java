
package solver;

import javax.swing.JOptionPane;

/**
 *
 * @author stefanoschatz
 */
public class MainGuiDialog {
     
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
         
             //test starts
        User user1 ;
       
        String name =JOptionPane.showInputDialog(" Hi User!!\n\n Please enter your name: ");
        String Surname =JOptionPane.showInputDialog("Enter your Surname: ");
        JOptionPane.showMessageDialog(null,"Welcome to the ETPsolver "+ name +" "+Surname);
        String message ="Please select the dataset from Toronto Benchmark "+name
                +"\n\n 1. Car-f-92 (Carleton92)"
                +"\n\n 2. Car-s-91 (Carleton91)"
                +"\n\n 3. Ear-f-83 (EarlHaig83)"
                +"\n\n 4. Hec-s-92 (EdHEC92)"
                +"\n\n 5. Kfu-s-93 (KingFahd93)"
                +"\n\n 6. Lse-f-91 (LSE91)"
                +"\n\n 7. Pur-s-93 (Pur93)"
                +"\n\n 8. Rye-s-93 (Rye92)"
                +"\n\n 9. Sta-f-83(St.Andrews83)"
                +"\n\n 10. Tre-s-92(Trent92)"
                +"\n\n 11. Uta-s-92(TorontoAS92)"
                +"\n\n 12. Ute-s-92(TorontoE92)"
                +"\n\n 13. yor-f-83(YorkMills83)"
                +"\n\n  0. To Exit ";
        String input1="";
        input1=JOptionPane.showInputDialog(message);
        switch(input1){
            case "1":
				dir_stu = dir_carf92_stu;
				dir_crs = dir_carf92_crs;
				break;
			case "2":
				dir_stu = dir_cars91_stu;
				dir_crs = dir_cars91_crs;
				break;
			case "3":
				dir_stu = dir_earf83_stu;
				dir_crs = dir_earf83_crs;
				break;
			case "4":
				dir_stu = dir_hecs92_stu;
				dir_crs = dir_hecs92_crs;
				break;
			case "5":
				dir_stu = dir_kfus93_stu;
				dir_crs = dir_kfus93_crs;
				break;
			case "6":
				dir_stu = dir_lsef91_stu;
				dir_crs = dir_lsef91_crs;
				break;
			case "7":
				dir_stu = dir_purs93_stu;
				dir_crs = dir_purs93_crs;
				break;
			case "8":
				dir_stu = dir_ryes93_stu;
				dir_crs = dir_ryes93_crs;
				break;
			case "9":
				dir_stu = dir_staf83_stu;
				dir_crs = dir_staf83_crs;
				break;
			case "10":
				dir_stu = dir_tres92_stu;
				dir_crs = dir_tres92_crs;
				break;
			case "11":
				dir_stu = dir_utas92_stu;
				dir_crs = dir_utas92_crs;
				break;
			case "12":
				dir_stu = dir_utes92_stu;
				dir_crs = dir_utes92_crs;
				break;
			case "13":
				dir_stu = dir_yorf83_stu;
				dir_crs = dir_yorf83_crs;
				break;
			case "0":
                             JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+" "+Surname);
				System.exit(0);
        }
        
        
        String message1 ="Please select the Algorithm that you would like to use "+ name
                //+"\n\n 1. Constructor"
                +"\n\n 1. Simulated Annealing"
                +"\n\n 2. Hill Climbing"
                +"\n\n 3. Tabu Search";
        String input2="";
               input2= JOptionPane.showInputDialog(message1);
       
                
        
        switch(input2){
            //Initial Solution
//             case "1":
//             long startTime = System.nanoTime();
//                Constructor.executeConstructor(dir_stu, dir_crs, 10);
//                long endTime = System.nanoTime();
//                long totalTime = endTime - startTime;
//              JOptionPane.showMessageDialog(null,"Total time : " + (double) totalTime / 100000000 + " Seconds"); 
//              JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+"\n\n"+ dir_crs+" saved successfully in the Solution file");
//                break;
                
               //Simulated Annealing Algorithm 
              case "1":
                String iteration= JOptionPane.showInputDialog("Enter the Iterations "+name+" :");
                int iterations =Integer.parseInt(iteration);
              long startTime1 = System.nanoTime();
                Optimizer.SimulatedAnnealing(dir_stu, dir_crs, 100, iterations);
                long endTime1 = System.nanoTime();
                long totalTime1 = endTime1 - startTime1;
                Constructor.executeConstructor(dir_stu, dir_crs, 45);
               JOptionPane.showMessageDialog(null,"Total time : " + (double) totalTime1 / 1000000000 + "Seconds");
              JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+"\n\n"+ dir_crs+" saved successfully in the Solution file");
                break;
                
                //Hill Climbing
               case "2":
               String iteration1= JOptionPane.showInputDialog("Enter the Iterations "+name+" :");
                int iterations1 =Integer.parseInt(iteration1);
                 long startTime2 = System.nanoTime();
                Optimizer.hillClimbing(dir_stu, dir_crs, 100, iterations1);
                //save the results;
                long endTime2 = System.nanoTime();
                long totalTime2 = endTime2 - startTime2;
                Constructor.executeConstructor(dir_stu, dir_crs, 45);
               
               JOptionPane.showMessageDialog(null,"Total time : " + (double) totalTime2 / 1000000000 + "Seconds");
              JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+"\n\n"+ dir_crs+" saved successfully in the Solution file");
                break;


                
                //Tabe Search Technique
                case"3":
                String iteration3= JOptionPane.showInputDialog("Enter the Iterations "+name+" :");
                int iterations3 =Integer.parseInt(iteration3);
                long startTime4=System.nanoTime();
                Optimizer.TabuSearch(dir_crs, dir_stu, dir_stu,iterations3);
                long endTime4=System.nanoTime();
                long totalTime4=endTime4-startTime4;
                Constructor.executeConstructor(dir_stu, dir_crs, 45);
                JOptionPane.showMessageDialog(null,"Total time : " + (double) totalTime4 / 1000000000 + "Seconds");
               JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+"\n\n"+ dir_crs+" saved successfully in the Solution file");
                        break;
            case "0":
                JOptionPane.showMessageDialog(null,"Thank you for using the Solver "+name+" "+Surname);
                System.exit(0);
            default:
                System.exit(0);
        }
         
         
    }
    
}
