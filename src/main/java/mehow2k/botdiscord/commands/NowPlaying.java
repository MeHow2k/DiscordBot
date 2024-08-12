package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NowPlaying extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("nowplaying")) {

            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inAudioChannel()) {
                event.reply("You need to be in a voice channel").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if (!selfVoiceState.inAudioChannel()) {
                event.reply("Nie jestem na kanale głosowym!").queue();
                return;
            }

            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("Nie jesteś na tym samym kanale co ja!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            if (guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack() == null) {
                event.reply("Nie gram niczego").queue();
                return;
            }
            AudioTrackInfo info = guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack().getInfo();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Aktualnie gram:");
            embedBuilder.setDescription("**Name:** `" + info.title + "`");
            embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
            embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
