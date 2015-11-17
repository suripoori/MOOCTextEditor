package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.lang.RuntimeException;
/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		String delims = "[ ]*";
		StringTokenizer st = new StringTokenizer(sourceText, delims);
		String w = "";
		while (st.hasMoreElements()){
			boolean foundStarter = false;
			w = st.nextToken();
			if(w.equals("")){
				System.out.println(starter);
				break;
			}
			for(ListNode ln : wordList){
				if (ln.getWord().equals(starter)){
					//System.out.println("Adding word " + w + " to " + starter +"'s nextWords");
					ln.addNextWord(w);
					starter = w;
					foundStarter = true;
					break;
				}
			}
			if (!foundStarter){
				//System.out.println("Creating newStarter " + starter);
				//System.out.println("Adding word " + w);
				ListNode newStarter = new ListNode(starter);
				newStarter.addNextWord(w);
				starter = w;
				wordList.add(newStarter);
			}
		}
		/*if (wordList.size() > 0){
			wordList.get(wordList.size()-1).addNextWord("");
		}*/
		boolean foundLast = false;
		for(ListNode ln : wordList){
			if (ln.getWord().equals(w)){
				ln.addNextWord("");
				foundLast = true;
			}
		}
		if (!foundLast){
			ListNode last = new ListNode(w);
			last.addNextWord("");
			wordList.add(last);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (wordList.size() == 0){
			return "";
		}
		starter = "";
		String output = "";
		for (int i=0; i<numWords; i++){
			String nextWord = getNextWord(starter);
			if (nextWord == null || nextWord.equals("")){
				starter = "";
				nextWord = getNextWord(starter);
			}
			starter = nextWord;
			if (output.equals("")){
				output = nextWord;
			}
			else{
				output += " " + nextWord;
			}
		}
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	private String getNextWord(String starter){
		for (ListNode ln : wordList){
			if (ln.getWord().equals(starter)){
				String nextWord = ln.getRandomNextWord(rnGenerator);
				return nextWord;
			}
		}
		return null;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString3 = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
		System.out.println(textString3);
		gen.retrain(textString3);
		System.out.println(gen);
		String res = gen.generateText(500);
		System.out.println(res);
		/*String[] words = res.split("[\\s]+");
        System.out.println("\n** Test #3: Checking requested generator word count...");
        System.out.println("Your generator produced " + words.length + " words. ");*/
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int index = generator.nextInt(nextWords.size());
	    return nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


