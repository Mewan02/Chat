//Mewan Couplé      ECE PARIS

import java.util.ArrayList;
import java.util.Map;

public class Authentification {

    Map<String, String> map;
    String login = "";
    String passwd = "";
    ArrayList<String> list = new ArrayList<>();

    public Authentification(Map<String, String> m, String login, String passwd) {
        map = m;
        this.login = login;
        this.passwd = passwd;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {

        return passwd;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(String user) {
        this.list.add(user);
    }

    public boolean HandleAuthentification() {
        boolean a = false;
        String checkUser = map.get(getLogin());
        if (checkUser != null && checkUser.equals(getPasswd())) {
            a = true;
        }
        return a;
    }

}
