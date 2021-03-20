import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
// using copyonwritearraylist avoids a concurrent modification exception
// whilst performing the lines:
//   permuteString(args[0], ans);
//   for (String str : results)
    
public class anagram {
    
    static CopyOnWriteArrayList<String> results = new CopyOnWriteArrayList<String>();
    static String dictfile = "/usr/share/dict/american-english";


    // generates unique permutations of str and stores them in results
    // Credit to GeeksforGeeks for inspiring this recursive function
    static void permuteString(String str, String ans){
	
        if (str.length() == 0) { 
            results.add(ans);
            return; 
        }   
	// this boolean array ensures only unique permutations
        boolean alpha[] = new boolean[26]; 
        for (int i = 0; i < str.length(); i++) { 
            char ch = str.charAt(i);  
            // Rest of the string after excluding the ith character
            String ros = str.substring(0, i) + str.substring(i + 1); 
            if (alpha[ch - 'a'] == false) 
                permuteString(ros, ans + ch); 
            alpha[ch - 'a'] = true; 
        } 
    }

    // lookupString uses the file stored in dictfile to lookup words.
    static boolean isValidWord(String query){
	System.out.print("Looking up " + query + "\r");
	BufferedReader reader;
	boolean isWord = false;
	try {
	    reader = new BufferedReader(new FileReader(dictfile));
	    String line = reader.readLine();
	    while(!line.equals("a")) // skip capitalized section
		line = reader.readLine();
	    while (line != null) {
		
		if (line.charAt(0) > query.charAt(0)){
		    // System.out.println("reached entry " + line);
		    // System.out.println(line.charAt(0) + " > " + str.charAt(0));
		    break;
		}
		if (query.equals(line)){
		    isWord = true;
		    break;
		}
		line = reader.readLine();
	    }
	    reader.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return isWord;
    }
    
    
    public static void main(String args[]) {
	if (args.length != 1){
	    System.out.println("Please supply one word as an argument.");
	    return;
	}
	
	String ans = "";
	System.out.print("Generating permutations...\r");
	permuteString(args[0], ans);
	for (String str : results){
	    // System.out.println("Looking up " + str);
	    if (!isValidWord(str)){
		results.remove(str);
	    }
	}
	System.out.print("done!\r");
	if (results.size() <= 1){
	    System.out.println("No anagrams found for " + args[0]);
	} else {
	    for (String str: results)
		System.out.print(str + " ");
	    System.out.print("\n");
	}
    }
    
}
