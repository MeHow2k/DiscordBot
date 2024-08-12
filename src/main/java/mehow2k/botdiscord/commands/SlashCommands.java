package mehow2k.botdiscord.commands;

import mehow2k.botdiscord.C;
import mehow2k.botdiscord.UI.Panel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("sum")) {
            OptionMapping number1 = event.getOption("number1");
            OptionMapping number2 = event.getOption("number2");
            int num1= number1.getAsInt();
            int num2= number2.getAsInt();
            int res=num1+num2;
            event.reply(num1+"+"+num2+" ="+res+"").queue();
        }
        if(event.getName().equals("mult")) {
            OptionMapping number1 = event.getOption("number1");
            OptionMapping number2 = event.getOption("number2");
            int num1= number1.getAsInt();
            int num2= number2.getAsInt();
            int res=num1*num2;
            event.reply(num1+"*"+num2+" ="+res+"").queue();
        }
        if(event.getName().equals("love")) {
            Random random = new Random();
            OptionMapping osoba1 = event.getOption("osoba1");
            OptionMapping osoba2 = event.getOption("osoba2");
            String num1= osoba1.getAsString();
            String num2= osoba2.getAsString();
            String res= String.valueOf(random.nextInt(100));
            event.reply("Miłość między "+ num1+" i "+num2+" to "+res+"%").queue();
        }
        if(event.getName().equals("gpt")) {
            OptionMapping prompt = event.getOption("prompt");
            String question = prompt.getAsString();
            String response = request(question);

            event.reply(response).queue();
        }
    }

    public static String request(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = C.ChatGPTapikey; // API key goes here
        String model = "gpt-3.5-turbo"; // current model of chatgpt api

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            System.out.println(body);
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // returns the extracted contents of the response.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            System.out.println("er : "+e);
            Panel.printLog("ChatGPT Error:"+e);
            return "Error";
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

}
