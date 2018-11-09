//Mewan Couplé      ECE PARIS

import java.io.*;
import java.net.Socket;
import java.util.*;


public class ClientHandler implements Runnable {

    private Socket client; //socket client

    private boolean isLogged;

    private static FileReader fileUser;
    private static FileReader fileTopic;
    private static Map<String, String> hmap = new HashMap<>();

    private FileWriter SaveUsers;
    private FileWriter SaveTopics;

    ClientHandler(Socket in) {
        client = in;
    }

    public void run() {
        try {
            Database BaseDonnees = new Database(fileUser, fileTopic, hmap); //creation de la base de donnée
            hmap = BaseDonnees.setUserMap(); //creation de la map pour les users
            Database SauvBaseDonnees = new Database(SaveUsers, SaveTopics); //database de sauvegarde
            //input output
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            oos.flush();
            DataInputStream datais = new DataInputStream(client.getInputStream());
            DataOutputStream dataos = new DataOutputStream(client.getOutputStream());

            int choix1 = datais.read();

            switch (choix1) {
                //1--Login d'un user et verifiaction------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                case 1:
                    User firstUser = (User) ois.readObject(); //on recoit le nouveau client
                    Authentification authentificationobject = new Authentification(hmap, firstUser.getLogin(), firstUser.getPassword()); //on cree un authentification avec ce nouveau client
                    //si les logins et les password correspondent && si il est bien dans la liste
                    if (authentificationobject.HandleAuthentification() == true && Arrays.asList(authentificationobject.getList()).contains(firstUser.getLogin()) == false) {
                        authentificationobject.setList(firstUser.getLogin());
                        dataos.writeBoolean(true);
                        this.isLogged = true;
                    } else {
                        dataos.writeBoolean(false);
                    }

                    break;
                //2--Creation d'un nouvel user------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                case 2:
                    User Newuser = (User) ois.readObject(); //lecture objet envoyer par le client, ici un nouvel user
                    String tosave = Newuser.getLogin() + "," + Newuser.getPassword();
                    SauvBaseDonnees.SaveUserFile(tosave); //sauvegarder le nouveau client dans la base de donné
                    dataos.writeUTF("L'utilisateur '" + Newuser.getLogin() + "' avec le mot de passe '" + Newuser.getPassword() + "' a été ajouté"); //envoyer ca a afficher
                    break;

            }

            int choix2 = datais.read();
            switch (choix2) {
                //3--Choix du topic------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                case 0:
                    String getTopic = BaseDonnees.LoadTopicName();

                    String[] table = getTopic.split(",");
                    dataos.writeUTF(BaseDonnees.LoadTopicName());
                    // Topic Topicchoosed = (Topic) ois.readObject();
                    break;
                //4--Ajout d'un topic------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                case 1: //    case 1:
                    Topic NewTopic = (Topic) ois.readObject();
                    String tosave = NewTopic.getTopicName();
                    SauvBaseDonnees.AddTopic(tosave);
                    dataos.writeUTF("Le topic " + tosave + " a été ajouté");
                    break;

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}