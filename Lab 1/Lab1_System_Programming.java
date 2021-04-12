import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;  

public class Lab1_System_Programming {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();

        int maxWordLength = 30;
        
        String englishDel ="[^a-zA-Z]";
        String englishVow = "aeiouyAEIOUY";
        String russianDel ="[^а-яА-Я]";
        String russianVow = "аеёиоуэыАЕЁИОУЭЫ";
        String ukrainianDel ="[^а-яА-Я’їієЇІЄ]";
        String ukrainianVow = "аоуеиіяєюїАОУЕИІЯЄЮЇ";

        String delimeters = englishDel;
        String vowels = englishVow;
        Set<String> words = new HashSet<String>();

        File file = new File(filename);
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            for (String string : scanner.nextLine().split(delimeters)) {
                if(!string.equals("")){
                words.add((string.length() > maxWordLength) ? string.substring(0, maxWordLength) : string);
                }
            }
        }
        scanner.close();
        
        int counter = 0;
        double ratio = 0;
        SortedMap<Double, Set<String>> wordsSortedMap = new TreeMap<Double, Set<String>>(); 
        for(String item : words){
            for (int j = 0; j < item.length(); j++) {
                counter += vowels.contains(item.subSequence(j, j+1)) ? 1 : 0;
            }
            ratio = (double)counter / (double)item.length();
            if (wordsSortedMap.get(ratio) == null){
                Set<String> set = new HashSet<String>();
                set.add(item);
                wordsSortedMap.put(ratio, set);
            } else {
                wordsSortedMap.get(ratio).add(item);
            }
            counter = 0;
        }

        ArrayList<String> resultList = new ArrayList<String>();
        for(Map.Entry<Double, Set<String>> item : wordsSortedMap.entrySet()){
            for (String string : item.getValue()) {
                resultList.add(string);
            }
        }

        System.out.print(resultList);
    }
}