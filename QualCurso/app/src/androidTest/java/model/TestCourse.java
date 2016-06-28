package model;

import android.test.AndroidTestCase;
import models.Course;

public class TestCourse extends AndroidTestCase {

    public void testSetIdCourse(){
        Course idCourse = new Course();
        idCourse.setId(42);
        assertEquals(idCourse.getId(), 42);
    }

    public void testSetIdCourseMustFail(){
        Course idCourse = new Course();
        idCourse.setId(-1);
        assertNotSame(idCourse.getId(), 10);
    }

    public void testGetIdCourse(){
        Course idCourse = new Course();
        idCourse.setId(15);
        assertEquals(15, idCourse.getId());
    }

    public void testGetIdBookMustFail(){
        Course idCourse = new Course();
        idCourse.setId(-121);
        assertNotSame(112, idCourse.getId());
    }

    public void testSetName(){
        Course nameCourse = new Course();
        nameCourse.setName("Fogo e Gelo");
        assertEquals(nameCourse.getName(), "Fogo e Gelo");
    }

    public void testSetNameMustFail(){
        Course nameCourse = new Course();
        nameCourse.setName("Fogo e Gelo");
        assertNotSame(nameCourse.getName(), "FOGO e Gelo");
    }

    public void testSetNameNotNull(){
        Course nameCourse = new Course();
        nameCourse.setName("Princesa de Fogo");
        assertNotNull(nameCourse.getName());
    }

}
