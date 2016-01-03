import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

/**
 * This Class displays a High Scores window.
 * @author Richard shi & Daniel Marin
 * @version 2.0, June 10 2013
 */
public class HighScores extends JFrame implements ActionListener {

	private JPanel contentPane;

	private JEditorPane webPanel;

	private DataManager dataManager;

	/**
	 * Creates the window along with the help page.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * highScores: Reference Variable for String. Holds html to load.
	 * <p>
	 * controlPanel: Reference Variable for JPanel. Holds the control Bar.
	 * <p>
	 * utilityPanel: Reference Variable for JPanel. Holds the utility Bar.
	 * <p>
	 * removeAllButton: Reference Variable for JButton. Removes all high scores from high score file.
	 * <p>
	 * exitButton: Reference Variable for JButton. Closes High Scores window.
	 * <p>
	 * printButton: Reference Variable for JButton. Prints out High Scores.
	 * 
	 * @param url
	 *            String to pass help page url into.
	 */
	public HighScores(DataManager dm) {
		try {
			dataManager = dm;
			String highScores = dataManager.quizResults.getQuizResultsHtml();
			setResizable(false);
			setTitle("High Scores");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 650, 550);

			contentPane = (JPanel) getContentPane();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			webPanel = new JEditorPane();
			webPanel.setContentType("text/html");
			webPanel.setText(highScores);
			webPanel.setEditable(false);
			contentPane.add(webPanel);

			JPanel controlPanel = new JPanel();
			getContentPane().add(controlPanel, BorderLayout.SOUTH);
			controlPanel.setLayout(new BorderLayout(0, 0));

			JPanel utilityPanel = new JPanel();
			controlPanel.add(utilityPanel, BorderLayout.WEST);
			utilityPanel.setLayout(new BorderLayout(0, 0));

			JButton printButton = new JButton("Print");
			utilityPanel.add(printButton, BorderLayout.WEST);

			JButton removeAllButton = new JButton("Remove All");
			removeAllButton.addActionListener(this);
			utilityPanel.add(removeAllButton, BorderLayout.EAST);
			printButton.addActionListener(this);

			JButton exitButton = new JButton();
			controlPanel.add(exitButton, BorderLayout.EAST);
			exitButton.addActionListener(this);
			exitButton.setText("Close");

			setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Prints out high Scores.
	 * @throws Exception IF any Errors occur
	 */
	public void print() throws Exception {
		webPanel.print();
	}

	/**
	 * Removes all scores from Score file.
	 * <p>
	 * Local Variable dictionary:
	 * <p>
	 * confirm: int for holding user choice for deleting all scores.
	 * <p>
	 * highScores: String for holding displayed html.
	 */
	public void removeAll() {
		int confirm = JOptionPane
				.showConfirmDialog(
						this,
						"Are you sure you want to delete all High Scores, ruining the save files of everyone who plays this work of art?",
						"Remove All High Scores", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			dataManager.quizResults.clearAll();
			dataManager.quizResults.SaveQuizResult();
			String highScores = dataManager.quizResults.getQuizResultsHtml();
			webPanel.setText(highScores);
		} else {
			return;
		}
	}

	/**
	 * Exits or Prints instructions window based on Button Press.
	 * @exception e Catchs any error that occurs and displays error message.
	 * @param a Reference Variable for ActionEvent. 
	 */
	public void actionPerformed(ActionEvent a) {
		try {
			if (a.getActionCommand().equals("Print")) {
				print();
			} else if (a.getActionCommand().equals("Remove All")) {
				removeAll();
			} else {
				HighScores.this.dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}