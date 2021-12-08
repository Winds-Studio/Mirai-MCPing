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

public class Owner extends CommandModel {
    Plugin plugin;

    public Owner(Plugin plugin) {
        super("lp");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (!plugin.config.isEnable()) return;
        if (!plugin.config.isOwner(sender.getSenderID())) return;
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup) sender;
            Group group = senderGroup.getGroup();
            if (args[0].contentToString().equalsIgnoreCase("/lp")) {
                group.sendMessage(
                        "�÷�:" +
                        "\n/lp [add/remove] [qq��] ��ӻ��Ƴ�����" +
                                "\n/lp list �鿴�����б�"
                );
                return;
            }

            if (args.length == 1) {
                if (args[0].contentToString().equalsIgnoreCase("list"))
                    sendList(group);
            }
            if (args.length == 2) {
                if (Util.isNum(args[1].contentToString())) {
                    long num = Long.parseLong(args[1].contentToString());
                    try {
                        if (args[0].contentToString().equalsIgnoreCase("add"))
                            add(group, num);

                        if (args[0].contentToString().equalsIgnoreCase("remove"))
                            remove(group, num);

                    } catch (IOException e) {
                        group.sendMessage("��д����ʱ�������쳣\n" + e);
                    }
                }
            }
        } else if (sender instanceof CommandSenderFriend) {
            CommandSenderFriend senderFriend = (CommandSenderFriend) sender;
            Friend friend = senderFriend.getFriend();
            if (args.length == 1) {
                if (args[0].contentToString().equalsIgnoreCase("list"))
                    sendList(friend);
            }
            if (args.length == 2) {
                if (Util.isNum(args[1].contentToString())) {
                    long num = Long.parseLong(args[1].contentToString());
                    try {
                        if (args[0].contentToString().equalsIgnoreCase("add"))
                            add(friend, num);

                        if (args[0].contentToString().equalsIgnoreCase("remove"))
                            remove(friend, num);

                    } catch (IOException e) {
                        friend.sendMessage("��д����ʱ�������쳣\n" + e);
                    }
                }
            }
        }

    }

    private void add(Object sendObject, long qqNum) throws IOException {
        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            plugin.config.addOwner(qqNum);
            group.sendMessage("�����" + qqNum + "Ϊ����");
        } else if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            plugin.config.addOwner(qqNum);
            friend.sendMessage("�����" + qqNum + "Ϊ����");
        }
    }

    private void remove(Object sendObject, long qqNum) throws IOException {
        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            plugin.config.removeOwner(qqNum);
            group.sendMessage(qqNum + "������������");
        } else if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            plugin.config.removeOwner(qqNum);
            friend.sendMessage(qqNum + "������������");
        }
    }

    private void sendList(Group group) {
        StringBuilder sb = new StringBuilder("�����б�:\n");
        for (Long i : plugin.config.getOwner()) {
            sb.append(i).append("\n");
        }
        group.sendMessage(sb.toString());
    }

    private void sendList(Friend friend) {
        StringBuilder sb = new StringBuilder("�����б�:\n");
        for (Long i : plugin.config.getOwner()) {
            sb.append(i).append("\n");
        }
        friend.sendMessage(sb.toString());
    }
}