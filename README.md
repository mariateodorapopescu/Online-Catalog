# Virtual Catalog 📚👩‍🎓

## About
Welcome to the Virtual School Marks documentation for the Object-Oriented Programming project. This project encompasses approximately 20-24 Java files, including classes and interfaces. Each class resides in a separate file for ease of implementation.

## Journey Highlights
- **Challenges Faced:** While not seemingly difficult, the project required meticulous attention, especially in designing the Memento pattern for Project Design and dealing with the complexities of the Visitor and Observer patterns, particularly in the Parent's File.
- **Implementation Duration:** Completed over approximately one and a half weeks, the project involved extensive coding, testing, and refining.
- **Language Complexity:** Despite a mix-up in the concepts of Firstname and Lastname due to my limited English proficiency, the program functions within normal parameters, meeting expectations.

## Design Patterns at Play
- **Singleton (Catalog):** Ensures a single instance of the catalog, logically preventing multiple catalogs per year/series/faculty.
- **Observer (Catalog):** Keeps a list of parents who receive notifications upon catalog updates, implementing the Observer pattern.

## User Classes
- **User with Parent, Assistant, Teacher, and Student:** Utilizes the Comparator interface to sort by name. Employs the Factory pattern for creating instances of various subclasses.

## Course Classes
- **Course with PartialCourse and FullCourse:** Implements Comparable for ordering in a TreeSet. Utilizes the Builder pattern for easing instantiation.

## Design Patterns Unleashed
- **Strategy (BestExamScore, BestPartialScore, BestTotalScore):** Employs multiple strategies for determining the best student based on preferences.
- **Visitor (ScoreVisitor):** Gathers information during visit methods for later use.

## Graphic Interface
- **File/Windows/GUI Overview:** Three windows are opened through a JDialogBox with JOptionPane (YES_NO_CANCEL), each allowing for the return to the main JDialogBox.
- **Search Student Tab (2.1):** Implements a basic search engine for displaying student information based on course selection.
- **Grade Validation Tab (2.2):** Utilizes the Visitor pattern for grade validation. Displays information interactively during validation.
- **Parent Notifications Tab (2.3):** Basic design displaying notifications for parents upon successful validation.

## Additional Notes
- **Input Handling:** Provides feedback on the console during validation, with messages like "In the process of validation..." or "Validation successfully completed!".
- **Data Sources:** Inspiration for input data is drawn from real-life scenarios, creating a rich test environment.

Happy exploring through the Virtual Catalog! 🚀📊 #JavaProgramming #ObjectOriented #DesignPatterns
