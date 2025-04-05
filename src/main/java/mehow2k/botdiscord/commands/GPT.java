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


public class GPT extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

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
        String model = "gpt-4o-mini"; // current model of chatgpt api

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            System.out.println("Rq body: "+body);
            //send request
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
            System.out.println("Error: "+e);
            Panel.printLog("ChatGPT Error: "+e);
            return "Error communicating with OpenAI API.";

        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

}
