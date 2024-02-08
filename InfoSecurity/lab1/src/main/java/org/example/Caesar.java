package org.example;

import java.util.List;

public final class Caesar {
    private final char start;
    private final char end;
    private final char size;

    public Caesar(char start, char end) {
        this.start = start;
        this.end = end;
        this.size = (char) (end - start + 1);
    }


    public String encrypt(String str, int k) {
        if (k < 0) throw new IllegalArgumentException("Shift must be non negative");
        var words = str.split("\\s");
        var sb = new StringBuilder();
        for (String word : words) {
            for (Character ch : word.toCharArray()) {
                if (ch < start || ch > end)
                    throw new IllegalArgumentException("String cotains illegal character " + ch);
                var fromStart = ch - start;
                var shifted = (fromStart + k) % size;
                sb.append((char) (shifted + start));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
