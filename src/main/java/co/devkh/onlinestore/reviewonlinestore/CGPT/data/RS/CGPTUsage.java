package co.devkh.onlinestore.reviewonlinestore.CGPT.data.RS;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CGPTUsage(
        @JsonProperty("prompt_tokens") String promptTokens,
        @JsonProperty("completion_tokens") Integer completionTokens,
        @JsonProperty("total_tokens") Integer totalTokens
) {
}
