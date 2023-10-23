import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import javax.swing.DefaultListModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyFile {
    MyFile(){};
    
    
    public static void writeFile(DefaultListModel<String> text) {
        // Specify the file name and path
        String fileName = "cache.txt";

        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

                

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < text.size(); i++) {
            bufferedWriter.write(text.get(i) + "\n"); // Append "\n" to each line
        }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile() {
        String fileName = "cache.txt";
        String output = "";
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(fileName);

            // Create a BufferedReader to improve read performance
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read and print the contents of the text file
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output = output + line + "\n";
            }
            
            // Close the BufferedReader and FileReader to release resources
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
