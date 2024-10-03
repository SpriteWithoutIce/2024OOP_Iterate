package Course;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/2 20:10
 */

import User.User;
import User.Teacher;
import User.Student;
import User.UserController;
import Utils.ErrorType;
import Utils.IO;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.regex.*;

/**
 * @author: coke_and_ice
 * TODO
 * 2024/10/2 20:10
 */
public class CourseController {
    private List<Course> courseList = new ArrayList<>();
    private Integer index = 0;
    private static final CourseController courseController = new CourseController();

    public static CourseController getCourseController() {
        return courseController;
    }

    public void createCourse(List<String> command) {
        if (command.size() != 5) {
            IO.output(ErrorType.Args_Count);
            return;
        }
        if (UserController.getUserController().userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
        User user = UserController.getUserController().userOnline.values().iterator().next();
        if (!user.getType().equals("Teacher")) {
            IO.output(ErrorType.Permission_Denied);
            return;
        }
//        获得当前登陆人作为teacher的对象
        Teacher teacher = UserController.getUserController().teachers.get(user.getUser_id());
//        课程数
        if (teacher.getCourseList().size() >= 10) {
            IO.output("Course count reaches limit\n");
            return;
        }
//        课程名称
        String name = command.get(1);
        String time = command.get(2);
        String credit = command.get(3);
        String hour = command.get(4);

        String re = "^[A-Za-z][A-Za-z0-9_-]{0,19}$";
        if (!name.matches(re)) {
            IO.output("Illegal course name\n");
            return;
        }

        for (Course course : teacher.getCourseList()) {
            if (course.getName().equals(name)) {
                IO.output("Course name exists\n");
                return;
            }
        }
//        课程时间
        re = "^([1-7])_([1-9]|1[0-4])-([1-9]|1[0-4])$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            IO.output("Illegal course time\n");
            return;
        }
        int day = Integer.parseInt(matcher.group(1));
        int start = Integer.parseInt(matcher.group(2));
        int end = Integer.parseInt(matcher.group(3));
        if (start > end) {
            IO.output("Illegal course time\n");
            return;
        }
        for (Course course : teacher.getCourseList()) {
            if (course.getDay() == day) {
                if (course.getStart() <= start && course.getEnd() >= start || course.getStart() <= end && course.getEnd() >= end) {
                    IO.output("Course time conflicts\n");
                    return;
                }
            }
        }
//        学分
        String re1 = "^\\d+$";
        String re2 = "^\\d*\\.\\d+$";
        if (!credit.matches(re1) && !credit.matches(re2)) {
            IO.output("Illegal course credit\n");
            return;
        }
        double credits = Double.parseDouble(credit);
        if (credits <= 0 || credits > 12) {
            IO.output("Illegal course credit\n");
            return;
        }
//        学时
        if (!hour.matches(re1)) {
            IO.output("Illegal course period\n");
            return;
        }
        int hours = Integer.parseInt(hour);
        if (hours <= 0 || hours > 1280) {
            IO.output("Illegal course period\n");
            return;
        }
//        编号
        int number = ++index;
        Course course = new Course(number, teacher, name, day, start, end, credits, hours);
        courseList.add(course);
        teacher.addCourse(course);
        IO.output("Create course success (courseId: C-" + number + ")\n");
    }

    public void listCourse(List<String> command) {
        if (command.size() != 1 && command.size() != 2) {
            IO.output(ErrorType.Args_Count);
            return;
        }
        if (UserController.getUserController().userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
        User user = UserController.getUserController().userOnline.values().iterator().next();
        if (command.size() == 1) {
            if (user.getType().equals("Teacher")) {
                Teacher teacher = UserController.getUserController().teachers.get(user.getUser_id());
                if (teacher.getCourseList().isEmpty()) {
                    IO.output("Course does not exist\n");
                    return;
                }
                teacher.getCourseList().sort((c1, c2) -> Integer.compare(c1.getNumber(), c2.getNumber()));
                for (Course course : teacher.getCourseList()) {
                    IO.output(course.toString());
                }
            } else {
                if (courseList.isEmpty()) {
                    IO.output("Course does not exist\n");
                    return;
                }
                courseList.sort(new CourseCompare());
                for (Course course : courseList) {
                    IO.output(course.getTeacher().getName()+" "+course.toString());
                }
            }
        } else {
            if (!user.getType().equals("Administrator")) {
                IO.output(ErrorType.Permission_Denied);
                return;
            }
            String user_id = command.get(1);
            if (!UserController.user_id_judgement(user_id)) {
                return;
            }
            if (!UserController.getUserController().userRegist.containsKey(user_id)) {
                IO.output("User does not exist\n");
                return;
            }
            if (!UserController.getUserController().userRegist.get(user_id).getType().equals("Teacher")) {
                IO.output("User id does not belong to a Teacher\n");
                return;
            }
            Teacher teacher = UserController.getUserController().teachers.get(user_id);
            if (teacher.getCourseList().isEmpty()) {
                IO.output("Course does not exist\n");
                return;
            }
            teacher.getCourseList().sort((c1, c2) -> Integer.compare(c1.getNumber(), c2.getNumber()));
            for (Course course : teacher.getCourseList()) {
                IO.output(teacher.getName()+" "+course.toString());
            }
        }
        IO.output("List course success\n");
    }

    public void selectCourse(List<String> command) {
        if (command.size() != 2) {
            IO.output(ErrorType.Args_Count);
            return;
        }
        if (UserController.getUserController().userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
        User user = UserController.getUserController().userOnline.values().iterator().next();
        if (!user.getType().equals("Student")) {
            IO.output(ErrorType.Permission_Denied);
            return;
        }
        String re = "^C-(\\d+)$";
        if (!command.get(1).matches(re)) {
            IO.output("Illegal course id\n");
            return;
        }
        Pattern pattern = Pattern.compile("C-(\\d+)");
        Matcher matcher = pattern.matcher(command.get(1));
        if(!matcher.find()){
            IO.output("Illegal course id\n");
            return;
        }
        int number = Integer.parseInt(matcher.group(1));
        if(number>index || number<=0){
            IO.output("Course does not exist\n");
            return;
        }
        Course select=null;
        for(Course course:courseList){
            if(course.getNumber()==number){
                if(!course.isValid()){
                    IO.output("Course does not exist\n");
                    return;
                }
                else{
                    select=course;
                    break;
                }
            }
        }
        int select_day=select.getDay();
        int select_start=select.getStart();
        int select_end=select.getEnd();

        Student student=UserController.getUserController().students.get(user.getUser_id());

        for (Course course : student.getSelectedCourse()) {
            if (course.getDay() == select_day) {
                if (course.getStart() <= select_start && course.getEnd() >= select_start || course.getStart() <= select_end && course.getEnd() >= select_end) {
                    IO.output("Course time conflicts\n");
                    return;
                }
            }
        }
        student.addCourse(select);
        select.addStudent(student);
        IO.output("Select course success (courseId: C-"+select.getNumber()+")\n");
    }

    public void cancelCourse(List<String> command){
        if(command.size()!=2){
            IO.output(ErrorType.Args_Count);
            return;
        }
        if (UserController.getUserController().userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
//        课程号不合法
        String re = "^C-(\\d+)$";
        if (!command.get(1).matches(re)) {
            IO.output("Illegal course id\n");
            return;
        }
//        课程号不存在
        Pattern pattern = Pattern.compile("C-(\\d+)$");
        Matcher matcher = pattern.matcher(command.get(1));
        if(!matcher.find()){
            IO.output("Illegal course id\n");
            return;
        }
        int number = Integer.parseInt(matcher.group(1));
        if(number>index || number<=0){
            IO.output("Course does not exist\n");
            return;
        }
        User user = UserController.getUserController().userOnline.values().iterator().next();
        if (user.getType().equals("Student")) {
            Student student=UserController.getUserController().students.get(user.getUser_id());
            for(Course course:student.getSelectedCourse()){
                if(course.getNumber()==number){
                    student.cancelCourse(course);
                    IO.output("Cancel course success (courseId: C-"+number+")\n");
                    return;
                }
            }
            IO.output("Course does not exist\n");
        }
        else if (user.getType().equals("Teacher")) {
            Teacher teacher=UserController.getUserController().teachers.get(user.getUser_id());
            for(Course course:teacher.getCourseList()){
                if(course.getNumber()==number){
                    teacher.cancelCourse(course);
                    courseList.remove(course);
                    IO.output("Cancel course success (courseId: C-"+number+")\n");
                    return;
                }
            }
            IO.output("Course does not exist\n");
        }
        else{
            for(Course course:courseList){
                if(course.getNumber()==number){
                    Teacher teacher=course.getTeacher();
                    teacher.cancelCourse(course);
                    for(Student student:course.getStudentList()){
                        student.cancelCourse(course);
                    }
                    courseList.remove(course);
                    IO.output("Cancel course success (courseId: C-"+number+")\n");
                    return;
                }
            }
        }
    }
}

class CourseCompare implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        int result = o1.getTeacher().getName().compareTo(o2.getTeacher().getName());
        if (result == 0) {
            return o1.getNumber() - o2.getNumber();
        }
        return result;
    }
}