import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.sql.JDBCType.NULL;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, ClassNotFoundException {
        String token = args[0]; //Add Bot token to args
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
            youtubeUrlRequest(msg, event);

        } else if (msg.getContentRaw().startsWith("doublexp")) {
            doubleXpCooldown(msg, event);
        } else if (msg.getContentRaw().startsWith("Cabbage")) {
            sendImage(msg, event);
        }

    }

    public void sendImage(Message msg, MessageReceivedEvent event) {
        try {
            URL url = new URL("https://runescape.wiki/images/thumb/d/de/Cabbage_(2017_Easter_event).png/300px-Cabbage_(2017_Easter_event).png");
            BufferedImage img = ImageIO.read(url);
            File file = new File("Cabbage.png");
            ImageIO.write(img, "png", file);
            event.getChannel().sendFile(file).queue();

        } catch (Exception e) {
            event.getChannel().sendMessage("Error fetching image.").queue();
        }


    }

    public void youtubeUrlRequest(Message msg, MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        long time = System.currentTimeMillis();
        String message = "https://www.youtube.com/results?search_query=" + msg.getContentRaw().substring(5);
        channel.sendMessage(message.replace(" ", "+")).queue();
        //   "https://oldschool.runescape.wiki/images/thumb/9/96/Cabbage_detail.png/1200px-Cabbage_detail.png"

    }

    public void doubleXpCooldown(Message msg, MessageReceivedEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/London"));
        LocalDateTime doublexp = LocalDateTime.of(2021, 2, 19, 12, 00);

        Duration duration = Duration.between(now, doublexp);
        long diff = Math.abs(duration.toMinutes());
        String message = (((diff / 60)) + " Hours untill Double XP.");
        MessageChannel channel = event.getChannel();
        //String message = "https://www.youtube.com/results?search_query=" + msg.getContentRaw().substring(5);
        channel.sendMessage(message).queue();
        //   "https://oldschool.runescape.wiki/images/thumb/9/96/Cabbage_detail.png/1200px-Cabbage_detail.png"

    }

}
