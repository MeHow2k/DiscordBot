package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Queue extends ListenerAdapter {
    public static List<AudioTrack> queue;

    public static List<AudioTrack> getQueue() {
        return queue;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("queue")) {
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
            queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Aktualna kolejka");
            if(queue.isEmpty()) {
                embedBuilder.setDescription("Kolejka pusta");
            }
            for(int i = 0; i < queue.size(); i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                embedBuilder.addField(i+1 + ":", info.title, false);
            }
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
