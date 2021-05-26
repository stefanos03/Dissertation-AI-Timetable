
package solver;
/**
 *
 * @author Stefanos Chatzileftheris
 */
public class Students {
    private int studentsId;
    private int examId;

    public Students(int studentsId, int examId) {
        this.studentsId = studentsId;
        this.examId = examId;
    }

    public int getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(int studentsId) {
        this.studentsId = studentsId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
   
}
