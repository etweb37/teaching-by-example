import java.util.List;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class WorkerAndEmployerExample {

    public static class Task {
        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        void doSomething() {
            System.out.println("   doing something for task: " + name);
        }
    }

    public static class Employer {
        String name;
        List<Task> tasks;

        public Employer(String name, List<Task> tasks) {
            this.name = name;
            this.tasks = tasks;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Worker {
        String name;
        Set<Employer> employers;

        public Worker(String name, Set<Employer> employers) {
            this.name = name;
            this.employers = employers;
        }

        @Override
        public String toString() {
            return "name";
        }

        void finishTasksForEmployers() {
            for (Employer employer : employers) {
                System.out.println(name + " is finishing tasks for employer: " + employer);
                for (Task task : employer.tasks) {
                    task.doSomething();
                }
            }
        }
    }

    /**
     * Overly simple example
     * Erick is a worker that is doing too many jobs. But he still needs to get tasks done.
     */
    public static void main(String[] args) {

        // He is going to make two new APIs for two different employees
        // so let's make some tasks
        Task task1 = new Task("Design New API");
        Task task2 = new Task("New API");
        Task task3 = new Task("Testing the New API");

        // Let's say our employers develop APIs and need these tasks completed in this order
        List<Task> newApiWorkflow = List.of(task1, task2, task3);

        // The employer holds tasks to be done, here's two of them
        // Also the workflows are the same for both so we will inject these
        Employer bankEmployer = new Employer("Bank Employer", newApiWorkflow);
        Employer mobileEmployer = new Employer("Mobile Employer", newApiWorkflow);

        // We will inject the employers - that provide tasks - to the worker, so the worker only needs to know about
        // iterating through the employers and tasks... he does not need to know about the tasks ahead of time
        Worker erick = new Worker("Erick", Set.of(bankEmployer, mobileEmployer));

        erick.finishTasksForEmployers();

    }
}
