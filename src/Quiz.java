import java.util.*;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * This enum holds a quizType constants
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
enum QuizType {
	QT_MultiChoice,
	QT_SingleQuestion
}

/**
 * This class holds a singular quiz card's data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
class QuizCard {	
	/**
	 * Reference Variable for String. Holds quiz card ID.
	 */
	@XmlElement(name = "QuizCardID")
	private String quizCardId;
	
	/**
	 * Reference Variable for String. Holds Quiz Card Filename
	 */
	@XmlElement(name = "QuizCardFilename")
	private String quizCardFilename; // An html file containing the quiz card (text/image/video/audio ...)
	
	/**
	 * Reference Variable for String. Holds Answer.
	 */
	@XmlElement(name = "Answer")
	private String answerId; 
	
	/**
	 * Gets the current Quiz Card ID.
	 * @return String that holds the quiz card ID.
	 */
	public String getQuizCardId() {
		return quizCardId;
	}

	/**
	 * Sets the quiz Card's ID
	 * @param quizCardId String to set ID to.
	 */
	public void setQuizCardId(String quizCardId) {
		this.quizCardId = quizCardId;
	}

	/**
	 * Gets the Current Quiz Card's filename.
	 * @return String that holds the current quiz card's filename.
	 */
	public String getQuizCardFilename() {
		return quizCardFilename;
	}

	/**
	 * Sets the current quiz Cards Filename
	 * @param quizCardFilename String to set the filename to.
	 */
	public void setQuizCardFilename(String quizCardFilename) {
		this.quizCardFilename = quizCardFilename;
	}
	
	/**
	 * Gets the Answer of the current quiz card.
	 * @return String that holds the answer.
	 */
	public String getAnswerID() {
		return answerId;
	}

	/**
	 * Sets the answer for the current quizCard.
	 * @param answerId
	 */
	public void setAnswerID(String answerId) {
		this.answerId = answerId;
	}
	
	/**
	 * Checks the users input with the quiz card's answer, and returns whether it was correct or not.
	 * @param userAnswer String to check answer with
	 * @return A boolean variable that holds whether the answer was correct or not.
	 */
	public boolean checkAnswer(String userAnswer) {
		return answerId.equalsIgnoreCase(userAnswer);
	}
}

/**
 * This class holds a whole quiz and its data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement(name="Quiz")
//@XmlAccessorType(XmlAccessType.FIELD)
class Quiz {
	/**
	 * List of QuizCards that correspond to this quiz.
	 */
	@XmlElement(name = "QuizCard")
	private List<QuizCard> quizCards = new ArrayList<QuizCard>();

	/**
	 * Current int quiz card index the class is on.
	 */
	private int quizIndex = 0;
	
	/**
	 * Returns the corresponding list of quiz Cards.
	 * @return the List of QuizCards.
	 */
	public List<QuizCard> getQuizCards() {
		return quizCards;
	}

	/**
	 * Set the current list of QuizCards
	 * @param quizCards the list to set the classes field to.
	 */
	public void setQuizCard(List<QuizCard> quizCards) {
		this.quizCards = quizCards;
	}
	
	/****************************************************
	 * Utilities
	 ****************************************************/	
	
	/**
	 * Adds a QuizCard to the list.
	 * @param card QuizCard to add to the list.
	 */
	public void addQuizCard(QuizCard card) {
		quizCards.add(card);
	}
	
	/**
	 * Gets the next QuizCard in the list.
	 * @return the next QuizCard in the list.
	 */
	public QuizCard getNextQuizCard() {
		if (quizIndex < quizCards.size() - 1)
			return quizCards.get(++quizIndex);
		return null;
	}

	/**
	 * Gets the current QuizCard
	 * @return the Current QuizCard
	 */
	public QuizCard getCard(){
		return quizCards.get(quizIndex); 
	}
	
	/**
	 * Gets the previous Quiz Card.
	 * @return The previous QuizCard.
	 */
	public QuizCard getPreviousQuizCard() {
		if (quizIndex > 0 )
			return quizCards.get(--quizIndex);
		return null;
	}
	
	/**
	 * Gets whether the current QuizCard is the first one or not.
	 * @return A boolean which holds whether it is the first or not.
	 */
	public boolean isFirst(){
		return quizIndex == 0;
	}
	
	/**
	 * Gets whether the current QuizCard is the last one or not.
	 * @return A boolean which holds whether it is the last or not.
	 */
	public boolean isLast(){
		return quizIndex == quizCards.size() - 1;
	}
	
	/**
	 * Gets current quiz index
	 * @return int that holds the current QuizIndex.
	 */
	public int getQuizIndex() {
		return quizIndex;
	}

	/**
	 * Resets quiz to first QuizCard.
	 */
	public void resetQuiz() {
		quizIndex = 0;
	}
	

	/****************************************************
	 * Helper functions. Using XML binding to serialize/de-serialize to/from file.
	 ****************************************************/
	/**
	 * Saves the current list of Quizzes into the XML file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of quiz File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * marshaller: Reference variable for Marshaller. Reads in data from a
	 * Quiz class and saves it to the XML file.
	 * 
	 * @param fileName
	 *            String reference variable. Holds the filename of the file to
	 *            save to.
	 * @param quiz
	 *            Reference variable for Quiz. Holds the class to get
	 *            data from to save to file.
	 * @exception JAXBException XML marshalling failure.
	 */
	public static void SaveQuiz(String fileName, Quiz quiz) {
		try {
			File file = new File(fileName);
			JAXBContext jc = JAXBContext.newInstance(Quiz.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(quiz, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the current quiz from file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of quiz file.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * unmarshaller: Reference variable for Unmarshaller. Reads in data from a
	 * quiz XML file and loads it into a Quiz class.
	 * <p>
	 * quiz: Reference Variable for Quiz. Holds class to load data 
	 * into.
	 * 
	 * @param fileName
	 *            String reference variable. Holds the filename of the file to
	 *            load from.
	 * @exception JAXBException XML marshalling failure.
	 * @return Quiz class with data loaded in.
	 */
	public static Quiz LoadQuiz(String fileName) {
		try {
			File file = new File(fileName);
			Quiz quiz = new Quiz();
			JAXBContext jc = JAXBContext.newInstance(Quiz.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			quiz = (Quiz) unmarshaller.unmarshal(file);
			return quiz;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}

