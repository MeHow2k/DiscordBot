package mehow2k.botdiscord.commands;

import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Play extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("play")) {
            OptionMapping url = event.getOption("name");
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inAudioChannel()){
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }
            Member self= event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()){
                event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());

            } else {
                if(selfVoiceState.getChannel() != memberVoiceState.getChannel()){
                    event.reply("You need to be on the same voice channel!").queue();
                    return;
                }
            }

            PlayerManager playerManager=PlayerManager.get();
            event.reply("Playing").queue();
            //can create embed here

            playerManager.play(event.getGuild(),event.getOption("url").getAsString());


        }
    }
}
