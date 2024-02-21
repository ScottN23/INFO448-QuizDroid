<<<<<<< HEAD
# INFO448-QuizDroid (Storage)
* An application that will allow users to take multiple-choice quizzes
* Now we will write the code to check the questions periodically, store the data, and allow for preferences

## Tasks
* Refactor the TopicRepository to read a local JSON file (data/questions.json) to use as the source of the Topics and Questions. Use a hard-coded file (available at http://tednewardsandbox.site44.com/questions.json) stored on the device for now
* The application should provide a "Preferences" action bar item that brings up a "Preferences" activity containing the application's configurable settings: URL to use for question data, and how often to check for new downloads measured in minutes. If a download is currently under way, these settings should not take effect until the next download starts.

## Implementation Notes
* Keep in mind in the next part, you're going to download that JSON file, so make sure that your code to open the file is flexible enough to read from a different location on the device
* Putting the file into the "assets" folder of the Android Studio project is easy, but can't be overwritten by what's downloaded

## Grading (5 pts)
* repo should be called 'quizdroid' on branch 'storage'
* 3 pts: TopicRepository pulls all data from a JSON file
* 2 pts: Preferences displays configuration

## Extra credit
* Use a custom JSON file of your own with your own questions; if you do this, submit screenshots using your questions. Include the URL at which your JSON file can be found (it must be Internet-accessible) so we can verify it. It must match the structure/format of the example. (2 pts)


=======
# INFO448-QuizDroid (Repository)
* An application that will allow users to take multiple-choice quizzes.
* Now we will refactor to use a domain model and an Application object

## Tasks
* Create a class called QuizApp extending android.app.Application and make sure it is referenced from the app manifest; override the onCreate() method to emit a message to the diagnostic log to ensure it is being loaded and run
* Use the "Repository" pattern to create a TopicRepository interface; create one implementation that simply stores elements in memory from a hard-coded list initialized on startup.
* Create domain objects for Topic and Question
  * a Question is question text, four answers, and an integer saying which of the four answers is correct
  * a Topic is a title, short description, long description, and a collection of Question objects
* Provide a method or property on QuizApp for accessing the TopicRepository.
* Refactor the activities in the application to use the TopicRepository.
  * On the topic list page, the title and the short description should come from the similar fields in the Topic object.
  * On the topic overview page, the title and long description should come from the similar fields in the Topic object. The Question object should be similarly easy to match up to the UI.

## Grading (5 pts)
* repo should be called 'quizdroid' on branch 'repository'
* 2pt: QuizApp extends Application, holds TopicRepository instance, is referenced from manifest, and writes to log
* 3pts: TopicRepository provides all access to the Topic/Quiz objects, and all data is coming from those objects

## Extra credit
* In the next part, we will need this application to need to access the Internet, among other things. Look through the list of permissions in the Android documentation, and add uses-permission elements as necessary to enable that now. (1 pt)
* Create some unit tests that test the TopicRepository (2 pts)
* Refactor the domain model so that Topics can have an icon to go along with the title and descriptions. (Use the stock Android icon for now if you don't want to test your drawing skills.) Refactor the topic list view to use the icon as part of the layout for each item in the list view. Display the icon on the topic overview page. (2pts)
>>>>>>> repository
