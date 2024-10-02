package Utils;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/1 21:19
 */

import Config.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import Utils.ErrorType;

/**
 * @author: coke_and_ice
 * TODO  
 * 2024/10/1 21:19
 */
public class IO {
    private static final Map<ErrorType,String> errorMap;
    public static void writeToFile(String content,String filePath){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath,true))) {
            bufferedWriter.write(content);
            bufferedWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    output
    public static void output(String content){
        writeToFile(content,Config.fileOutput);
    }
    public static void output(ErrorType errorType){
        writeToFile(errorMap.get(errorType),Config.fileOutput);
    }

    //    clear
    public static void clear_file(String filePath){
        File file=new File(filePath);
        if(file.exists()){
            file.delete();
        }
        writeToFile("",filePath);
    }

    static{
        Map<ErrorType,String> temp=new HashMap<>();
        temp.put(ErrorType.Args_Count,"Illegal argument count\n");
        temp.put(ErrorType.User_Id,"Illegal user id\n");
        temp.put(ErrorType.User_Not_Exist,"User does not exist\n");
        temp.put(ErrorType.Permission_Denied,"Permission denied\n");
        temp.put(ErrorType.No_Online,"No one is online\n");
        errorMap=Collections.unmodifiableMap(temp);
    }
}
