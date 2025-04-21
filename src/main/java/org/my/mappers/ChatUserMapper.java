package org.my.mappers;
import org.my.dto.DtoChatUser;
import org.my.models.ChatUser;

public class ChatUserMapper {
    public static DtoChatUser mapChatUserToDto(ChatUser chatUser) {
        DtoChatUser dtoChatUser = new DtoChatUser();
        dtoChatUser.setName(chatUser.getName());
        return dtoChatUser;
    }
}
