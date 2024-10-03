package Parse;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/1 21:28
 */

import Course.CourseController;
import User.UserController;
import Utils.IO;

import java.util.*;

import static java.lang.System.exit;

/**
 * @author: coke_and_ice
 * TODO
 * 2024/10/1 21:28
 */
public class Parse {
    //    Command Parsing
    private static Parse parse = new Parse();

    public Parse() {
    }

    public static Parse getInstance() {
        return parse;
    }

    private static final Set<String> commandSet;

    static {
        Set<String> temp = new HashSet<>();
        temp.add("quit");
        temp.add("register");
        temp.add("login");
        temp.add("logout");
        temp.add("printInfo");
        temp.add("createCourse");
        temp.add("selectCourse");
        temp.add("listCourse");
        temp.add("cancelCourse");

        temp.add("switch");
        temp.add("inputCourseBatch");
        temp.add("outputCourseBatch");
        temp.add("listStudent");
        temp.add("removeStudent");
        temp.add("listCourseSchedule");
        temp.add("uploadCourseSchedule");
        temp.add("openFile");

        commandSet = Collections.unmodifiableSet(temp);
    }

    public void Parse_main(List<String> command) {
//        1.1存在性分析
        if (!commandSet.contains(command.get(0))) {
            IO.output("Command '" + command.get(0) + "' not found\n");
            return;
        }
//        1.3
        switch (command.get(0)) {
            case "quit" -> UserController.getUserController().Quit(command);
            case "register" -> UserController.getUserController().User_register(command);
            case "login" -> UserController.getUserController().User_login(command);
            case "logout" -> UserController.getUserController().User_logout(command);
            case "printInfo" -> UserController.getUserController().User_printInfo(command);
            case "createCourse" -> CourseController.getCourseController().createCourse(command);
            case "listCourse" -> CourseController.getCourseController().listCourse(command);
            case "selectCourse" -> CourseController.getCourseController().selectCourse(command);
            case "cancelCourse" -> CourseController.getCourseController().cancelCourse(command);
        }
    }
}
