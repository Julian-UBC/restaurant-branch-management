# Restaurant Branch Management

## About the Project
We are interested in creating an inventory-related database management system and we chose the food industry. 
This projects aims to create a database for the management of multiple restaurant branches. 
The database will track various aspects of restaurant operations including reservations, employee management, 
a list of menus offered at each branch and its associated ingredients, the customer's loyalty and subscription.  

## GUI Guide
Due to time constraint, we decided to only implement some model: branch, menu, and reservation. 
We have a table on the left and several buttons on the right with the following functionality:
1. Selection: users can choose which table they want to see
2. Insert: users can insert values to attributes of the table they choose
3. Update: users can select row and update the attributes of the table
4. Delete: users can select row and delete the row
5. Projection: users can choose which attributes to show in the table
6. Filter: users can filter the attributes
7. Join: users can input number of days to view all reservations during given time for every branch
8. MoreWindow: user can click the table and it will each show a table
    1. Average cost for each menu category
    2. Average Cost of Each Category which have more than one menu item
    3. average cost for each category where the average cost is less than or equal to all the average costs calculated for each category
    4. Show menus that are served in all branches

## Technology
The project will be developed using the CPSC department's Oracle database system, Java, and JDBC. We will be using Java Swing for the GUI. 
