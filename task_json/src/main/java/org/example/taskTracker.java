package org.example;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.io.File;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Tasks {
    private ArrayList<String> taskList = new ArrayList<>();
    private ArrayList<String> taskListInProgress = new ArrayList<>();
    private ArrayList<String> taskListDone = new ArrayList<>();
    private static int idCounter = 0;
    private int id;


    public void setTaskListDone(ArrayList<String> taskListDone) {
        this.taskListDone = taskListDone;
    }
    public void setTaskListInProgress(ArrayList<String> taskListInProgress) {
        this.taskListInProgress = taskListInProgress;
    }
    public void setTaskList(ArrayList<String> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<String> getTaskList() {
        return taskList;
    }

    public ArrayList<String> getTaskListInProgress() {
        return taskListInProgress;
    }

    public ArrayList<String> getTaskListDone() {
        return taskListDone;
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
        this.id = ++idCounter;
        taskList.add("ID: " + id + ". " + input + ". Last update: " + getUpdateTime());
    }

    public void taskUpdater(int index, String input) {
        for (int i = 0; i < taskList.size(); i++) {
            String task = taskList.get(i);
            if (task.contains("ID: " + index)) {
                String updatedTask = "ID: " + index + ". " + input + ". Last update: " + getUpdateTime();
                taskList.set(i, updatedTask);
                break;
            }
        }
        for (int i = 0; i < taskListInProgress.size(); i++) {
            String taskProgress = taskListInProgress.get(i);
            if (taskProgress.contains("ID: " + index)) {
                String updatedTask = "ID: " + index + ". " + input + ". Last update: " + getUpdateTime();
                taskListInProgress.set(i, updatedTask);
                break;
            }
        }
    }

    public void taskMarker(int index, String command) {
        Iterator<String> iteratorList = taskList.iterator();
        Iterator<String> iteratorInProgress = taskListInProgress.iterator();
        Iterator<String> iteratorDone = taskListDone.iterator();

        if (command.equalsIgnoreCase("mark in progress")) {
            for (String taskProgress : taskListInProgress) {
                if (taskProgress.contains("ID: " + index)) {
                    System.out.println("Task already in progress.");
                    break;
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                if (task.contains("ID: " + index)) {
                    iteratorList.remove();
                    taskListInProgress.add(task);
                    break;
                }
            }
        } else if (command.equalsIgnoreCase("mark done")) {
            for (String taskDone : taskListDone) {
                if (taskDone.contains("ID: " + index)) {
                    System.out.println("Task already done.");
                    break;
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                if (task.contains("ID: " + index)) {
                    iteratorList.remove();
                    taskListDone.add(task);
                    break;
                }
            }
            while (iteratorInProgress.hasNext()) {
                String taskProgress = iteratorInProgress.next();
                if (taskProgress.contains("ID: " + index)) {
                    iteratorInProgress.remove();
                    taskListDone.add(taskProgress);
                    break;
                }
            }
        } else if (command.equalsIgnoreCase("delete")) {
            while (iteratorDone.hasNext()) {
                String taskDone = iteratorDone.next();
                if (taskDone.contains("ID: " + index)) {
                    iteratorDone.remove();
                    break;
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                if (task.contains("ID: " + index)) {
                    iteratorList.remove();
                    break;
                }
            }
            while (iteratorInProgress.hasNext()) {
                String taskProgress = iteratorInProgress.next();
                if (taskProgress.contains("ID: " + index)) {
                    iteratorInProgress.remove();
                    break;
                }
            }
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
                    case "add" :
                        System.out.println("Type in the task: ");
                        String taskToAdd = sc.nextLine();
                        tasksFolder.addTask(taskToAdd);
                        System.out.println("Task added successfully. (id: " + tasksFolder.getId() + ")");
                        break;
                    case "update" :
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
                    case "delete" :
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
                                    for (String task : tasksFolder.getTaskList()) {
                                        System.out.println(task);
                                    }
                                }
                                break;
                            case "done":
                                if (tasksFolder.getTaskListDone().isEmpty()) {
                                    System.out.println("List empty.");
                                } else {
                                    for (String task : tasksFolder.getTaskListDone()) {
                                        System.out.println(task);
                                    }
                                }
                                break;
                            case "in progress":
                                if (tasksFolder.getTaskListInProgress().isEmpty()) {
                                    System.out.println("List empty.");
                                } else {
                                    for (String task : tasksFolder.getTaskListInProgress()) {
                                        System.out.println(task);
                                    }
                                }
                                break;
                            case "all":
                                System.out.println("To-do task list: ");
                                for (String task : tasksFolder.getTaskList()) {
                                    System.out.println(task);
                                }
                                System.out.println("Task list in progress: ");
                                for (String task : tasksFolder.getTaskListInProgress()) {
                                    System.out.println(task);
                                }
                                System.out.println("Done tasks list: ");
                                for (String task : tasksFolder.getTaskListDone()) {
                                    System.out.println(task);
                                }
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
        Map<String, Object> prettyList = new HashMap<>();
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
    public static void main(String[] args) {
        FileSaver fileSaver = new FileSaver();
        Tasks tasksFolder = null;
        Choice choice = new Choice();
        String fileName = "tasks.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tasksFolder = objectMapper.readValue(new File(fileName), Tasks.class);
            System.out.println("Task been read from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading: " + e.getMessage());
            tasksFolder = new Tasks();
            System.out.println("File has been created automatically.");
        }
        if (tasksFolder != null) {
            choice.switcher(tasksFolder);
            fileSaver.saveTasks(tasksFolder, fileName);
        } else {
            System.out.println("No tasks to process.");
        }
    }
}