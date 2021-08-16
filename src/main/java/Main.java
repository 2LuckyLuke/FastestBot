import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.EnumSet;
import java.util.List;

public class Main {
    public static String prefix = "!";

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault("ODQ0MTUyNTIxNDg4MjAzNzc2.YKOQTA.QfK45Pz2ql2T3-EDFpLDkSFTWN4").build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.streaming("Imagine this happening in 2021", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new VoiceHandler());


    }
}
