package org.mtstream.cng.stringGenerator.sentenceFiller;

import org.mtstream.cng.stringGenerator.AbstractSentenceFiller;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.util.regex.Pattern;

public class SentencePatternFiller extends AbstractSentenceFiller {
    @Override
    protected Pattern getPattern() {
        return Pattern.compile("~(.{4})");
    }

    @Override
    public String getReplacement(String group) {
        String replacement = ResourceReader.getRandomSentence(group);
        if(replacement.equals(ResourceReader.NOT_FOUND)){
            replacement = "*找不到句子"+group+"*";
        }
        return replacement;
    }
}
