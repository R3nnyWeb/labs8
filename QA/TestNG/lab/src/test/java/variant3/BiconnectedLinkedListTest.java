package variant3;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class BiconnectedLinkedListTest {
    /**
     * -- Часть 1. Готовые тесты.
     **/
    @Test
    public void test_addInEnd() {
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInEnd(5);
        list.addInEnd(6);
        list.addInEnd(7);
        //assertTrue(list.isEmpty());
        assertFalse(list.isEmpty());
        assertEquals(list.deleteFirst(), 5);
        assertEquals(list.deleteFirst(), 6);
        assertEquals(list.deleteFirst(), 7);
    }

    @Test
    public void test_addInBeginning() {
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInBeginning(5);
        list.addInBeginning(6);
        list.addInBeginning(7);
        //assertFalse(list.isEmpty());
        assertEquals(list.deleteFirst(), 7);
        assertEquals(list.deleteFirst(), 6);
        assertEquals(list.deleteFirst(), 5);
    }

    /**
     * -- Часть 2. Напишите свои после этой строки.
     **/
    @Test
    public void test_addInEnd_delLast() {
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInEnd(5);
        list.addInEnd(6);
        list.addInEnd(7);
        assertFalse(list.isEmpty());
        assertEquals(list.deleteLast(), 7);
        assertEquals(list.deleteLast(), 6);
        assertEquals(list.deleteLast(), 5);
    }

    @Test
    public void test_addInBeginning_delLast() {
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInBeginning(5);
        list.addInBeginning(6);
        list.addInBeginning(7);
        assertFalse(list.isEmpty());
        assertEquals(list.deleteLast(), 5);
        assertEquals(list.deleteLast(), 6);
        assertEquals(list.deleteLast(), 7);
    }

    //Вынесем данные за пределы тестов
    private BiconnectedLinkedList<Integer> getIntegerList(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInEnd(5);
        list.addInEnd(6);
        list.addInEnd(7);
        return list;
    }
    @Test
    public void test_isEmpty(){
        BiconnectedLinkedList<Integer> list1 = new BiconnectedLinkedList<>();
        BiconnectedLinkedList<Integer> list2 = getIntegerList();
        assertTrue(list1.isEmpty());
        assertFalse(list2.isEmpty());
    }
    @Test
    public void test_positiveElementsNumber(){
        BiconnectedLinkedList<Integer> list = getIntegerList();
        assertEquals(list.getElementsNumber(), 3);
    }

    @Test
    public void test_zeroElementsNumber(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        assertEquals(list.getElementsNumber(), 0);
    }
    @Test
    public void test_deleteList(){
        BiconnectedLinkedList<Integer> list = getIntegerList();
        list.deleteList();
        assertTrue(list.isEmpty());
    }
    @Test
    public void test_nullDeleteFirst(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        assertThrows(NullPointerException.class, list::deleteFirst);
    }
    @Test
    public void test_nullDeleteLast(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        assertThrows(NullPointerException.class, list::deleteLast);
    }
    @Test
    public void test_oneElementDeleteFirst(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInBeginning(1);
        assertEquals(list.deleteFirst(), 1);
        assertTrue(list.isEmpty());
    }
    @Test
    public void test_oneElementDeleteLast(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInBeginning(1);
        assertEquals(list.deleteLast(), 1);
        assertTrue(list.isEmpty());
    }

    @Test
    public void test_fullLogicTest(){
        BiconnectedLinkedList<Integer> list = new BiconnectedLinkedList<>();
        list.addInBeginning(4);
        list.addInBeginning(5);
        list.addInBeginning(6);
        list.addInEnd(7);
        list.addInEnd(8);
        list.addInEnd(9);
        //6,5,4,7,8,9
        assertEquals(list.getElementsNumber(), 6);
        assertEquals(list.deleteFirst(), 6);
        assertEquals(list.deleteFirst(), 5);
        assertEquals(list.getElementsNumber(), 4);

        assertEquals(list.deleteLast(), 9);
        assertEquals(list.deleteLast(), 8);
        assertEquals(list.getElementsNumber(), 2);

        list.deleteList();
        assertEquals(list.getElementsNumber(), 0);
        assertTrue(list.isEmpty());

        assertThrows(NullPointerException.class, list::deleteFirst);
        assertThrows(NullPointerException.class, list::deleteLast);

        list.addInBeginning(1);
        assertEquals(list.deleteFirst(), 1);
        assertTrue(list.isEmpty());

        list.addInEnd(1);
        assertEquals(list.deleteLast(), 1);
        assertTrue(list.isEmpty());
    }
}