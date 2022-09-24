package org.mtstream.cng.stringGenerator.sentenceFiller;

import org.mtstream.cng.resourcesInteractor.ResourceReader;
import org.mtstream.cng.stringGenerator.AbstractSentenceFiller;
import org.mtstream.cng.stringGenerator.SpecialElementHandler;

import java.util.regex.Pattern;

public class SpecialElementFiller extends AbstractSentenceFiller {
    @Override
    protected Pattern getPattern() {
        return Pattern.compile("@(.|[^@#]+)#");
    }

    @Override
    public String getReplacement(String group) {
        String replacement = SpecialElementHandler.resolve(group);
        if (replacement.equals(ResourceReader.NOT_FOUND)) return "*找不到特殊元素"+group+"*";
        return replacement;
    }
}
