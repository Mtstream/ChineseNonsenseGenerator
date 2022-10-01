package org.mtstream.cng.sentenceElement.sentenceFiller;

import org.mtstream.cng.Main;
import org.mtstream.cng.sentenceElement.AbstractSentenceFiller;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.util.regex.Pattern;

public class WordFiller extends AbstractSentenceFiller {
    @Override
    public Pattern getPattern() {
        return Pattern.compile("/(.{4})");
    }

    @Override
    public String getReplacement(String group) {
        String replacement = ResourceReader.getRandomWord(group);
        if(replacement.equals(ResourceReader.NOT_FOUND)){
            replacement = Main.outputError("*找不到词语"+group+"*");
        }
        return replacement;
    }
}
