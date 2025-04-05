package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffle extends ListenerAdapter {
    public static List<AudioTrack> queue;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("shuffle")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inAudioChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if (!selfVoiceState.inAudioChannel()) {
                event.reply("Im not on the voice channel.").queue();
                return;
            }

            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());

            if (queue.isEmpty()) {
                event.reply("Queue is empty.").queue();
                return;
            }

            Collections.shuffle(queue);

            guildMusicManager.getTrackScheduler().getQueue().clear();
            guildMusicManager.getTrackScheduler().getQueue().addAll(queue);
            event.reply("Queue has been shuffled.").queue();
        }

    }
}
