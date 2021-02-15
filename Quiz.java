package edu.uga.cs1302.quiz; //this properly packages the quiz class

import java.util.ArrayList;
import java.util.Random;

public class Quiz{

    ArrayList<Question> currentQuiz = new ArrayList<Question>(); //this creates an arraylist of question obejcts to represent a quiz
    Random random = new Random(); //this allows for the random generation of integers

    public Quiz(){	    
	currentQuiz.add(new Question()); //this adds 2 questions to the arraylist
        currentQuiz.add(new Question());
      
	while(currentQuiz.get(0).same(currentQuiz.get(1))){ //makes the program replace a question asking about the same country
	    currentQuiz.remove(1);
	    currentQuiz.add(new Question());
	}

	currentQuiz.add(new Question()); //this adds a new question object
	
	while(currentQuiz.get(2).same(currentQuiz.get(1)) || currentQuiz.get(2).same(currentQuiz.get(0))){ //makes the program replace a question asking about the same country

	    currentQuiz.remove(2);
	    currentQuiz.add(new Question());
	}
	currentQuiz.add(new Question()); //this creates a new question object
	
	while(currentQuiz.get(3).same(currentQuiz.get(2)) || currentQuiz.get(3).same(currentQuiz.get(1)) ||
	      currentQuiz.get(3).same(currentQuiz.get(0))){ //makes the program replace a question asking about the same country

	    currentQuiz.remove(3);
	    currentQuiz.add(new Question());
	}
	currentQuiz.add(new Question()); //this adds a new question object 
	
	while(currentQuiz.get(4).same(currentQuiz.get(3)) || currentQuiz.get(4).same(currentQuiz.get(2)) ||
	      currentQuiz.get(4).same(currentQuiz.get(1)) || currentQuiz.get(4).same(currentQuiz.get(0))){ //makes the program replace a question asking about the same country
   
	    currentQuiz.remove(4);
	    currentQuiz.add(new Question());
	}
	currentQuiz.add(new Question()); //this adds a new question object 
	   
	while(currentQuiz.get(5).same(currentQuiz.get(4)) || currentQuiz.get(5).same(currentQuiz.get(3)) ||
	      currentQuiz.get(5).same(currentQuiz.get(2)) || currentQuiz.get(5).same(currentQuiz.get(1)) ||
	      currentQuiz.get(5).same(currentQuiz.get(0))){ //makes the program replace a question asking about the same country

	    currentQuiz.remove(5);
	    currentQuiz.add(new Question());
	}
    }
    
    public ArrayList<Question> getQuiz(){ //this creates a method to get the arraylist of question objects
	return currentQuiz;
    }
}

