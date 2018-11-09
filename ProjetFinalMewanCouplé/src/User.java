//Mewan Couplé      ECE PARIS

import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private String password;
    private String Topicname;


    public User(String login, String password) {
        this.login = login;
        this.password = password;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getTopicname() {
        return Topicname;
    }

    public void setTopicname(String topicname) {
        Topicname = topicname;
    }
}
