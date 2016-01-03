import java.io.File;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * This class holds a singular Profile's Data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement
class Profile {
	/**
	 * Reference Variable for String. Holds User ID.
	 */
	private String userId;
	/**
	 * Reference Variable for String. Holds Profile Bookmark Lesson ID.
	 */
	private String bookmarkLessonID;
	/**
	 * int variable for Profile Bookmark Card Index
	 */
	private int bookmarkCardIndex;

	/**
	 * Gets Bookmarked Lesson ID
	 * 
	 * @return String Profile BookMark Lesson ID
	 */
	public String getBookmarkLessonID() {
		return bookmarkLessonID;
	}

	/**
	 * Sets the BookMarked Lesson ID.
	 * 
	 * @param bookmarkLessonID
	 *            String to set bookmark LessonId to.
	 */
	@XmlElement(name = "BookmarkLessonID")
	public void setBookmarkLessonID(String bookmarkLessonID) {
		this.bookmarkLessonID = bookmarkLessonID;
	}

	/**
	 * gets the bookmarked Lesson Card Index
	 * 
	 * @return int variable of bookmarked Lesson Card index.
	 */
	public int getBookmarkCardIndex() {
		return bookmarkCardIndex;
	}

	/**
	 * Sets the bookmarked Lesson Card Index
	 * 
	 * @param bookmarkCardIndex
	 *            Int bookmarkindex to set to.
	 */
	@XmlElement(name = "BookmarkLessonCard")
	public void setBookmarkCardIndex(int bookmarkCardIndex) {
		this.bookmarkCardIndex = bookmarkCardIndex;
	}

	/**
	 * Gets Bookmarked User ID.
	 * 
	 * @return String userID.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets Bookmarked User ID.
	 * 
	 * @param userId
	 *            String userId to set to.
	 */
	@XmlAttribute(name = "UserID")
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

/**
 * This class holds all Profile Data.
 * 
 * @author Richard shi
 * @version 2.0, June 10 2013
 */
@XmlRootElement(name = "Profiles")
@XmlAccessorType(XmlAccessType.FIELD)
class UserProfiles {
	/**
	 * Reference Variable for List<Profile>. Holds All Profiles from file.
	 */
	@XmlElement(name = "Profile")
	private List<Profile> profiles = new ArrayList<Profile>();

	/**
	 * Gets List of All Profiles
	 * 
	 * @return a List<Profiles> with all the profiles.
	 */
	public List<Profile> getProfiles() {
		return profiles;
	}

	/**
	 * Sets the list of All profiles.
	 * 
	 * @param profiles
	 *            List to set field to.
	 */
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	/**
	 * Gets a user Profile by its userId
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * p: Reference Variable for Profile. Temporarily holds every Profile in the
	 * list.
	 * 
	 * @param userId
	 *            The String to identify a profile with.
	 * @return The user Profile with the matching user ID. Returns null if it
	 *         doesn't exist.
	 */
	public Profile getUserProfile(String userId) {
		for (Profile p : profiles) {
			if (p.getUserId().equalsIgnoreCase(userId))
				return p;
		}
		return null;
	}

	/**
	 * Updates user profile.
	 * 
	 * @param p
	 *            The Profile to update.
	 * @return A boolean variable that displays Whether the profile was updated
	 *         correctly or not.
	 */
	public boolean updateUserProfile(Profile p) {
		if (!removeUser(p.getUserId()))
			return false; // User doesn't exist

		profiles.add(p);
		return true;
	}

	/**
	 * Adds user to the list of Profiles. Local Variable Dictionary:
	 * <p>
	 * p: Reference Variable for Profile to be added to the list.
	 * 
	 * @param userId
	 *            The String that will be the new Profiles User ID.
	 * @return A boolean variable that displays Whether the profile with the
	 *         User id Already exists or not.
	 */
	public boolean addUser(String userId) {
		if (isUserExists(userId))
			return false;

		Profile p = new Profile();
		p.setUserId(userId);
		profiles.add(p);
		return true;
	}

	/**
	 * Removes user from profile list. Local Variable Dictionary:
	 * <p>
	 * i: int loop variable
	 * 
	 * @param userId
	 *            String reference variable. This method removes the Profile
	 *            with this userID.
	 * @return Whether the profile to be remove was found or not.
	 */
	public boolean removeUser(String userId) {
		for (int i = 0; i < profiles.size(); i++) {
			if (profiles.get(i).getUserId() == userId) {
				profiles.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a user exists in the list or not.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * p : Reference Variable for profile. Used to temporarily hold all
	 * profiles.
	 * 
	 * @param userId
	 *            String reference variable. This method checks whether a
	 *            Profile exists in the list with this userID.
	 * @return Whether the profile exists in the list or not.
	 */
	public boolean isUserExists(String userId) {
		for (Profile p : profiles) {
			if (p.getUserId().equalsIgnoreCase(userId))
				return true;
		}
		return false;
	}

	/****************************************************
	 * Helper functions. Using XML binding to serialize/de-serialize to/from
	 * file.
	 ****************************************************/

	/**
	 * Saves the current list of profiles into the XML file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of user
	 * Profiles File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * marshaller: Reference variable for Marshaller. Reads in data from a
	 * userProfiles class and saves it to the XML file.
	 * 
	 * @param fileName
	 *            String reference variable. Holds the filename of the file to
	 *            save to.
	 * @param profiles
	 *            Reference variable for UserProfiles. Holds the class to get
	 *            data from to save to file.
	 * @exception JAXBException XML marshalling failure.
	 */
	public static void SaveProfile(String fileName, UserProfiles profiles) {
		try {
			File file = new File(fileName);
			JAXBContext jc = JAXBContext.newInstance(UserProfiles.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(profiles, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the current list of profiles from the XML profile file.
	 * <p>
	 * Local Variable Dictionary
	 * <p>
	 * file: Reference variable for File. Holds File representation of user
	 * Profiles File.
	 * <p>
	 * jc: Reference variable for JAXBContext. Provides a way to access XML
	 * binding methods.
	 * <p>
	 * unmarshaller: Reference variable for Unmarshaller. Reads in data from a
	 * user Profiles XML file and loads it into a userProfiles class.
	 * <p>
	 * profiles: Reference Variable for UserProfiles. Holds class to load data
	 * into.
	 * 
	 * @param fileName
	 *            String reference variable. Holds the filename of the file to
	 *            load from.
	 * @return UserProfiles class with data loaded in.
	 * @exception JAXBException XML marshalling failure.
	 */
	public static UserProfiles LoadProfile(String fileName) {
		try {
			File file = new File(fileName);
			UserProfiles profiles = new UserProfiles();
			JAXBContext jc = JAXBContext.newInstance(UserProfiles.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			profiles = (UserProfiles) unmarshaller.unmarshal(file);
			return profiles;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
