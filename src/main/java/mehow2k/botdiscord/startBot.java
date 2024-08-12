package mehow2k.botdiscord;

import mehow2k.botdiscord.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class startBot {
    public static void main(String[] args) {
        System.out.println("Bot Dzozef "+C.version );
        System.out.println("Executed without GUI");
        loadConfig();
        //utworzenie obiektu JDA z tablica przywilejow
        JDABuilder jdaBuilder=JDABuilder.create(C.TOKEN, Arrays.asList(C.gatewayIntents));
//        JDABuilder jdaBuilder=JDABuilder.createDefault(TOKEN);
        JDA jda = jdaBuilder.build();

        //dodanie nasluchiwania wydarzeń discorda
        jda.addEventListener(new Listeners());
        jda.addEventListener(new SlashCommands());
        jda.addEventListener(new Play());
        jda.addEventListener(new Skip());
        jda.addEventListener(new Stop());
        jda.addEventListener(new Queue());
        jda.addEventListener(new ClearQueue());
        jda.addEventListener(new NowPlaying());
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
                if (C.TOKEN.equals("") || C.GuildID.equals(""))
                    throw new RuntimeException("Cant load config.txt. Check that you provided all tokens. ");
                if (C.ChatGPTapikey.equals("")) System.out.println("GPT API Key not defined. Check config.txt");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't find file. Try to create new.");
        }
    }
}
