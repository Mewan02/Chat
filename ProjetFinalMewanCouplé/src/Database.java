//Mewan Couplé      ECE PARIS

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {

    FileReader listUsers;
    FileReader listTopics;

    Map<String, String> hmap;
    FileWriter saveUser;
    FileWriter saveTopic;

    public Database(FileReader listUsers, FileReader listTopics, Map<String, String> map) throws IOException {

        this.listUsers = new FileReader("Users.txt");
        this.listTopics = new FileReader("Topic.txt");
        this.hmap = new HashMap<>();
    }

    public Database(FileWriter saveUser, FileWriter saveTopic) throws IOException {
        this.saveUser = new FileWriter("Users.txt", true);
        this.saveTopic = new FileWriter("Topic.txt", true);
    }


    public Map setUserMap() throws IOException {
        BufferedReader buffer = new BufferedReader(listUsers);
        String line = buffer.readLine();
        while (line != null) {
            String[] table = line.split(",");
            hmap.put(table[0], table[1]);
            line = buffer.readLine();
        }
        buffer.close();
        this.listUsers.close();
        return hmap;
    }

    public String LoadTopicName() throws IOException {
        BufferedReader buffer = new BufferedReader(listTopics);
        String line = buffer.readLine();
        String toReturn = "";
        while (line != null) {
            toReturn += line;
            line = buffer.readLine();
        }
        buffer.close();
        listTopics.close();
        return toReturn;
    }

    public void SaveUserFile(String textline) throws IOException {
        this.saveUser.write(textline);
        this.saveUser.write("\r\n");
        this.saveUser.close();
    }

    public void AddTopic(String textline) throws IOException {

        this.saveTopic.write(textline);
        this.saveTopic.write(",");
        this.saveTopic.write("\n");
        this.saveTopic.close();
    }

}
