package mehow2k.botdiscord.listeners;

import mehow2k.botdiscord.commands.*;
import net.dv8tion.jda.api.JDA;

public class EventListenersLoader {
    JDA jda;
    public EventListenersLoader (JDA jda){
        this.jda=jda;
    }
    public void load (){
        jda.addEventListener(new Listeners());
        jda.addEventListener(new ShowVersion());
        jda.addEventListener(new Sum());
        jda.addEventListener(new Mult());
        jda.addEventListener(new Love());
        jda.addEventListener(new Play());
        jda.addEventListener(new Skip());
        jda.addEventListener(new Stop());
        jda.addEventListener(new Queue());
        jda.addEventListener(new ClearQueue());
        jda.addEventListener(new Shuffle());
        jda.addEventListener(new NowPlaying());
        jda.addEventListener(new Weather());
        jda.addEventListener(new GPT());
    }
}
