import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.Channel;
import java.util.EnumSet;


public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = args[0];

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES

        );

        JDABuilder.createLight(token, intents)
                .addEventListeners(new Main())
                .setActivity(Activity.watching("Cabbage Grow"))
                .setStatus(OnlineStatus.ONLINE)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("This is how you configure Java Logging with SLF4J");

    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        if (author.isBot())
            return;

        if (!event.getAuthor().isBot()) {
            System.out.println("event is bot");
            if (message.getContentRaw().toLowerCase().startsWith("voice")) {
                System.out.println("event is voice");
                //String arg = content.substring("voice".length());
                onEchoCommand(event);

            }
            if (message.getContentRaw().toLowerCase().contains("cabbage")) {
                System.out.println("event is cabbage");
                //String arg = content.substring("voice".length());
                String cabbage = message.getContentRaw().toLowerCase();
                int counter = 0;
                while (cabbage.contains("cabbage")) {
                    cabbage.replaceFirst("cabbage", "");
                    counter++;
                }
                cabbageCounter(event, cabbage);
            }
        }
    }

    private void cabbageCounter(GuildMessageReceivedEvent event, String cabbage) {
        TextChannel textChannel = event.getChannel();
        textChannel.sendMessage("Unable to connect to ``" + textChannel + "Amount " + cabbage).queue(); // never forget to queue()!
        System.out.println(cabbage);

    }

    private void onEchoCommand(GuildMessageReceivedEvent event) {
        Member member = event.getMember();                              // Member is the context of the user for the specific guild, containing voice state and roles
        GuildVoiceState voiceState = member.getVoiceState();            // Check the current voice state of the user
        VoiceChannel channel = voiceState.getChannel();                 // Use the channel the user is currently connected to

        System.out.println(member);
        System.out.println(voiceState);
        System.out.println(channel);


        if (channel != null) {
            System.out.println("Test 2");
            connectTo(channel);
            onConnecting(channel, event.getChannel());
        } else {

            onUnknownChannel(event.getChannel(), "your voice channel"); // Tell the user about our failure
        }
    }

    private void connectTo(VoiceChannel channel) {
        Guild guild = channel.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        //EchoHandler handler = new EchoHandler();
        //audioManager.setSendingHandler(handler);
        //audioManager.setReceivingHandler(handler);
        audioManager.openAudioConnection(channel);
    }

    private void onConnecting(VoiceChannel channel, TextChannel textChannel) {
        textChannel.sendMessage("Connecting to " + channel.getName()).queue(); // never forget to queue()!
    }

    private void onUnknownChannel(MessageChannel channel, String comment) {
        channel.sendMessage("Unable to connect to ``" + comment + "``, no such channel!").queue(); // never forget to queue()!
    }
}