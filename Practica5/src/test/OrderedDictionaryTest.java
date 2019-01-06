package test;

import material.Position;
import material.ordereddictionary.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderedDictionaryTest {
    OrderedDictionary<String, Integer> dict;

    private OrderedDictionary<String, Integer> newDictionary(){
        return new AVLOrderedDict<>();
    }


    @BeforeEach
    void setUp() {
        dict = newDictionary();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size() {
        assertEquals(dict.size(),0);
        dict.insert("Angel", 9151592);
        assertEquals(dict.size(),1);
        dict.insert("Angel", 9151591);
        assertEquals(dict.size(),2);
        dict.insert("Jose",  9100000);
        assertEquals(dict.size(),3);
    }

    @Test
    void isEmpty() {
        assertEquals(dict.isEmpty(),true);
        dict.insert("Angel", 9151592);
        assertEquals(dict.isEmpty(),false);
    }

    @Test
    void find() {
        dict.insert("Angel", 9151592);
        dict.insert("Angel", 9151591);
        dict.insert("Jose",  9100000);
        Entry<String,Integer> contacto = dict.find("Jose");
        assertEquals(contacto.getValue().intValue(),9100000);
    }

    @Test
    void findAll() {
        int [] telefono = {9151592,9151591,9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose",  telefono[2]);
        TreeSet <Integer> cjtoTelefonos = new TreeSet<Integer>();
        for (int cont = 0; cont < 3; cont++)
            cjtoTelefonos.add(telefono[cont]);

        Iterable<Entry <String,Integer>> it = dict.findAll("Angel");
        for (Entry <String,Integer> contacto : it) {
            assertEquals(cjtoTelefonos.contains(contacto.getValue()),true);
        }
    }

    @Test
    void insert() {
        Random random = new Random(0);
        final int N=1000;
        for (int cont = 0; cont < N; cont++) {
            dict.insert(Integer.toString(random.nextInt(N)), cont);
        }
    }

    @Test
    void remove() {
        int [] telefono = {9151592,9151591,9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose",  telefono[2]);
        Entry <String,Integer> e1 = dict.find("Jose");
        dict.remove(e1);
        Entry <String,Integer> f1 = dict.find("Jose");
        assertEquals(f1,null);
        assertEquals(dict.size(),2);
        Entry <String,Integer> e2 = dict.find("Angel");
        dict.remove(e2);
        assertEquals(dict.size(),1);
        Entry <String,Integer> e3 = dict.find("Angel");
        dict.remove(e3);
        assertEquals(dict.size(),0);
    }

    @Test
    void findRange() {
    	int [] telefono = {9151592,9151591,9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Amalio", telefono[0]);
        dict.insert("Carlos", telefono[1]);
        dict.insert("Jose",  telefono[2]);
        

        RuntimeException exception = assertThrows(RuntimeException.class, () -> dict.findRange("Jose", "Angel"));
        assertEquals("Invalid range. (min>max)", exception.getMessage());

        Iterable<Entry<String,Integer>> range;
        String output, expected;
        final StringBuilder sb = new StringBuilder();

        sb.setLength(0);//clear
        range = dict.findRange("K", "K");
        assertNotNull(range);
        expected = "";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = dict.findRange("X", "Z");
        assertNotNull(range);
        expected = "";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = dict.findRange("C", "C");
        assertNotNull(range);
        expected = " 1";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = dict.findRange("A", "D");
        assertNotNull(range);
        expected = "Carlos";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = dict.findRange("A", "B");
        assertNotNull(range);
        expected = "Amalio Angel";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = dict.findRange("A", "Z");
        assertNotNull(range);
        expected = "Amalio Angel Carlos Jose";
        range.forEach(p -> sb.append(' ').append(p));
        output = sb.toString();
        assertEquals(expected, output);
    }
}