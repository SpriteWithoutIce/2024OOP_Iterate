/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/1 19:51
 */

import Config.Config;
import Parse.Parse;
import Utils.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * @author: coke_and_ice
 * TODO  
 * 2024/10/1 19:51
 */
public class Test {
    public static void main(String[] args) {
        IO.clear_file(Config.fileOutput);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Config.fileInput))) {
            String line;
            List<String> command=new ArrayList<>();
            while ((line = bufferedReader.readLine())  != null) {
                command=parseCommand(line);
                if (line.trim().isEmpty()) {
                    continue;
                }
//                IO.output(line+"\n");
                Parse.getInstance().Parse_main(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseCommand(String input) {
        String[] parts = input.trim().split("\\s+");
        return new ArrayList<>(Arrays.asList(parts));
    }
}
