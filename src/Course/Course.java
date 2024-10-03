package Course;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/2 20:09
 */

import User.Student;
import User.Teacher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: coke_and_ice
 * TODO
 * 2024/10/2 20:09
 */
public class Course {
    private String name;
    private Integer day;
    private Integer start;
    private Integer end;
    private double credit;
    private int hour;
    private int number;
    private Teacher teacher;
    private boolean valid;
    private List<Student> studentList=new ArrayList<>();

    public Course(int number, Teacher teacher, String name, Integer day, Integer start, Integer end, double credit, int hour) {
        this.number = number;
        this.teacher = teacher;
        this.name = name;
        this.day = day;
        this.start = start;
        this.end = end;
        this.credit = credit;
        this.hour = hour;
        this.valid=true;
    }

    public void addStudent(Student student){
        studentList.add(student);
    }

    public String toString(){
        return "C-"+number+" "+name+" "+day+"_"+start+"-"+end+" "+credit+" "+hour+"\n";
    }

    public String getName() {
        return name;
    }


    public double getCredit() {
        return credit;
    }

    public int getHour() {
        return hour;
    }

    public int getNumber() {
        return number;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public boolean isValid() {
        return valid;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}



