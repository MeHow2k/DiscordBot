package mehow2k.botdiscord;

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
        System.out.println("Gotowy."+event.getGuildTotalCount());
        Panel.printLog("Build complete! Ready! ");
        Guild guild = event.getJDA().getGuildById(C.GuildID);
        guild.upsertCommand("sum","Dodawanie").addOptions(
                new OptionData(OptionType.INTEGER,"number1","first num",true),
                new OptionData(OptionType.INTEGER,"number2","second num",true)
        ).queue();
        guild.upsertCommand("mult","Mnożenie").addOptions(
                new OptionData(OptionType.INTEGER,"number1","first num",true),
                new OptionData(OptionType.INTEGER,"number2","second num",true)
        ).queue();
        guild.upsertCommand("love","Love meter").addOptions(
                new OptionData(OptionType.STRING,"osoba1","Pierwsza osoba",true),
                new OptionData(OptionType.STRING,"osoba2","Druga osoba",true)
        ).queue();
        guild.upsertCommand("gpt","Zadaj pytanie").addOptions(
                new OptionData(OptionType.STRING,"prompt","Podaj zapytanie",true)
        ).queue();

        guild.upsertCommand("play","Zagram Ci piosenkę z YT,").addOptions(
                new OptionData(OptionType.STRING,"url","Song URL",true)
        ).queue();
        guild.upsertCommand("stop","Zatrzymuje muzykę").addOptions(

        ).queue();
        guild.upsertCommand("skip","Pominę piosenkę jak Ci się nie podoba").addOptions(

        ).queue();
        guild.upsertCommand("queue","Pokażę kolejkę utworów").addOptions(

        ).queue();
        guild.upsertCommand("clear","Wyczyszcze kolejkę utworów").addOptions(

        ).queue();
        guild.upsertCommand("nowplaying","Aktualnie odtwarzany utwór").addOptions(

        ).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        //String authorName= event.getAuthor().getName();
        String authorName= event.getAuthor().getEffectiveName();

        //odpowiada użytkownikowi, który napisal komende
        if(message.equalsIgnoreCase("!hello")){
            event.getGuildChannel().sendMessage("Cześć "+authorName+"! :)").queue();
        }
        //wysyla film ASM
        if(message.equalsIgnoreCase("!film")){
            event.getGuildChannel().sendMessage("Rozumiemy o co chodzi! "+"<link>").queue();
        }
        message.toLowerCase();
        //sends message when somebody's message contains string
        if(message.contains("8051")) {
            if(!event.getAuthor().isBot())
            event.getGuildChannel().sendMessage("Panie "+authorName+" znalazłeś tajny kod!").queue();
        }

    }
}
