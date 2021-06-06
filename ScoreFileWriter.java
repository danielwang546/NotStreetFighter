import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreFileWriter implements Writer{

    private File scores;

    public ScoreFileWriter() {
        scores = new File("scores.txt");
        try {
            scores.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writePtsToFile(int points, String name) {
        HashMap<Integer, String> unsorted = new HashMap<Integer, String>();
        String[] fileLines = readFromFile();

        for(String line : fileLines) {
            StringTokenizer st = new StringTokenizer(line);
            unsorted.put(Integer.valueOf(st.nextToken()), st.nextToken());
        }
        unsorted.put(points, name);

        Map<Integer, String> sorted = new TreeMap<Integer, String>(Collections.reverseOrder());
        sorted.putAll(unsorted);

        String sortedStr = "";
        for(Map.Entry<Integer, String> entry : sorted.entrySet()) {
            sortedStr += entry.getKey() + " " + entry.getValue() + "\n";
        }

        writeToFile(sortedStr);
    }

    public String[] getTopScores() {
        String[] out = new String[10];
        try { 
            Scanner scan = new Scanner(scores);

            for(int i = 0; i < 10; i++) {
                if(!scan.hasNextLine())
                    break;
                out[i] = scan.nextLine();
            }

            scan.close();
        }  catch(IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeToFile(String str) {
        try {
            FileWriter fw = new FileWriter(scores, false);
            fw.write(str);
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] readFromFile() {
        ArrayList<String> fileContents = new ArrayList<String>();
        try {
            Scanner scan = new Scanner(scores);
            while(scan.hasNextLine()) {
                fileContents.add(scan.nextLine());
            }
            scan.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return Arrays.copyOf(fileContents.toArray(), fileContents.size(), String[].class);
    }
}
    