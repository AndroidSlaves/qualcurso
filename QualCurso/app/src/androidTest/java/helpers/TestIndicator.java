package helpers;

import android.test.AndroidTestCase;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Evaluation;

/**
 * Created by vinisilvacar on 16/06/16.
 */
public class TestIndicator extends AndroidTestCase {
    public static final String DEFAULT_INDICATOR = "defaultIndicator";

    public void testShouldGetAListOfIndicators() {
        ArrayList<Indicator> indicators = Indicator.getIndicators();

        assert(indicators.size() > 0);
        assertEquals(indicators.get(0).getValue(), DEFAULT_INDICATOR);
        assertEquals(indicators.get(1).getValue(), new Evaluation().fieldsList().get(7));
        assertEquals(indicators.get(8).getValue(), new Book().fieldsList().get(2));
        assertEquals(indicators.get(12).getValue(), new Article().fieldsList().get(1));
    }

    public void testShouldIndicatorByValue() {
        Indicator indicator = Indicator.getIndicatorByValue(new Evaluation().fieldsList().get(7));
        assertEquals(new Evaluation().fieldsList().get(7), indicator.getValue());

        Indicator indicator2 = Indicator.getIndicatorByValue(new Evaluation().fieldsList().get(7));
        assertEquals(new Evaluation().fieldsList().get(7), indicator2.getValue());

    }

    public void testShouldModifyIndicator(){
        Indicator indicator = Indicator.getIndicatorByValue(new Evaluation().fieldsList().get(7));
        indicator.setName("qualCurso");
        indicator.setValue("qualCurso");
        assertEquals("qualCurso",indicator.getName());

        Indicator indicator2 = Indicator.getIndicatorByValue(new Evaluation().fieldsList().get(7));
        indicator2.setName("app Qual Curso");
        indicator2.setValue("Qual Curso");
        assertEquals("app Qual Curso", indicator2.getName());
        assertEquals("Qual Curso", indicator2.getValue());
    }
}
