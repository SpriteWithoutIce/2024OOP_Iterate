package User;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/2 20:50
 */

import Course.Course;

import java.util.*;

/**
 * @author: coke_and_ice
 * TODO  
 * 2024/10/2 20:50
 */
public class Teacher extends User{
    private List<Course> courseList=new ArrayList<>();
    private List<Student> studentList=new ArrayList<>();

    public Teacher(String user_id, String name, String code, String type) {
        super(user_id, name, code, type);
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public void cancelCourse(Course course){
        courseList.remove(course);
    }
}
