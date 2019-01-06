package tests;

import material.maps.*;
import material.maps.Map; //No confundir con la interfaz Map de Java.util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashTableMapTest {
    Integer output, oldValue;
    Map<String, Integer> map;
    
    
    public Map<String, Integer> newMapInstance(){
        return new HashTableMapSC<>();
    }
    
    public Map<String, Integer> newMapInstance(int capacity){
        return new HashTableMapSC<>(capacity);
    }

    @BeforeEach
    void setUp() {
        map = newMapInstance();
        output = null;
        oldValue = null;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size() {
        assertEquals(map.size(), 0);

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        map.put("Abraham", new Integer(912127003));
        assertEquals(map.size(), 3);
        map.put("Mayte", new Integer(912127004));
        map.put("Raul", new Integer(912127005));
        map.put("Jesus", new Integer(91212006));
        map.put("Juan", new Integer(912127006));
        assertEquals(map.size(), 7);

        map.remove("Angel");
        map.remove("Mayte");
        assertEquals(map.size(), 5);
    }

    @Test
    void isEmpty() {
        assertTrue(map.isEmpty());
        map.put("Jesus", new Integer(91212006));
        assertFalse(map.isEmpty());
        map.remove("Jesus");
        assertTrue(map.isEmpty());
    }

    @Test
    void put() {
        Exception e = assertThrows(IllegalStateException.class,()->map.put(null, new Integer(0)));
        assertEquals("Invalid key: null.", e.getMessage());

        oldValue = map.put("Jesus", new Integer(91212006));
        output = map.get("Jesus");
        assertEquals(91212006, output.intValue());
        assertNull(oldValue);

        oldValue = map.put("Jesus", new Integer(91212007));

        output = map.get("Jesus");
        assertEquals(91212007, output.intValue());
        assertEquals(91212006, oldValue.intValue());

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        map.put("Mayte", new Integer(912127004));
        map.put("Raul", new Integer(912127005));
        map.put("Juan", new Integer(912127006));
        map.put("Abraham", new Integer(912127003));
        assertEquals(map.size(), 7);


    }

    @Test
    void get() {
        Exception e = assertThrows(IllegalStateException.class, ()-> map.get(null));
        assertEquals("Invalid key: null.", e.getMessage());

        output = map.get("Abraham");
        assertNull(output);

        map.put("Abraham", new Integer(912127003));
        output = map.get("Abraham");
        assertEquals(912127003,output.intValue());

    }

    @Test
    void remove() {
        Exception e = assertThrows(IllegalStateException.class, ()-> map.get(null));
        assertEquals("Invalid key: null.", e.getMessage());

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        assertEquals(2, map.size());


        oldValue = map.remove("Abraham");
        assertEquals(2, map.size());
        assertNull(oldValue);

        oldValue = map.remove("Jose");
        assertEquals(1,map.size());
        assertEquals(912127001, oldValue.intValue());
        output = map.get("Jose");
        assertNull(output);

        oldValue = map.remove("Angel");
        assertEquals(0,map.size());
        assertEquals(912127002, oldValue.intValue());
        output = map.get("Angel");
        assertNull(output);
        assertTrue(map.isEmpty());
    }

    @Test
    void keys() {
        String[] nombres = new String[]{"Jose", "Angel", "Abraham", "Mayte", "Raul", "Jesus", "Juan"};
        List<String> list = new LinkedList<>(Arrays.asList(nombres));

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        map.put("Abraham", new Integer(912127003));
        map.put("Mayte", new Integer(912127004));
        map.put("Raul", new Integer(912127005));
        map.put("Jesus", new Integer(91212006));
        map.put("Juan", new Integer(912127006));
        assertEquals(list.size(),map.size());

        for (String s : map.keys()) {
            assertTrue(list.contains(s));
            list.remove(s);
        }
        assertTrue(list.isEmpty());

    }

    @Test
    void values() {
        Integer[] values = new Integer[]{912127001,912127002,912127003,912127004,912127005,912127006,912127006};
        List<Integer> list = new LinkedList<>(Arrays.asList(values));

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        map.put("Abraham", new Integer(912127003));
        map.put("Mayte", new Integer(912127004));
        map.put("Raul", new Integer(912127005));
        map.put("Jesus", new Integer(912127006));
        map.put("Juan", new Integer(912127006));
        assertEquals(list.size(),map.size());

        for (Integer integer : map.values()) {
            assertTrue(list.contains(integer));
            list.remove(integer);
        }
        assertTrue(list.isEmpty());

    }

    @Test
    void entries() {
        Integer[] values = new Integer[]{912127001,912127002,912127003,912127004,912127005,912127006,912127006};
        List<Integer> integerList = new LinkedList<>(Arrays.asList(values));
        String[] nombres = new String[]{"Jose", "Angel", "Abraham", "Mayte", "Raul", "Jesus", "Juan"};
        List<String> stringList = new LinkedList<>(Arrays.asList(nombres));

        map.put("Jose", new Integer(912127001));
        map.put("Angel", new Integer(912127002));
        map.put("Abraham", new Integer(912127003));
        map.put("Mayte", new Integer(912127004));
        map.put("Raul", new Integer(912127005));
        map.put("Jesus", new Integer(912127006));
        map.put("Juan", new Integer(912127006));
        assertEquals(stringList.size(),map.size());

        for (Entry<String, Integer> entry : map.entries()) {
            assertTrue(stringList.contains(entry.getKey()));

            int indexOfKey = stringList.indexOf(entry.getKey());
            stringList.remove(indexOfKey);
            output = integerList.remove(indexOfKey);
            assertEquals(output.intValue(), entry.getValue().intValue());
        }
        assertTrue(stringList.isEmpty());

    }

    @Test
    void rehash() {
        map = newMapInstance(100);
        int n = 1000;
        for(int i=0; i<n; i++){
            map.put(Integer.toString(i),i);
            assertNull(output);
        }
        assertEquals(n, map.size());
        for(int i=0; i<n; i++){
            Integer integer = map.get(Integer.toString(i));
            assertNotNull(integer);
            assertEquals(i, integer.intValue());
        }
    }

    @Test
    void collisions() {
            map = newMapInstance(100);
            int n = 2000;
            int n2 = 500;

            for(int i=0; i<n; i++)
                output = map.put(Integer.toString(i),i);

            //insert n elements [0, n)
            for(int i=n/2; i<n;i++){
                output = map.remove(Integer.toString(i));
                assertNotNull(output);
                assertEquals(i, output.intValue());
            }

            //remove n/2 elements
            for(int i=0; i<n/2; i++){
                output = map.get(Integer.toString(i));
                assertNotNull(output);
                assertEquals(i,output.intValue());
            }

            //insert n2 new elements [n,n+n2)
            for(int i=0; i<n2; i++){
                map.put(Integer.toString(i+n), i+n);
            }

            //check first batch
            for(int i=0; i<n/2; i++){
                Integer integer = map.get(Integer.toString(i));
                assertNotNull(integer);
                assertEquals(i, integer.intValue());
            }

            //check second batch
            for(int i=0;i<n2;i++){
                Integer integer = map.get(Integer.toString(i+n));
                assertNotNull(integer);
                assertEquals(i+n, integer.intValue());
            }

    }
}