import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;


/**
 * Quite badly written code, but I also couldn't be bothered to neither improve it nor to comment it, to bad.
 */
public class VoiceHandler extends ListenerAdapter {
    private Guild guild = null;
    private long everyoneID = 663511269696995364L;
    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        super.onGuildVoiceJoin(event);
        if (guild == null){
            guild = event.getGuild();
        }
        String roleName = event.getChannelJoined().getName().substring(4).replace(" ", "-").toLowerCase();
        Role channelRole = null;

        if (guild.getTextChannelsByName(roleName, false).isEmpty()){
            guild.createRole().setName(roleName).complete();
            long newRoleId = 1;
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    newRoleId = r.getIdLong();
                    channelRole = r;
                    break;
                }
            }
            Category parent = guild.getCategoryById("663511269696995375");

            EnumSet<Permission> allow = null;
            EnumSet<Permission> deny = EnumSet.of(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ);

            guild.createTextChannel(roleName, parent).addRolePermissionOverride(everyoneID, allow, deny).addRolePermissionOverride(newRoleId, deny, allow).queue();
        }else{
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    channelRole = r;
                    break;
                }
            }
        }
        guild.addRoleToMember(event.getMember(), channelRole).queue();

    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        super.onGuildVoiceMove(event);
        if (guild == null){
            guild = event.getGuild();
        }
        String roleName;
        Role channelRole = null;
        roleName = event.getChannelLeft().getName().substring(4).replace(" ", "-").toLowerCase();

        for (Role r : guild.getRoles()){
            if(r.getName().equalsIgnoreCase(roleName)){
                channelRole = r;
                break;
            }
        }
        guild.removeRoleFromMember(event.getMember(), channelRole).complete();
        if (event.getChannelLeft().getMembers().isEmpty()) {
            for (TextChannel t : guild.getTextChannels()) {
                if (t.getName().equalsIgnoreCase(roleName)) {
                    t.delete().complete();
                }
            }
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    r.delete().complete();
                    break;
                }
            }
        }

        roleName = event.getChannelJoined().getName().substring(4).replace(" ", "-").toLowerCase();
        if (guild.getTextChannelsByName(roleName, false).isEmpty()){
            guild.createRole().setName(roleName).complete();
            long newRoleId = 1;
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    newRoleId = r.getIdLong();
                    channelRole = r;
                    break;
                }
            }
            Category parent = guild.getCategoryById("663511269696995375");

            EnumSet<Permission> allow = null;
            EnumSet<Permission> deny = EnumSet.of(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ);

            guild.createTextChannel(roleName, parent).addRolePermissionOverride(everyoneID, allow, deny).addRolePermissionOverride(newRoleId, deny, allow).queue();
        }else{
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    channelRole = r;
                    break;
                }
            }
        }
        guild.addRoleToMember(event.getMember(), channelRole).queue();

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        super.onGuildVoiceLeave(event);
        if (guild == null){
            guild = event.getGuild();
        }

        String roleName;
        Role channelRole = null;
        roleName = event.getChannelLeft().getName().substring(4).replace(" ", "-").toLowerCase();

        for (Role r : guild.getRoles()){
            if(r.getName().equalsIgnoreCase(roleName)){
                channelRole = r;
                break;
            }
        }
        guild.removeRoleFromMember(event.getMember(), channelRole).complete();

        if (event.getChannelLeft().getMembers().isEmpty()){
            roleName = event.getChannelLeft().getName().substring(4).replace(" ", "-").toLowerCase();
            for (TextChannel t :  guild.getTextChannels()){
                if(t.getName().equalsIgnoreCase(roleName)){
                    t.delete().complete();
                }
            }
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    r.delete().complete();
                    break;
                }
            }
        }
    }
}
