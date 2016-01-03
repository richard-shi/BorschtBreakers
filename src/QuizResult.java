import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class holds the score results from a Quiz.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement
class QuizResult {
	/**
	 * Reference Variable for Date. Holds Date and time of Quiz taking. 
	 */
	private Date dateTaken;
	/**
	 * Reference Variable for Float. Holds score value.
	 */
	private Float score;
	/**
	 * Reference Variable for String. Holds the User ID of the Quiz Result.
	 */
	private String userId;
	/**
	 * Reference Variable for String. Holds the Lesson ID of the Quiz taken.
	 */
	private String lessonId;

	/**
	 * Gets the User ID of the QuizResult.
	 * @return String with the User ID.
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Sets the userID of the QuizResult.
	 * @param userId The String to set the userId to.
	 */
	@XmlElement(name = "UserID")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the ID of the Lesson the QuizResults belongs to.
	 * @return String with the Lesson Id.
	 */
	public String getLessonId() {
		return lessonId;
	}

	/**
	 * Sets the Lesson ID
	 * @param lessonId String to set LessonId to.
	 */
	@XmlElement(name = "Level")
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * Gets the Date Taken
	 * @return a Date class with the current Date.
	 */
	public Date getDate() {
		return dateTaken;
	}

	/**
	 * Sets the current Date taken
	 * @param dateTaken the Date to set it to.
	 */
	@XmlElement(name = "DateTaken")
	public void setDate(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	/**
	 * Returns the User's Quiz Score.
	 * @return A Float with the score.
	 */
	public Float getScore() {
		return score;
	}

	/**
	 * sets the score value.
	 * @param score The Float to set score to.
	 */
	@XmlElement(name = "Score")
	public void setScore(Float score) {
		this.score = score;
	}
}

/**
 * This class compares two QuizResults.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
class ScoreComparator implements Comparator<QuizResult> {
	/**
	 * Compares two QuizResults, and returns the comparison
	 * @param o1 The first QuizResult to compare
	 * @param o2 The second QuizResult to compare
	 * @return -1 if o2 is smaller, 0 if they are both the same and 1 if o2 is bigger.
	 */
	public int compare(QuizResult o1, QuizResult o2) {
		return o2.getScore().compareTo(o1.getScore());
	}
}

/**
 * This class holds Quiz Result data for all Quiz Results.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement(name = "QuizResults")
//@XmlAccessorType(XmlAccessType.FIELD)
class QuizResults {
	/**
	 * Reference Variable for List<QuizResults>. Holds all QuizResults in file.
	 */
	private List<QuizResult> results = new ArrayList<QuizResult>();
	/**
	 * Reference Variable for String. String constant for QuizResults filename
	 */
	private final String quizResultFilename = "quiz_results.xml";
	/**
	 * Reference Variable for String. Holds quiz Results file filepath
	 */
	private String quizResultFullPath;

	/**
	 * Constructor
	 */
	public QuizResults() {}
	
	/**
	 * 
	 * @param filePath String for holding the filePath of the Quiz Results File.
	 * @throws Exception If any error occurs.
	 */
	public QuizResults(String filePath) throws Exception{
		quizResultFullPath = URLDecoder.decode(filePath + "/" + quizResultFilename,"UTF-8");		
	}
	
	/**
	 * gets a list of all the QuizResults from the file.
	 * @return A list of QuizResults.
	 */
	@XmlElement(name = "QuizResult")	
	public List<QuizResult> getQuizResults() {
		sortScores();
		return results;
	}

	/**
	 * Sets List of QuizResults
	 * @param results the new List to set to.
	 */
	public void setQuizResults(List<QuizResult> results) {
		this.results = results;
	}

	/****************************************************
	 * Helper functions. Using XML binding to serialize/de-serialize to/from
	 * file.
	 ****************************************************/
	
	/**
	 * Saves the current list of quizResult into the XML file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of QuizResults File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * marshaller: Reference variable for Marshaller. Reads in data from a
	 * QuizResults class and saves it to the XML file.
	 * 
	 * @exception JAXBException XML marshalling failure.
	 */
	public void SaveQuizResult() {
		try {
			File file = new File(quizResultFullPath);
			JAXBContext jc = JAXBContext.newInstance(QuizResults.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(this, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the current quizResults from file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of QuizResults file.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * unmarshaller: Reference variable for Unmarshaller. Reads in data from a
	 * QuizResults XML file and loads it into a QuizResults class.
	 * <p>
	 * quizResults: Reference Variable for QuizResults. Holds class to load data 
	 * into.
	 * 
	 * @exception JAXBException XML marshalling failure.
	 * @return QuizResults class with data loaded in.
	 */
	public void LoadQuizResults() {
		try {
			File file = new File(quizResultFullPath);
			QuizResults quizResults = new QuizResults();
			if (file.exists()) {
				JAXBContext jc = JAXBContext.newInstance(QuizResults.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				quizResults = (QuizResults) unmarshaller.unmarshal(file);
				this.results = quizResults.results;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/****************************************************
	 * Utilities
	 ****************************************************/
	/**
	 * Sorts the Scores in the QuizResults class
	 * <p>
	 * Local Variable dictionary:
	 * <p>
	 * comparator: Reference Variable of Comparator<QuizResult>. Holds a ScoreComparator that compares QuizResult classes.
	 */
	private void sortScores() {
		Comparator<QuizResult> comparator = new ScoreComparator();
		Collections.sort(results, comparator);
	}

	/**
	 * Gets the QuizResults formatted into Html Data.
	 * <p>
	 * Local Variable dictionary:
	 * <p>
	 * htmlResult: Reference variable for String. Holds the Html Data
	 * <p>
	 * q: Reference Variable for QuizResult. Temporarily holds All quizResult classes.
	 * @return htmlResult
	 */
	public String getQuizResultsHtml() {
		String htmlResult = "<html><head><title>Quiz Results</title></head><body><h1>Quiz Results</h1>"
				+ "<table class=MsoTableGrid border=1><tr><td><b>Score</b></td><td><b>User Id</b></td><td><b>Level</b></td><td><b>Date Taken</b></td></tr>";
		for (QuizResult q : results) {
			htmlResult += "<tr><td>" + q.getScore() + "</td><td>"
					+ q.getUserId() + "</td><td>" + q.getLessonId() + "</td><td>" + q.getDate() + "</td></tr>";
		}
		htmlResult += "</table></body></html>";
		return htmlResult;
	}	
	
	/**
	 * Clears QuizResults of all QuizResult Classes.
	 */
	public void clearAll() {
		results.clear();
	}
	
	/**
	 * Adds a quizResult to the QuizResult List
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * i: For loop variable.
	 * @param userId String to set UserID of.
	 * @param lessonId String to set LessonID of.
	 * @param dateTaken Date to set date of.
	 * @param score Float to set score of.
	 */
	public void addUserQuizResult(String userId, String lessonId,
			Date dateTaken, Float score) {
		QuizResult result = new QuizResult();
		result.setUserId(userId);
		result.setLessonId(lessonId);
		result.setDate(dateTaken);
		result.setScore(score);
		results.add(result);
		sortScores();
		
		// Only keep at most 10 records
		for (int i = 10; i < results.size(); i++)
			results.remove(i);
	}

	/**
	 * Adds a quizResult to the QuizResult List
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * dateTaken: Reference Variable for Date. Holds Date Class with current Date and Time.
	 * @param userId String to set UserID of.
	 * @param lessonId String to set LessonID of.
	 * @param score Float to set score of.
	 */
	public void addUserQuizResult(String userId, String lessonId, Float score) {
		Date dateTaken = new Date();
		addUserQuizResult(userId, lessonId, dateTaken, score);
	}

	/**
	 * Checks if the Quiz has been taken before.
	 * @param userId String userId to check.
	 * @param lessonId String LessonId to check.
	 * @return Whether the Quiz has been taken by the user with userId or not.
	 */
	public boolean hasQuizBeenTaken(String userId, String lessonId) {
		for (QuizResult result : results) {
			if (result.getUserId().equalsIgnoreCase(userId)
					&& result.getLessonId().equalsIgnoreCase(lessonId)) {
				return true;
			}
		}
		return false;
	}
}