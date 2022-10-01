package org.mtstream.cng.stringGenerator;

import org.mtstream.cng.sentenceElement.AbstractSentenceFiller;
import org.mtstream.cng.sentenceElement.sentenceFiller.RandomPickFiller;
import org.mtstream.cng.sentenceElement.sentenceFiller.SentencePatternFiller;
import org.mtstream.cng.sentenceElement.sentenceFiller.SpecialElementFiller;
import org.mtstream.cng.sentenceElement.sentenceFiller.WordFiller;

import java.util.*;

public class SentenceFactory {

    private static SentenceFactory MAIN_FACTORY;

    private List<AbstractSentenceFiller> fillerList;

    private int level;

    public SentenceFactory() {
        this.fillerList = new ArrayList<>();
        this.level = 0;
    }

    public void insertFiller(AbstractSentenceFiller filler) {
        fillerList.add(filler);
    }

    public String fillSentence(String structure)  {
        level++;
        System.out.println(indent(this.level-1)+"Start Filling: "+structure);
        String origin = structure;

        while (!checkFilledCompletely(origin)){
            System.out.println(indent(this.level-1)+"Filling: "+origin);
            for(AbstractSentenceFiller filler : fillerList) {
                origin = filler.fill(origin);
            }
        }
        System.out.println(indent(this.level-1)+"Filled: "+origin);
        level--;
        return origin;
    }

    public boolean checkFilledCompletely(String str) {
        for(AbstractSentenceFiller filler : fillerList){
            if(filler.getPattern().matcher(str).find()) {
                return false;
            }
        }
        return true;
    }

    public static String indent(int times) {
        return String.valueOf(new char[times*2]).replace("\0", " ");
    }

    public static void bootstrap() {
        SentenceFactory factory = new SentenceFactory();
        factory.insertFiller(new RandomPickFiller());
        factory.insertFiller(new WordFiller());
        factory.insertFiller(new SentencePatternFiller());
        factory.insertFiller(new SpecialElementFiller());
        MAIN_FACTORY = factory;
    }

    public static SentenceFactory getMainFactory() {
        return MAIN_FACTORY;
    }
}
