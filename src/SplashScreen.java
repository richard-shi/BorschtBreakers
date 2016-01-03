import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.net.URL;

/**
 * This class displays a SplashScreen
 * 
 * @author Daniel Marin
 * @version 2.0, June 10 2013
 */
public class SplashScreen extends JDialog {
	/**
	 * Reference variable for String. Holds String Constrant filename for splash Picture.
	 */
	public static final String SPLASH_FILE_NAME = "companylogo.png";

	/**
	 * Main Constructor.
	 * <p>
	 * Local Variable Dictionary:
	 * <p>
	 * imageFilePath: String reference variable that holds image file path.
	 * <p>
	 * controlPanel: Reference Variable for JPanel. Holds the control Bar
	 * <p>
	 * icon: Reference variable for URL. Holds filePath in URL form.
	 * <p>
	 * image: Reference variable for ImageIcon. Holds Image.
	 * <p>
	 * label: Reference variable for JLabel. Holds Image.
	 * @exception Exception If any error occurs.
	 */
	public SplashScreen() {
		try {
			String imageFilePath = this.getClass()
					.getResource(SPLASH_FILE_NAME).toExternalForm();

			setModalityType(ModalityType.APPLICATION_MODAL);

			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("BorschtBreakers: Legend of Russia");
			setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 735, 738);

			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));

			URL icon = new URL(imageFilePath);
			ImageIcon image = new ImageIcon(icon);
			JLabel label = new JLabel("", image, JLabel.CENTER);

			contentPane.add(label, BorderLayout.SOUTH);
		} catch (Exception e) {

		}
	}
}