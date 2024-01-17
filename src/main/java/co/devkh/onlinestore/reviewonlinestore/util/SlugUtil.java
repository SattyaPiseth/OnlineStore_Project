package co.devkh.onlinestore.reviewonlinestore.util;

public class SlugUtil {
    public static String createSlug(String input) {
        // Logic to generate a slug from input (e.g., product name)
        // Example: replace spaces with dashes and make it lowercase
        return input.toLowerCase().replaceAll("\\s+", "-");
    }
}
