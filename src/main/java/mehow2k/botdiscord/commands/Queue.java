package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
try {
    if (event.getName().equals("queue")) {
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
        event.deferReply().queue();

        if (queue.isEmpty()) {
            EmbedBuilder emptyQueueEmbed = new EmbedBuilder().setTitle("Queue").setDescription("Queue is empty");
            event.getHook().sendMessageEmbeds(emptyQueueEmbed.build()).queue();
            return;
        }

        List<MessageEmbed> embeds = new ArrayList<>();
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Queue");
        int page = 1;
        for (int i = 0; i < queue.size(); i++) {
            AudioTrackInfo info = queue.get(i).getInfo();
            embedBuilder.addField(i + 1 + ":", info.title, false);

            // Kiedy osiągnięto 25 pól, dodaj embed do listy i rozpocznij nowy
            if ((i + 1) % 25 == 0 || i == queue.size() - 1) {
                page++;
                embeds.add(embedBuilder.build());
                embedBuilder = new EmbedBuilder().setTitle("Queue - part "+page);
            }
        }
        // Wyślij wszystkie wiadomości
        for (MessageEmbed embed : embeds) {
            event.getHook().sendMessageEmbeds(embed).queue();
        }

    }
}catch (Exception e){e.printStackTrace();}
    }
}
