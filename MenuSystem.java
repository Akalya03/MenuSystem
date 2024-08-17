import java.util.*;

public class MenuSystem {
    private Map<String, Map<String, Cache<String, String>>> menuCache;
    private static final int CACHE_MAX_SIZE = 10; // Maximum number of items per meal/type in the cache
    private static final long CACHE_EXPIRY_DURATION = 60*60*6; // 6 hours in seconds

    public MenuSystem() {
        menuCache = new HashMap<>();
    }

    // Add Menu item
    public void addMenuItem(String mealTime, String category, String item) {
        if (!isValidInput(mealTime, category, item)) {
            System.out.println("Error: Meal time, category, and item must not be empty.");
            return;
        }

        mealTime = mealTime.trim().toLowerCase();
        category = category.trim().toLowerCase();
        item = item.trim();

        menuCache.computeIfAbsent(mealTime, k -> new HashMap<>())
                .computeIfAbsent(category, k -> new Cache<>(CACHE_MAX_SIZE, CACHE_EXPIRY_DURATION))
                .put(item, item);

        System.out.println("Added " + item + " to " + capitalize(mealTime) + " under " + capitalize(category));
    }

    // Display Menu
    public void displayMenu(String mealTime) {
        if (!isValidInput(mealTime)) {
            System.out.println("Error: Meal time must not be empty.");
            return;
        }

        mealTime = mealTime.trim().toLowerCase();

        Map<String, Cache<String, String>> mealMenu = menuCache.get(mealTime);

        if (mealMenu == null) {
            System.out.println("No menu available for " + capitalize(mealTime));
            return;
        }

        System.out.println("\n" + capitalize(mealTime) + " Menu:");
        for (Map.Entry<String, Cache<String, String>> entry : mealMenu.entrySet()) {
            System.out.print("  " + capitalize(entry.getKey()) + ": ");
            List<String> items = entry.getValue().getAllValues();
            System.out.println(String.join(", ", items));
        }
        System.out.println();
    }

    // Input validation
    private boolean isValidInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Capitalize first letter of string
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Main method
    public static void main(String[] args) {
        MenuSystem menuSystem = new MenuSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. Add a menu item");
                System.out.println("2. Display a menu");
                System.out.println("3. Exit");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Enter meal time (breakfast, lunch, dinner, snacks): ");
                        String mealTime = scanner.nextLine();
                        System.out.println("Enter category (starters, main course, desserts, beverages): ");
                        String category = scanner.nextLine();
                        System.out.println("Enter item name: ");
                        String item = scanner.nextLine();
                        menuSystem.addMenuItem(mealTime, category, item);
                        break;
                    case 2:
                        System.out.println("Enter meal time to display (breakfast, lunch, dinner, snacks): ");
                        String displayMealTime = scanner.nextLine();
                        menuSystem.displayMenu(displayMealTime);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
