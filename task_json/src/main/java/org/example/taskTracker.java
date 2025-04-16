package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
class Tasks {
    private ArrayList<String> taskList = new ArrayList<>();
    private ArrayList<String> taskListInProgress = new ArrayList<>();
    private ArrayList<String> taskListDone = new ArrayList<>();

    public ArrayList<String> getTaskList() {
        return taskList;
    }

    public ArrayList<String> getTaskListInProgress() {
        return taskListInProgress;
    }

    public ArrayList<String> getTaskListDone() {
        return taskListDone;
    }

    public void addTask(String input) {
        taskList.add(input);
    }

    public void taskUpdater(int index, String input) {
        for (String task : taskList) {
            int keyIndex = task.indexOf(".");
            for (char elem : task.toCharArray()) {
                if (task.contains(Integer.toString(index))) {
                    if (Character.getNumericValue(elem) == index) {
                        String partToReplace = task.substring(keyIndex + 2);
                        String replacedTask = task.replace(partToReplace, input);
                        taskList.set(taskList.indexOf(task), replacedTask);
                    }
                } else {
                    for (String taskProgress : taskListInProgress) {
                        if (taskProgress.contains(Integer.toString(index))) {
                            String partToReplace = taskProgress.substring(keyIndex + 2);
                            String replacedTask = taskProgress.replace(partToReplace, input);
                            taskListInProgress.set(taskListInProgress.indexOf(taskProgress), replacedTask);
                        }
                    }
                }
            }
        }
    }
    public void taskMarker(int index, String command) {
        Iterator<String> iteratorList = taskList.iterator();
        Iterator<String> iteratorInProgress = taskListInProgress.iterator();
        Iterator<String> iteratorDone = taskListDone.iterator();

        if (command.equalsIgnoreCase("mark in progress")) {
            for (String taskProgress : taskListInProgress) {
                for (char elemOfProgress : taskProgress.toCharArray()) {
                    if (Character.getNumericValue(elemOfProgress) == index) {
                        System.out.println("Task already in progress.");
                        break;
                    }
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                for (char elem : task.toCharArray()) {
                    if (Character.getNumericValue(elem) == index) {
                        iteratorList.remove();
                        taskListInProgress.add(task);
                        break;
                    }
                }
            }
        } else if (command.equalsIgnoreCase("mark done")) {
            for (String taskDone : taskListDone) {
                for (char elemOfDone : taskDone.toCharArray()) {
                    if (Character.getNumericValue(elemOfDone) == index) {
                        System.out.println("Task already done.");
                        break;
                    }
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                for (char elem : task.toCharArray()) {
                    if (Character.getNumericValue(elem) == index) {
                        iteratorList.remove();
                        taskListDone.add(task);
                        break;
                    }
                }
            }
            while (iteratorInProgress.hasNext()) {
                String taskProgress = iteratorInProgress.next();
                for (char elemOfProgress : taskProgress.toCharArray()) {
                    if (Character.getNumericValue(elemOfProgress) == index) {
                        iteratorInProgress.remove();
                        taskListDone.add(taskProgress);
                        break;
                    }
                }
            }
        } else if (command.equalsIgnoreCase("delete")) {
            while (iteratorDone.hasNext()) {
                String taskDone = iteratorDone.next();
                for (char elemOfDone : taskDone.toCharArray()) {
                    if (Character.getNumericValue(elemOfDone) == index) {
                        iteratorDone.remove();
                        break;
                    }
                }
            }
            while (iteratorList.hasNext()) {
                String task = iteratorList.next();
                for (char elem : task.toCharArray()) {
                    if (Character.getNumericValue(elem) == index) {
                        iteratorList.remove();
                        break;
                    }
                }
            }
            while (iteratorInProgress.hasNext()) {
                String taskProgress = iteratorInProgress.next();
                for (char elemOfProgress : taskProgress.toCharArray()) {
                    if (Character.getNumericValue(elemOfProgress) == index) {
                        iteratorInProgress.remove();
                        break;
                    }
                }
            }
        }
    }
}
class Choice {
    private int taskID = 0;
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
                        if (tasksFolder.getTaskList().isEmpty()) {
                            taskID = 0;
                        }
                        System.out.println("Type in the task: ");
                        String taskToAdd = sc.nextLine();
                        ++ taskID;
                        tasksFolder.addTask("ID: " + taskID + ". " + taskToAdd);
                        System.out.println("Task added successfully. (id: " + taskID + ")");
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
        prettyList.put("To-do tasks", tasksFolder.getTaskList());
        prettyList.put("In progress", tasksFolder.getTaskListInProgress());
        prettyList.put("Done", tasksFolder.getTaskListDone());

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
        Tasks tasksFolder = new Tasks();
        Choice choice = new Choice();
        choice.switcher(tasksFolder);
        String fileName = "tasks.json";
        fileSaver.saveTasks(tasksFolder, fileName);
    }
}