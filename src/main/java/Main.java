import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

import static java.sql.JDBCType.NULL;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, ClassNotFoundException {
        //String token = "ODAwODk1NjM4NzQwNTMzMjQ4.YAYyMA.6Jx1VheaLNG99Yy0Y1mmvOCJi48";
        String token = args[0];
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new Main())
                .setActivity(Activity.watching("Cabbage Grow"))
                .build();
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("This is how you configure Java Logging with SLF4J");

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Message msg = event.getMessage();
        if (msg.getContentRaw().startsWith("!song")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            String message = "https://www.youtube.com/results?search_query=" + msg.getContentRaw().substring(5);
            channel.sendMessage(message.replace(" ", "+")).queue();
            //   "https://oldschool.runescape.wiki/images/thumb/9/96/Cabbage_detail.png/1200px-Cabbage_detail.png"

        }

    }

}
