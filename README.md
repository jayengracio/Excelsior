
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

## Who Did What
### Sprint 1:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Designed the general layout of the UI. | Worked on components outside of this sprint's requirements. | Designed the button UI and button class. | Worked on components outside of this sprint's requirements. |

Additional Features/Work:
 - The team designed the general layout of the GUI. Opted for minimalist design.
 <br/>
 
### Sprint 2:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| <img width=300/> Created Character and ComicPane classes and added basic functionality.| <img width=300/> Implemented button functionalities to selecting left and right character. | Functionality to flip character on button press, display character warning popup when image character is not selected, empty functionality in character. | Added Button to create popup to select from character poses and allowed the application to read input from a directory directly instead of having to hard code each character pose individually.|

Additional Features/Work:
 - All buttons have on hover, click and exiting effects to show responsiveness
 - Selecting character had deselect functionality which was later removed.
 <br/>
 
### Sprint 3:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Removed antialiasing from character images when changing gender. | Implemented changing the selected character's gender. It removes the character's long hair and hair ribbon. | Implemented changing of hair colour and created colour palette, change face of left and right char button along with selection, colourbox| Implemented the colour selection for skin colour.|

Additional Features/Work:
 - General anti-aliasing work.
 - Character selection and pose changing button built on top of each other.
 <br/>

### Sprint 4:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Ensured users couldn’t put text into the speech bubble over a certain limit. Limit was based on the number of lines in the bubble and whether a word went over a certain character limit for the last line. | Implemented the input functionalities of spoken dialogue/thought bubble. | Worked on additional components outside of this sprint’s requirements. Looked into moving the project to Java 14 and JavaFX 16 to standardise project| Added additional feature - changed buttons to a set of more icons rather than the text buttons originally added (based on Tony's buttons)|

Additional Features/Work:
 - Text limiters.
 - Styling changes such as text font.
 <br/>
 
### Sprint 5:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Added dynamic resizing to textBubble. Three possible sizes are allocated based on specific limits for each font size. | Implemented top and bottom narration's input functionalities. | Worked on additional components outside of this sprint’s requirements. Can be read in the additional features section below. | Upgraded buttons again to a more personalised style that came out higher resolution and proved to be more self explanatory|

Additional Features/Work:
 - Built upon previous feedback given to the team.
 - Researched useful technologies for the next sprint.
 - Improved popups with blurred background to highlight them (HighlightedPopup class).
 - Added exit popup button functionality for improved user experience.
 - Refactored code to load all popups at the beginning instead of creating each time for added speed.
 <br/>
 
### Sprint 6:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Moved colour picking functionality into colourPalette class and modified design to allow the user to change lip colour. | Implemented the functionalities of perusing, selecting, deleting and moving finished/saved panels. Additional features to go along with these functionalities with more details below.| <img width=300/>  Panel minimising for panel display, fixed skin/hair colour change bugs, reset app face after comic is saved. General Refactoring. | Added stylised icons to the buttons for colour changing as it was necessary to be able to switch between hair/lip and skin.

Additional Features/Work:
 - Built upon previous feedback given to the team.
 - Researched useful technologies for the next sprint.
 - Selecting a different panel while there are unsaved changes will display an alert asking for actions such as "Save" or "Don't Save".
 - Deleting a panel will display an alert asking for confirmation.
 - If there are no saved/finished panels in the scroll pane, the delete  and move panel left/right menu items will be grayed out.
 - You can't move the panel to the left if it's the first panel. The same with trying to move the panel right if it's the last panel.
 <br/>

### Sprint 7:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Added functionality to load xml files and display the comic strip in the GUI. Ensured that any input from the xml file was parsed correctly. If the content of the tags was erroneous the parser would compensate by using a default option or leaving it empty. When parsing colour, parser accepts words or hex as acceptable input| <img width=700/> Implemented the XMLSaver class. It saves the finished/saved panels in an XML file. It checks for bubble types and stores the colours in hex form.  | <img width=500/> Worked on additional components outside of this sprint’s requirements. Can be read in the additional features section below. | <img width=500/> Added a file locating feature to choose the XML file in which to load. |

Additional Features/Work:
 - Built upon previous feedback given to the team.
 - Added lip colour functionality in saving of panels, and resetAppFace.
 - Maven Switchover
 - Narration class and dynamic narration handling.
 - Researched useful technologies for the next sprint.
 <br/>
 
### Sprint 8:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Created images from comic panels and stored them in a folder with html document. End screen added to folder once images are stored. | Worked on additional components outside of this sprint’s requirements. Can be read in the additional features section below. | HtmlSaver functionality. Html set comic title popup. | Bug testing issues with loading previously made files. |

Additional Features/Work:
 - Built upon previous feedback given to the team.
 - Cleaning up the god class.
 - Optimizations to code and performance
 - Bug fixing and fixing error exceptions.
 <br/>

### Sprint 9:
| Adam | John E | Sean | John K |
|------|--------|------| ------ |
| Allowed users to choose between 4 options of html. The first three options change the layout of the images. The last option displays a GIF of all the images on the html page. GIF is also stored in the folder with the HTML. Warning image used when XmlLoader can't find desired character pose. Warning pop-up notifies users when xml is loaded incorrectly or the XML is not well formed. | <img width=1200/>Worked on additional components outside of this sprint’s requirements. Can be read in the additional features section below. | <img width=1100/> Lip Anti Aliasing removal. Creation of in depth help menu. Html popup creation and choice of themes for html pages. Splash screen and loading screen. html comic panel staying square during responsive sizing.| <img width=1000/> Improved size of window to be able to see wider selection of poses available.|

Additional Features/Work:
 - Built upon previous feedback given to the team.
 - Cleaning up the god class.
 - Restructured the project.
 - Documentation comments
 - Loading Screens
 - App's favicon and updated icons for better clarity
 <br/>
 
### Additional Bits
 - Moved to Maven for dependency management and other useful features (simplified jar making process).
 - Decided against adding a dark theme as not only was it a sizable undertaking but it didn’t seem to be appealing aesthetically (taking into consideration the text bubbles).

### Testing 
 - Completed tests using a combination of standard output testing and unit testing.
 - Tested most of our application manually using standard output testing.
 - Tested some backend classes using unit testing.
 
## Special Thanks

 - To our mental stability for staying strong
 <img src="https://media.tenor.com/images/27492c89406a77784e0a552327b1473e/tenor.gif">
