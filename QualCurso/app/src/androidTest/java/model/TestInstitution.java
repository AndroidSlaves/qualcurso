package model;

import android.test.AndroidTestCase;

import libraries.DataBaseStructures;
import models.Institution;
import unb.mdsgpp.qualcurso.QualCurso;

public class TestInstitution extends AndroidTestCase {
    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
        DataBaseStructures db = new DataBaseStructures();
        db.dropDB();
        db.initDB();
    }
    @Override
    protected void setUp() {

    }

    @Override
    protected void tearDown() {
    }

    public void testShouldCreateNewInstitutionOnDataBase() {
        Institution institution = new Institution();
        institution.setAcronym("three");
        institution.save();
        assertEquals("three", Institution.last().getAcronym());
        institution = Institution.last();
        institution.delete();
    }
}
