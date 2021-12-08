package tax.cute.mcpingplugin.commands;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.SingleMessage;
import tax.cute.mcpingplugin.Plugin;
import tax.cute.mcpingplugin.util.Util;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderFriend;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class Enable extends CommandModel {
    Plugin plugin;
    public Enable(Plugin plugin) {
        super("enable");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if(args.length != 1) return;
        if(!plugin.config.isOwner(sender.getSenderID())) return;
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup)sender;
            Group group = senderGroup.getGroup();

            try {
                if (Util.isBoolean(args[0].contentToString())) {
                    this.plugin.config.setEnable(Boolean.parseBoolean(args[0].contentToString()));
                    group.sendMessage("�ѽ�enable����Ϊ" + args[0]);
                } else {
                    group.sendMessage("ʹ�÷���:/enable [true/false] ���û����ñ����");
                }
            } catch (IOException e) {
                group.sendMessage("��д����ʱ�������쳣" + e);
            }
        }

        if (sender instanceof CommandSenderFriend) {
            CommandSenderFriend senderFriend = (CommandSenderFriend)sender;
            Friend friend = senderFriend.getFriend();

            try {
                if (Util.isBoolean(args[0].contentToString())) {
                    this.plugin.config.setEnable(Boolean.parseBoolean(args[0].contentToString()));
                    friend.sendMessage("�ѽ�enable����Ϊ" + args[0]);
                } else {
                    friend.sendMessage("ʹ�÷���:/enable [true/false] ���û����ñ����");
                }
            } catch (IOException e) {
                friend.sendMessage("��д����ʱ�������쳣" + e);
            }
        }
    }
}
