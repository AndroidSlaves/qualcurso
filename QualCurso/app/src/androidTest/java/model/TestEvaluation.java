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

        Evaluation evaluation_2 = new Evaluation();
        evaluation_2.setYear(1900);
        evaluation_2.setModality("modality");
        evaluation_2.setMasterDegreeStartYear(1);
        evaluation_2.save();

        assertEquals(initialCount+1, Evaluation.count());
        assertEquals(Evaluation.getAll().size(), Evaluation.count());

        evaluation_2.delete();
    }


    public void testShouldGetEvaluationOnDataBase() throws ClassNotFoundException, SQLException{

        Evaluation evaluation_1 = new Evaluation();
        evaluation_1.setDoctorateStartYear(1);
        evaluation_1.setTriennialEvaluation(2);
        evaluation_1.setPermanentTeachers(3);
        evaluation_1.setModality("modality");
        evaluation_1.setArtisticProduction(3);
        evaluation_1.save();

        Evaluation evaluation_2 = Evaluation.get(Evaluation.last().getId());
        assertEquals(evaluation_1.getDoctorateStartYear(), evaluation_2.getDoctorateStartYear());
        assertEquals(evaluation_1.getTriennialEvaluation(), evaluation_2.getTriennialEvaluation());
        assertEquals(evaluation_1.getPermanentTeachers(), evaluation_2.getPermanentTeachers());
        assertEquals(evaluation_1.getArtisticProduction(), evaluation_2.getArtisticProduction());
        evaluation_1.delete();
    }


    public void testShouldGetAllEvaluationsOnDataBase() throws ClassNotFoundException, SQLException {
        int total = Evaluation.count();

        assertEquals(total, Evaluation.getAll().size());
        assertEquals(Evaluation.first().getTheses(), Evaluation.getAll().get(0).getTheses());
        assertEquals(Evaluation.first().getDissertations(), Evaluation.getAll().get(0).getDissertations());
        assertEquals("", Evaluation.first().get("test"));
    }


    public void testShouldGetTheFirstEvaluationOnDataBase() throws ClassNotFoundException, SQLException {
        Evaluation firstEvaluation = Evaluation.first();
        assertEquals(firstEvaluation.getModality(), Evaluation.getAll().get(0).getModality());
    }


    public void testShouldGetTheLastEvaluationOnDataBase() throws ClassNotFoundException, SQLException {
        Evaluation lastEvaluation = Evaluation.last();
        int last = Evaluation.getAll().size()-1;
        assertEquals(lastEvaluation.getArtisticProduction(), Evaluation.getAll().get(last).getArtisticProduction());
    }
}
