package com.fastestbot.luckyluke;

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
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashMap;


/**
 * Quite badly written code, but I also couldn't be bothered to neither improve it nor to comment it, to bad.
 *
 * Update ca. 2-5 Months later: Code isn't that bad anymore, but I can still not be bothered to comment it. It's selfexplanatory anyway (:
 */

public class VoiceHandler extends ListenerAdapter {
    private Guild guild = null;
    private long everyoneID = 663511269696995364L;

    HashMap<String, String> roleToVoiceID = new HashMap<>();
    HashMap<String, String> textToVoiceID = new HashMap<>();

    private TextChannel createTextChannel(String name, Role role, Category parent){
        EnumSet<Permission> allow = null;
        EnumSet<Permission> deny = EnumSet.of(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ);

        Main.appendToLogFile(LocalDateTime.now() + ": Creating Text Channel '" + name + "'.");
        return guild.createTextChannel(name, parent).addRolePermissionOverride(everyoneID, allow, deny).addRolePermissionOverride(role.getIdLong(), deny, allow).complete();
    }

    private Role createRole(String name){
        Main.appendToLogFile(LocalDateTime.now() + ": Creating Role '" + name + "'.");
        return guild.createRole().setName(name).complete();
    }

    private void setGuild(Guild guild){
        if (this.guild == null)this.guild = guild;
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        super.onGuildVoiceJoin(event);
        setGuild(event.getGuild());
        Category parent = event.getChannelJoined().getParent();
        Role role;
        TextChannel textChannel;

        String roleName = event.getChannelJoined().getName().substring(4).replace(" ", "-").toLowerCase(); //todo fix this shit

        Main.appendToLogFile(LocalDateTime.now() + ": '" + event.getMember().getEffectiveName() + "' joined the Channel '" + event.getChannelJoined().getName() + "'.");

        if (!roleToVoiceID.containsKey(event.getChannelJoined().getId())){
            role = createRole(roleName);
            textChannel = createTextChannel(roleName, role, parent);

            roleToVoiceID.put(event.getChannelJoined().getId(), role.getId());
            textToVoiceID.put(event.getChannelJoined().getId(), textChannel.getId());
        }else{
            role = guild.getRoleById(roleToVoiceID.get(event.getChannelJoined().getId()));
        }
        guild.addRoleToMember(event.getMember(), role).complete();
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        super.onGuildVoiceLeave(event);
        setGuild(event.getGuild());
        Role channelRole = guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId()));

        Main.appendToLogFile(LocalDateTime.now() + ": '" + event.getMember().getEffectiveName() + "' left the Channel '" + event.getChannelLeft().getName() + "'.");

        guild.removeRoleFromMember(event.getMember(), channelRole).complete();
        if (event.getChannelLeft().getMembers().isEmpty()){
            Main.appendToLogFile(LocalDateTime.now() + ": Deleting Text Channel '" + guild.getTextChannelById(textToVoiceID.get(event.getChannelLeft().getId())).getName() + "'.");
            guild.getTextChannelById(textToVoiceID.get(event.getChannelLeft().getId())).delete().complete();
            textToVoiceID.remove(event.getChannelLeft().getId());
            Main.appendToLogFile(LocalDateTime.now() + ": Deleting Role '" + guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId())).getName() + "'.");
            guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId())).delete().complete();
            roleToVoiceID.remove(event.getChannelLeft().getId());
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        super.onGuildVoiceMove(event);
        setGuild(event.getGuild());
        Role role = guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId()));
        TextChannel textChannel;
        Category parent = event.getChannelJoined().getParent();

        String roleName = event.getChannelJoined().getName().substring(4).replace(" ", "-").toLowerCase();

        Main.appendToLogFile(LocalDateTime.now() + ": '" + event.getMember().getEffectiveName() + "' moved from the Channel '" + event.getChannelLeft().getName() + "' to the Channel '" + event.getChannelJoined().getName() + "'.");

        guild.removeRoleFromMember(event.getMember(), role).complete();
        if (event.getChannelLeft().getMembers().isEmpty()) {
            Main.appendToLogFile(LocalDateTime.now() + ": Deleting Text Channel '" + guild.getTextChannelById(textToVoiceID.get(event.getChannelLeft().getId())).getName() + "'.");
            guild.getTextChannelById(textToVoiceID.get(event.getChannelLeft().getId())).delete().complete();
            textToVoiceID.remove(event.getChannelLeft().getId());
            Main.appendToLogFile(LocalDateTime.now() + ": Deleting Role '" + guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId())).getName() + "'.");
            guild.getRoleById(roleToVoiceID.get(event.getChannelLeft().getId())).delete().complete();
            roleToVoiceID.remove(event.getChannelLeft().getId());
        }

        if (!roleToVoiceID.containsKey(event.getChannelJoined().getId())){
            role = createRole(roleName);
            textChannel = createTextChannel(roleName, role, parent);

            roleToVoiceID.put(event.getChannelJoined().getId(), role.getId());
            textToVoiceID.put(event.getChannelJoined().getId(), textChannel.getId());
        }else{
            role = guild.getRoleById(roleToVoiceID.get(event.getChannelJoined().getId()));
        }
        guild.addRoleToMember(event.getMember(), role).complete();
    }
}
