package tax.cute.mcpingplugin.commands;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import tax.cute.mcpingplugin.Plugin;
import tax.cute.mcpingplugin.Server;
import tax.cute.mcpingplugin.util.Util;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderFriend;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;
import java.util.List;

public class BindServer extends CommandModel {
    Plugin plugin;

    public BindServer(Plugin plugin) {
        super("bindServer");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (!plugin.config.isEnable()) return;
        if (!plugin.config.isOwner(sender.getSenderID())) return;

        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup) sender;
            Group group = senderGroup.getGroup();
            try {
                if (args[0].contentToString().equalsIgnoreCase("/bindServer")) {
                    group.sendMessage(
                            "# �÷�" +
                                    "\n# /bindServer add [Ⱥ��] [����] [��ַ] ���һ����" +
                                    "\n# /bindServer remove [Ⱥ��] �Ƴ�һ����" +
                                    "\n# /bindServer list �鿴���б�" +
                                    "\n# ps:����\"this\"��ָ��Ⱥ �Ƴ�ʱ����all��ָ����Ⱥ"
                    );
                    return;
                }
                if (args[0].contentToString().equalsIgnoreCase("add")) addBind(args, group);
                if (args[0].contentToString().equalsIgnoreCase("remove")) removeBind(args, group);
                if (args[0].contentToString().equalsIgnoreCase("list")) getBind(group);
            } catch (IOException e) {
                group.sendMessage("��д�����ļ�ʱ�������쳣\n" + e);
            }
        }

        if (sender instanceof CommandSenderFriend) {
            CommandSenderFriend senderFriend = (CommandSenderFriend) sender;
            Friend friend = senderFriend.getFriend();
            try {
                if (args[0].contentToString().equalsIgnoreCase("/bindServer")) {
                    friend.sendMessage(
                            "# �÷�" +
                                    "\n# /bindServer add [Ⱥ��] [����] [��ַ] ���һ����" +
                                    "\n# /bindServer remove [Ⱥ��] �Ƴ�һ����" +
                                    "\n# /bindServer list �鿴���б�" +
                                    "\n# ps:����\"this\"��ָ��Ⱥ �Ƴ�ʱ����all��ָ����Ⱥ"
                    );
                    return;
                }
                if (args[0].contentToString().equalsIgnoreCase("add")) addBind(args, friend);
                if (args[0].contentToString().equalsIgnoreCase("remove")) removeBind(args, friend);
                if (args[0].contentToString().equalsIgnoreCase("list")) getBind(friend);
            } catch (IOException e) {
                friend.sendMessage("��д�����ļ�ʱ�������쳣\n" + e);
            }
        }
    }

    private void addBind(SingleMessage[] args, Object sendObject) throws IOException {
        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            if (args.length != 4) {
                group.sendMessage("������������");
                return;
            }
            long num = 0;
            if (args[1].contentToString().equalsIgnoreCase("this"))
                num = group.getId();
            else if (Util.isNum(args[1].contentToString()))
                num = Long.parseLong(args[1].contentToString());
            else
                group.sendMessage("�������������(��Ҫ����)");

            String cmd = args[2].contentToString();
            String host = args[3].contentToString();
            if (plugin.config.addBindServer(new Server(num, cmd, host)))
                group.sendMessage("�󶨳ɹ� ���ڸ�Ⱥ���� " + cmd + " ��ȡ" + host + "����Ϣ");
            else group.sendMessage("��ʧ�� ��Ⱥ�Ѱ�");
        } else if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            if (args.length != 4) {
                friend.sendMessage("������������");
                return;
            }
            long num;
            if (Util.isNum(args[1].contentToString()))
                num = Long.parseLong(args[1].contentToString());
            else {
                friend.sendMessage("�������������(��Ҫ����)");
                return;
            }

            String cmd = args[2].contentToString();
            String host = args[3].contentToString();
            if (plugin.config.addBindServer(new Server(num, cmd, host)))
                friend.sendMessage("�󶨳ɹ� ���ڸ�Ⱥ���� " + cmd + " ��ȡ" + host + "����Ϣ");
            else friend.sendMessage("��ʧ�� ��Ⱥ�Ѱ�");
        }
    }

    private void removeBind(SingleMessage[] args, Object sendObject) throws IOException {
        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            if (args.length != 2) {
                group.sendMessage("�����������");
                return;
            }
            long num = -1;
            if (args[1].contentToString().equalsIgnoreCase("all")) {
                group.sendMessage("����հ�����(" + plugin.config.clearBindServer() + "��)");
            } else {
                if (args[1].contentToString().equalsIgnoreCase("this"))
                    num = group.getId();
                else if (Util.isNum(args[1].contentToString()))
                    num = Long.parseLong(args[1].contentToString());
                else
                    group.sendMessage("�������������(��Ҫ����)");

                if (num != -1) {
                    if (plugin.config.removeBindServer(num))
                        group.sendMessage(num + "���ٰ󶨷�����");
                    else
                        group.sendMessage("���ʧ��:��Ⱥû�а󶨷�����");
                }
            }
        } else if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            if (args.length != 2) {
                friend.sendMessage("�����������");
                return;
            }
            long num = -1;
            if (args[1].contentToString().equalsIgnoreCase("all")) {
                friend.sendMessage("����հ�����(" + plugin.config.clearBindServer() + "��)");
            } else {
                if (Util.isNum(args[1].contentToString()))
                    num = Long.parseLong(args[1].contentToString());
                else
                    friend.sendMessage("�������������(��Ҫ����)");

                if (num != -1) {
                    if (plugin.config.removeBindServer(num))
                        friend.sendMessage(num + "���ٰ󶨷�����");
                    else
                        friend.sendMessage("���ʧ��:��Ⱥû�а󶨷�����");
                }
            }
        }
    }

    private void getBind(Object sendObject) {
        List<Server> servers = plugin.config.getBindServers();
        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            if (servers.size() < 1) {
                group.sendMessage("û������");
                return;
            }
            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            int count = 0;
            for (int i = 0; i < servers.size(); i++) {
                count++;
                builder.add(group.getBot().getId(), "Server" + (i + 1), new PlainText(
                        "Group:" + servers.get(i).getGroup() +
                                "\nCmd:" + servers.get(i).getCmd() +
                                "\nHost:" + servers.get(i).getHost()
                ));
                //����count ���ͺϲ�ת�� ���ForwardMessageBuilder
                if (count == 100) {
                    count = 0;
                    group.sendMessage(builder.build());
                    builder = new ForwardMessageBuilder(group);
                }
                if (count > 100) {
                    group.sendMessage("�������������쳣,�����޷���λ���쳣,����ϵ������");
                    return;
                }
            }
            //��ʹû�ϰ�,���Ҳ�ᷢ��
            if (count > 0) group.sendMessage(builder.build());
        } else if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            if (servers.size() < 1) {
                friend.sendMessage("û������");
                return;
            }
            ForwardMessageBuilder builder = new ForwardMessageBuilder(friend);
            int count = 0;
            for (int i = 0; i < servers.size(); i++) {
                count++;
                builder.add(friend.getBot().getId(), "Server" + (i + 1), new PlainText(
                        "Group:" + servers.get(i).getGroup() +
                                "\nCmd:" + servers.get(i).getCmd() +
                                "\nHost:" + servers.get(i).getHost()
                ));
                //����count ���ͺϲ�ת�� ���ForwardMessageBuilder
                if (count == 100) {
                    count = 0;
                    friend.sendMessage(builder.build());
                    builder = new ForwardMessageBuilder(friend);
                }
                if (count > 100) {
                    friend.sendMessage("�������������쳣,�����޷���λ���쳣,����ϵ������");
                    return;
                }
            }
            //��ʹû�ϰ�,���Ҳ�ᷢ��
            if (count > 0) friend.sendMessage(builder.build());
        }
    }
}
