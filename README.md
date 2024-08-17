# MenuSystem and Cache

Overview:
The program is designed to manage and cache menu items for four daily meals: breakfast, lunch, dinner, and snacks. Each meal can include different categories such as starters, main course, desserts, and beverages. To optimize performance, the program employs an efficient caching mechanism that holds menu data on-demand, ensures the cache does not exceed a specified size, and automatically evicts stale entries based on time.
Assumptions:
•	The program assumes predefined meal categories (starters, main course, desserts, and beverages) for simplicity.
•	The maximum cache size is set to 10 entries, and the time-based eviction is triggered for entries older than 6 hours.
•	The program runs in a command-line interface (CLI), making it easy for users to interact with and manage menu items.
Program Functions:
•	Add Menu Items: Users can add menu items for each of the four meals (breakfast, lunch, dinner, and snacks). Each meal's items are categorized into "starters," "main course," "desserts," and "beverages."
•	Display Menu Items: The menu items can be displayed for each four meals according to the user’s choice.
•	Retrieve Menu Information: Users can retrieve menu items for a specific meal and category. The program first checks the cache for the requested data. If the data is in the cache, it is returned and marked as recently used; otherwise, it is retrieved from storage and then cached.
•	Cache Management: The cache is managed using a Least Recently Used (LRU) strategy implemented through a LinkedHashMap. This structure allows for automatic removal of the least recently accessed entries when the cache exceeds its maximum size. Time-based eviction is handled by checking the timestamps of entries. If an entry is older than 6 hours, it is evicted from the cache.
•	Main Method: We use a switch to display the options to select the menu and to add add menu items. 
•	Error Handling: NumberFormatException and Exception e are used for smooth functioning of the code.

