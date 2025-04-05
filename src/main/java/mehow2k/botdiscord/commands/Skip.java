package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Skip extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("skip")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inAudioChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()) {
                event.reply("Im not on the voice channel").queue();
                return;
            }

            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You need to be on the same voice channel!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            guildMusicManager.getTrackScheduler().getAudioPlayer().stopTrack();
            if (guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack() == null) {
                event.reply("Currently the song is not playing.").queue();
                return;
            }
            AudioTrackInfo info = guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack().getInfo();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Skipped. Currently playing:");
            embedBuilder.setDescription("**Name:** `" + info.title + "`");
            embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
            embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
