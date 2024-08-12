package mehow2k.botdiscord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import mehow2k.botdiscord.lavaplayer.GuildMusicManager;
import mehow2k.botdiscord.lavaplayer.PlayerManager;
import mehow2k.botdiscord.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Stop extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("stop")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inAudioChannel()) {
                event.reply("Musisz być na tym samym kanale co ja!").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()) {
                event.reply("Nie jestem na kanale głosowym.").queue();
                return;
            }

            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("Nie jesteś na tym samym kanale co ja!").queue();
                return;
            }

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
            trackScheduler.getQueue().clear();
            trackScheduler.getAudioPlayer().stopTrack();

            if (guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack() == null) {
                event.reply("Nie gram aktualnie niczego.").queue();
                return;
            }
            AudioTrackInfo info = guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack().getInfo();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Stopped");
            embedBuilder.setDescription("**Name:** `" + info.title + "`");
            embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
            embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
            event.replyEmbeds(embedBuilder.build()).queue();
            event.reply("Stopped").queue();
        }
    }
}
