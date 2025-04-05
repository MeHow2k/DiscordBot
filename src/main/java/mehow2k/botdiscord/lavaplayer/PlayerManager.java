package mehow2k.botdiscord.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private  static PlayerManager INSTANCE;
    private Map<Long,GuildMusicManager> guildMusicManagers = new HashMap<>();
    private AudioPlayerManager audioPlayerManager= new DefaultAudioPlayerManager();
    private PlayerManager(){
        YoutubeAudioSourceManager ytSourceManager = new dev.lavalink.youtube.YoutubeAudioSourceManager();
        audioPlayerManager.registerSourceManager(ytSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager,com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager.class);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);

    }
    public static  PlayerManager get() {
        if(INSTANCE==null){
            try{
            INSTANCE= new PlayerManager();}catch (Exception e){e.printStackTrace();}
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
                System.out.println("TRACK \""+ track.getInfo().title+"\" LOADED.");
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    guildMusicManager.getTrackScheduler().queue(track);
                }
                System.out.println("PLAYLIST: \""+ playlist.getName() +"\" LOADED.");
            }

            @Override
            public void noMatches() {
                System.out.println("noMaches for "+ trackURL);
            }

            @Override
            public void loadFailed(FriendlyException e) {
                e.printStackTrace();
            }
        });
    }
}
