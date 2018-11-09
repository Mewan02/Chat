//Mewan Couplé      ECE PARIS

import java.io.Serializable;


public class Topic implements Serializable {

    private String name;

    public Topic(String topicName) {
        this.name = topicName;
    }

    public String getTopicName() {
        return name;
    }

}
