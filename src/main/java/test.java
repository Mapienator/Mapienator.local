import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class test  {
    public static void main(String[] args)  {

//        if(message.equals("!join")) {
//        // Checks if the bot has permissions.
//        if(!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
//            // The bot does not have permission to join any voice channel. Don't forget the .queue()!
//            channel.sendMessage("I do not have permissions to join a voice channel!").queue();
//            return;
//        }
//        // Creates a variable equal to the channel that the user is in.
//        VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
//        // Checks if they are in a channel -- not being in a channel means that the variable = null.
//        if(connectedChannel == null) {
//            // Don't forget to .queue()!
//            channel.sendMessage("You are not connected to a voice channel!").queue();
//            return;
//        }
//        // Gets the audio manager.
//        AudioManager audioManager = event.getGuild().getAudioManager();
//        // When somebody really needs to chill.
//        if(audioManager.isAttemptingToConnect()) {
//            channel.sendMessage("The bot is already trying to connect! Enter the chill zone!").queue();
//            return;
//        }
//        // Connects to the channel.
//        audioManager.openAudioConnection(connectedChannel);
//        // Obviously people do not notice someone/something connecting.
//        channel.sendMessage("Connected to the voice channel!").queue();
//    } else if(message.equals("!leave")) { // Checks if the command is !leave.        }
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
        } catch (NullPointerException e) {
            URL facts = getClass().getClassLoader().getResource("CabbageFacts.text");
            if (facts == null) {
                event.getChannel().sendMessage("Error resource is NULL").queue();
            }
            event.getChannel().sendMessage("Error. No idea what. NullPointerException").queue();
        }


    }

    public void yakTimer(Message msg, MessageReceivedEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/London"));
        LocalDateTime doublExp = LocalDateTime.of(2021, 3, 1, 12, 0);
        LocalDateTime afterDoublExp = LocalDateTime.of(2021, 4, 12, 12, 0);

        Duration duration = Duration.between(now, doublExp);
        Duration runningDuration = Duration.between(now, afterDoublExp);
        long diff = Math.abs(duration.toMinutes());
        long diff2 = Math.abs(runningDuration.toMinutes());
        if (now.isBefore(doublExp)) {
            String message = (((diff / 60)) + " Hours until Yak Track.");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        } else if (now.isAfter(doublExp) && now.isBefore(afterDoublExp)) {
            String message = ("Yak Track is currently live!!!! So stop wasting time!" + "\r\n" + "Time reminding untill the Yak Track is over: " + ((diff2 / 60)) + " Hours ");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        } else if (now.isAfter(afterDoublExp)) {
            String message = ("You waited too long. Yak Track ended. Hope you got all the rewards.");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
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
            String message = ("You waited too long. DoubleXP ended. No idea when the next one is <3");
            MessageChannel channel = event.getChannel();
            channel.sendMessage(message).queue();
        }
    }

}