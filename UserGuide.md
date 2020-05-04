
# <div align="center"> UMLEditor UserGuide </div>

### What is UMLEditor?
The Unified Modeling Language (UML) is a general-purpose, developmental, modeling language made for software engineers. It provides a standardized visualization the design of a system. UMLEditor is intended to allow the user to create, edit, and visualize the basic functions of UML.

### Functionality
UMLEditor can
* Add/remove
  * classes
  * fields
  * methods
  * relationships (between classes)
* Rename 
  * classes
  * fields
  * methods 
* Change Relationship types 
* Save/load files

### <div align="center"> Getting Started </div>

#### Installation Requirements
There is only one installation requirements for running UMLEditor. Apache Maven. Below is an outsourced link to a guide on how to install Maven Windows and Linux. 
 
* [Install Maven](https://www.educative.io/edpresso/how-to-install-maven-in-windows-and-linux-unix)

#### Downloading ULMEditor
After installation of Apache Maven or if you already have it installed. There are a few further steps to run the program. 

1. Download UMLEditor on [GitHub](https://github.com/mucsci-students/2020sp-420-TeamAwesome) <img src="pictures/UMLEditor%20images/Download.PNG" width="1000" height="600">

2. click the <img src="pictures/UMLEditor%20images/Download%20Button.PNG" width="100" height="30"> button in the right hand corner.  To download the zip file.   

3. Extract the files and create a folder. 


#### How to Run
##### Windows
1. Download UMLEditor on [GitHub](https://github.com/mucsci-students/2020sp-420-TeamAwesome) 
2. Open a terminal
3. Navigate to the directory where you have UMLEditor. If you are unfamiliar with how to do so see [Command Prompt Basic Commands](https://www.watchingthenet.com/how-to-navigate-through-folders-when-using-windows-command-prompt.html).
4. Type `run-uml-console.bat` for a console view or `./run-uml-gui.bat` for a GUI view
5. Type `help` to see a list of commands and descriptions 

##### Mac/Linux
1. Download UMLEditor on [GitHub](https://github.com/mucsci-students/2020sp-420-TeamAwesome) 
2. Open a terminal
3. Navigate to the directory where you have UMLEditor. If you are unfamiliar with how to do so see [Terminal Basic Commands](https://macpaw.com/how-to/use-terminal-on-mac).
4. Type `run-uml-console.sh` for a console view or `./run-uml-gui.sh` for a GUI view
5. Type `help` to see a list of commands and descriptions 

### Console Example Functions 

#### Help Menu
Once UMLeditor is running type `help` and hit enter to print out a list of valid commands and their descriptions.

<img src="pictures/UMLEditor%20images/help%2C%20console 1.PNG">
<img src="pictures/UMLEditor%20images/help%2C%20console 2.PNG">

Typing `help <command>` will show the description of a specified command.  For example below we specify the add command. 

<img src="pictures/UMLEditor%20images/help%2C%20console 3.PNG">

#### Class Creation 
Creating a class is in the form `add class <className>`. Here we create two classes Student_1 and Teacher_1. Then list the classes to illustrate progress.

<img src="pictures/UMLEditor%20images/Class%2C%20Console.PNG">

#### Field Creation 
Creating a field is in the form `add field <class_name> <field_name> <type>`. Here we create four fields two for Student_1 and two for Teacher_1. Then list the classes to illustrate progress.

<img src="pictures/UMLEditor%20images/Fields%2C%20Console.PNG">

#### Relationship Creation 
The relationships supported are 
* `aggregation`	
  * -----<>
* `composition`
  * ----<=>
* `inheritance`
  * ------->
* `realization`
  * - - - - ->
  
Creating a relationship is in the form `add relationship <class_name1> <relationship_name> <class_name2>`. Here we create an aggregation relationship between our two classes. Then list the relationships to illustrate progress.

<img src="pictures/UMLEditor%20images/Relationship%2C%20Console.PNG">


#### Method Creation 
Creating a method is in the form `add method <class_name1> <return_type> <parameter_list>`. Here we create a method called GetMajor which accepts ID as a parameter and has a return type string. Then list the classes to illustrate progress.

<img src="pictures/UMLEditor%20images/Methods%2C%20Console.PNG">

#### List
There are four examples of listing above. 

To view a list of classes their fields and methods type `list classes`. The lists take the form of boxes with the classes name and its associated attributes inside. 

To view a list relationships between classes type `list relationships <class_name>`. The list takes the form of boxes for each class containing their associated attributes with a delimiter for relationship type between the classes.

#### Rename
* classes `edit class <old_class_name> <new_class_name>`. 
* fields `edit field <source_class> <old_name> <name_name>`.
* methods `edit method <source_class> <old_name> <name_name> <paramter_list>`.

#### Remove
* classes `remove class <class_name>`.
* fields `remove field <class_name> <field_name>`.
* methods `remove method <class_name> <method_name> <paramter_list>`.

#### Save
To save the current state of the UML diagram type `save`. If a file has not been set it will prompt the user for one.  

#### Load
To load a previously save UML state type `load <file_path>`. 

#### Quit
Typing `quit` or `exit` will exit the program.
 
### GUI Menus and Example Functions

#### File Menu  
Click the "file" tab or right click on any whitespace to bring up the command menu.  

<img src="pictures/UMLEditor%20images/file%20tab%2C%20gui.PNG">

#### Actions Menu 
Click the "actions" tab or right click on a class to bring up the actions menu.

<img src="pictures/UMLEditor%20images/actions%20tab%2C%20gui.PNG.jpg">
 
#### Dynamic Classes and Relationships
Once you've created a class you can drag them to better position them to your liking. Relationship indicators will move dynamically with the classes.

#### Example Function

<img src="pictures/UMLEditor%20images/example%20function%20gui.png">

* Classes appear bold at the top of the box that contains their contents.
* Fields are depicted underneath the first solid line in the box. In the form `<fieldType> <fieldName>.`
* Methods appear underneath the second solid line in the box. In the form `<methodType> <MethodName> <parameters>.`
* Relationships are depicted as lines inbetween the classes who's ends delineate them.
  * Aggregation  <img src="pictures/UMLEditor%20images/Aggregation%2C%20gui%20-%20Copy.png">
  * Composition  <img src="pictures/UMLEditor%20images/composition%2C%20gui.png">
  * Inheritance  <img src="pictures/UMLEditor%20images/Inheritance%2C%20gui.png">
  * Realization  <img src="pictures/UMLEditor%20images/Realization%2C%20gui.png">  
