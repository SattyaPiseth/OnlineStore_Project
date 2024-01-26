package co.devkh.onlinestore.reviewonlinestore.CGPT.data.RS;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record CGPTRS(
        String id,
        String object,
        String model,
        LocalDate created,
        @JsonProperty("choices")List<CGPTChoice> choices,

        @JsonProperty("usage") CGPTUsage usage
        ) {
}
