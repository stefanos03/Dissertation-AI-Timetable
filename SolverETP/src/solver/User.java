package solver;

import javax.swing.JOptionPane;


/**
 *
 * @author stefanoschatz
 */
public class User {
    private int id;
    private String name;
    private static int nextId;
    
     public User (String n){
         this.name=n;
         this.id= nextId;
         nextId++;
     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User name:"+name+", Id:"+id;
    }
     public static int getnextId(){
         return User.nextId;
     }
    
            
}
