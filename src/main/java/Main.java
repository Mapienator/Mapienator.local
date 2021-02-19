import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.JarFile;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
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
        if (msg.getContentRaw().toLowerCase().startsWith("doublexp")) {
            doubleXpCooldown(msg, event);
        } else if (msg.getContentRaw().toLowerCase().startsWith("cabbage")) {
            sendImage(msg, event);
        }

    }

    public void sendImage(Message msg, MessageReceivedEvent event) {
        try {
            ArrayList<String> list = new ArrayList<>();
            InputStream facts = Main.class.getClassLoader().getResourceAsStream("CabbageFacts.text"); // Remember to add the text to the jar. However you want to do that.
            Scanner scanner = new Scanner(facts);
            int rows = 0;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                list.add(data);
                rows++;
            }
            int random = (int) (Math.random() * rows);
            scanner.close();
            //String line = Files.readAllLines(Paths.get(facts.getFile())).get(random);
            String line = list.get(random);
            URL url = new URL("https://runescape.wiki/images/thumb/d/de/Cabbage_(2017_Easter_event).png/300px-Cabbage_(2017_Easter_event).png");
            BufferedImage img = ImageIO.read(url);
            File file = new File("Cabbage.png");
            ImageIO.write(img, "png", file);
            event.getChannel().sendMessage(line).addFile(file).queue();

        } catch (FileNotFoundException e) {
            event.getChannel().sendMessage("Error fetching data").queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("Error fetching image.").queue();
        } catch (NullPointerException e){
            URL facts = getClass().getClassLoader().getResource("CabbageFacts.text");
            if (facts == null) {
                event.getChannel().sendMessage("Error resource is NULL").queue();
            }
            event.getChannel().sendMessage("Error. No idea what. NullPointerException").queue();
        }


    }


    public void doubleXpCooldown(Message msg, MessageReceivedEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/London"));
        LocalDateTime doublExp = LocalDateTime.of(2021, 2, 19, 12, 0);
        LocalDateTime afterDoublExp = LocalDateTime.of(2021, 3, 1, 12, 0);

        Duration duration = Duration.between(now, doublExp);
        Duration runningDuration = Duration.between(now, afterDoublExp);
        long diff = Math.abs(duration.toMinutes());
        long diff2 = Math.abs(runningDuration.toMinutes());
        if (now.isBefore(doublExp)) {
            String message = (((diff / 60)) + " Hours until Double XP.");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        } else if (now.isAfter(doublExp) || now.isBefore(afterDoublExp)) {
            String message = ("Double XP is currently live!!!! So stop wasting Xp!" + "\r\n" + "Time reminding untill Double Xp is over: " + ((diff2 / 60)) + " Hours");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        } else if (now.isAfter(afterDoublExp)) {
            String message = ("You waited too long. DoubleXP ended ");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        }
    }
}
