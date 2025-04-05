package mehow2k.botdiscord.commands;

import mehow2k.botdiscord.C;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShowVersion extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("showversion")) {
            event.reply("Bot version: "+ C.version +" date: "+C.versionDate+"\nGitHub: https://github.com/MeHow2k/DiscordBot").queue();
        }
    }
}
