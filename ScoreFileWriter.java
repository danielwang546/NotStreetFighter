import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.ArrayList;
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
        try {
            FileWriter scoresFW = new FileWriter(scores, false);
            Scanner scan = new Scanner(scores);
            HashMap<Integer, String> unsorted = new HashMap<Integer, String>();
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                int highScore = Integer.valueOf(st.nextToken());
                String hsName = String.valueOf(st.nextToken());
                unsorted.put(highScore, hsName);
            }
            unsorted.put(points, name);

            Map<Integer, String> sorted = new TreeMap<Integer, String>();
            sorted.putAll(unsorted);

            for(Map.Entry<Integer, String> entry : sorted.entrySet()) {
                scoresFW.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
            
            scoresFW.close();
            scan.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
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
        return (String[])fileContents.toArray();
    }
}
    