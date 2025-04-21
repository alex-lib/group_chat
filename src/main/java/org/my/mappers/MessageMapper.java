package org.my.mappers;
import org.my.dto.DtoMessage;
import org.my.models.Message;

public class MessageMapper {
    public static DtoMessage mapMessageToDto(Message message) {
        DtoMessage dtoMessage = new DtoMessage();
        dtoMessage.setText(message.getMessage());
        dtoMessage.setDateTime(message.getDateTime().toString());
        dtoMessage.setUserName(message.getUser().getName());
        return dtoMessage;
    }
}