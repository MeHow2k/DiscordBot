package mehow2k.botdiscord.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private  static PlayerManager INSTANCE;
    private Map<Long,GuildMusicManager> guildMusicManagers = new HashMap<>();
    private AudioPlayerManager audioPlayerManager= new DefaultAudioPlayerManager();
    private PlayerManager(){
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }
    public static  PlayerManager get(){
        if(INSTANCE==null){
            INSTANCE= new PlayerManager();
        }
        return INSTANCE;
    }
    public  GuildMusicManager getGuildMusicManager(Guild guild){
        return  guildMusicManagers.computeIfAbsent(guild.getIdLong(),(guildId)->{
            GuildMusicManager guildMusicManager=new GuildMusicManager(audioPlayerManager,guild);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getAudioForwarder());
            return guildMusicManager;
        });
    }

    public void play(Guild guild, String trackURL){
        GuildMusicManager guildMusicManager= getGuildMusicManager(guild);
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                guildMusicManager.getTrackScheduler().queue(track);
                System.out.println("track loaded");
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    guildMusicManager.getTrackScheduler().queue(track);
                }
                System.out.println("playlist loaded");
            }

            @Override
            public void noMatches() {
                System.out.println("noMaches");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                e.printStackTrace();
            }
        });
    }
}
