package com.fastestbot.luckyluke;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;


public class Main {
    public static String prefix = "!";

    public static void main(String[] args) throws LoginException {
        appendToLogFile(LocalDateTime.now() + ": ---------------Starting Bot---------------");
        JDA jda = JDABuilder.createDefault(args[0]).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.streaming("Imagine this happening in 2021", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new VoiceHandler());
    }

    public static void appendToLogFile(String text) {
        try{
            FileWriter fileWriter = new FileWriter("log.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e){
            System.out.println("Error Writing file: " + e);
        }
    }
}
