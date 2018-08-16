package com.webapp.testbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Timer;

/**
 * Hello world!
 *
 */
public class App extends ListenerAdapter
{
    public static void main( String[] args ) throws Exception
    {
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.token).buildAsync();
        jda.addEventListener(new App());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent evt)
    {
        //objects
        User objUsers = evt.getAuthor();
        MessageChannel objMsgChannel = evt.getChannel();
        Message objMsg = evt.getMessage();


        //commands
        if(objMsg.getContentRaw().equalsIgnoreCase(Ref.prefix + "Tes"))
        {
            objMsgChannel.sendMessage(objUsers.getAsMention()+" HALO "+objUsers.getName()).queue();



        }


    }

}
