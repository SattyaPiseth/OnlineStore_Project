package co.devkh.onlinestore.reviewonlinestore.CGPT.data.RS;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CGPTChoice(
        String text,
        Integer index,
        String logprobs,
        @JsonProperty("finish_reason") String finishReason
) {
}
