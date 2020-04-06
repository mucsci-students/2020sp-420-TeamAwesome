
#<div align="center"> UMLEditor UserGuide </div>

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

1. Download UMLEditor on [GitHub](https://github.com/mucsci-students/2020sp-420-TeamAwesome) <img src="file:///C:/Users/benba/Pictures/UMLEditor%20images/Download.PNG" width="1000" height="600">

2. click the <img src="file:///C:/Users/benba/Pictures/UMLEditor%20images/Capture.PNG" width="100" height="30"> button in the right hand corner.  To download the zip file.   

3. Extract the files and create a folder. 


#### How to Run
1. Open a terminal. Type `cmd` in your windows search bar and hit `enter`. 
3. Type `bash` and hit `enter`.
4. Navigate to the directory where you have UMLEditor. If you are unfamiliar with how to do so see [Command Prompt Basic Commands](https://www.digitalcitizen.life/command-prompt-how-use-basic-commands /2020sp-420-TeamAwesome).
5. Type `./run-uml-console.sh` for a console view or `./run-uml-gui.sh` for a GUI view

### Console Examples Function 
Once UMLeditor is running type `help` and hit enter to print out a list of valid commands and their descriptions.

<img src="file:///C:/Users/benba/Pictures/UMLEditor%20images/help,%20console.PNG">

##### Classes 

Creating a class is in the form `add class <className>` . Here we create classes. Rename one, then remove it. Listing the classes to illustrate progress.

<img src="  .PNG"> <!-- class console--> 

##### Fields

Creating a field is in the form `add field <className> <fieldName> <type>` . Here we create two fields one of type int and one of type string. Rename one, then remove it. Listing the fields to illustrate progress.

<img src="  .PNG"> <!-- field console--> 

##### Relationships

Creating a relationship is in the form `add relationship <className1> <relationshipName> <className2>`. Here we create a relationship between two classes. Rename it, then remove it. Listing the relationships to illustrate progress.

<img src="  .PNG"> <!-- Relationship console-->

##### Methods

Creating a method is in the form `add method <className1> <returnType> <parameterList>`. Here we create a method with return type int.Listing the methods to illustrate progress.

<img src="  .PNG"> <!-- method console-->

##### Quite

Typing `quite` will exit the program.
 


 


