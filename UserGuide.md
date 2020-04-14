
# <div align="center"> UMLEditor UserGuide </div>

### What is UMLEditor?

The Unified Modeling Language (UML) is a general-purpose, developmental, modeling language made for software engineers. It provides a standardized visualization the design of a system. UMLEditor is intended to allow the user to create, edit, and visualize the basic functions of UML.

### Functionality
UMLEditor can
* Add/remove/rename 
  * classes
  * fields
  * methods
  * relationships (between classes)
* Save/Load files 

### <div align="center"> Getting Started </div>

#### Installation Requirements
There are two installation requirements for running UMLEditor. Apache Maven, and Linux Bash Shell. Below are outsourced links to for guides on how to install Maven and Bash on windows.
 
* [Install Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/)  

* [Install Bash](https://itsfoss.com/install-bash-on-windows/)  

#### Downloading ULMEditor

After installation of Apache Maven, and Linux Bash Shell, or if you already have them installed. There are a few further steps to run the program. 

1. Download UMLEditor on [GitHub](https://github.com/mucsci-students/2020sp-420-TeamAwesome) <img src="pictures/UMLEditor%20images/Download.PNG" width="1000" height="600">

2. click the <img src="pictures/UMLEditor%20images/Download%20Button.PNG" width="100" height="30">button in the right hand corner.  To download the zip file.   

3. Extract the files and create a folder. 


#### How to Run
1. Open a terminal. Type `cmd` in your windows search bar and hit `enter`. 
3. Type `bash` and hit `enter`.
4. Navigate to the directory where you have UMLEditor. If you are unfamiliar with how to do so see [Command Prompt Basic Commands](https://www.digitalcitizen.life/command-prompt-how-use-basic-commands/2020sp-420-TeamAwesome).
5. Type `./run-uml-console.sh` for a console view or `./run-uml-gui.sh` for a Graphical User Interface (GUI) view

### Console Example Functions 

#### Help Menu
Once UMLeditor is running type `help` and hit enter to print out a list of valid commands and their descriptions.

<img src="pictures/UMLEditor%20images/help%2C%20console.PNG">

##### Classes 

Creating a class is in the form `add class <className>`. Here we create two classes. Rename one, then remove it. Listing the classes to illustrate progress.

<img src="pictures/UMLEditor%20images/Class%2C%20Console.PNG">

##### Fields

Creating a field is in the form `add field <className> <fieldName> <type>`. Here we create two fields one of type int and one of type string. Rename one, then remove it. Listing the fields to illustrate progress.

<img src="pictures/UMLEditor%20images/Fields%2C%20Console.PNG">

##### Relationships

Creating a relationship is in the form `add relationship <className1> <relationshipName> <className2>`. Here we create a relationship between two classes. Rename it, then remove it. Listing the relationships to illustrate progress.

<img src="pictures/UMLEditor%20images/Relationship%2C%20Console.PNG">

##### Methods

Creating a method is in the form `add method <className1> <returnType> <parameterList>`. Here we create a method with return type int. Listing the methods to illustrate progress.

<img src="pictures/UMLEditor%20images/Methods%2C%20Console.PNG">

##### Quit

Typing `quit` will exit the program.
 
<img src="pictures/UMLEditor%20images/Quit%2C%20Console.PNG">

### Console Example Functions

##### Command Menu 
Right click to bring up the command menu. (pic) Within the GUI interface you can click and grag classes. (pic)
