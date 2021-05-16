## Tintin Excelsior
Stage 3 Software Engineering Project By:
 >- Adam Caffrey
 >- John Engracio
 >- John Keegan
 >- Sean Mc Donnell

## How To Run
Built and Tested using Java 14 and JavaFX 16<br/>

Using IntelliJ (Might require VM options):
> Run the Main class

Using the jar file:
> 1. In command line: Change the working directory to where the jar file is located.
> 2. Type in: "java -jar Tintin-Excelsior.jar"

Using Maven: (also builds jar file) :
> 1. In terminal: Type command "mvn clean compile assembly:single"  
> 2. In terminal: Type command "mvn exec:java"

## Workflow

-   **_Overview:_** For all the 9 sprints we had for this project, we met up 3 times a week and talked about how to split the work, who will work on what and checked on each other in the occasional case that a teammate may require help. We had our own Discord server for us to communicate with each other which proved to be very convenient. Everyone had different amount of experiences with the different technologies that we used in this project like JavaFX and Maven, so being able to have a platform like Discord was extremely helpful.
    
-   **_Sprints_**: For each sprint, we were tasked with working with a number of User Stories (instructions) from the project's white paper that was provided to us at the start of the module. Our first impressions of the project was that it looked quite intimidating. We were basically asked to build an application from the ground up without any sort of skeletons/templates to work with like with previous software engineering projects in the past. But as each week came to pass, working on the project and with each other became easier.

-   **_Using Feedback_**: Every week, we were given feedback on how well we did in that week's sprint by the TA. We were given advice on what we could do to improve the code as well as some additional tips we could use for the next sprint. Using this given information, we created some additional work for us to further improve the project outside of what is asked of us in the next sprint as reasonably as we could. These feedback were very useful and assisted us in tackling stuff that we could have overlooked.

## Sprints


||Sprint 1 | Our Build |
|:--| :-- | :--|
|✔| 1. Launch the Application as a GUI | We built the application GUI using JavaFX.
<br/>

||Sprint 2 | Our Build|
|:--|:--| :-- |
|✔| 2. Load a specific character pose into a panel | A button that will bring up a popup of a table that contains all the character poses that are available to choose from.|
|✔| 3. Flip the orientation of a character within the Panel | A button that will flip the character horizontally. It will flip the other way every time it is clicked.| 
<br/>

||Sprint 3 | Our Build|
|:--|:--| :-- |
|✔| 4. Switch the gender of a character within a panel | A button that will change the gender of a character by removing the long hair and hair ribbon. NOTE: The default gender of a character is female.|
|✔| 5. Change the skin colour of a character in the panel | A colour palette that a user can choose from which changes the skin colour of the selected character in the panel. |
|✔| 6. Change the hair colour of a character in the panel | A colour palette that  user can choose from which changes the hair colour of the selected character in the panel. |
 <br/>

||Sprint 4 | Our Build |
|:--|:--| :-- |
|✔| 7. Add spoken dialogue for a character in a panel | A button that allows the user to add a spoken dialogue bubble (if the character is not empty) with dynamic text sizes to allow for longer texts. |
|✔| 8. Add a thought bubble for a character in a panel | A button that allows the user to add a thought bubble (if the character is not empty) with dynamic text sizes to allow for longer texts.|
<br/>

||Sprint 5 | Our Build|
|:--|:--| :-- |
|✔| 9. Add narrative text above the panel| A button that allows the user to add a narration text at the top of the panel with dynamic text sizes to allow for longer texts.|
|✔| 10. Add narrative text underneath the panel | A button that allows the user to add a narration text at the bottom of the panel with dynamic text sizes to allow for longer texts.|
<br/>

||Sprint 6 | Our Build|
|:--|:--| :-- |
|✔| 11. Peruse a row of thumbnails of other panels | A scrollable pane that contains all the saved/finished panels. Clicking one of the panels will select and highlight it.|
|✔| 12. Select previous panel to work on again | A selected panel from the pane of saved/finished panels will be automatically brought up into the working panel to allow users to work on it again. |
|✔| 13. Delete a panel in the scrolling list of finished panels | A menu item with a keybinding that will delete the selected panel from the pane of saved/finished panels. It displays an alert that asks users for confirmation of the deletion.|
|✔| 14. Change the ordering of the panels in the scrolling list | A menu item for moving the selected panel to the left or to the right. It has a keybinding for easier access. |
<br/>

||Sprint 7 | Our Build |
|:--|:--| :-- |
|✔| 15. Save comic strip in XML format | A menu item that saves the scroll pane containing the saved/finished panels into an XML file of the user's chosen directory.|
|✔| 16. Load previous comic strip in XML format | A menu item that loads a user's chosen comic strip file (in XML form).|
<br/>

||Sprint 8 | Our Build |
|:--|:--|:-- |
|✔| 17. Save comic strip in HTML format | A menu item that allows the user to save and view the scroll pane containing the saved/finished panels in an HTML file. This will show a popup that allows users to give the comic strip a title and aesthetic options of what the HTML file will look like. |
<br/>

||Sprint 9 | |
|:--|:--| :-- |
|✔| 18. Add new character poses to the application easily | The project dynamically reads files from the folder that contains all the character poses and load it into the character pose GUI menu. |
