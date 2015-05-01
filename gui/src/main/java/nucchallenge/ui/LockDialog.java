package nucchallenge.ui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Lock screen Frame window.
 */
public class LockDialog extends JDialog
{
    private DialogResult result;
    private Credentials credentials;

    private final String TITLE = "NUC Health - Login";

    private final String INVALID_CREDENTIALS_ERROR = "Invalid credentials!";
    private final String INVALID_ENTRY_ERROR = "You must specify credentials!";

    private JPanel contentPane;
    private JPanel formPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    public LockDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(loginButton);

        result = DialogResult.NONE;
        credentials = null;

        setTitle(TITLE);
        setResizable(false);
        setPreferredSize(contentPane.getMinimumSize());

        errorLabel.setVisible(false);
        errorLabel.setText(INVALID_ENTRY_ERROR);

        loginButton.addActionListener(e -> onOK());

        // Call onCancel() when cross is clicked
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // Call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Create the GUI and show it.
     * <p/>
     * For thread safety, this method should be invoked from a separate
     * event-dispatching thread.
     */
    public static DialogResult showDialog(Credentials credentials)
    {
        // Create and set up the window.
        LockDialog lockDialog = new LockDialog();
        lockDialog.credentials = credentials;

        lockDialog.pack();
        // Ensure frame appears centered on screen.
        // (http://stackoverflow.com/a/2442614/1363247)
        lockDialog.setLocationRelativeTo(null);

        // Display the window.
        lockDialog.setVisible(true);

        return lockDialog.getResult();
    }

    /**
     * Get the result of this dialog.
     * @return DialogResult Result of this dialog
     */
    public DialogResult getResult()
    {
        return result;
    }

    private void onOK()
    {
        errorLabel.setVisible(false);

        if (usernameField.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
            errorLabel.setText(INVALID_ENTRY_ERROR);
        } else {
            errorLabel.setText(INVALID_CREDENTIALS_ERROR);
        }

        if (credentials != null
                && credentials.getUsername().equals(usernameField.getText())
                && credentials.getPassword().equals(String.valueOf(
                    passwordField.getPassword())))
        {
            // Credentials are valid
            result = DialogResult.OK;
            dispose();
        }

        errorLabel.setVisible(true);
    }

    private void onCancel()
    {
        result = DialogResult.CANCEL;
        dispose();
    }
}
