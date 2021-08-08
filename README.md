# Pekarski-cop3330-RhythmGamesEC
Makes Image with Score Information Based on User Input (DDR)

As of August 7th, 2021

Features to add: Redesign GUI such that the various Buttons are all MenuBar buttons
Edit the redesign of Score Images created by the program. Add Ranks based on Scores
Change search button to one that looks like an icon (say a magnifying glass)

Overview of Features and Buttons

Score Fields:
Song Selection:
Once Songs are loaded, User can select any song from the list
Difficulty:
User can select from five options based on the DDR Difficulty Settings
Score:
User can select any score from 0 to 1,000,000 based on the possible range of Scores in DDR.
A limitation of this feature currently is that scores do not have to be in increments of 10 or valid songs for the specific song.
Combo:
User can select whether they passed, failed, or got any of the different Full Combo types for the Score.

Clear Selection:
Deselects the score in the Table
Enter Score:
When the Score Fields are entered with valid values, the score is then added to the Table
Make Image:
When a Score is selected in the Table, the user can then make an Image based on the data for that Score.
First select the directory where your image will be created, then choose the Directory of the Song. 
For example, if I wanted to make the image for the song "Ace for Aces", then I would select the Directory 'Stepmania 5\Songs\DDR Songs\Ace for Aces'
The image will then appear in the directory where you set it to with the name of the file including the name of the song and the difficulty.

Enable Search
When this button is clicked, the user may type search criteria into the text field and search for specific songs.
The user is able to edit these fields while the search is active.

Load Songs List
When wishing to add new songs, the user will need to click this button to choose the directory where their songs are kept.
The Choice Box for the Song Names will then be populated with the sub directory names of the directory chosen.

Save:
The user can save their Scores to a JSON file, which can be reloaded by the application.

Load:
The user, when the table is empty, can load previous created JSON files, which the Scores will then populate the table.
Due to the implementation of Loading Songs, the table can include Songs of multiple different folders (say DDR songs, ITG songs, etc.)
