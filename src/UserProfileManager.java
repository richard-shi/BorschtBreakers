import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class displays a JFrame prompting user for username
 * 
 * @author Daniel Marin and Richard Shi
 * @version 1.3, May 31 2013
 */
public class UserProfileManager extends JDialog implements ActionListener {

 /**
  * Reference Variable for JPanel. Holds title label and fields.
  */
 private JPanel contentPane;
 /**
  * Reference Variable for JTextField. Gets Username
  */
 private JTextField usernameField;

 private MainPanel mainPanel;
 /**
  * Reference Variable for Font constant for text font
  */
 public static final Font textFont = new Font("Times New Roman", Font.BOLD,
   12);
 /**
  * Reference Variable for Font constant for title font.
  */
 public static final Font titleFont = new Font("Times New Roman", Font.BOLD,
   20);
 /**
  * int constant for number of textfield columns
  */
 public static final int fieldColumns = 20;

 /**
  * Creates the frame.
  * <p>
  * Local Variable Dictionary:
  * <p>
  * loginPanel: Reference Variable for JPanel. Holds login screen canvas.
  * <p>
  * usernameLabel: Reference Variable for JLabel. Holds username prompt
  * label.
  * <p>
  * title: Reference Variable for JLabel. Holds title.
  * <p>
  * gl_loginPanel: Reference Variable for GroupLayout. Holds Layout Manager
  * for window.
  */
 public UserProfileManager(MainPanel mainPanel) {
  this.mainPanel = mainPanel;
  setModalityType(ModalityType.APPLICATION_MODAL);

  setResizable(false);
  setAlwaysOnTop(true);
  setTitle("Register/Log In");
  setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  setBounds(100, 100, 450, 300);
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  setContentPane(contentPane);
  contentPane.setLayout(new CardLayout(0, 0));

  JPanel loginPanel = new JPanel();
  contentPane.add(loginPanel, "name_26965553862217");

  usernameField = new JTextField();
  usernameField.setColumns(fieldColumns);

  JLabel usernameLabel = new JLabel("Username:");
  usernameLabel.setFont(textFont);

  JLabel title = new JLabel("Log In/Register");
  title.setFont(titleFont);

  JButton playGameButton = new JButton("Play Game");
  playGameButton.addActionListener(this);

  JButton returnButton = new JButton("Return to Menu");
  returnButton.addActionListener(this);

  GroupLayout gl_loginPanel = new GroupLayout(loginPanel);
  gl_loginPanel
    .setHorizontalGroup(gl_loginPanel
      .createParallelGroup(Alignment.TRAILING)
      .addGroup(
        gl_loginPanel.createSequentialGroup()
          .addGap(149).addComponent(title)
          .addContainerGap(152, Short.MAX_VALUE))
      .addGroup(
        gl_loginPanel
          .createSequentialGroup()
          .addContainerGap(105, Short.MAX_VALUE)
          .addComponent(usernameLabel,
            GroupLayout.PREFERRED_SIZE, 64,
            GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(
            ComponentPlacement.UNRELATED)
          .addGroup(
            gl_loginPanel
              .createParallelGroup(
                Alignment.LEADING)
              .addComponent(
                returnButton,
                GroupLayout.PREFERRED_SIZE,
                130,
                GroupLayout.PREFERRED_SIZE)
              .addComponent(
                usernameField,
                GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE)
              .addComponent(
                playGameButton))
          .addGap(89)));
  gl_loginPanel
    .setVerticalGroup(gl_loginPanel
      .createParallelGroup(Alignment.TRAILING)
      .addGroup(
        gl_loginPanel
          .createSequentialGroup()
          .addGap(69)
          .addComponent(title)
          .addGap(18)
          .addGroup(
            gl_loginPanel
              .createParallelGroup(
                Alignment.BASELINE)
              .addComponent(
                usernameField,
                GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE)
              .addComponent(
                usernameLabel,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE))
          .addGap(18)
          .addComponent(playGameButton)
          .addPreferredGap(
            ComponentPlacement.UNRELATED)
          .addComponent(returnButton).addGap(59)));
  loginPanel.setLayout(gl_loginPanel);
  setVisible(true);
 }

 public void actionPerformed(ActionEvent a) {
  if (a.getActionCommand().equals("Play Game")) {
   String usernameFieldText = usernameField.getText();
   if (usernameFieldText.equals("") || usernameFieldText == null) {
    JOptionPane.showMessageDialog(this,
      "Please type a username in.", "Error",
      JOptionPane.ERROR_MESSAGE);
   } else {
    mainPanel.setUsername(usernameFieldText);
    this.dispose();
   }
  } else {
   this.dispose();
  }
 }
}
