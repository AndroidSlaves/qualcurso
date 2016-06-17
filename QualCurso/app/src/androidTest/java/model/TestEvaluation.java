package model;

import android.database.SQLException;
import android.test.AndroidTestCase;

import models.Evaluation;

/**
 * Created by Vinícius Carvalho on 16/06/2016.
 */
public class TestEvaluation extends AndroidTestCase {
    @Override
    protected void tearDown() throws Exception{

    }

    public void testShouldCountEvaluationsOnDataBase() throws ClassNotFoundException, SQLException {
        int initialCount = Evaluation.count();

        Evaluation evaluation = new Evaluation();
        evaluation.setYear(2016);
        evaluation.setModality("modality");
        evaluation.setMasterDegreeStartYear(1);
        evaluation.save();

        assertEquals(initialCount+1, Evaluation.count());
        assertEquals(Evaluation.getAll().size(), Evaluation.count());

        evaluation.delete();
    }
}
