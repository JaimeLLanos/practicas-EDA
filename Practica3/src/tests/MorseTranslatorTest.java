package tests;

import material.usecase_p3.MorseTranslator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseTranslatorTest {

    MorseTranslator translator;


    @BeforeEach
    void setUp() {
        char[] charset = {  'a','h','r','u','l',
                            'n', 'd','o','m','e',
                            'w'};
        String[] codes={    ".","..","...","..-",".-"
                            ,".-.", ".--","-","-.","--."
                            ,"---"};

        translator = new MorseTranslator(charset,codes);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void decode() {
        String input = ".";
        String expected = "a";
        String output = translator.decode(input);
        assertEquals(expected,output);

        input = ". ";
        expected = "a";
        output = translator.decode(input);
        assertEquals(expected, output);

        input = ". .";
        expected = "aa";
        output = translator.decode(input);
        assertEquals(expected, output);

        input = "..";
        expected = "h";
        output = translator.decode(input);
        assertEquals(expected, output);


        input = ".. "+"- "+".- "+". "+" "+"-."+"..-"+".-."+".--"+"-";
        expected = "hola mundo";
        output = translator.decode(input);
        assertEquals(expected, output);

        input = ".. --..- .- -  ---- ....- .--";
        expected = "hello world";
        output = translator.decode(input);
        assertEquals(expected,output);

    }

    @Test
    void encode() {
        String input = "a";
        String expected = ". ";
        String output = translator.encode(input);
        assertEquals(expected,output);

        input = "a ";
        expected = ".  ";
        output = translator.encode(input);
        assertEquals(expected,output);

        input = "a a";
        expected = ".  . ";
        output = translator.encode(input);
        assertEquals(expected,output);

        input = "h";
        expected = ".. ";
        output = translator.encode(input);
        assertEquals(expected,output);

        input = "u";
        expected = "..-";
        output = translator.encode(input);
        assertEquals(expected,output);

        input = "hola mundo";
        expected = ".. "+"- "+".- "+". "+" "+"-."+"..-"+".-."+".--"+"- ";
        output = translator.encode(input);
        assertEquals(expected,output);

        input = "hello world";
        expected = ".. --..- .- -  ---- ....- .--";
        output = translator.encode(input);
        assertEquals(expected,output);


    }
}