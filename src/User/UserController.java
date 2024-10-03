package User;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/1 21:47
 */

import Utils.IO;

import java.util.*;

import Utils.*;

import static java.lang.System.exit;

/**
 * @author: coke_and_ice
 * TODO
 * 2024/10/1 21:47
 */
public class UserController {
    public TreeMap<String, User> userOnline = new TreeMap<String, User>();
    public TreeMap<String, User> userRegist = new TreeMap<String, User>();
    private static final UserController userController = new UserController();

    public TreeMap<String, Teacher> teachers = new TreeMap<String, Teacher>();
    public TreeMap<String, Student> students = new TreeMap<String, Student>();

    public static UserController getUserController() {
        return userController;
    }

    public void Quit(List<String> command){
        if (command.size() != 1) {
            IO.output("Illegal argument count\n");
            return;
        }
//            TODO:强制用户下线
        NavigableMap<String, User> descendingMap = userOnline.descendingMap();
        for(User user : descendingMap.values()){
            IO.output(user.getUser_id()+" Bye~\n");
        }
        IO.output("----- Good Bye! -----\n");
        exit(0);
    }

    public void User_register(List<String> command) {
//        参数数量不合法
        if (command.size() != 6) {
            IO.output(ErrorType.Args_Count);
            return;
        }
        String user_id = command.get(1);
        String name = command.get(2);
        String code = command.get(3);
        String recode = command.get(4);
        String type = command.get(5);
//        学工号
        if (!user_id_judgement(user_id)) {
            return;
        }
//        已注册判断
        if (userRegist.containsKey(user_id)) {
            IO.output("User exists\n");
            return;
        }

        String re = null;
//        姓名
        re = "[A-Za-z][A-Za-z_]{3,15}$";
        if (!name.matches(re)) {
            IO.output("Illegal user name\n");
            return;
        }
//        密码
        re = "^(?=[A-Za-z0-9@_%$]{6,16}$)(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@_%$]).*$";
        if (!code.matches(re)) {
            IO.output("Illegal password\n");
            return;
        }
//        密码=确认密码
        if (!code.equals(recode)) {
            IO.output("Passwords do not match\n");
            return;
        }
        if (!type.equals("Administrator") && !type.equals("Teacher") && !type.equals("Student")) {
            IO.output("Illegal identity\n");
            return;
        }
//        新建User，并放入Register列表
        User regist = new User(user_id, name, code, type);
        userRegist.put(user_id, regist);
        if(type.equals("Teacher")){
            Teacher teacher=new Teacher(user_id, name, code, type);
            teachers.put(user_id,teacher);
        }
        else if(type.equals("Student")){
            Student student=new Student(user_id, name, code, type);
            students.put(user_id,student);
        }

        IO.output("Register success\n");
    }

    public void User_login(List<String> command) {
        if (command.size() < 3) {
            IO.output(ErrorType.Args_Count);
            return;
        }
        String user_id = command.get(1);
        String code = command.get(2);
//        学工号
        if (!user_id_judgement(user_id)) {
            return;
        }
//        用户未注册
        if (!userRegist.containsKey(user_id)) {
            IO.output(ErrorType.User_Not_Exist);
            return;
        }
//        用户已登录
        if (userOnline.containsKey(user_id)) {
            IO.output(user_id + " is online\n");
            return;
        }
        User user = userRegist.get(user_id);
        if (!user.getCode().equals(code)) {
            IO.output("Wrong password\n");
            return;
        }
        userOnline.put(user_id, user);
        IO.output("Welcome to ACP, " + user_id + "\n");
        return;
    }

    public void User_logout(List<String> command) {
//        参数不合法
        if (command.size() != 1 && command.size() != 2) {
            IO.output(ErrorType.Args_Count);
            return;
        }
//        无用户在线
        if (userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
        if (command.size() == 1) {
            User user = userOnline.values().iterator().next();
            userOnline.remove(user.getUser_id());
            IO.output(user.getUser_id() + " Bye~\n");
        } else {
            String user_id = command.get(1);
            User user = userOnline.values().iterator().next();
            if (!user.getType().equals("Administrator")) {
                IO.output(ErrorType.Permission_Denied);
                return;
            }
            if (!user_id_judgement(user_id)) {
                return;
            }
            if (!userRegist.containsKey(user_id)) {
                IO.output(ErrorType.User_Not_Exist);
                return;
            }
            if(!userOnline.containsKey(user_id)){
                IO.output(user_id+" is not online\n");
                return;
            }
            userOnline.remove(user_id);
            IO.output(user_id + " Bye~\n");
        }
    }

    public void User_printInfo(List<String> command){
        if(command.size()!=1 && command.size()!=2){
            IO.output(ErrorType.Args_Count);
            return;
        }
        if (userOnline.isEmpty()) {
            IO.output(ErrorType.No_Online);
            return;
        }
        if(command.size()==2){
            String user_id = command.get(1);
            User user = userOnline.values().iterator().next();

//            权限
            if (!Objects.equals(user.getType(), "Administrator")) {
                IO.output(ErrorType.Permission_Denied);
                return;
            }
//            学工号不合法
            if (!user_id_judgement(user_id)) {
                return;
            }
//            用户不存在
            if(!userRegist.containsKey(user_id)){
                IO.output("User does not exist\n");
                return;
            }
            User user_print=userRegist.get(user_id);
            IO.output(user_print.toString());
        }
        else{
            User user = userOnline.values().iterator().next();
            IO.output(user.toString());
        }
    }

    public static boolean user_id_judgement(String user_id) {
        String re1 = "^AD(0[0-9][1-9]|[1-9][0-9][0-9])";
        String re2 = "^(0[0-9][0-9][0-9][1-9]|[1-9][0-9][0-9][0-9][0-9])";
        String re3 = "^(2[1-4]|19)(0[1-9]|[1-3][0-9]|4[0-3])([1-6])(0[0-9][1-9]|[1-9][0-9][0-9])$";
        String re4 = "^(SY|ZY)(2[1-4])(0[1-9]|[1-3][0-9]|4[0-3])([1-6])(0[1-9]|[1-9][0-9])$";
        String re5 = "^BY(2[1-4]|19)(0[1-9]|[1-3][0-9]|4[0-3])([1-6])(0[1-9]|[1-9][0-9])$";
        if (!user_id.matches(re1) && !user_id.matches(re2) && !user_id.matches(re3) && !user_id.matches(re4) && !user_id.matches(re5)) {
            IO.output(ErrorType.User_Id);
            return false;
        }
        return true;
    }

}
