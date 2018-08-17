package com.webapp.testbot;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import java.time.format.DateTimeFormatter;


/**
 * Hello world!
 */
public class App extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.token).build();
        jda.addEventListener(new App());


    }


    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        //objects
        User objUser = evt.getAuthor();
        MessageChannel objMsgChannel = evt.getChannel();
        Message objMsg = evt.getMessage();
        Member objMember = evt.getMember();
        Guild objGuild = evt.getGuild();

        //commands
        if (objMsg.getContentRaw().contains(Ref.prefix)) {

            if (objMsg.getContentRaw().equalsIgnoreCase(Ref.prefix + "help") || objMsg.getContentRaw().equalsIgnoreCase(Ref.prefix + "commands")) {
                objMsgChannel.sendMessage(Ref.info).queue();

//----------------------------------------------------------------------------------------------

            } else if (objMsg.getContentRaw().equalsIgnoreCase(Ref.prefix + "halo")) {
                objMsgChannel.sendMessage(objUser.getAsMention() + " HALO! " + objMember.getNickname() + " berada di channel " + objMsgChannel.getName()).queue();

//----------------------------------------------------------------------------------------------

            } else if (objMsg.getContentRaw().contains(Ref.prefix + "count")) {
                String[] command = (objMsg.getContentRaw().split(" "));

                for (int i = 1; i <= Integer.valueOf(command[1]); i++) {
                    objMsgChannel.sendMessage("" + i).queue();
                }
                objMsgChannel.sendMessage("done").queue();

//----------------------------------------------------------------------------------------------

            } else if (objMsg.getContentRaw().contains(Ref.prefix + "changenick") || objMsg.getContentRaw().contains(Ref.prefix + "cn")) {
                GuildController guildcntrl = new GuildController(objGuild);

                String[] nickArray = (objMsg.getContentRaw().split(" "));
                String namalengkap = nickArray[2];

                for (int i = 3; i < nickArray.length - 1; i++) {
                    namalengkap = namalengkap + " " + nickArray[i];
                }

                String kelas = "(" + nickArray[nickArray.length - 1].toUpperCase() + ")";
                String namatambahkelas = namalengkap + " " + kelas;

                if (objMsg.getMentionedMembers().get(0).getPermissions().contains(Permission.NICKNAME_MANAGE)) {
                    objMsgChannel.sendMessage("Changing " + objMsg.getMentionedMembers().get(0).getNickname() + "\'s nickname to " + namatambahkelas + "...").queue();
                }
                try {
                    guildcntrl.setNickname(objMsg.getMentionedMembers().get(0), namatambahkelas).queue();
                } catch (Exception e) {
                    objMsgChannel.sendMessage(objMsg.getMentionedMembers().get(0).getNickname() + "\'s nickname cannot be changed to " + namatambahkelas + ". Reason:" + e).queue();
                }

//----------------------------------------------------------------------------------------------

            } else if (objMsg.getContentRaw().contains(Ref.prefix + "info")) {

                EmbedBuilder embedbuilder = new EmbedBuilder();

                DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");







                try {
                    objMsg.getMentionedMembers().get(0); //try error code

                    String[] roles = objMsg.getMentionedMembers().get(0).getRoles().toString().split(",");
                    String role = "";
                    String mentionrole = "";


                    if(objMsg.getMentionedMembers().get(0).getRoles().toString().length() > 2) {
                        mentionrole = roles[0].substring(roles[0].indexOf(":") + 1, roles[0].indexOf("("));

                        for (int i = 1; i < roles.length; i++) {
                            role = roles[i].substring(roles[i].indexOf(":") + 1, roles[i].indexOf("("));
                            mentionrole = mentionrole + ", " + role;
                        }

                    }else if(objMsg.getMentionedMembers().get(0).getRoles().toString().length() == 2){
                        mentionrole = "no roles";
                    }

                    String game = "";
                    if(objMsg.getMentionedMembers().get(0).getGame() == null){
                        game = "not playing";
                    } else if (objMsg.getMentionedMembers().get(0).getGame() != null){
                        game = objMsg.getMentionedMembers().get(0).getGame().toString();
                    }

                    MessageEmbed embed = embedbuilder
                            .setTitle("Info about " + objMsg.getMentionedMembers().get(0).getNickname())
                            .setColor(0xbb5acf)
                            .setThumbnail(objMsg.getMentionedUsers().get(0).getEffectiveAvatarUrl())
                            .addField("huwala ini nama aslinya", objMsg.getMentionedUsers().get(0).getName(), true)
                            .addField("ini nickname nya", objMsg.getMentionedMembers().get(0).getNickname(), true)
                            .addField("ini ID nya", objMsg.getMentionedUsers().get(0).getId(), true)
                            .addField("ini join date nya", String.valueOf(objMsg.getMentionedMembers().get(0).getJoinDate().format(formatdate)), true)
                            .addField("ini statusnya", objMsg.getMentionedMembers().get(0).getOnlineStatus().getKey(), true)
                            .addField("ini roles nya", mentionrole, true)
                            .addField("lagi main", game,true)
                            .build();

                    objMsgChannel.sendMessage(new MessageBuilder().setEmbed(embed).build()).queue();


                }catch(Exception e){

                    String[] roles = objMember.getRoles().toString().split(",");
                    String role = "";
                    String mentionrole = "";


                    if(objMember.getRoles().toString().length() > 2) {
                        mentionrole = roles[0].substring(roles[0].indexOf(":") + 1, roles[0].indexOf("("));

                        for (int i = 1; i < roles.length; i++) {
                            role = roles[i].substring(roles[i].indexOf(":") + 1, roles[i].indexOf("("));
                            mentionrole = mentionrole + ", " + role;
                        }

                    }else if(objMember.getRoles().toString().length() == 2){
                        mentionrole = "no roles";
                    }

                    String game = "";
                    if(objMember.getGame() == null){
                        game = "not playing";
                    } else if (objMember.getGame() != null){
                        game = objMember.getGame().toString();
                    }

                    MessageEmbed embed = embedbuilder
                            .setTitle("Nilai " + objMember.getNickname())
                            .setColor(0xbb5acf)
                            .setThumbnail(objUser.getEffectiveAvatarUrl())
                            .addField("huwala ini nama aslinya", objUser.getName(), true)
                            .addField("ini nickname nya", objMember.getNickname(), true)
                            .addField("ini ID nya", objUser.getId(), true)
                            .addField("ini join date nya", String.valueOf(objMember.getJoinDate().format(formatdate)), true)
                            .addField("ini statusnya", objMember.getOnlineStatus().getKey(), true)
                            .addField("ini roles nya", mentionrole, true)
                            .addField("lagi main", game,true)
                            .build();

                    objMsgChannel.sendMessage(new MessageBuilder().setEmbed(embed).build()).queue();


                }

//----------------------------------------------------------------------------------------------

            } //else if(){}

        }

    }
}
