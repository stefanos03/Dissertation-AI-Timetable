package solver;

import java.util.*;
import java.io.*;
import java.util.Map;
import static java.util.Map.*;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class ReaderFile {
	Set<String> exam;
        // Carter datasets: instance name, timeslots
	final Map<String, Integer> carterDatasetsMap = Map.ofEntries(entry("car-s-91", 35), entry("car-f-92", 32),
			entry("ear-f-83", 24), entry("hec-s-92", 18), entry("kfu-s-93", 20), entry("lse-f-91", 18),
			entry("pur-s-93", 42), entry("rye-s-93", 23), entry("sta-f-83", 35), entry("tre-s-92", 23),
			entry("uta-s-92", 35), entry("ute-s-92", 10), entry("yor-f-83", 21));
        
	public ReaderFile(String fileDirectory) {
		try {
			FileReader fileReader = new FileReader(fileDirectory);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			readExam(bufferedReader);
		} catch(Exception e) {}
	}
	
	public Set<String> getExam() {
		return this.exam;
	}
	
	public void readExam(BufferedReader bufferedReader) {
		exam = new HashSet<String>();
		String courseLine = null;
		try {
			while((courseLine = bufferedReader.readLine()) != null) {
				String[] array = courseLine.split(" ");
				exam.add(array[0]);
			}			
		} catch(IOException e) {
			System.out.println("Error e");
		}
	}
	
	public void printSet() {
		Set<String> sortedCourse = new TreeSet<String>(exam); 
        System.out.println(sortedCourse);
	}
	
	public int getSize() {
		return exam.size();
	}
	
}
