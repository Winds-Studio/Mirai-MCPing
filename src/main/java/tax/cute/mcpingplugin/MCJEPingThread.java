package tax.cute.mcpingplugin;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.mcpingplugin.util.*;

import java.io.IOException;
import java.util.List;

public class MCJEPingThread extends Thread {
    Plugin plugin;
    String host;
    int port;
    Object sendObject;
    public int status = -1;

    public MCJEPingThread(Plugin plugin, String host, int port, Object sendObject) {
        this.host = host;
        this.port = port;
        this.sendObject = sendObject;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ping();
    }

    public void ping() {
        if (port == -1) port = 25565;

        if (sendObject instanceof Group) {
            Group group = (Group) sendObject;
            Srv srv = Srv.getSrv(host,Util.MC_SRV);
            if (srv != null) {
                host = srv.getSrvHost();
                port = srv.getSrvPort();
                group.sendMessage("��⵽����Srv��¼ ���Զ���ת��\n>>\n" + host + ":" + port);
            }
            JETypeset typeset;
            try {
                //��ȡ��Ϣ���Ű�
                typeset = JETypeset.getTypeset(host, port, plugin.JETypesetText);
            } catch (Exception e) {
                this.status = 0;
                return;
            }

            //�Ƿ���ͼ��(���Ҫ���͵Ļ�)
            if (typeset.getFavicon_bytes() != null) {
                Image image = group.uploadImage(ExternalResource.create(typeset.getFavicon_bytes()));
                group.sendMessage(image.plus(typeset.getMotdText()));
            } else {
                group.sendMessage(typeset.getMotdText());
            }

            //����ModList(���Ҫ���͵Ļ�)
            //������ֱ�ӽ���
            if (typeset.getModList() == null) return;
            if (typeset.getModList().size() < 1) return;
            //�����ϲ�ת�������¼
            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            List<String> modList = typeset.getModList();
            int count = 0;
            for (int i = 0; i < modList.size(); i++) {
                count++;
                builder.add(group.getBot().getId(), "Mod" + (i + 1), new PlainText(modList.get(i)));
                //����100�ŷ���,����100������
                if (count == 100) {
                    count = 0;
                    group.sendMessage(builder.build());
                    builder = new ForwardMessageBuilder(group);
                }
                if (count > 100) {
                    group.sendMessage("�������������쳣,�����޷���λ���쳣,����ϵ������");
                    return;
                }
                //����500ֹͣ����,��ֹˢ��
                if (i > 500) {
                    group.sendMessage("Mod��������500,���ڰ�ȫ����,�޷��鿴����(ʲô���ӷ���װ500��Mod?)");
                    return;
                }
            }
            //��ʹС��100,���Ҳ�ᷢ��
            if (count > 0) group.sendMessage(builder.build());
            status = 1;
        }

        if (sendObject instanceof Friend) {
            Friend friend = (Friend) sendObject;
            Srv srv = Srv.getSrv(host, Util.MC_SRV);
            if (srv != null) {
                host = srv.getSrvHost();
                port = srv.getSrvPort();
                friend.sendMessage("��⵽����Srv��¼ ���Զ���ת��\n>>\n" + host + ":" + port);
            }

            JETypeset typeset;
            try {
                //��ȡ��Ϣ���Ű�
                typeset = JETypeset.getTypeset(host, port, plugin.JETypesetText);
            } catch (Exception e) {
                this.status = 0;
                return;
            }

            //�Ƿ���ͼ��(���Ҫ���͵Ļ�)
            if (typeset.getFavicon_bytes() != null) {
                Image image = friend.uploadImage(ExternalResource.create(typeset.getFavicon_bytes()));
                friend.sendMessage(image.plus(typeset.getMotdText()));
            } else {
                friend.sendMessage(typeset.getMotdText());
            }

            //����ModList(���Ҫ���͵Ļ�)
            //������ֱ�ӽ���
            if (typeset.getModList() == null) return;
            if (typeset.getModList().size() < 1) return;
            //�����ϲ�ת�������¼
            ForwardMessageBuilder builder = new ForwardMessageBuilder(friend);
            List<String> modList = typeset.getModList();
            int count = 0;
            for (int i = 0; i < modList.size(); i++) {
                count++;
                builder.add(friend.getBot().getId(), "Mod" + (i + 1), new PlainText(modList.get(i)));
                //����100�ŷ���,����100������
                if (count == 100) {
                    count = 0;
                    friend.sendMessage(builder.build());
                    builder = new ForwardMessageBuilder(friend);
                }
                if (count > 100) {
                    friend.sendMessage("�������������쳣,�����޷���λ���쳣,����ϵ������");
                    return;
                }
                //����500ֹͣ����,��ֹˢ��
                if (i > 500) {
                    friend.sendMessage("Mod��������500,���ڰ�ȫ����,�޷��鿴����(ʲô���ӷ���װ500��Mod?)");
                    return;
                }
            }
            //��ʹС��100,���Ҳ�ᷢ��
            if (count > 0) friend.sendMessage(builder.build());
            status = 1;
        }
    }
}