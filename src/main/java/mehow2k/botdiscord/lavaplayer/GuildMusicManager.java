package mehow2k.botdiscord.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {
    private TrackScheduler trackScheduler;
    private AudioForwarder audioForwarder;


    public GuildMusicManager (AudioPlayerManager manager, Guild guild){
        AudioPlayer audioPlayer= manager.createPlayer();
        trackScheduler= new TrackScheduler(audioPlayer);
        audioPlayer.addListener(trackScheduler);
        audioForwarder=new AudioForwarder(audioPlayer,guild);

    }

    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public AudioForwarder getAudioForwarder() {
        return audioForwarder;
    }
}
