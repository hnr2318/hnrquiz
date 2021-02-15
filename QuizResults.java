package edu.uga.cs1302.quiz; //this properly packages the class

import java.util.ArrayList; //these imports allow for the date to be stored 
import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  

public class QuizResults implements Serializable{

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //this creates the format for storing the date and time
    private String dateString; //these string store the date and time
    private String infoString;
    private int score;
    private Date date;
    private String scoreText;
    private static final long serialVersionUID = -2790881574583608765L;

    
    public QuizResults(Date date, int score){ //this creates the quiz result constructor
	this.date = date;
	this.score = score;
    }

    public String getInfo(){ //this method is used to get one line including the score and date of a quizresult object
	dateString = "";
	scoreText = "";
	scoreText = Integer.toString(score);
	infoString = "";
	infoString = "Date: " + format.format(date) + "\tScore: " + scoreText + "\n";
	return infoString;
    }
}
