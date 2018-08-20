package com.webapp.testbot;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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
                    objMsgChannel.sendMessage(objMsg.getMentionedMembers().get(0).getNickname() + "\'s nickname cannot be changed to " + namatambahkelas + ". Reason: " + e.getMessage()).queue();
                }

//----------------------------------------------------------------------------------------------

            } else if (objMsg.getContentRaw().contains(Ref.prefix + "info")) {

                EmbedBuilder embedbuilder = new EmbedBuilder();

                DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");

                try {
                    objMsg.getMentionedMembers().get(0); //try code that make error

                    String roleraw = "";
                    String role = "";
                    String mentionrole = objMsg.getMentionedMembers().get(0).getRoles().get(0).toString();

                    if(objMsg.getMentionedMembers().get(0).getRoles().size() > 0) {
                        mentionrole = mentionrole.substring(mentionrole.indexOf(":") + 1, mentionrole.indexOf("("));

                        for (int i = 1; i < objMsg.getMentionedMembers().get(0).getRoles().size(); i++) {
                            roleraw = objMsg.getMentionedMembers().get(0).getRoles().get(i).toString();
                            role = roleraw.substring(roleraw.indexOf(":") + 1, roleraw.indexOf("("));
                            mentionrole = mentionrole + ", " + role;
                        }

                    }else if(objMsg.getMentionedMembers().get(0).getRoles().size() == 0){
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
                            .setColor(objMsg.getMentionedMembers().get(0).getColorRaw())
                            .setThumbnail(objMsg.getMentionedUsers().get(0).getEffectiveAvatarUrl())
                            .addField(Ref.realName, objMsg.getMentionedUsers().get(0).getName(), true)
                            .addField(Ref.nickName, objMsg.getMentionedMembers().get(0).getNickname(), true)
                            .addField(Ref.theId, objMsg.getMentionedUsers().get(0).getId(), true)
                            .addField(Ref.joinDate, String.valueOf(objMsg.getMentionedMembers().get(0).getJoinDate().format(formatdate)), true)
                            .addField(Ref.status, objMsg.getMentionedMembers().get(0).getOnlineStatus().getKey(), true)
                            .addField(Ref.roles, mentionrole, true)
                            .addField(Ref.playing, game,true)
                            .build();

                    objMsgChannel.sendMessage(new MessageBuilder().setEmbed(embed).build()).queue();


                }catch(Exception e){

                    String roleraw = "";
                    String role = "";
                    String mentionrole = objMember.getRoles().get(0).toString();

                    if(objMember.getRoles().size() > 0) {
                        mentionrole = mentionrole.substring(mentionrole.indexOf(":") + 1, mentionrole.indexOf("("));

                        for (int i = 1; i < objMember.getRoles().size(); i++) {
                            roleraw = objMember.getRoles().get(i).toString();
                            role = roleraw.substring(roleraw.indexOf(":") + 1, roleraw.indexOf("("));
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
                            .setTitle("Info about " + objMember.getNickname())
                            .setColor(objMember.getColorRaw())
                            .setThumbnail(objUser.getEffectiveAvatarUrl())
                            .addField(Ref.realName, objUser.getName(), true)
                            .addField(Ref.nickName, objMember.getNickname(), true)
                            .addField(Ref.theId, objUser.getId(), true)
                            .addField(Ref.joinDate, String.valueOf(objMember.getJoinDate().format(formatdate)), true)
                            .addField(Ref.status, objMember.getOnlineStatus().getKey(), true)
                            .addField(Ref.roles, mentionrole, true)
                            .addField(Ref.playing, game,true)
                            .build();

                    objMsgChannel.sendMessage(new MessageBuilder().setEmbed(embed).build()).queue();
                }

//----------------------------------------------------------------------------------------------

            } else if(objMsg.getContentRaw().contains(Ref.prefix + "nilai")){

                try(FileReader fr = new FileReader("nilai.txt")){
                    LineNumberReader lnr = new LineNumberReader(fr);
                    BufferedReader br = new BufferedReader(fr);

                    String nilairaw;
                    String nilaigabung;
                    String nilai;
                    int i = 0;
                    ArrayList<String> arrayNilai = new ArrayList<>();
                    while((nilairaw = br.readLine()) != null){

                        if(nilairaw.contains("jovan")){
                            nilaigabung = nilairaw.split(":")[1].split(" ")[i];
                            i++;


                        }
                    }




                }catch(Exception e){
                    objMsgChannel.sendMessage("failed. Reason: " + e.getMessage()).queue();
                }










//----------------------------------------------------------------------------------------------
            } //else if(){}

        }

    }
}
