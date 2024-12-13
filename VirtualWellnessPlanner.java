import java.util.*;

// Abstract UserProfile class
abstract class UserProfile {
    protected String name;
    protected int age;
    protected int availableTime;
    protected String fitnessGoals;

    public UserProfile(String name, int age, int availableTime, String fitnessGoals) {
        this.name = name;
        this.age = age;
        this.availableTime = availableTime;
        this.fitnessGoals = fitnessGoals;
    }

    public abstract void generatePlan();

    public void displayUserInfo() {
        System.out.println("\nUser Information:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Available Time: " + availableTime + " mins");
        System.out.println("Fitness Goals: " + fitnessGoals);
    }
}

// MoodAnalyzer interface
interface MoodAnalyzer {
    List<Activity> analyzeMood(String mood);
}

// Parent Activity class
class Activity {
    protected String name;
    protected String category;
    protected int duration;

    public Activity(String name, String category, int duration) {
        this.name = name;
        this.category = category;
        this.duration = duration;
    }

    public void displayActivityDetails() {
        System.out.println("Activity: " + name + ", Category: " + category + ", Duration: " + duration + " mins");
    }
}

// Subclasses for specific activity types
class PhysicalActivity extends Activity {
    public PhysicalActivity(String name, int duration) {
        super(name, "Physical", duration);
    }

    @Override
    public void displayActivityDetails() {
        super.displayActivityDetails();
        System.out.println("Motivation: Stay strong and keep moving!");
    }
}

class MentalActivity extends Activity {
    public MentalActivity(String name, int duration) {
        super(name, "Mental", duration);
    }

    @Override
    public void displayActivityDetails() {
        super.displayActivityDetails();
        System.out.println("Motivation: A calm mind brings inner strength.");
    }
}

class RelaxationActivity extends Activity {
    public RelaxationActivity(String name, int duration) {
        super(name, "Relaxation", duration);
    }

    @Override
    public void displayActivityDetails() {
        super.displayActivityDetails();
        System.out.println("Motivation: Take a deep breath and relax.");
    }
}

// Additional Feature: NutritionAdvice class
class NutritionAdvice {
    private static final String[] tips = {
        "Stay hydrated by drinking at least 8 cups of water daily.",
        "Include more fruits and vegetables in your diet.",
        "Avoid skipping meals to maintain steady energy levels.",
        "Opt for whole grains over refined grains.",
        "Limit your intake of sugary beverages."};

    public static String getRandomTip() {
        Random random = new Random();
        return tips[random.nextInt(tips.length)];
    }
}

// Additional Feature: Progress Tracker
class ProgressTracker {
    private List<String> completedActivities;

    public ProgressTracker() {
        this.completedActivities = new ArrayList<>();
    }

    public void logActivity(String activityName) {
        completedActivities.add(activityName);
        System.out.println(activityName + " logged as completed.");
    }

    public void displayProgress() {
        System.out.println("\n--- Completed Activities ---");
        if (completedActivities.isEmpty()) {
            System.out.println("No activities completed yet.");
        } else {
            for (String activity : completedActivities) {
                System.out.println(activity);
            }
        }
    }
}

// Implementation of UserProfile
class WellnessUser extends UserProfile implements MoodAnalyzer {
    private List<Activity> dailyPlan;
    private ProgressTracker progressTracker;

    public WellnessUser(String name, int age, int availableTime, String fitnessGoals) {
        super(name, age, availableTime, fitnessGoals);
        this.dailyPlan = new ArrayList<>();
        this.progressTracker = new ProgressTracker();
    }

    @Override
    public List<Activity> analyzeMood(String mood) {
        List<Activity> suggestions = new ArrayList<>();
        switch (mood.toLowerCase()) {
            case "stressed":
                suggestions.add(new RelaxationActivity("Meditation", 15));
                suggestions.add(new RelaxationActivity("Listening to Music", 20));
                break;
            case "energetic":
                suggestions.add(new PhysicalActivity("Jogging", 30));
                suggestions.add(new PhysicalActivity("Yoga", 20));
                break;
            case "tired":
                suggestions.add(new RelaxationActivity("Reading", 15));
                suggestions.add(new MentalActivity("Journaling", 10));
                break;
            default:
                System.out.println("Invalid mood. Showing general suggestions.");
                suggestions.add(new PhysicalActivity("Stretching", 10));
                suggestions.add(new RelaxationActivity("Breathing Exercises", 10));
                break;
        }
        return suggestions;
    }

    @Override
    public void generatePlan() {
        System.out.println("\nGenerating a wellness plan for " + name + "...");
        for (Activity activity : dailyPlan) {
            activity.displayActivityDetails();
        }
        System.out.println("Plan generated successfully!");
    }

    public void addActivityToPlan(Activity activity) {
        if (availableTime >= activity.duration) {
            dailyPlan.add(activity);
            availableTime -= activity.duration;
            System.out.println(activity.name + " added to your plan.");
        } else {
            System.out.println("Not enough time for " + activity.name + ". Try a shorter activity.");
        }
    }

    public void completeActivity(String activityName) {
        for (Activity activity : dailyPlan) {
            if (activity.name.equalsIgnoreCase(activityName)) {
                progressTracker.logActivity(activityName);
                dailyPlan.remove(activity);
                break;
            }
        }
    }

    public void showPlanSummary() {
        System.out.println("\n--- Daily Plan Summary ---");
        for (Activity activity : dailyPlan) {
            activity.displayActivityDetails();
        }
        System.out.println("Remaining Time: " + availableTime + " mins");
    }

    public void resetPlan() {
        dailyPlan.clear();
        System.out.println("\nYour daily plan has been reset.");
    }

    public void showProgress() {
        progressTracker.displayProgress();
    }
}

// Main Application
public class VirtualWellnessPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User Registration
        System.out.println("Welcome to the Virtual Wellness Planner!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your daily available time (in minutes): ");
        int availableTime = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter your fitness goals: ");
        String fitnessGoals = scanner.nextLine();

        WellnessUser user = new WellnessUser(name, age, availableTime, fitnessGoals);
        user.displayUserInfo();

        while (true) {
            // Mood-Based Suggestions
            System.out.print("\nHow are you feeling today (e.g., stressed, energetic, tired)? ");
            String mood = scanner.nextLine();
            List<Activity> suggestions = user.analyzeMood(mood);

            System.out.println("\n--- Suggested Activities ---");
            for (Activity activity : suggestions) {
                activity.displayActivityDetails();
            }

            // Plan Customization
            System.out.println("\nCustomize your daily plan:");
            for (Activity activity : suggestions) {
                System.out.print("Would you like to add " + activity.name + " to your plan? (yes/no): ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    user.addActivityToPlan(activity);
                }
            }

            // Show Plan Summary
            user.showPlanSummary();

            // Option to Log Progress
            System.out.print("\nWould you like to mark any activity as completed? Enter the name or type 'no': ");
            String completedActivity = scanner.nextLine();
            if (!completedActivity.equalsIgnoreCase("no")) {
                user.completeActivity(completedActivity);
            }

            // Show Progress
            user.showProgress();

            // Display Nutrition Tip
            System.out.println("\nNutrition Tip: " + NutritionAdvice.getRandomTip());

            // Option to Reset Plan
            System.out.print("\nWould you like to reset your daily plan? (yes/no): ");
            String resetChoice = scanner.nextLine();
            if (resetChoice.equalsIgnoreCase("yes")) {
                user.resetPlan();
            }

            // Continue or Exit
            System.out.print("\nWould you like to continue planning or exit? (continue/exit): ");
            String nextStep = scanner.nextLine();
            if (nextStep.equalsIgnoreCase("exit")) {
                System.out.println("\nThank you for using the Virtual Wellness Planner. Stay healthy and take care!");
                break;
            }
        }

        scanner.close();
    }
}
