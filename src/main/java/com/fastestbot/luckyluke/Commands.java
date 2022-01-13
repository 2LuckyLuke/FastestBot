package com.fastestbot.luckyluke;

import com.fastestbot.luckyluke.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Commands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(":");

        if (event.getChannel().getId().equals("663522966633709568")) {
            if (!event.getMessage().getAttachments().isEmpty()
                    || args[0].equalsIgnoreCase("http")
                    || args[0].equalsIgnoreCase("https")
                ){
                event.getMessage().addReaction("U+2B06").queue();
                event.getMessage().addReaction("U+2B07").queue();
                System.out.println("User '" + event.getAuthor().getName() + "' has sent a meme; adding up and down votes.");
            }
        }
        if (args[0].equalsIgnoreCase(Main.prefix + "ping")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("pong").queue();
            System.out.println("User '" + event.getAuthor().getName() + "' has requested a pong; sending it now.");
        }

        if (args[0].equalsIgnoreCase(Main.prefix + "fragfinn")){
            event.getChannel().sendTyping().queue();
            String response = "<@561491781733187584> ";
            response = response + event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" "));
            event.getChannel().sendMessage(response);
        }
    }
}
