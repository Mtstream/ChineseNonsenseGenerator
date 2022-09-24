package org.mtstream.cng.stringGenerator;

import org.mtstream.cng.stringGenerator.sentenceFiller.RandomPickFiller;
import org.mtstream.cng.stringGenerator.sentenceFiller.SentencePatternFiller;
import org.mtstream.cng.stringGenerator.sentenceFiller.SpecialElementFiller;
import org.mtstream.cng.stringGenerator.sentenceFiller.WordFiller;

import java.util.*;

public class SentenceFactory {

    public static String fillSentence(String structure)  {
        System.out.println("Start Filling: "+structure);
        String origin = structure;
        List<AbstractSentenceFiller> fillerList = new ArrayList<>(List.of(new SpecialElementFiller(), new RandomPickFiller(), new SentencePatternFiller(), new WordFiller()));
        while (!checkFilledCompletely(fillerList, origin)){
            for(AbstractSentenceFiller filler : fillerList) {
                origin = filler.fill(origin);
            }
        }
        return origin;
    }

    public static boolean checkFilledCompletely(List<AbstractSentenceFiller> fillerList, String str) {
        for(AbstractSentenceFiller filler : fillerList){
            if(filler.getPattern().matcher(str).find()) {
                return false;
            }
        }
        return true;
    }
}
