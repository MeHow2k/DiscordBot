package mehow2k.botdiscord;

import mehow2k.botdiscord.listeners.EventListenersLoader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class startBot {
    public static void main(String[] args) {
        System.out.println("Bot Discord "+C.version );
        System.out.println("Executed without GUI");
        loadConfig();
        //utworzenie obiektu JDA z tablica przywilejow
        JDABuilder jdaBuilder=JDABuilder.create(C.TOKEN, Arrays.asList(C.gatewayIntents));
        JDA jda = jdaBuilder.build();

//dodanie nasluchiwania wydarzeń discorda
        EventListenersLoader ell= new EventListenersLoader(jda);
        ell.load();

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
                }
                //chat gpt api key
                C.ChatGPTapikey=(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't find config file. Creating...");
            try {
                //otwarcie pliku ustawień
                File config = new File("config.txt");
                FileWriter out = new FileWriter(config);
                //wpisanie aktualnych ustawień do pliku ustawień
                out.write("Write your Discord Token here" + "\n" + "Write your server ID here"+"\n"+"Write your ChatGPTAPI key here");
                out.close();
            } catch (IOException ee) {
                System.out.println("Can't create config.txt file: "+ee.toString());
            }
            System.out.println("Config file created, write your Discord Token and Server ID.");
            System.exit(0);
        }
    }
}
