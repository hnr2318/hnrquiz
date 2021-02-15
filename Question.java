package edu.uga.cs1302.quiz; //this properly packages the class

import java.util.ArrayList;
import java.util.Random;

public class Question { 

    private String goodAns; //these variables are used to represent the answer choices for each question
    private String badAns1;
    private String badAns2;
    private int a = 0; //these ints are used to represent indices in the arraylist of country objects 
    private int b = 0;
    private int c = 0;
    private String questString; //this string will represent the question displayed on the screen
    private int temp;
    String badCont1 = ""; //these strings will represent the countries of the answers
    String goodCont = "";
    String badCont2 = "";
    String goodName = "";
    boolean note = true; //this boolean will be used in a while loop to ensure the answers are all different
    private int countFirst = 0;
    
    GeographyQuiz help = new GeographyQuiz(); //this creates a geography quiz object to access the getter for the arraylist of countries
    ArrayList<String> answers = new ArrayList<String>(); //this creates an arraylist of answers
    ArrayList<Country> agh = help.getObj();
    
    public Question(){ //creates question object
	Random random = new Random();
	while(note == true){
	    countFirst++;
	    badCont1 = (agh.get(a)).getContinent(); //This is the continent of bad answer 1
	    goodCont = (agh.get(b)).getContinent(); //This is the continent of good anaswer
	    badCont2 = (agh.get(c)).getContinent(); //This is the continent of bad answer 2
	    
	    if((a != b) && (b != c) && (a != c)){ //this checks to make sure the indices are different
		if (!badCont1.equals(goodCont)){ //these if statements check that the right answers are all different
		    if (!goodCont.equals(badCont2)){
			if(!badCont1.equals(badCont2)){
			    note = false;
			}
		    }
		}
	    }
	    a = random.nextInt(195);

	    if(countFirst == 1){ //this makes sure the right answer is only randomly generated once 
		b = random.nextInt(195); //This assigns 3 different indices to the variables
	    }
	    
	    c = random.nextInt(195);
	}
	goodName = (help.getObj().get(b)).getName(); //this stores the country being asked
	goodAns = (help.getObj().get(b)).getContinent(); //this stores the correct answer
	questString = "On which continent is " + goodName + " located?"; //this creates the question to be showed on screen
	for(int i=0; i < 3; i++){ 
	    answers.add(badCont1); //this adds the answers to an answers array
	    answers.add(goodCont);
	    answers.add(badCont2);	    
	}
    }

    public ArrayList<String> getAnswers(){
	return answers; //this method is used to get the array of answers
    }
   
    public String getQuestString(){
	return questString; //this method is used to get the string form of the question
    }

    public String getNameQ(){ //this method is used to get the country being asked 
	return goodName;
    }
    
    public String getGoodAns(){ //getter for correct answer
	return goodAns;
    }

    public boolean same(Question b){ //this method checks if the countries of 2 questions are the same
	boolean hmm = false;
	if (this.getNameQ().equals(b.getNameQ())){
		hmm = true;
	    }
	return hmm;
    }
    
}
