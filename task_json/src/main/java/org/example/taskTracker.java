package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.io.*;
import java.io.File;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Tasks {
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

class Choice {
    public void switcher(Tasks tasksFolder) {

        System.out.println("This is task list.");
        Scanner sc = new Scanner(System.in, "ibm866");
        while (true) {
            System.out.print("Choose action: ");
            System.out.println("Add, Update, Mark in progress, Mark done, Delete, List, Exit");
            String commandInput = sc.nextLine();
            if (commandInput.equalsIgnoreCase("exit")) {
                break;
            } else {
                switch (commandInput.toLowerCase().trim()) {
                    case "add":
                        System.out.println("Type in the task: ");
                        String taskToAdd = sc.nextLine();
                        tasksFolder.addTask(taskToAdd);
                        System.out.println("Task added successfully. (id: " + tasksFolder.getId() + ")");
                        break;
                    case "update":
                        System.out.println("Type in the task number: ");
                        int numberToUpdate = sc.nextInt();
                        System.out.println("Type in new task: ");
                        sc.nextLine();
                        tasksFolder.taskUpdater(numberToUpdate, sc.nextLine());
                        break;
                    case "mark in progress":
                        if (tasksFolder.getTaskList().isEmpty() && tasksFolder.getTaskListDone().isEmpty()) {
                            System.out.println("No task to mark.");
                        } else {
                            System.out.println("Type in the task number: ");
                            int taskToMark = sc.nextInt();
                            sc.nextLine();
                            tasksFolder.taskMarker(taskToMark, commandInput);
                        }
                        break;
                    case "mark done":
                        if (tasksFolder.getTaskListInProgress().isEmpty() && tasksFolder.getTaskList().isEmpty()) {
                            System.out.println("No task to mark.");
                        } else {
                            System.out.println("Type in the task number: ");
                            int taskToMark = sc.nextInt();
                            sc.nextLine();
                            tasksFolder.taskMarker(taskToMark, commandInput);
                        }
                        break;
                    case "delete":
                        if (tasksFolder.getTaskList().isEmpty() && tasksFolder.getTaskListInProgress().isEmpty() &&
                                tasksFolder.getTaskListDone().isEmpty()) {
                            System.out.println("List empty!");
                        } else {
                            System.out.println("Type in the task number: ");
                            int taskToDelete = sc.nextInt();
                            sc.nextLine();
                            tasksFolder.taskMarker(taskToDelete, commandInput);
                        }
                        break;
                    case "list":
                        System.out.println("What list to show? To-do, in progress, all");
                        String listType = sc.nextLine();
                        switch (listType.toLowerCase().trim()) {
                            case "todo", "to-do":
                                if (tasksFolder.getTaskList().isEmpty()) {
                                    System.out.println("List empty.");
                                } else {
                                    tasksFolder.getTaskList().forEach((key, value) -> {
                                        System.out.println(key + value);
                                    });
                                }
                                break;
                            case "done":
                                if (tasksFolder.getTaskListDone().isEmpty()) {
                                    System.out.println("List empty.");
                                } else {
                                    tasksFolder.getTaskListDone().forEach((key, value) -> {
                                        System.out.println(key + value);
                                    });
                                }
                                break;
                            case "in progress":
                                if (tasksFolder.getTaskListInProgress().isEmpty()) {
                                    System.out.println("List empty.");
                                } else {
                                    tasksFolder.getTaskListInProgress().forEach((key, value) -> {
                                        System.out.println(key + value);
                                    });
                                }
                                break;
                            case "all":
                                System.out.println("To-do task list: ");
                                tasksFolder.getTaskList().forEach((key, value) -> {
                                    System.out.println(key + value);
                                });
                                System.out.println("Task list in progress: ");
                                tasksFolder.getTaskListInProgress().forEach((key, value) -> {
                                    System.out.println(key + value);
                                });
                                System.out.println("Done tasks list: ");
                                tasksFolder.getTaskListDone().forEach((key, value) -> {
                                    System.out.println(key + value);
                                });
                                break;
                        }
                }
            }
        }
        sc.close();
    }
}
class FileSaver {
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
class taskTracker {
    final static String fileName = "tasks.json";
    public static void main(String[] args) {
        FileSaver fileSaver = new FileSaver();
        Tasks tasksFolder = null;
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