package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.RuntimeException;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		char [] chArray = word.toCharArray();
		TrieNode temp = root;
		boolean insert = false;
		try{
			for (char c : chArray){
				if (temp.getChild(Character.toLowerCase(c)) != null){
					temp = temp.getChild(Character.toLowerCase(c));
					if (temp.getText().equals(word.toLowerCase()) && !temp.endsWord()){
						temp.setEndsWord(true);
						insert = true;
					}
				}
				else {
					temp = temp.insert(Character.toLowerCase(c));
					insert = true;
				}
			}
			temp.setEndsWord(true);
			if(insert){
				size++;
				return insert;
			}
			else {
				return false;
			}
		}
		catch (RuntimeException e){
			throw e;
		}
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		char[] chArray = s.toCharArray();
		TrieNode temp = root;
		if (chArray.length == 0){
			return false;
		}
		for (char c : chArray){
			if (temp.getChild(Character.toLowerCase(c)) != null){
				temp = temp.getChild(Character.toLowerCase(c));
			}
			else{
				return false;
			}
		}
		if (temp.getText().equals(s.toLowerCase()) && temp.endsWord()){
			return true;
		}
		else {
			return false;
		}
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> suggestions = new LinkedList<String>();
    	 char[] chArray = prefix.toCharArray();
    	 TrieNode temp = root;
    	 Queue<TrieNode> bfsq = new LinkedList<TrieNode>();
    	 // Identify the stem
    	 for (char c : chArray){
    		 if (temp.getChild(Character.toLowerCase(c)) != null){
    			 temp = temp.getChild(Character.toLowerCase(c));
    		 }
    	 }
    	 if(!temp.getText().equals(prefix)){
    		 return suggestions;
    	 }
    	 Set<Character> childSet;
    	 //System.out.println("Prefix is: " + temp.getText());
    	 if (temp.endsWord()){
    		 suggestions.add(temp.getText());
    	 }
    	 while(true){
    		 // Identify the children of temp
        	 childSet = temp.getValidNextCharacters();
        	 // Add all the child nodes to the bfsq
        	 for (char c : childSet){
        		 bfsq.add(temp.getChild(c));
        	 }
        	 if (bfsq.isEmpty()){
        		 break;
        	 }
        	 else if (suggestions.size() >= numCompletions){
    			 //System.out.println("Breaking out of loop: " + bfsq.size() + " " + suggestions.size());
    			 break;
    		 }
    		 else{
    			 temp = bfsq.remove();
    			 if (temp.endsWord()){
    				 suggestions.add(temp.getText());
    			 }
    		 }
    	 }
    	 return suggestions;
    	 
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}