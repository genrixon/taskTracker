package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.*;
import java.io.File;

public class taskTracker {
    final static String fileName = "tasks.json";
    public static void main(String[] args) {
        FileSaver fileSaver = new FileSaver();
        Tasks tasksFolder;
        Choice choice = new Choice();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tasksFolder = objectMapper.readValue(new File(fileName), Tasks.class);
            int maxID = 0;
            for (Map.Entry<String, String> tasks : tasksFolder.getTaskList().entrySet()) {
                String rawID = tasks.getKey();
                String[] parts = rawID.split("[: .]");
                int currentId = Integer.parseInt(parts[2]);
                if (currentId > maxID) {
                    maxID = currentId;
                }
            }
            for (Map.Entry<String, String> tasks : tasksFolder.getTaskListInProgress().entrySet()) {
                String rawID = tasks.getKey();
                String[] parts = rawID.split("[: .]");
                int currentId = Integer.parseInt(parts[2]);
                if (currentId > maxID) {
                    maxID = currentId;
                }
            }
            for (Map.Entry<String, String> tasks : tasksFolder.getTaskListDone().entrySet()) {
                String rawID = tasks.getKey();
                String[] parts = rawID.split("[: .]");
                int currentId = Integer.parseInt(parts[2]);
                if (currentId > maxID) {
                    maxID = currentId;
                }
            }
            tasksFolder.setId(maxID);
            System.out.println("Tasks been read from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading: " + e.getMessage());
            tasksFolder = new Tasks();
            System.out.println("File has been created automatically.");
        }
        choice.switcher(tasksFolder);
        fileSaver.saveTasks(tasksFolder, fileName);
    }
}