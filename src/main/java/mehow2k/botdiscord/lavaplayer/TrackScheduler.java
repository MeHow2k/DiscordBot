package mehow2k.botdiscord.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import mehow2k.botdiscord.UI.Panel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue=new LinkedBlockingQueue<>();

    public TrackScheduler(AudioPlayer audioPlayer) {
     this.audioPlayer=audioPlayer;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        audioPlayer.startTrack(queue.poll(), false);
    }
    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        exception.printStackTrace();
        Panel.printLog(exception.toString());
    }

    public void queue(AudioTrack track){
        if(!audioPlayer.startTrack(track,true))
        queue.offer(track);
    }

}
