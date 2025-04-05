package mehow2k.botdiscord.commands;

import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import mehow2k.botdiscord.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class ClearQueue extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("clear")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inAudioChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()) {
                event.reply("Im not on the voice channel.").queue();
                return;
            }

            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = null;

                guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());

            TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
            trackScheduler.getQueue().clear();

            if (guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack() == null) {
                event.reply("Currenty not playin anything.").queue();
                return;
            }
            event.reply("Queue cleared").queue();

        }
    }
}
