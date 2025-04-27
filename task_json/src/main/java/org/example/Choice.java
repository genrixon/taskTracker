package org.example;

import java.util.Scanner;

public class Choice {
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
