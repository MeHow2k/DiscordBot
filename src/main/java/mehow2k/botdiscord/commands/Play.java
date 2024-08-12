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
                event.reply("Musisz być na tym samym kanale co ja!").queue();
                return;
            }
            Member self= event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()){
                event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());

            } else {
                if(selfVoiceState.getChannel() != memberVoiceState.getChannel()){
                    event.reply("Musisz być na tym samym kanale co ja!").queue();
                    return;
                }
            }

            PlayerManager playerManager=PlayerManager.get();
            event.reply("Playing").queue();

//            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
//            AudioTrackInfo info = guildMusicManager.getTrackScheduler().getAudioPlayer().getPlayingTrack().getInfo();
//            EmbedBuilder embedBuilder = new EmbedBuilder();
//            embedBuilder.setTitle("Gram aktualnie:");
//            embedBuilder.setDescription("**Name:** `" + info.title + "`");
//            embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
//            embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
//            event.replyEmbeds(embedBuilder.build()).queue();

            playerManager.play(event.getGuild(),event.getOption("url").getAsString());


        }
    }
}
