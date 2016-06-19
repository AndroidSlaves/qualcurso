package model;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import models.Book;


public class TestBook extends AndroidTestCase {


    public void testSetIdBook(){
        Book idBook = new Book();
        idBook.setId(10);
        assertEquals(idBook.getId(), 10);
    }

    public void testSetIdBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-1);
        assertNotSame(idBook.getId(), 10);
    }

    public void testGetIdBook(){
        Book idBook = new Book();
        idBook.setId(15);
        assertEquals(15, idBook.getId());
    }

    public void testGetIdBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-121);
        assertNotSame(112, idBook.getId());
    }

    public void testSetIntegralTextBook(){
        Book idBook = new Book();
        idBook.setId(21);
        assertEquals(idBook.getId(), 21);
    }

    public void testSetIntegralTextBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-10);
        assertNotSame(idBook.getId(), 10);
    }

    public void testGetIntegralTextBook(){
        Book idBook = new Book();
        idBook.setId(21);
        assertEquals(21, idBook.getId());
    }

    public void testGetIntegralTextBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-155);
        assertNotSame(idBook.getId(), 551);
    }


}
