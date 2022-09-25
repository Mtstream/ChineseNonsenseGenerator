package org.mtstream.cng.sentenceElement.sentenceFiller;

import org.mtstream.cng.resourcesInteractor.ResourceReader;
import org.mtstream.cng.sentenceElement.AbstractSentenceFiller;
import org.mtstream.cng.sentenceElement.SpecialElementHandler;

import java.util.regex.Pattern;

public class SpecialElementFiller extends AbstractSentenceFiller {
    @Override
    public Pattern getPattern() {
        return Pattern.compile("@(.|[^@#]+)#");
    }

    @Override
    public String getReplacement(String group) {
        String replacement = SpecialElementHandler.resolve(group);
        if (replacement.equals(ResourceReader.NOT_FOUND)) return "*找不到特殊元素*";
        return replacement;
    }
}
