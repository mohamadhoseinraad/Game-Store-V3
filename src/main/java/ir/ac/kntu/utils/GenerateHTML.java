package ir.ac.kntu.utils;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

public class GenerateHTML {
    public static void generateHTML(String title, ArrayList<Object> input) {
        try {
            FileWriter myWriter = new FileWriter("export" + java.time.LocalDateTime.now() + ".html");
            addStartCode(myWriter, title);
            addArrayList(myWriter, input);
            addEndCode(myWriter);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void addStartCode(FileWriter fileWriter, String title) throws IOException {
        fileWriter.write("<!DOCTYPE html> \n <html>\n<head>\n");
        fileWriter.write("<title>" + title + "</title>\n\n</head>\n<body>\n");
        fileWriter.write("<h1>");
        fileWriter.write(title);
        fileWriter.write("</h1>");
        fileWriter.write("<br>");
    }

    public static void addEndCode(FileWriter fileWriter) throws IOException {
        fileWriter.write("\n</body>\n</html>");
    }

    public static void addArrayList(FileWriter fileWriter, ArrayList<Object> input) throws IOException {
        for (Object object : input) {
            fileWriter.write("<p>");
            fileWriter.write(object.toString());
            fileWriter.write("</p>");
            fileWriter.write("<br>");
        }

    }

}