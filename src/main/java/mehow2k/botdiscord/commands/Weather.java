package mehow2k.botdiscord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.JSONObject;

import static mehow2k.botdiscord.WeatherForecast.WeatherData.*;

public class Weather extends ListenerAdapter {
        @Override
        public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
            if (event.getName().equals("weather")) {
                OptionMapping city = event.getOption("miejscowosc");
            try {

                // Get location data
                JSONObject cityLocationData = (JSONObject) getLocationData(city.getAsString());

                    double latitude = (double) cityLocationData.get("latitude");
                    double longitude = (double) cityLocationData.get("longitude");
                    String latitudeText="";
                    String longitudeText="";
                    if(latitude>=0) latitudeText = String.valueOf(latitude)+"N";
                    else latitudeText = String.valueOf(latitude*-1)+"S";

                    if(longitude>=0) longitudeText = String.valueOf(longitude)+"E";
                    else longitudeText = String.valueOf(longitude*-1)+"W";

                String[] data = getWeatherData(latitude, longitude);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Weather for " + city.getAsString() +" ("+ latitudeText+" "+longitudeText+")");
                embedBuilder.addField("Time:",data[0], false);
                embedBuilder.addField("Weather:",data[1], false);
                embedBuilder.addField("Temperature: ",data[2]+ " °C", false);
                embedBuilder.addField("Humidity: ",data[3]+" %", false);
                embedBuilder.addField("Wind:",data[4], false);
                embedBuilder.addField("Precipitation probability: ",data[5]+" %", false);
                embedBuilder.addField("Precipitation:",data[6]+" mm", false);
                embedBuilder.addField("Cloud cover:",data[7]+ " %", false);
                embedBuilder.addField("Pressure:",data[8]+ " hPa", false);
                event.replyEmbeds(embedBuilder.build()).queue();
            }catch (NullPointerException npe){event.reply("Could not find that city.").queue();}
        }
    }
}
