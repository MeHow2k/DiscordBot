package mehow2k.botdiscord.UI;

import mehow2k.botdiscord.C;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Frame extends JFrame {

    public Frame(){
        super("Bot Discord by MeHow2k "+C.version);
        //Image icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage();
        //setIconImage(icon);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,700);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        Panel panel = new Panel();
        setContentPane(panel);
        setVisible(true);
    }
    public static void loadConfig(){
        //wczytanie ustawień z pliku ustawień
        try {
            File config = new File("config.txt");
            String absolutePath = config.getAbsolutePath();
            Scanner scanner = new Scanner(new FileInputStream(absolutePath),"UTF-8");
            // sprawdzanie czy linia tekstu istnieje
            while(scanner.hasNextLine()){
                //kazda linia pliku odpowiada za inne ustawienie
                //token
                C.TOKEN=(scanner.nextLine());
                //guild id
                try {
                    C.GuildID=Long.parseLong(scanner.nextLine());
                }catch (NumberFormatException nfe){
                    System.out.println("Guild ID is invalid. Check config!");
                    if(C.isUI) Panel.printLog("Guild ID is invalid. Check config!");
                }
                //chat gpt api key
                C.ChatGPTapikey=(scanner.nextLine());
                if (C.TOKEN.equals("") || C.GuildID.equals("")){
                    if(C.isUI) Panel.printLog("Cant load config.txt. Check that you provided all tokens."); else
                    throw new RuntimeException("Can load config.txt. Check that you provided all tokens. ");
                }
                if (C.ChatGPTapikey.equals("")) {
                    if(C.isUI) Panel.printLog("GPT API Key not defined. Check config.txt.");
                    System.out.println("GPT API Key not defined. Check config.txt");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't find file.");
            Panel.printLog("Can't find config file. Open settings to create one.");
        }
    }
    public static void main(String[] args) {
        new Frame();
        System.out.println("Bot Discord by MeHow2k "+C.version);
        Panel.printLog("Bot Discord by MeHow2k "+C.version);
        System.out.println("Executed with GUI");
        Panel.printLog("Executed with GUI");
        C.isUI=true;
        loadConfig();
    }


}
