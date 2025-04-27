package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
public class FileSaver {
    public void saveTasks(Tasks tasksFolder, String fileName) {
        Map<String, Object> prettyList = new TreeMap<>();
        prettyList.put("taskList", tasksFolder.getTaskList());
        prettyList.put("taskListInProgress", tasksFolder.getTaskListInProgress());
        prettyList.put("taskListDone", tasksFolder.getTaskListDone());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File(fileName), prettyList);
            System.out.println("Task list saved as " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
}
