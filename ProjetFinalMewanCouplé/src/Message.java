//Mewan Couplé      ECE PARIS

import java.io.Serializable;

public class Message implements Serializable {

    private String content = "";
    private Topic topic;

    public Message(String content, Topic topic) {
        this.content = content;
        this.topic = topic;
    }


    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

}
