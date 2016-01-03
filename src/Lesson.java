import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;

/**
 * This class holds Lesson Data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Lesson {
	/**
	 * Reference Variable for String. Holds Lesson ID.
	 */
	@XmlElement(name = "LessonID")
	private String lessonId;
	
	/**
	 * Reference Variable for String. Holds Quiz File Name. 
	 */
	@XmlElement(name = "QuizFileName")
	private String quizFileName;

	/**
	 * Reference Variable for List<String>. Holds list of the lessons Card filenames.
	 */
	@XmlElementWrapper(name = "CardFilenames")
	@XmlElement(name = "CardFileName")
	private List<String> cardFileNames;

	/**
	 * Gets Lesson ID
	 * @return String Lesson ID
	 */
	public String getLessonId() {
		return lessonId;
	}

	/**
	 * Sets the Lesson ID
	 * @param lessonId String to set Lesson ID to.
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * Gets Quiz FileName
	 * @return String Quiz Filename
	 */
	public String getQuizFileName() {
		return quizFileName;
	}

	/**
	 * Sets the Quiz File name for this Lesson
	 * @param quizFileName String to set Quiz Filename to.
	 */
	public void setQuizFileName(String quizFileName) {
		this.quizFileName = quizFileName;
	}

	/**
	 * Gets a List of Card Filenames.
	 * @return List<String> of card filenames
	 */
	public List<String> getCardFileNames() {
		return cardFileNames;
	}

	/**
	 * Sets the list of card fileNames.
	 * @param cardFileNames List<String> to set card FileName List to.
	 */
	public void setCardFileNames(List<String> cardFileNames) {
		this.cardFileNames = cardFileNames;
	}

	/**
	 * Gets the amount of cards in lesson.
	 * @return int amount of cards. 
	 */
	public int getTotalCards() {
		return cardFileNames.size();
	}
}

/**
 * This class holds the Lesson Definition data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement(name="Lessons")
@XmlAccessorType(XmlAccessType.FIELD)
class LessonDefinition {
	
	/**
	 * Reference Variable for List<Lesson>. Holds list of the lessons defined in Lesson Definition file.
	 */
	@XmlElement(name="Lesson")
	private List<Lesson> lessons;
	
	/**
	 * Sets the list of Lessons.
	 * @param lessons List<Lessons> to set list to.
	 */
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	/**
	 * gets List of Lessons.
	 * @return List of Lessons.
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}
}

/**
 * This class holds the Lesson Definition data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
class LessonData {
	/**
	 * Reference Variable for String. Holds Lesson Definition Filename
	 */
	private String defFileName;
	/**
	 * int variable for holding current card index.
	 */
	private int cardIndex = 0;
	/**
	 * Reference Variable for LessonDefinition. Holds representation of what files equate to a lesson.
	 */
	LessonDefinition lessonDef = new LessonDefinition();

	/**
	 * Loads Lesson Definition file into LessonDefinition classes.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of lesson Definition File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML binding methods.
	 * <p>
	 * unmarshaller: Reference variable for Unmarshaller. Reads in data from XML and binds it to specific variables in a Lesson definition class.
	 * @param defFileName String filename for Lesson Definition.
	 * @throws Exception If LessonDefinition Cannot be loaded from file.
	 */
	public void loadLessonDef(String defFileName) throws Exception {
		this.defFileName = defFileName;

		// Load the lesson data based on the lesson definition file
		try {
			File file = new File(defFileName);
			JAXBContext jc = JAXBContext.newInstance(LessonDefinition.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			lessonDef = (LessonDefinition) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			// e.printStackTrace();
			throw new Exception(
					"Cannot load lesson from lesson definition file.");
		}
	}

	/**
	 * Saves Lesson Definition file by getting data from LessonDefinition
	 * classes.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of lesson
	 * Definition File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * marshaller: Reference variable for Marshaller. Reads in data from a
	 * Lesson Definition class and writes it to the LessonDefinition file.
	 * 
	 * @param defFileName
	 *            String filename for Lesson Definition.
	 * @throws Exception
	 *             If data Cannot be saved into file.
	 */
	public void saveLessonDef(String defFileName) throws Exception {
		try {
			// For debugging only
			File file = new File(defFileName);
			JAXBContext jc = JAXBContext.newInstance(LessonDefinition.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(lessonDef, file);
		} catch (JAXBException e) {
			// e.printStackTrace();
			throw new Exception(
					"Cannot save lesson into lesson definition file.");
		}
	}
	
	/**
	 * Gets the current LessonDefinition Class
	 * @return current LessonDefinition class.
	 */
	public LessonDefinition getLessonDef() {
		return lessonDef;
	}

	/**
	 * Sets the current LessonDefinition Class
	 * @param lessonDef LessonDefintion class to set current one to.
	 */
	public void setLessonDef(LessonDefinition lessonDef) {
		this.lessonDef = lessonDef;
	}
}
