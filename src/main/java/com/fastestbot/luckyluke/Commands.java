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
            }
        }
        if (args[0].equalsIgnoreCase(Main.prefix + "ping")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("pong").queue();
        }
    }
}
