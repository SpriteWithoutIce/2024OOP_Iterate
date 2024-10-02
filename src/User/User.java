package User;/**
 * @Description:
 * @author Coke_And_Ice
 * @date 2024/10/1 19:53
 */

/**
 * @author: coke_and_ice
 * TODO  
 * 2024/10/1 19:53
 */
public class User {
    private String user_id;
    private String name;
    private String code;
    private String type;

    public User(String user_id,String name,String code,String type){
        this.user_id=user_id;
        this.name=name;
        this.code=code;
        this.type=type;
    }
    public String toString(){
        return "User id: "+user_id+"\n"+"Name: "+name+"\n"+"Type: "+type+"\n"+"Print information success\n";
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
