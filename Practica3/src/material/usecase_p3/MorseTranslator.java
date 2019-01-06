package material.usecase_p3;

import java.util.Iterator;

import material.Position;
import material.tree.binarytree.LinkedBinaryTree;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import material.tree.iterators.BFSIterator;;

public class MorseTranslator {

    /**
     * Generates a new MorseTranslator instance given two arrays:
     * one with the character set and another with their respective
     * codifications in morse. (the charset never contains spaces)
     * @param charset
     * @param codes
     */
	private LinkedBinaryTree<Character> charTree;
	private char[] charset;
	private String[] codes;
	
    public MorseTranslator(char[] charset, String[] codes){
        this.charset = charset;
        this.codes = codes;
        charTree = new LinkedBinaryTree<>();
        charTree.addRoot(' ');
        int cont;
        String aux;
        Position<Character> pos;
        for(int i = 0; i<charset.length; i++) {
        	cont = 0;
        	aux = codes[i];
        	pos = charTree.root();
        	while(cont<aux.length()) {
        		if(aux.charAt(cont) == '.') {
        			if(charTree.hasLeft(pos)) {
        				pos = charTree.left(pos);
        			}else {
        				if(cont < aux.length()-1) {
        					charTree.insertLeft(pos, ' ');
        					pos = charTree.left(pos);
        				}else {
            				charTree.insertLeft(pos, charset[i]);
        				}
        			}
        		}else if(aux.charAt(cont) == '-'){
        			if(charTree.hasRight(pos)) {
        				pos = charTree.right(pos);
        			}else {
        				if(cont < aux.length()-1) {
        					charTree.insertRight(pos, ' ');
        					pos = charTree.right(pos);
        				}else {
            				charTree.insertRight(pos, charset[i]);
        				}
        			}
        		}
        		cont++;
        	}
        }
    }

    /**
     * Decodes a String with a message in morse code and returns
     * another String in plaintext. The input String may contain
     * the characters: ' ', '-' '.'.
     * @param morseMessage
     * @return a plain text translation of the morse code
     */
    public String decode(String morseMessage){
        int cont = 0;
        String result = "";
        Position<Character> pos = charTree.root();
    	while(cont<morseMessage.length()) {
        	if(morseMessage.charAt(cont) == '.') {
        		if(charTree.hasLeft(pos)) {
        			pos = charTree.left(pos);
        		}else {
    				result = result.concat(pos.getElement().toString());
        			pos = charTree.root();
        			pos = charTree.left(pos);
        		}
        	}else if(morseMessage.charAt(cont) == '-'){
        		if(charTree.hasRight(pos)) {
        			pos = charTree.right(pos);
        		}else {
    				result = result.concat(pos.getElement().toString());
        			pos = charTree.root();
        			pos = charTree.right(pos);
        		}
        	}else {
        		if(cont == morseMessage.length()-1) {
        			break;
        		}else {
        			if(!result.equals(""))
        				result = result.concat(pos.getElement().toString());
        			else
        				result = pos.getElement().toString();
        			pos = charTree.root();
        		}
        	}
        	cont++;
        }
    	
    	if(!result.equals(""))
			result = result.concat(pos.getElement().toString());
		else
			result = pos.getElement().toString();
    	
    	return result;
    }

    /**
     * Receives a String with a message in plaintext. This message
     * may contain any character in the charset.
     * @param plainText
     * @return a morse code message
     */
    public String encode(String plainText){
        int cont = 0;
        String morseMessage = "";
        BFSIterator it;
        Character aux;
        Position<Character> parent;
        Position<Character> currentPos;
        boolean found = false;
    	while(cont < plainText.length()) {
            aux = plainText.charAt(cont);
            found = false;
            it = new BFSIterator(charTree);
            currentPos = (Position<Character>) it.next();
    		while(it.hasNext() && !found) {
        		currentPos = (Position<Character>) it.next();
        		parent = charTree.parent(currentPos);
        		if(aux != ' ') {
	        		if(charTree.left(parent) == currentPos) {
	        			morseMessage = morseMessage.concat(".");
	        		}else {
	        			morseMessage = morseMessage.concat("-");
	        		}
        		}else {
        			if(cont<plainText.length()-1)
        				morseMessage = morseMessage.concat("  ");
        			else
        				morseMessage = morseMessage.concat(" ");
        			found = true;
        		}
        		
        		if(currentPos.getElement() == aux) {
        			found = true;
        		}
        	}
    		cont++;
        }
        return morseMessage + " ";
    }



}
