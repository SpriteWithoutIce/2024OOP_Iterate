package User;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/2 20:51
 */

import Course.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: coke_and_ice
 * TODO  
 * 2024/10/2 20:51
 */
public class Student extends User{
    private List<Course> selectedCourse=new ArrayList<>();
    public Student(String user_id, String name, String code, String type) {
        super(user_id, name, code, type);
    }

    public void addCourse(Course course){
        selectedCourse.add(course);
    }

    public void cancelCourse(Course course){
        selectedCourse.remove(course);
    }

    public List<Course> getSelectedCourse() {
        return selectedCourse;
    }
}
