//Mewan Couplé      ECE PARIS

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Client {


    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException {
        try {

            boolean arret = false;

            ObjectOutputStream oos;
            ObjectInputStream ois;
            DataInputStream datais;
            DataOutputStream dataos;


            //Obligé de le mettre dans la boucle car sinon on ne peut plus naviguer
            //creation du socket client
            Socket client = new Socket(InetAddress.getLocalHost(), 9999);

            //input output
            oos = new ObjectOutputStream(client.getOutputStream());
            dataos = new DataOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            datais = new DataInputStream(client.getInputStream());
            oos.flush();

            while (!arret) { //(arret != true)


                //IHM MENU
                System.out.println("   ");
                System.out.println("      MENU");
                System.out.println("   1: Connexion au chat");
                System.out.println("   2: Creation d'un identifiant et mot de passe");
                System.out.println("   3: Quitter programme");

                //variable pour le resultat du choix et blindage
                String choixstring;
                int choix1 = 0;
                while (choix1 != 1 && choix1 != 2 && choix1 != 3) {
                    choixstring = JOptionPane.showInputDialog("Entrer le choix (entre 1 et 3)");
                    choix1 = Integer.parseInt(choixstring);
                }
                //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                switch (choix1) {
                    //1--Login d'un user et verifiaction------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    case 1:
                        System.out.println(" ");
                        System.out.println("Vous avez choisi de vous connecter au chat");
                        dataos.write(1);//envoyer valeur 1 au ClientHandler

                        //demande a lutilisateur
                        String identifiant = JOptionPane.showInputDialog("Identifiant");
                        String mdp = JOptionPane.showInputDialog("Mot de passe");
                        //creation d'un user si il est dans la base
                        oos.writeObject(new User(identifiant, mdp));
                        boolean answer = datais.readBoolean();
                        if (!answer) {
                            System.out.println("Mauvais itentifiant, acces non autorisé");
                        } else {

                            System.out.println("Vous etes connecté " + identifiant);
                            System.out.println(" ");
                            boolean arret2 = false;

                            while (!arret2) { //arret2 != true

                                //IHM CHAT
                                oos.flush();
                                dataos.flush();
                                System.out.println("   ");
                                System.out.println("      CHAT");
                                System.out.println("   1: Joindre un topic");
                                System.out.println("   2: Creer un topic");
                                System.out.println("   3: Quitter le chat");

                                //variable pour le resultat du choix2 pour le chat et blindage
                                int choix2 = 0;
                                while (choix2 != 1 && choix2 != 2 && choix2 != 3) {
                                    choixstring = JOptionPane.showInputDialog("Entrer le choix (entre 1 et 3)");
                                    choix2 = Integer.parseInt(choixstring);
                                }
                                //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                                switch (choix2) {
                                    //3--Rejoindre un topic------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                    case 1:
                                        dataos.write(0); //envoyer valeur 0 au ClientHandler  //GROS PROBLEME ICI, JE NE COMPRENDS PAS

                                        System.out.println("Vous avez choisi de joindre un topic, lequel voulez vous choisir :");
                                        System.out.println("   " + datais.readUTF()); //affichage de la liste de topic

                                        //inutile ici vu que haut dessus ne fontionne pas

                                        String topicchoix = JOptionPane.showInputDialog("Nom du topic a joindre"); //demande a l'utilisateur le nom du topic a rejoindre
                                        oos.writeObject(new Topic(topicchoix)); //envoie du topic choisi
                                        oos.writeObject(new User(identifiant, mdp)); //envoie de l'identifiant et du mdp pour faire du traitement apres
                                        break;
                                    //4--Ajout d'un topic------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                    case 2:
                                        dataos.write(1);  //envoyer valeur 1 au ClientHandler                                                          //envoyer valeur 2 au ClientHandler //case 2:        dataos.write(1);//envoyer valeur 2 au ClientHandler
                                        System.out.println(" ");
                                        System.out.println("Vous avez choisi de creer un topic, entrer le nom du topic");

                                        String topicName = JOptionPane.showInputDialog("Nom du topic : ");
                                        oos.writeObject(new Topic(topicName)); //demande a l'utilisateur du nouveau topic

                                        System.out.println(" ");

                                        System.out.println(datais.readUTF()); //affichage du topic si il a bien ete pris en compte
                                        arret2 = false;
                                        break;

                                    case 3:
                                        System.out.println(" ");
                                        System.out.println("Vous avez choisi de quitter le chat");
                                        arret2 = true; //quitter le chat
                                        break;
                                }

                            }
                        }

                        break;
                    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    //2--Creation d'un nouvel user------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    case 2:
                        System.out.println(" ");
                        System.out.println("Vous avez choisi de creer un nouveau compte");
                        dataos.write(2);//envoyer valeur 2 au ClientHandler

                        String newidentifiant = JOptionPane.showInputDialog("Entrer votre nouvel identifiant");
                        String newmdp = JOptionPane.showInputDialog("Entrer votre mot de passe");

                        oos.writeObject(new User(newidentifiant, newmdp)); //envoie du nouvel user au Handle

                        System.out.println(datais.readUTF()); //reception et affichage si tou est bon

                        arret = false;

                        break;
                    case 3:
                        System.out.println(" ");
                        System.out.println("Vous avez choisi de quitter le programme");
                        System.out.println("Au revoir");
                        arret = true; //quitter le programme
                        break;

                    default:
                        break;
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }


}
