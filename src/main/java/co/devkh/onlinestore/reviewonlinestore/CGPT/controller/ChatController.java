package co.devkh.onlinestore.reviewonlinestore.CGPT.controller;

import co.devkh.onlinestore.reviewonlinestore.CGPT.data.RQ.CURQ;
import co.devkh.onlinestore.reviewonlinestore.CGPT.data.RS.CGPTRS;
import co.devkh.onlinestore.reviewonlinestore.CGPT.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("chat")
    public CGPTRS sendMessage(@ModelAttribute @RequestBody CURQ userRequest){
        return chatService.inputText(userRequest);
    }
}
