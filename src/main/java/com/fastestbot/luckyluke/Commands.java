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

        /* info
        if (args[0].equalsIgnoreCase(com.fastestbot.luckyluke.Main.prefix + "info")){
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("DisCart Bot Information");
            info.setDescription("This is team Deltas attempt to create the best letter-trading bot");
            info.addField("Creators", "Team Delta", false);
            info.setColor(0x3333cc);
            info.setFooter("Created for the SEG", "https://cdn.discordapp.com/icons/690131313151311960/0106d484f7fdd570c7d3904f71db371b.webp");

            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
        */
        if (args[0].equalsIgnoreCase(Main.prefix + "ping")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("pong").queue();
        }

        /* clear command
        if (args[0].equalsIgnoreCase(com.fastestbot.luckyluke.Main.prefix + "clear")) {
            try {
                int amountToClear = Integer.parseInt(args[1]) + 1;
                if (amountToClear > 0){
                    List<Message> messages = event.getChannel().getHistory().retrievePast(amountToClear).complete();
                    messages.forEach(message -> System.out.println("Deleting: " + message));
                    event.getChannel().deleteMessages(messages).complete();
                }
            }catch (Exception e){
                event.getChannel().sendMessage("The clear command requires an integer as argument. I.e.: " + com.fastestbot.luckyluke.Main.prefix + "clear 5 (deletes the last 5 messages)").queue();
                throw new IllegalArgumentException("clear command: no integer as argument");
            }
        }
        */

        /* kill command
        if (args[0].equalsIgnoreCase(com.fastestbot.luckyluke.Main.prefix + "kill")){
            event.getChannel().sendMessage("shutting down").complete();
            List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
            event.getChannel().deleteMessages(messages).complete();
            event.getJDA().shutdown();
        }
        */
    }
}
