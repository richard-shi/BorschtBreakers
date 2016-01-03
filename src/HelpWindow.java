import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;

/**
 * This class displays a window with a help file in it.
 * 
 * @author Daniel Marin 
 * @version 2.0, June 10 2013
 */
public class HelpWindow extends JFrame implements ActionListener {
	/**
	 * Reference variable for JPanel. Jpanel is content pane of JFrame
	 */
	private JPanel contentPane;
	/**
	 * Reference variable for JEditorPane. Displays Help File. 
	 */
	private JEditorPane webPanel;
	/**
	 * String to hold help html page URL
	 */
	public final String INSTRUCTIONS_FILE_NAME = "instructions.htm";

	
	/**
	 * Main Constructor. Creates the window along with the help page.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * helpDataPath: Reference Variable for String. Holds fully formated help file path.
	 * <p>
	 * controlPanel: Reference Variable for JPanel. Holds the control Bar
	 * <p>
	 * exitButton: Reference Variable for JButton. Closes Help window.
	 * <p>
	 * printButton: Reference Variable for JButton. Prints out help file.
	 * @param dataPath
	 *            String to pass help page url into.
	 */
	public HelpWindow(String dataPath) {
		try {
			String helpDataPath = dataPath + "/" + INSTRUCTIONS_FILE_NAME;

			setResizable(false);
			setTitle("Instructions");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 650, 550);

			contentPane = (JPanel) getContentPane();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			webPanel = new JEditorPane("file://" + helpDataPath);
			webPanel.setEditable(false);
			contentPane.add(webPanel);

			JPanel controlPanel = new JPanel();
			getContentPane().add(controlPanel, BorderLayout.SOUTH);
			controlPanel.setLayout(new BorderLayout(0, 0));

			JButton exitButton = new JButton();
			controlPanel.add(exitButton, BorderLayout.EAST);
			exitButton.addActionListener(this);
			exitButton.setText("Close");

			JButton printButton = new JButton("Print");
			controlPanel.add(printButton, BorderLayout.WEST);
			printButton.addActionListener(this);

			setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error in HelpWindow: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Prints out instructions.
	 * @throws Exception IF any error occurs.
	 */
	public void printInstructions() throws Exception {
		webPanel.print();
	}

	/**
	 * Exits or Prints instructions window based on Button Press.
	 * @exception e Catchs any error that occurs and displays error message.
	 * @param a Reference Variable for ActionEvent. 
	 */
	public void actionPerformed(ActionEvent a) {
		try {
			if (a.getActionCommand().equals("Print")) {
				printInstructions();
			} else {
				HelpWindow.this.dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error in actionPerformed: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
