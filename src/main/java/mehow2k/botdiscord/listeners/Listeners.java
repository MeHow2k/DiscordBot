package mehow2k.botdiscord.listeners;

import mehow2k.botdiscord.C;
import mehow2k.botdiscord.UI.Panel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Listeners extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Bot has been installed on "+event.getGuildTotalCount()+" servers.");
        Panel.printLog("Build complete! Ready! ");
        Guild guild = event.getJDA().getGuildById(C.GuildID);
        guild.upsertCommand("showversion","Current version info").addOptions(

        ).queue();
        guild.upsertCommand("sum","Addition").addOptions(
                new OptionData(OptionType.INTEGER,"number1","First number",true),
                new OptionData(OptionType.INTEGER,"number2","Second number",true)
        ).queue();
        guild.upsertCommand("mult","Multiplication").addOptions(
                new OptionData(OptionType.INTEGER,"number1","First number",true),
                new OptionData(OptionType.INTEGER,"number2","Second number",true)
        ).queue();
        guild.upsertCommand("love","Love meter").addOptions(
                new OptionData(OptionType.STRING,"osoba1","First person",true),
                new OptionData(OptionType.STRING,"osoba2","Second person",true)
        ).queue();
        guild.upsertCommand("gpt","Answering questions").addOptions(
                new OptionData(OptionType.STRING,"prompt","Enter question",true)
        ).queue();

        guild.upsertCommand("weather","Ask for weather information").addOptions(
                new OptionData(OptionType.STRING,"miejscowosc","Enter city name",true)

        ).queue();

        guild.upsertCommand("play","Play requested song (SoundCloud)").addOptions(
                new OptionData(OptionType.STRING,"url","Song URL",true)
        ).queue();
        guild.upsertCommand("stop","Stop music").addOptions(

        ).queue();
        guild.upsertCommand("skip","Skip current song").addOptions(

        ).queue();
        guild.upsertCommand("queue","Show queue").addOptions(

        ).queue();
        guild.upsertCommand("clear","Clear queue").addOptions(

        ).queue();
        guild.upsertCommand("nowplaying","Show currenly playing song").addOptions(

        ).queue();
        guild.upsertCommand("shuffle","Shuffle playlist").addOptions(

        ).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String authorName= event.getAuthor().getEffectiveName();

        //odpowiada użytkownikowi, który napisal komende
        if(message.equalsIgnoreCase("!hello")){
            event.getGuildChannel().sendMessage("Hi "+authorName+"! ").queue();
        }

    }
}
