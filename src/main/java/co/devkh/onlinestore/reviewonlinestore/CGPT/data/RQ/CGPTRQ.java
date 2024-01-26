package co.devkh.onlinestore.reviewonlinestore.CGPT.data.RQ;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CGPTRQ(
        @JsonProperty("model") String model,
        @JsonProperty("prompt") String prompt,
        @JsonProperty("max_tokens") Integer maxTokens,
        @JsonProperty("temperature") Double temperature,
        @JsonProperty("top_p")  Double topP
) {
}
