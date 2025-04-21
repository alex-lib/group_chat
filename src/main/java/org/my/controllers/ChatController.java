package org.my.controllers;
import org.apache.logging.log4j.util.Strings;
import org.my.dto.DtoChatUser;
import org.my.dto.DtoMessage;
import org.my.mappers.ChatUserMapper;
import org.my.mappers.MessageMapper;
import org.my.models.ChatUser;
import org.my.models.Message;
import org.my.repositories.ChatUserRepository;
import org.my.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {
    @Autowired
    private ChatUserRepository chatUserRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/init")
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<ChatUser> chatUserOpt = chatUserRepository.findBySessionId(sessionId);
        response.put("result", chatUserOpt.isPresent());
        return response;
    }

    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name) {
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        ChatUser user = new ChatUser();
        user.setSessionId(sessionId);
        user.setName(name);
        chatUserRepository.save(user);
        response.put("result", true);
        return response;
    }

    @PostMapping("/message")
    public Map<Object, Object> sendMessage(@RequestParam String message)  {
        if(Strings.isEmpty(message)) {
            return Map.of("result", false);
        }
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        ChatUser user = chatUserRepository.findBySessionId(sessionId).get();
        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setUser(user);
        newMessage.setDateTime(LocalDateTime.now());
        messageRepository.saveAndFlush(newMessage);
        return Map.of("result", true);
    }

    @GetMapping("/message")
    public List<DtoMessage> getMessagesList() {
       return messageRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime")).stream()
               .map(MessageMapper::mapMessageToDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<DtoChatUser> getUsersList() {
        return chatUserRepository.findAll().stream()
                .map(ChatUserMapper::mapChatUserToDto)
                .collect(Collectors.toList());
    }
}