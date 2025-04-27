package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class Tasks {
    @JsonProperty("taskList")
    private Map<String, String> taskList = new TreeMap<>();
    @JsonProperty("taskListInProgress")
    private Map<String, String> taskListInProgress = new TreeMap<>();
    @JsonProperty("taskListDone")
    private Map<String, String> taskListDone = new TreeMap<>();
    private int id;

    public void setTaskListDone(TreeMap<String, String> taskListDone) {
        this.taskListDone = taskListDone;
    }

    public void setTaskListInProgress(TreeMap<String, String> taskListInProgress) {
        this.taskListInProgress = taskListInProgress;
    }

    public void setTaskList(TreeMap<String, String> taskList) {
        this.taskList = taskList;
    }

    public TreeMap<String, String> getTaskList() {
        return (TreeMap<String, String>) taskList;
    }

    public TreeMap<String, String> getTaskListInProgress() {
        return (TreeMap<String, String>) taskListInProgress;
    }

    public TreeMap<String, String> getTaskListDone() {
        return (TreeMap<String, String>) taskListDone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUpdateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void addTask(String input) {
        setId(++id);
        taskList.put("ID: " + id + ". ", input + ". " + "Last updated: " + getUpdateTime());
    }

    public void taskUpdater(int index, String input) {
        String keyIndex = "ID: " + index + ". ";
        String tail = ". Last updated: " + getUpdateTime();
        if (taskList.containsKey(keyIndex)) {
            taskList.put(keyIndex, input + tail);
        }
        if (taskListInProgress.containsKey(keyIndex)) {
            if (taskList.containsKey(keyIndex)) {
                taskList.put(keyIndex, input + tail);
            }
        }
    }

    public void taskMarker(int index, String command) {
        String keyIndex = "ID: " + index + ". ";
        String valueTODO = taskList.get(keyIndex);
        String valueINPROGRESS = taskListInProgress.get(keyIndex);
        String valueDONE = taskListDone.get(keyIndex);
        switch (command) {
            case "mark in progress":
                if (taskListInProgress.containsKey(keyIndex)) {
                    System.out.println("Task already in progress.");
                    break;
                }
                taskList.remove(keyIndex, valueTODO);
                taskListInProgress.put(keyIndex, valueTODO);
                break;
            case "mark done":
                if (taskListDone.containsKey(keyIndex)) {
                    System.out.println("Task already done.");
                    break;
                } else if (taskList.containsKey(keyIndex)) {
                    taskList.remove(keyIndex, valueTODO);
                    taskListDone.put(keyIndex, valueTODO);
                } else if (taskListInProgress.containsKey(keyIndex)) {
                    taskListInProgress.remove(keyIndex, valueINPROGRESS);
                    taskListDone.put(keyIndex, valueINPROGRESS);
                }
                break;
            case "delete":
                if (taskList.containsKey(keyIndex)) {
                    taskList.remove(keyIndex, valueTODO);
                } else if (taskListInProgress.containsKey(keyIndex)) {
                    taskListInProgress.remove(keyIndex, valueINPROGRESS);
                } else if (taskListDone.containsKey(keyIndex)) {
                    taskListDone.remove(keyIndex, valueDONE);
                }
                break;
        }
    }
}
