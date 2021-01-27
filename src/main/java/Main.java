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
import java.io.File;

import static java.sql.JDBCType.NULL;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "ODAwODk1NjM4NzQwNTMzMjQ4.YAYyMA.KcHzbDmWkAtPzln6dz8SyGiu9gI";
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new Main())
                .setActivity(Activity.watching("Cabbage Grow"))
                .build();

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
