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
                event.reply("Musisz być na kanale głosowym!").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()) {
                event.reply("Nie jestem na kanale głosowym.").queue();
                return;
            }

            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("Musisz być na tym samym kanale co ja!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
            trackScheduler.getQueue().clear();

            if (guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack() == null) {
                event.reply("Nie gram aktualnie niczego.").queue();
                return;
            }
            event.reply("Kolejka wyczyszczona").queue();

        }
    }
}
