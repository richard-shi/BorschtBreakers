import java.util.*;
import java.io.File;
import java.net.URLDecoder;
/**
 * This class handles the data used by the BorschtBreakers Application.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
public class DataManager {
	/**
	 * Reference Variable for String. String constant for Profiles file filename.
	 */
	private final String PROFILES_FILE_NAME = "UserProfiles.xml";
	/**
	 * Reference Variable for String. Holds profile file filepath.
	 */
	private String profileFilePath;
	/**
	 * Reference Variable for String. Holds file path for data folder.
	 */
	private String dataPath;
	/**
	 * Reference Variable for UserProfiles. Holds User Profile data.
	 */
	private UserProfiles userProfiles = new UserProfiles();
	/**
	 * Reference Variable for List<Lesson>. Holds Lessons. 
	 */
	private List<Lesson> lessons;
	/**
	 * Reference Variable for QuizResults. Holds quiz results for every player. 
	 */
	public QuizResults quizResults;

	/**
	 * Class constructor. 
	 * @param dataPath Reference variable for String. Holds filePath for data folder.
	 * @throws Exception Thrown if any errors occur.
	 */
	public DataManager(String dataPath) throws Exception {
		this.dataPath = dataPath;
		quizResults = new QuizResults(dataPath);
		quizResults.LoadQuizResults();
		profileFilePath = URLDecoder.decode(dataPath + File.separator
				+ PROFILES_FILE_NAME, "UTF-8");
	}

	/**
	 * Loads Profile into userProfiles.
	 * @throws Exception If userProfiles is null.
	 */
	public void loadProfile() throws Exception {
		userProfiles = UserProfiles.LoadProfile(profileFilePath);
		if (userProfiles == null)
			throw new Exception("Cannot load user profiles from filepath: " + profileFilePath);
	}

	/**
	 * Loads Lessons from Lesson Def file.
	 * <p>
	 * Local Variable dictionary:
	 * <p>
	 * lessonData: Reference variable for LessonData. Holds the Lessons Data.
	 * <p>
	 * lessonFilePath: Reference variable for String. Holds the Lesson files path.
	 * @param lessonDefFileName Reference variable for String. Holds Lesson Definition File Filename.
	 * @throws Exception Thrown if any errors occur.
	 */
	public void loadLesson(String lessonDefFileName) throws Exception {
		LessonData lessonData = new LessonData();
		String lessonFilePath = dataPath + File.separator + lessonDefFileName;
		lessonData.loadLessonDef(lessonFilePath);
		lessons = lessonData.getLessonDef().getLessons();
	}
	
	/**
	 * Gets all user Profiles.
	 * @return The UserProfiles class.
	 */
	public UserProfiles getAllUserProfiles() {
		return userProfiles;
	}

	/**
	 * Gets a userProfile based on its name
	 * @param userId Reference variable for String. Holds the name of the wanted userProfile.
	 * @return The Profile 
	 */
	public Profile getUserProfile(String userId) {
		return userProfiles.getUserProfile(userId);
	}

	/**
	 * Adds a new Profile
	 * @param userId the name of the new profile to add.
	 * @return Whether there is already a user with the name or not.
	 */
	public boolean addUser(String userId) {
		return userProfiles.addUser(userId);
	}

	/**
	 * Saves all user profiles.
	 */
	public void saveUserProfiles() {
		userProfiles.SaveProfile(profileFilePath, userProfiles);
	}

	/**
	 * Gets all lessons.
	 * @return a List of all Lessons
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}
}
