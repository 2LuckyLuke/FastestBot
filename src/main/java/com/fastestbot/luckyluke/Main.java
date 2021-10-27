package com.fastestbot.luckyluke;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;


public class Main {
    public static String prefix = "!";

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(args[0]).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.streaming("Imagine this happening in 2021", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new VoiceHandler());
    }
}
