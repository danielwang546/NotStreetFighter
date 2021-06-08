import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
        TreeMap<Integer, ArrayList<String>> sorted = new TreeMap<Integer, ArrayList<String>>(Collections.reverseOrder());
        String[] fileLines = readFromFile();

        for(String line : fileLines) {
            int space = line.indexOf(" ");
            int pts = Integer.valueOf(line.substring(0, space));
            String nm = line.substring(space + 1);

            if(!sorted.containsKey(pts)) {
                sorted.put(pts, new ArrayList<String>());
            }
            sorted.get(pts).add(nm);
        }
        if(!sorted.containsKey(points)) {
            sorted.put(points, new ArrayList<String>());
        }
        sorted.get(points).add(name);

        String sortedStr = "";
        for(Map.Entry<Integer, ArrayList<String>> entry : sorted.entrySet()) {
            for(String nameWPts : entry.getValue()) {
                sortedStr += entry.getKey() + " " + nameWPts + "\n";
            }
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
    