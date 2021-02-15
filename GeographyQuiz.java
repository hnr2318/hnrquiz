package edu.uga.cs1302.quiz; //This packages the class with the others

import org.apache.commons.csv.*; //These imports allow for the javafx design 
import java.net.URL;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;  //This imports allows for the catching of exceptions
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class GeographyQuiz extends Application implements Serializable{

    private Date date; //These variables are declared outside of the methods to be used by multiple 
    private Stage primaryStage; //The variables are all private to only be accessed by this class
    private Stage helpStage; //These variables create the needed stages
    private Stage startStage = new Stage();
    private Stage resultStage;
    private int number = 1;
    private Text response = new Text();
    private RadioButton correct;
    private String correctName;
    private int score = 0; //These counts are used to decide when actions should be performed
    private int count = 0;
    private Scene startScene;
    private int A = 0; //These numbers are used to randomize the order od answers 
    private int B = 0;
    private int C = 0;
    private Random random = new Random(); //This creates a random object to randomize index 
    private QuizResults store;
    private ArrayList<QuizResults> sheep;
    private int size;
    private ArrayList<QuizResults> murder = new ArrayList<QuizResults>();
    private boolean how = true;
    private QuizResults kill;
    private int count2 = 0;
    private int times = 0;
    private static final long serialVersionUID = -2790881574583608765L;
    private ToggleGroup group = new ToggleGroup(); //This creates a togglegroup so that only 1 answer can be selected at a time
    private RadioButton answer1 = new RadioButton(""); //This creates the radio buttons
    private RadioButton answer2 = new RadioButton("");
    private RadioButton answer3 = new RadioButton("");
    private Text quizEm = new Text(""); //This creates new text objects
    private Text quizNum = new Text("");
    private VBox startBox = new VBox();
    private Button submitB = new Button("");
    private Quiz kelp;
    private ArrayList<Question> now;
    private ArrayList<Country> countryBoy = new ArrayList<Country>();
    
    public void init() { //This tells the program to parse the string before continuing with the start method
	parseString();
    } 

    public static void main(String args[]){ //This creates the start method as the main method
       launch(args);
    }

    public void parseString(){ //This method is used to create an arrayList of country objects from the provided csv file
        ClassLoader classLoad = getClass().getClassLoader();
        URL web = classLoad.getResource("country_continent.csv");

        if (web == null) { //This accounts for if the file cannot be found
            System.exit(1);
        } 

        File file = new File(web.getFile());
        try (Reader readEm = new FileReader(file)){
            CSVParser parseEm = CSVFormat.DEFAULT.parse(readEm);

            for (CSVRecord record : parseEm){
                String country = record.get(0); //this assigns the object at index 0 to a string
                String continent = record.get(1); //this assigns the object at index 1 to a string
                countryBoy.add(new Country(country, continent)); // this creates a new country object
            }
	    
	} catch (IOException ioEx){ //This catches an io exception
            ioEx.printStackTrace(); 
            System.exit(1); 
        }
    }
  
    public void writeToFile(QuizResults pimp){ //This creates a method to read the quiz results into a .dat file
	try {
	    File fileName = new File("quizzes.dat");
	    ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(fileName, true));
	    objectOut.writeObject(pimp); //This writes the object to the file
	    objectOut.flush();
        } catch (IOException ioException) { //This catches an io exception
            ioException.printStackTrace();
	    System.exit(1);
	}
    }
    
    public ArrayList<QuizResults> readFromFile(){ //This creates a method to write information from the .dat file into an arraylist of quizresult objects 
	try{
	    murder.clear(); //this clears the arraylist for every quiz that is taken
	    File fileName = new File("quizzes.dat");
	    FileInputStream fileIn = new FileInputStream(fileName);
	    try {
		while (fileIn.available() != 0){ //this reads through the file until it reaches the end
		    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
		    kill = (QuizResults) objectIn.readObject(); //this turns the information into a quiz result object
		    murder.add(kill); //this adds the quiz result objects into an arraylist
		}
	    }catch(ClassNotFoundException cnfe){ //This accounts for if the class is not found 
		cnfe.printStackTrace();
		System.exit(1);
	    }
	}catch (IOException ioEx){ //This catcges an io exception
	    ioEx.printStackTrace();
	    System.exit(1);
	}
	return murder; //This returns the filled arraylist
    }

    public String allInfo(ArrayList<QuizResults> enter){ //This creates a method to get the score and date for all quiz result objects in an array list
	 String fullAns = "";
	for(int i=enter.size() - 1; i >= 0; i--){
	    fullAns += enter.get(i).getInfo();
        }
        return fullAns; //this returns the complete string 
    }
    
    public ArrayList<Country> getObj(){ //This method is used to get the arraylist of country objects 
        init(); //this calls the init method to parse through the csv file to return the arraylist
        return countryBoy;
    }
    
    @Override
    public void start(Stage primaryStage){ //This creates a method to set up the basic javafx 
	Button startB = new Button("Start"); //These lines create the buttons on the main screen
	Button resultB = new Button("Results History");
	Button helpB = new Button("Help");
	Button quitB = new Button("Quit");

	quitB.setOnAction(this::quitHandler); //These lines refer a button push to the buttonn handlers
	helpB.setOnAction(this::helpHandler);
	startB.setOnAction(this::startHandler);
	resultB.setOnAction(this::resultsHandler);
	
	final Text welc = new Text(); //This creates text objects
	final Text desc = new Text();
		
	VBox hype = new VBox(); //This creates a vbox layout for the main screen
	hype.setAlignment(Pos.CENTER);
	hype.setSpacing(50);
	hype.getChildren().addAll(welc, desc, startB, resultB, helpB, quitB);
	hype.setStyle("-fx-background-color: #f0fff0;"); //this fills the color of the background 
	
	this.primaryStage = primaryStage;
	Scene scene = new Scene(hype, 650, 600); //This creates the scene for the main screen
	
	desc.setText("--------------------------------------------------" + "\nIn this game you will be presented with the name of a country.\n" + "Your job is to choose the continent that the country is located on.\n" + "There will be 6 questions to answer, and 3 possible answers for each question.\n" + "Good luck!\n"+ "--------------------------------------------------");

	desc.setFont(Font.font("SanSerif Plain", 15));
	welc.setTextAlignment(TextAlignment.CENTER);
	desc.setTextAlignment(TextAlignment.CENTER);

	DropShadow ds = new DropShadow(); //This adds a drop shadow effect to the title on the main screen 
	ds.setOffsetY(3.0f);
	ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
	
	welc.setEffect(ds);
	welc.setCache(true);
	welc.setFill(Color.TEAL);
	welc.setText("Welcome to Geography Quiz!");
	welc.setFont(Font.font("SanSerif Bold", FontWeight.BOLD, 30));
	
	primaryStage.setTitle("Geography Quiz");
	primaryStage.setScene(scene);
	primaryStage.show(); //This shows the stage for the main screen
    }
    
    public void startHandler(ActionEvent event){ //This creates a handler for the start method
	times ++;
	answer1.setSelected(false); //This unselects all the answer choices for each question
	answer2.setSelected(false);
	answer3.setSelected(false);
	submitB.setDisable(true);
	submitB.setDisable(false);
	
	if (count == 0){ //This creates a new quiz object only for the first question of each quiz
	    kelp = new Quiz();
	}
       
	now = kelp.getQuiz();
	submitB.setText("Submit"); //This creates a submit button for the quiz page
	submitB.setOnAction(this::submitHandler);
	
	while((A == B) || (B == C) || (A == C)){ //This loop randomizes the order of the answer choices
	    A = random.nextInt(3);
	    B = random.nextInt(3);
	    C = random.nextInt(3);
	}
	
	answer1.setText((now.get(count)).getAnswers().get(A)); //This sets the answers equal to continents from a question object
	answer2.setText((now.get(count)).getAnswers().get(B));
	answer3.setText((now.get(count)).getAnswers().get(C));
	correctName = now.get(count).getGoodAns(); //This gets the correct answer
	
	if((now.get(count).getGoodAns()).equals((now.get(count)).getAnswers().get(A))){ //this branched if statement searches for which answer choice is correct
	    correct = answer1;
	}else if((now.get(count).getGoodAns()).equals((now.get(count)).getAnswers().get(B))){
	    correct = answer2;
	}else{
	    correct = answer3;
	}

	answer1.setToggleGroup(group); //This adds the 3 answer choices to a toggle group 
	answer2.setToggleGroup(group);
	answer3.setToggleGroup(group);
     
	quizNum.setText("Question " + number); //This creates a text to show what question the player is on 
	quizNum.setFont(Font.font("SanSerif Bold", FontWeight.BOLD, 25));
	quizNum.setFill(Color.TEAL);
	quizEm.setText(now.get(count).getQuestString());
	quizEm.setFont(Font.font("SanSerif Plain", 15));
	
	if((count == 0) && (times == 1)){ //This sets a lsit of actions to only perform once 
	    startStage.setTitle("Quiz");
	    startStage.initModality(Modality.APPLICATION_MODAL); //This makes it so that the user cannot interact with the main screen while another window is open
	    startStage.initOwner(primaryStage);
	    startStage.setX(primaryStage.getX());
	    startStage.setY(primaryStage.getY());
	
	    startBox.setAlignment(Pos.CENTER);
	    startBox.setSpacing(50);
	    startBox.getChildren().addAll(quizNum, quizEm, answer1, answer2, answer3, submitB, response); //This adds elements into the vbox
	    startBox.setStyle("-fx-background-color: #f0fff0;"); //this sets the background color of the quiz scene

	    startScene = new Scene(startBox, 650, 600); 
	    startStage.setScene(startScene);
	
	    this.startStage = startStage;
	    this.startBox = startBox;
	}
	if(count == 0){
	    startScene.setRoot(startBox); 
	    startStage.showAndWait();
	}
    }

    public void submitHandler(ActionEvent event){ //This creates an event handler for pressing the submit button
	number++; //These counts are increased to check for multiple actions
	count++;
	count2++; //this sets the font and color the the response text  
	if(count == 1){
            response.setFill(Color.TEAL);
	    response.setFont(Font.font("SanSerif Plain", FontPosture.ITALIC, 15));
	}
	if(correct.isSelected()){ //This prints out a response and increases score if the player answers correctly 
	    response.setText("Correct! :)");
	    score++;
	}else{ //This prints out a response and the right answer if the player answers incorrectly 
	    response.setText("Incorrect! :(" + "\n Correct Answer: " + correctName); 
	    }
	try{
	    TimeUnit.SECONDS.sleep(3); //This delays the transition from one question to the next
	}catch(InterruptedException ie){ //This catches an interrupted exception
	    System.out.println(ie);
	}

	if (count == 6){ //This handles the events after the player has answered the last question
	    Button closeB = new Button("Close"); //This creates a close button for the final score screen
	    closeB.setOnAction(this::closeHandler);
     	    
	    Text titleText = new Text(); //this creates new text objects
	    Text scoreText = new Text();
	    
	    titleText.setText("Congrats! You've finished the Quiz!"); //These lines set the color, font, and size of the text objects
	    titleText.setFont(Font.font("SanSerif Bold", FontWeight.BOLD, 25));
	    titleText.setFill(Color.TEAL);
	    scoreText.setText("Your final score is: " + score + " out of 6");
            scoreText.setFont(Font.font("SanSerif Plain", 20));
	    
	    VBox finalBox = new VBox();//This creates a new vbox for the final score page
	    finalBox.setAlignment(Pos.CENTER);
	    finalBox.setStyle("-fx-background-color: #f0fff0;"); //These lines define the color and alignment of the vbox
	    finalBox.setSpacing(60);
	    finalBox.getChildren().addAll(titleText, scoreText, closeB, response);
	    
	    date = Calendar.getInstance().getTime(); //this saves the date once the quiz is done
	    sheep = new ArrayList<QuizResults>(); //these lines create a quiz results object and add it to an arraylist
	    store = new QuizResults(date, score);
	    sheep.add(store);

	    startScene.setRoot(finalBox); 
	    writeToFile(store); //this writes the quiz results object to the quizzes.dat file

	    this.sheep = sheep;
	    count = 0; //these lines reset the counts for the next quiz
	    number = 1;
	    score = 0;
	    	     
	}else{
	    ToggleButton nextQuestion = new ToggleButton("invisible");
	    nextQuestion.setOnAction(this::startHandler); //this creates a button to call the startHandler again
	    nextQuestion.fire();
	}
    }

    public ArrayList<QuizResults> getArray(){ //this creates a method to get the arraylist of quiz results
	return sheep;
    }
    
    public void resultsHandler(ActionEvent event){ //this creates an event handler for the results button
	Button closeB = new Button("Close"); //this creates a close button for the quiz results page 
	closeB.setOnAction(this::closeRHandler);
	
	Text dateEm = new Text(); //this creates a new text object
	dateEm.setText("");
	dateEm.setText(allInfo(readFromFile()));
	dateEm.setFont(Font.font("Verdana", 15));
	dateEm.setTextAlignment(TextAlignment.CENTER);
	
	Text showEm = new Text(); //this creates new text objects
	Text introEm = new Text();
	showEm.setText("Quiz Results"); //these lines customize the font and size of the text objects
	showEm.setFill(Color.TEAL);
	introEm.setText("Below is a list of previous quiz scores and dates:");
	introEm.setFont(Font.font("Verdana", 20));
	showEm.setTextAlignment(TextAlignment.CENTER);
	showEm.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
	introEm.setTextAlignment(TextAlignment.CENTER);
	
	ScrollPane scrollPane = new ScrollPane(); //this creates a scroll pane for the quiz results 
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportWidth(280);
        scrollPane.setContent(dateEm); //this adds the quiz result strings inside the scroll pane 
	
	Stage resultStage = new Stage(); //this creates a stage for the results page
	resultStage.setTitle("Quiz Results");
	resultStage.initModality(Modality.APPLICATION_MODAL); //this makes it so that the user cannot interact with the main screen while the results screen is open
	resultStage.initOwner(primaryStage);
	resultStage.setX(primaryStage.getX());
	resultStage.setY(primaryStage.getY());
		
	VBox resultBox = new VBox(); //this creates a vbox for the result page
	resultBox.setAlignment(Pos.CENTER);
	resultBox.setSpacing(60);
	resultBox.getChildren().addAll(showEm, introEm, dateEm, closeB, scrollPane);
	resultBox.setStyle("-fx-background-color: #f0fff0;"); //this adds a color to the background 
	
	Scene resultScene = new Scene(resultBox, 650, 600); //this creates a scene for the results page 
	resultStage.setScene(resultScene);
	
	this.resultStage = resultStage;
	resultStage.showAndWait(); //this shows the results stage
    }
    
    public void helpHandler(ActionEvent event){ //this creates an event handler for the help button 
	Button closeB = new Button("Close"); //this creates a close button for the help page
	closeB.setOnAction(this::closeHHandler);

	Text welcEm = new Text(); //this creates new text objects for the help page
	Text helpEm = new Text();
	welcEm.setText("Help Page");
	helpEm.setText("Once you press start, the quiz will prompt you with the question:\n" + "On which continent is _________ located?\n\n" + "You will then see a list of 3 possible answers, only one being correct.\n" + "To select an answer, click on one of the 3 choices.\n" + " You can change your answer anytime before you submit your response.\n\n" + "To submit and move to the next question, click submit.\n" + " The quiz will then alert you if you missed or passed the question,\n" + " as well as show you the correct answer.\n\n" + "Once you sbmit the last question, the quiz will reveal your final score.\n" + "You can view your quiz result history by returning to the main screen\n" + " and clicking 'Result History'\n\n" + "--------------------------------------------------" + "\n\nIf you no longer need help,\n" + " click the 'Close' button below to return to the main screen.");
	helpEm.setFont(Font.font("SanSerif Plain", 15));
	helpEm.setTextAlignment(TextAlignment.CENTER); //these lines set the color, font, and size of the text objects
	welcEm.setFont(Font.font("SanSerif Bold", FontWeight.BOLD, 25));
	welcEm.setTextAlignment(TextAlignment.CENTER);
	welcEm.setFill(Color.TEAL);
	
	Stage helpStage = new Stage(); //this cretaes a stage for the help screen
	helpStage.setTitle("Help");
	helpStage.initModality(Modality.APPLICATION_MODAL); //this makes it so that the user cannot interact with the main screen while the help screen is open
	helpStage.initOwner(primaryStage);
	helpStage.setX(primaryStage.getX());
	helpStage.setY(primaryStage.getY());
	
	VBox helpBox = new VBox(); //this creates a vbox for the help page
	helpBox.setAlignment(Pos.CENTER);
	helpBox.setSpacing(60);
	helpBox.getChildren().addAll(welcEm, helpEm, closeB);
	helpBox.setStyle("-fx-background-color: #f0fff0;"); //this adds a background color for the vbox
	
	Scene helpScene = new Scene(helpBox, 650, 600); //this creates a scene for the help page 
	helpStage.setScene(helpScene);
	
	this.helpStage = helpStage;
	
	helpStage.showAndWait(); //this shows the help stage 
    }

    public void closeHHandler(ActionEvent event){ //this creates an event handler for the close button in the help screen
	helpStage.close();
    }

    public void closeHandler(ActionEvent event){ //this creates an event handler for the close button in the final score screen
        startStage.close();
    }

    public void closeRHandler(ActionEvent event){ //this creates an event handler for the close button in the results screen
	resultStage.close();
    }

    public void quitHandler(ActionEvent event){ //this creates an event handler for the quit button 
	Platform.exit();
    }

}

