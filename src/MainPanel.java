import javax.swing.JFrame;
import java.awt.CardLayout;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import javax.swing.border.BevelBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import virtualkeyboard.gui.DialogVirtualKeyboardReal;

/**
 * This application teaches users the Russian language and quizzes them on it.
 * 
 * @author Richard shi & Daniel Marin
 * @version 2.0, June 10 2013
 */
public class MainPanel implements ActionListener {
	/**
	 * Reference Variable for String. Holds root file path
	 */
	public static String rootPath;
	/**
	 * int constant for holding Splash Screen Wait time
	 */
	public static final int SPLASHSCREEN_WAIT_TIME = 3000;
	/**
	 * Reference Variable for Font. Font constant for Holding main Menu Font.
	 */
	public static final Font MAIN_MENU_FONT = new Font("Tahoma", Font.PLAIN, 18);
	/**
	 * Reference Variable for Font. Font constant for Holding title font.
	 */
	public static final Font TITLE_FONT = new Font("Impact", Font.PLAIN, 40);
	/**
	 * Reference Variable for String. Holds Data Folder Location constant.
	 */
	public static final String DATA_FOLDER = "data";
	/**
	 * Reference Variable for String. Constant for Lesson Definition File name.
	 */
	public static String lessonDefFilename = "lessondef.xml";
	/**
	 * Reference Variable for String. Holds Virtual Keyboard Locale.
	 */
	public static final String KEYBOARD_LOCALE = "ru";

	/**
	 * Reference Variable for JFrame. Holds Main Game JFrame.
	 */
	private JFrame BorschtbreakersFrame;
	/**
	 * Reference Variable for JButton. Button Logs off user.
	 */
	private JButton logOffButton;
	/**
	 * Reference Variable for JButton. Button changes the current question to
	 * the next in the lesson.
	 */
	private JButton nextQuestion;
	/**
	 * Reference Variable for JLabel. Displays current User.
	 */
	private JLabel statusLabel;
	/**
	 * Reference Variable for JButton. Button submits answer.
	 */
	private JButton submitButton;
	/**
	 * Reference Variable for quizTextField. Holds JTextField for typing in quiz
	 * answer.
	 */
	private JTextField quizTextField;
	/**
	 * Reference Variable for JButton. Holds JButton that changes the current
	 * question to the previous in the lesson.
	 */
	private JButton previousQuestion;
	/**
	 * Reference Variable for JEditorPane. Holds JEditorPane that displays the
	 * Lessons.
	 */
	private JEditorPane lessonDisplay;
	/**
	 * Reference Variable for JEditorPane. Holds JEditorPane that displays the
	 * quizes.
	 */
	private JEditorPane quizDisplay;
	/**
	 * Reference Variable for CardLayout. Controls the Layout for the main
	 * JFrame.
	 */
	private CardLayout cl = new CardLayout();
	/**
	 * Reference Variable for DataManager. Manages and Controls all data within
	 * the program.
	 */
	private DataManager dataManager;
	/**
	 * Reference Variable for String. Holds current user.
	 */
	private String currentUser;
	/**
	 * Reference Variable for String. Holds current Lesson File Path.
	 */
	private String currentLessonFile;
	/**
	 * Reference Variable for String. Holds current Quiz File path.
	 */
	private String currentQuizFile;
	/**
	 * Reference Variable for Profile. Holds current user Profile.
	 */
	private Profile currentProfile;
	/**
	 * Reference Variable for List<Lesson>. Holds a list of the current Lessons
	 * from the Lesson Definition file.
	 */
	private List<Lesson> currentLessons;
	/**
	 * int for holding current Lesson index in List.
	 */
	private int currentLessonIndex;
	/**
	 * int for holding the current Lesson card file index.
	 */
	private int currentLessonCardIndex;
	/**
	 * Reference Variable for String. Holds the previous Lesson ID.
	 */
	private String previousLessonID;
	/**
	 * Reference Variable for String. Holds the current Lesson ID.
	 */
	private String currentLessonID;
	/**
	 * int for holding the amount of questions within the current quiz.
	 */
	private int currentQuestionAmount;
	/**
	 * int for holding the current amount of questions correctly answered.
	 */
	private int currentCorrectAnswers;
	/**
	 * Reference Variable for Quiz. Holds the current Quiz.
	 */
	private Quiz currentLessonQuiz;
	/**
	 * int for holding the amount of questions answered within the current quiz.
	 */
	private int progressQuizIndex = -1;

	/**
	 * Main method. Starts Game. Local Variable Dictionary:
	 * <p>
	 * MainPanel: Reference Variable for MainPanel. Main GUI class and Driver
	 * Class.
	 * 
	 * @param args
	 *            String[] array for passing parameters to class.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			lessonDefFilename = args[0];
		}
		MainPanel window = new MainPanel();
		window.BorschtbreakersFrame.setVisible(true);
	}

	/**
	 * Class Constructor. Creates the main JFrame and sets up the driver class.
	 * Local Variable Dictionary:
	 * <p>
	 * e : Reference variable for Exception. Holds Error Message.
	 */
	public MainPanel() {
		try {
			// rootPath = this.getClass().getClassLoader().getResource("")
			// .getPath();
			//rootPath = this.getClass().getResource(".").getPath() + "/";
			String tmpPath = this.getClass().getResource(".").getPath();
			File file = new File(tmpPath);
		    rootPath = file.getAbsolutePath() + "\\";
		        
			showSplashScreen();
			initializeGUI();
			dataManager = new DataManager(rootPath + DATA_FOLDER);
			dataManager.loadProfile();
			dataManager.loadLesson(lessonDefFilename);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(BorschtbreakersFrame,
					"Error in MainPanel: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Initializes GUI of the main JFrame.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * menuPanel: Reference Variable for JPanel. Holds Main Menu Canvas
	 * <p>
	 * mainMenuPanel: Reference Variable for JPanel. Holds Main Menu Buttons +
	 * Logo
	 * <p>
	 * gameLogo: Reference Variable for JLabel. Displays Logo and/or title
	 * <p>
	 * playGameButton: Reference Variable for JButton. Plays Game.
	 * <p>
	 * instructionsButton: Reference Variable for JButton. Opens a HelpWindow
	 * that displays information about the game.
	 * <p>
	 * highScoresButton: Reference Variable for JButton. Opens a highScores
	 * window that displays the highScores.
	 * <p>
	 * quitButton: Reference Variable for JButton. Quits game.
	 * <p>
	 * statusPanel: Reference Variable for JPanel. Holds status bar content.
	 * <p>
	 * lessonPanel: Reference Variable for JPanel. Holds Lessons.
	 * <p>
	 * quizPanel: Reference Variable for JPanel. Holds Quizzes.
	 * <p>
	 * lessonScrollPane: Reference Variable for JScrollPane. Adds Scroll Bars to
	 * the Lesson Display if needed.
	 * <p>
	 * lessonToolBar: Reference Variable for JPanel. Displays ToolBar in the
	 * Lesson screen to navigate with.
	 * <p>
	 * returnButton_1: Reference Variable for JButton. Holds JButton which
	 * returns user to mainMenu
	 * <p>
	 * lessonChangePanel: Reference Variable for JPanel. Holds the JPanel that
	 * holds the JButtons that change between classes.
	 * <p>
	 * nextLesson: Reference Variable for JButton. Changes the current lesson
	 * card file to the next one in the lesson definition.
	 * <p>
	 * previousLesson: Reference Variable for JButton. Changes the current
	 * lesson card file to the previous one in the lesson definition.
	 * <p>
	 * quizPanel: Reference Variable for JSplitPane. Holds Both the Displayed
	 * Quiz HTML File as well as the Quiz Game Question Components.
	 * <p>
	 * quizDisplayPanel: Reference Variable for JPanel. Displays the JEditorPane
	 * that Displays the Quiz Files.
	 * <p>
	 * quizScrollPane: Reference Variable for JScrollPane. Adds ScrollBars to
	 * the Quiz Display Panel.
	 * <p>
	 * quizContent: Reference Variable for JPanel. Holds the main quiz
	 * components
	 * <p>
	 * quizGame: Reference Variable for JPanel. Holds the components that make
	 * up the quiz system.
	 * <p>
	 * quizAnswerPrompt: Reference Variable for JLabel. Displays a prompt to get
	 * the user's input.
	 * <p>
	 * gl_quizGame: Reference Variable for GroupLayout. Organizes the Layout of
	 * the Quiz Game Components.
	 * <p>
	 * quizToolBar: Reference Variable for JPanel. Holds JButtons for navigating
	 * the Quizzes.
	 * <p>
	 * utilPanel: Reference Variable for JPanel. Holds and Positions the open
	 * Keyboard Button.
	 * <p>
	 * openKeyboardButton: Reference Variable for JButton. Opens up the Russian
	 * keyboard.
	 * <p>
	 * questionChangePanel: Reference Variable for JPanel. Organizes the
	 * navigation buttons by putting them close to each other.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */

	private void initializeGUI() throws Exception {
		BorschtbreakersFrame = new JFrame();
		BorschtbreakersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorschtbreakersFrame.setResizable(false);
		BorschtbreakersFrame.setTitle("BorschtBreakers: Legend of Russia");
		BorschtbreakersFrame.setBounds(100, 100, 800, 600);
		BorschtbreakersFrame.getContentPane().setLayout(cl);

		JPanel menuPanel = new JPanel();
		BorschtbreakersFrame.getContentPane().add(menuPanel, "1");
		menuPanel.setLayout(new BorderLayout(0, 0));

		JPanel mainMenuPanel = new JPanel();
		menuPanel.add(mainMenuPanel, BorderLayout.CENTER);
		mainMenuPanel.setToolTipText("");
		mainMenuPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainMenuPanel.setForeground(Color.WHITE);
		mainMenuPanel.setBackground(Color.WHITE);
		mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

		JLabel gameLogo = new JLabel("BorschtBreakers");
		gameLogo.setToolTipText("100% Real Russian Borscht!");
		gameLogo.setFont(TITLE_FONT);
		gameLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenuPanel.add(gameLogo);

		JButton playGameButton = new JButton("Play Game");
		playGameButton.setFont(MAIN_MENU_FONT);
		playGameButton
				.setToolTipText("Only 17% chance of death by blue screen!");
		playGameButton.setBackground(Color.WHITE);
		playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		playGameButton.addActionListener(this);
		mainMenuPanel.add(playGameButton);

		JButton instructionsButton = new JButton("Information");
		instructionsButton.setFont(MAIN_MENU_FONT);
		instructionsButton.setToolTipText("Learn How To Learn!");
		instructionsButton.setBackground(Color.WHITE);
		instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructionsButton.addActionListener(this);
		mainMenuPanel.add(instructionsButton);

		JButton highScoresButton = new JButton("High Scores");
		highScoresButton.setFont(MAIN_MENU_FONT);
		highScoresButton.setToolTipText("Who is number one?!");
		highScoresButton.setBackground(Color.WHITE);
		highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		highScoresButton.addActionListener(this);
		mainMenuPanel.add(highScoresButton);

		logOffButton = new JButton("Log Off");
		logOffButton.setToolTipText("Goodbye Everyone!");
		logOffButton.setFont(MAIN_MENU_FONT);
		logOffButton.setEnabled(false);
		logOffButton.setBackground(Color.WHITE);
		logOffButton.setAlignmentX(0.5f);
		logOffButton.addActionListener(this);
		mainMenuPanel.add(logOffButton);

		JButton quitButton = new JButton("Quit");
		quitButton.setFont(MAIN_MENU_FONT);
		quitButton.setToolTipText("Forget This, I'm outta here!");
		quitButton.setBackground(Color.WHITE);
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		quitButton.addActionListener(this);
		mainMenuPanel.add(quitButton);

		JPanel statusPanel = new JPanel();
		statusPanel.setToolTipText("WHO ARE YOU AND WHY ARE YOU IN MY HOUSE?!");
		FlowLayout flowLayout = (FlowLayout) statusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		menuPanel.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(600, 30));

		statusLabel = new JLabel();
		setStatus(statusLabel, currentUser);
		statusLabel.setFont(MAIN_MENU_FONT);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusPanel.add(statusLabel);

		JPanel lessonPanel = new JPanel();
		lessonPanel.setBackground(Color.WHITE);
		BorschtbreakersFrame.getContentPane().add(lessonPanel, "2");
		lessonPanel.setLayout(new BorderLayout(0, 0));

		lessonDisplay = new JEditorPane();
		lessonDisplay.setEditable(false);

		JScrollPane lessonScrollPane = new JScrollPane();
		lessonScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		lessonScrollPane.setViewportView(lessonDisplay);
		lessonPanel.add(lessonScrollPane, BorderLayout.CENTER);

		JPanel lessonToolBar = new JPanel();
		lessonToolBar.setBackground(Color.WHITE);
		lessonToolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		lessonToolBar.setPreferredSize(new Dimension(600, 70));
		lessonPanel.add(lessonToolBar, BorderLayout.SOUTH);
		lessonToolBar.setLayout(new BorderLayout(0, 0));

		JButton returnButton_1 = new JButton("Main Menu");
		returnButton_1.setToolTipText("Let's go home!");
		returnButton_1.addActionListener(this);
		lessonToolBar.add(returnButton_1, BorderLayout.WEST);

		JPanel lessonChangePanel = new JPanel();
		lessonToolBar.add(lessonChangePanel, BorderLayout.EAST);
		lessonChangePanel.setLayout(new BorderLayout(0, 0));

		JButton nextLesson = new JButton("Next Lesson");
		nextLesson.addActionListener(this);
		nextLesson.setToolTipText("Next");
		lessonChangePanel.add(nextLesson, BorderLayout.EAST);

		JButton previousLesson = new JButton("Previous Lesson");
		previousLesson.setToolTipText("Previous");
		previousLesson.addActionListener(this);
		lessonChangePanel.add(previousLesson, BorderLayout.WEST);

		JSplitPane quizPanel = new JSplitPane();
		quizPanel.setOneTouchExpandable(true);
		quizPanel.setContinuousLayout(true);
		quizPanel.setDividerLocation(200);
		quizPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		BorschtbreakersFrame.getContentPane().add(quizPanel, "3");
		//
		quizDisplay = new JEditorPane();
		quizDisplay.setEditable(false);

		JPanel quizDisplayPanel = new JPanel();
		quizDisplay.setLayout(new BorderLayout(0, 0));
		quizPanel.setLeftComponent(quizDisplayPanel);
		quizDisplayPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane quizScrollPane = new JScrollPane();
		quizScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		quizScrollPane.setViewportView(quizDisplay);
		quizDisplayPanel.add(quizScrollPane);

		JPanel quizContent = new JPanel();
		quizPanel.setRightComponent(quizContent);
		quizContent.setLayout(new BorderLayout(0, 0));

		JPanel quizGame = new JPanel();
		quizContent.add(quizGame, BorderLayout.CENTER);

		quizTextField = new JTextField();
		quizTextField.setColumns(20);

		JLabel quizAnswerPrompt = new JLabel("Enter Answer Here:");

		submitButton = new JButton("Submit Answer");
		submitButton.addActionListener(this);
		GroupLayout gl_quizGame = new GroupLayout(quizGame);
		gl_quizGame.setHorizontalGroup(gl_quizGame.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_quizGame
						.createSequentialGroup()
						.addGap(205)
						.addComponent(quizAnswerPrompt)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(quizTextField,
								GroupLayout.PREFERRED_SIZE, 148,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(submitButton)
						.addContainerGap(227, Short.MAX_VALUE)));
		gl_quizGame
				.setVerticalGroup(gl_quizGame
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_quizGame
										.createSequentialGroup()
										.addContainerGap(413, Short.MAX_VALUE)
										.addGroup(
												gl_quizGame
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																quizAnswerPrompt)
														.addComponent(
																quizTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																submitButton))
										.addGap(39)));
		quizGame.setLayout(gl_quizGame);

		JPanel quizToolBar = new JPanel();
		quizToolBar.setBackground(Color.WHITE);
		quizToolBar.setPreferredSize(new Dimension(600, 70));
		quizContent.add(quizToolBar, BorderLayout.SOUTH);
		quizToolBar.setLayout(new BorderLayout(0, 0));

		JPanel utilPanel = new JPanel();
		quizToolBar.add(utilPanel, BorderLayout.WEST);
		utilPanel.setLayout(new BorderLayout(0, 0));

		JButton openKeyboardButton = new JButton("Open Keyboard");
		openKeyboardButton.addActionListener(this);
		utilPanel.add(openKeyboardButton, BorderLayout.EAST);

		JPanel questionChangePanel = new JPanel();
		questionChangePanel.setBackground(Color.WHITE);
		quizToolBar.add(questionChangePanel, BorderLayout.EAST);
		questionChangePanel.setLayout(new BorderLayout(0, 0));

		previousQuestion = new JButton("Previous Question");
		previousQuestion.addActionListener(this);
		questionChangePanel.add(previousQuestion, BorderLayout.WEST);

		nextQuestion = new JButton("Next Question");
		nextQuestion.setEnabled(false);
		nextQuestion.addActionListener(this);
		questionChangePanel.add(nextQuestion);

		mainMenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "Play Game");
		mainMenuPanel.getActionMap().put("Play Game", new AbstractAction() {
			/**
			 * Plays the Game. Local Variable Dictionary:
			 * 
			 * @param e
			 *            ActionEvent Reference variable for ActionEvent object.
			 * @exception ex
			 *                Thrown if any error occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					playGame();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(BorschtbreakersFrame,
							"Error MainPanel::actionPerformed: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		mainMenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false),
				"Instructions");
		mainMenuPanel.getActionMap().put("Instructions", new AbstractAction() {
			/**
			 * Opens a Help window. Local Variable Dictionary:
			 * 
			 * @param e
			 *            ActionEvent Reference variable for ActionEvent object.
			 * @exception ex
			 *                Thrown if any error occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					new HelpWindow(rootPath + DATA_FOLDER);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(BorschtbreakersFrame,
							"Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		mainMenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false),
						"High Scores");
		mainMenuPanel.getActionMap().put("High Scores", new AbstractAction() {
			/**
			 * Opens High Scores. Local Variable Dictionary:
			 * 
			 * @param e
			 *            ActionEvent Reference variable for ActionEvent object.
			 * @exception ex
			 *                Thrown if any error occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					HighScores highScores = new HighScores(dataManager);
					highScores.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(BorschtbreakersFrame,
							"Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Starts game, opens instructions, displays high scores, logs off user and
	 * quits program depending on the button pressed.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * highScores: Reference Variable for HighScores.
	 * 
	 * @param a
	 *            Reference Variable for ActionEvent. Holds ActionEvent command.
	 * @exception e
	 *                Thrown if any error occurs.
	 */
	public void actionPerformed(ActionEvent a) {
		try {
			if (a.getActionCommand().equals("Play Game")) {
				playGame();
			} else if (a.getActionCommand().equals("Information")) {
				new HelpWindow(rootPath + DATA_FOLDER);
			} else if (a.getActionCommand().equals("High Scores")) {
				HighScores highScores = new HighScores(dataManager);
				highScores.setVisible(true);
			} else if (a.getActionCommand().equals("Log Off")) {
				logOff();
			} else if (a.getActionCommand().equals("Main Menu")) {
				returnToMainMenu();
			} else if (a.getActionCommand().equals("Previous Lesson")
					|| a.getActionCommand().equals("Next Lesson")) {
				traverseLesson(a.getActionCommand());
			} else if (a.getActionCommand().equals("Open Keyboard")) {
				openKeyboard();
			} else if (a.getActionCommand().equals("Previous Question")) {
				previousQuestion();
			} else if (a.getActionCommand().equals("Next Question")) {
				nextQuestion();
			} else if (a.getActionCommand().equals("Submit Answer")) {
				submitAndCheckAnswer();
			} else {
				BorschtbreakersFrame.dispose();
				System.exit(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(BorschtbreakersFrame,
					"Error in actionPerformed: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Logs Off user.
	 */
	private void logOff() {
		currentUser = null;
		currentLessonFile = null;
		currentQuizFile = null;
		currentProfile = null;
		currentLessons = null;
		currentLessonIndex = 0;
		currentLessonCardIndex = 0;
		currentLessonID = null;
		setStatus(statusLabel, null);
	}

	/**
	 * returns user from Lesson GUI to Main Menu
	 */
	private void returnToMainMenu() {
		currentProfile.setBookmarkCardIndex(currentLessonCardIndex);
		currentProfile.setBookmarkLessonID(currentLessonID);
		dataManager.getAllUserProfiles().updateUserProfile(currentProfile);
		dataManager.saveUserProfiles();
		setStatus(statusLabel, currentUser);
		cl.show(BorschtbreakersFrame.getContentPane(), "1");
	}

	/**
	 * Ends game. Sets bookmarks back to the beginning and saves profiles.
	 */
	private void endGame() {
		currentProfile.setBookmarkCardIndex(0);
		currentProfile.setBookmarkLessonID(currentLessons.get(0).getLessonId());
		dataManager.getAllUserProfiles().updateUserProfile(currentProfile);
		dataManager.saveUserProfiles();
		setStatus(statusLabel, currentUser);
		cl.show(BorschtbreakersFrame.getContentPane(), "1");
	}

	/**
	 * Plays Game.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * i: for loop variable
	 * <p>
	 * For Loop #1:
	 * <p>
	 * Loop Variable: i
	 * <p>
	 * condition: i < currentLessons.size();
	 * <p>
	 * increment: 1
	 * <p>
	 * purpose: to find the lesson file that matches the gotten lesson ID. s:
	 * Reference Variable for Lesson. Used to temporarily hold a lesson from the
	 * Lesson List.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void playGame() throws Exception {
		if (currentUser == null || currentUser.equals("")) {
			new UserProfileManager(this);
		}
		if (currentUser == null || currentUser.equals(""))
			return;

		
		if (dataManager.addUser(currentUser)) {
			dataManager.saveUserProfiles();
		}
	
		currentProfile = dataManager.getUserProfile(currentUser);

		currentLessonID = currentProfile.getBookmarkLessonID();
		currentLessonCardIndex = currentProfile.getBookmarkCardIndex();
		currentLessons = dataManager.getLessons();

		if (currentLessonID == null) {
			currentLessonID = currentLessons.get(0).getLessonId();
		}

		for (int i = 0; i < currentLessons.size(); i++) {
			Lesson s = currentLessons.get(i);
			if (s.getLessonId().equalsIgnoreCase(currentLessonID)) {
				currentLessonFile = s.getCardFileNames().get(
						currentLessonCardIndex);
				currentLessonIndex = i;
				break;
			}
		}
		String lessonFilePath = preparePath(currentLessonFile);
		lessonDisplay.setPage(lessonFilePath);
		
		cl.show(BorschtbreakersFrame.getContentPane(), "2");
	}

	/**
	 * Traverses the Lesson card list.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * previousLesson: Reference Variable for Lesson. Holds the previous lesson
	 * <p>
	 * nextLesson: Reference Variable for Lesson. Holds the next lesson
	 * 
	 * @param command
	 *            Reference Variable for String. Holds the command, which
	 *            determines whether the next or previous lesson will be loaded.
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void traverseLesson(String command) throws Exception {
		if (currentProfile == null) {
			return;
		}
		if (command.equals("Previous Lesson")) {
			if (currentLessonCardIndex == 0) {
				if (currentLessonIndex == 0) {
					return;
				}
				Lesson previousLesson = currentLessons
						.get(currentLessonIndex -= 1);
				currentLessonID = previousLesson.getLessonId();
				currentLessonCardIndex = previousLesson.getCardFileNames()
						.size() - 1;
			} else {
				currentLessonCardIndex--;
			}
		} else if (command.equals("Next Lesson")) {
			if (currentLessonCardIndex == currentLessons
					.get(currentLessonIndex).getTotalCards() - 1) {
				if (!dataManager.quizResults.hasQuizBeenTaken(currentUser,
						currentLessonID)) {
					previousLessonID = currentLessonID;
					openQuiz();
				}
				if (currentLessonIndex == currentLessons.size() - 1) {
					endGame();
					return;
				}
				Lesson nextLesson = currentLessons.get(++currentLessonIndex);
				currentLessonID = nextLesson.getLessonId();
				currentLessonCardIndex = 0;
			} else {
				currentLessonCardIndex++;
			}
		}
		currentLessonFile = currentLessons.get(currentLessonIndex)
				.getCardFileNames().get(currentLessonCardIndex);
		lessonDisplay.setPage(preparePath(currentLessonFile));
	}

	/**
	 * Opens the Quiz.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * currentLesson: Reference Variable for Lesson. Holds the current lesson
	 * <p>
	 * quizXmlFile: Reference Variable for String. Holds the file path for the
	 * XML file.
	 * <p>
	 * card: Reference Variable for QuizCard. Holds the current card.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void openQuiz() throws Exception {
		Lesson currentLesson = currentLessons.get(currentLessonIndex);

		String quizXmlFile = URLDecoder.decode(rootPath + DATA_FOLDER
				+ File.separator + currentLesson.getQuizFileName(), "UTF-8");

		currentLessonQuiz = Quiz.LoadQuiz(quizXmlFile);
		currentQuestionAmount = currentLessonQuiz.getQuizCards().size();
		QuizCard card = currentLessonQuiz.getCard();
		loadQuestion(card);
		cl.show(BorschtbreakersFrame.getContentPane(), "3");
	}

	/**
	 * Submits user answer and checks if it is the correct answer to the current
	 * question. If correct it increments the correct answer counter.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * userAnswer: Reference Variable for String. Holds the user's answer.
	 * <p>
	 * response: Reference Variable for String. Holds the game's response to the
	 * users performance.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void submitAndCheckAnswer() throws Exception {
		String userAnswer = quizTextField.getText();
		String response;
		if (currentLessonQuiz.getCard().checkAnswer(userAnswer)) {
			currentCorrectAnswers++;
			response = "Correct Answer!";
		} else {
			response = "Incorrect Answer!";
		}
		progressQuizIndex = currentLessonQuiz.getQuizIndex();

		JOptionPane.showMessageDialog(BorschtbreakersFrame, response,
				"Question", JOptionPane.INFORMATION_MESSAGE);

		if (!currentLessonQuiz.isLast()) {
			nextQuestion.setEnabled(true);
		} else {
			endQuiz();

		}

		submitButton.setEnabled(false);
	}

	/**
	 * Opens Russian Keyboard Local Variable Dictionary:
	 * <p>
	 * locale: Reference Variable for Locale. Controls Language of Keyboard.
	 * dlg: Reference Variable for DialogVirtualKeyboardReal. Displays a on
	 * screen Russian keyboard.
	 */
	private void openKeyboard() {
		Locale locale = new Locale(KEYBOARD_LOCALE);
		DialogVirtualKeyboardReal dlg = new DialogVirtualKeyboardReal(
				BorschtbreakersFrame, false, quizTextField);
		dlg.setLocaleL(locale);
	}

	/**
	 * Changes current question displayed to the next one in the Question List.
	 * Local Variable Dictionary:
	 * <p>
	 * card: Reference Variable for QuizCard. Holds current Quiz question.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void nextQuestion() throws Exception {
		QuizCard card = currentLessonQuiz.getNextQuizCard();
		if (card != null)
			loadQuestion(card);
	}

	/**
	 * Changes current question displayed to the previous one in the Question
	 * List. Local Variable Dictionary:
	 * <p>
	 * card: Reference Variable for QuizCard. Holds current Quiz question.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void previousQuestion() throws Exception {
		QuizCard card = currentLessonQuiz.getPreviousQuizCard();
		if (card != null)
			loadQuestion(card);

	}

	/**
	 * Loads game with question data from QuizCard passed to it.
	 * 
	 * @param card
	 *            Reference Variable for QuizCard. Holds current Quiz question.
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void loadQuestion(QuizCard card) throws Exception {
		nextQuestion.setEnabled(false);
		previousQuestion.setEnabled(true);
		quizTextField.setText("");

		if (progressQuizIndex < currentLessonQuiz.getQuizIndex()) {
			submitButton.setEnabled(true);
		} else {
			nextQuestion.setEnabled(true);
			submitButton.setEnabled(false);
		}

		if (currentLessonQuiz.isFirst())
			previousQuestion.setEnabled(false);

		currentQuizFile = preparePath(card.getQuizCardFilename());
		quizDisplay.setPage(currentQuizFile);
	}

	/**
	 * Converts Absolute FilePaths into URLs.
	 * 
	 * @param path
	 *            Reference variable for String. Used to hold the path to be
	 *            converted.
	 * @return String Path variable correctly formated
	 */
	private String preparePath(String path) {
		return "file:///" + rootPath + DATA_FOLDER + "/" + path;
	}

	/**
	 * Ends the Quiz. Tells user score and Saves profiles. Local Variable
	 * Dictionary:
	 * <p>
	 * score: Float variable for holding score.
	 * <p>
	 * pass: Reference Variable for String. Holds Game response to scores.
	 * 
	 * @throws Exception
	 *             If any errors occur.
	 */
	private void endQuiz() throws Exception {
		float score = (float) ((double) currentCorrectAnswers / (double) currentQuestionAmount) * 100.0f;
		String pass;
		if (score >= 75.0) {
			pass = " passed!";
		} else {
			pass = " failed.";
		}

		JOptionPane.showMessageDialog(BorschtbreakersFrame,
				"Congratulations! You" + pass + " You got "
						+ currentCorrectAnswers + " questions out of "
						+ currentQuestionAmount + " right, for a score of "
						+ score + ".", "Quiz Over!",
				JOptionPane.INFORMATION_MESSAGE);
		dataManager.quizResults.addUserQuizResult(currentUser,
				previousLessonID, score);
		dataManager.quizResults.SaveQuizResult();
		dataManager.saveUserProfiles();
		progressQuizIndex = -1;
		cl.show(BorschtbreakersFrame.getContentPane(), "2");
	}

	/**
	 * Sets the current Username. Used by UserProfileManager.s
	 * 
	 * @param username
	 *            String for holding username to set currentUser to.
	 */
	protected void setUsername(String username) {
		currentUser = username;
	}

	/**
	 * Displays the Splash Screen Local Variable Dictionary:
	 * <p>
	 * screen: Reference Variable for SplashScreen. Displays a SplashScreen.
	 * timer: Reference Variable for Timer.
	 */
	private void showSplashScreen() {
		final SplashScreen screen = new SplashScreen();
		Timer timer = new Timer(SPLASHSCREEN_WAIT_TIME, new ActionListener() {
			/**
			 * Disposes of splashScreen after a certain period of time.
			 * 
			 * @param e
			 *            Reference variable for ActionEvent.
			 */
			public void actionPerformed(ActionEvent e) {
				screen.setVisible(false);
				screen.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		screen.setVisible(true);
	}

	/**
	 * Sets the displayed User on the Status Bar
	 * 
	 * @param label
	 *            JLabel to change text of
	 * @param user
	 *            Text to change it to.
	 */
	private void setStatus(JLabel label, String user) {
		if (user == null || user.equals("")) {
			label.setText("You are not logged in.");
			logOffButton.setEnabled(false);
		} else {
			label.setText("Current User: " + currentUser);
			logOffButton.setEnabled(true);
		}
	}
}
