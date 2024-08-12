package mehow2k.botdiscord;

import net.dv8tion.jda.api.requests.GatewayIntent;

public class C {
    public static final String version = "v1.0 by MeHow2k";
    public static boolean isUI = false;
    //TOKEN bota
    public static String TOKEN = "";
    //ID SERWERA
    public static Long GuildID = 3l;

    //tabliuca uprawnien
    public static final GatewayIntent[] gatewayIntents =
            {GatewayIntent.MESSAGE_CONTENT,GatewayIntent.DIRECT_MESSAGES,
                    GatewayIntent.GUILD_MESSAGES,GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_PRESENCES};
    public static String ChatGPTapikey = "";

}
