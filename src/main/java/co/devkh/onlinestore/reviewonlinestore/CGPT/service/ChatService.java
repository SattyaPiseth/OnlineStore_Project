package co.devkh.onlinestore.reviewonlinestore.CGPT.service;

import co.devkh.onlinestore.reviewonlinestore.CGPT.data.RQ.CGPTRQ;
import co.devkh.onlinestore.reviewonlinestore.CGPT.data.RQ.CURQ;
import co.devkh.onlinestore.reviewonlinestore.CGPT.data.RS.CGPTRS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final RestTemplate restTemplate;

    @Value("${chatgpt.api.key}")
    private String chatGptAPIKey;
    @Value("${chatgpt.url}")
    private String chatGptUrl;
    @Value("${chatgpt.model}")
    private String chatGptModel;

    public CGPTRS inputText(CURQ request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+chatGptAPIKey);

        CGPTRQ gptRequest = new CGPTRQ(
                chatGptModel,
                request.message(),
                2048,
                0.1,
                1.0
        );

        HttpEntity<Object> httpEntity = new HttpEntity<Object> (gptRequest,headers);

    ResponseEntity<CGPTRS> responseEntity = restTemplate.exchange(chatGptUrl,
            HttpMethod.POST,httpEntity,CGPTRS.class);
        return responseEntity.getBody();
    }
}
