package co.devkh.onlinestore.reviewonlinestore.CGPT.data.RQ;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CURQ(
        @JsonProperty("message")String message
) {
}
