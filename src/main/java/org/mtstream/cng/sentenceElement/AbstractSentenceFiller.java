package org.mtstream.cng.sentenceElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSentenceFiller {

    abstract public Pattern getPattern();

    abstract public String getReplacement(String group);

    public final String fill(String sentenceStructure) {
        Matcher matcher = getPattern().matcher(sentenceStructure);
        String origin = sentenceStructure;
        while (matcher.find()) {
            String replacement = getReplacement(matcher.group(1));
            origin = origin.replaceFirst(matcher.group(0), replacement);
        }
        return origin;
    }
}
