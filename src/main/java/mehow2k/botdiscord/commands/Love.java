package mehow2k.botdiscord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Random;

public class Love extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("love")) {
            Random random = new Random();
            OptionMapping osoba1 = event.getOption("osoba1");
            OptionMapping osoba2 = event.getOption("osoba2");
            String num1= osoba1.getAsString();
            String num2= osoba2.getAsString();
            String res= String.valueOf(random.nextInt(100));
            if(num1.contains("8051")|| num2.contains("8051"))
            { event.reply("Love between "+ num1+" and "+num2+" is 8051%").queue();  }
            else{
                event.reply("Love between "+ num1+" and "+num2+" is "+res+"%").queue();}
        }
    }
}
