package nucchallenge.ui;

import nucchallenge.utils.Gender;
import nucchallenge.utils.Patient;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class PatientDialog extends JDialog
{
    private DialogResult result;
    private Patient patient;

    private DefaultListModel<String> listModel;

    private final String TITLE = "NUC Health - %s Patient";

    private final String BUTTON_CREATE_LABEL = "Create";
    private final String BUTTON_SAVE_LABEL = "Save";

    private JPanel contentPane;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel formPanel;
    private JTextField nameField;
    private JList illnessCodeList;
    private JFormattedTextField idField;
    private JTextField illnessCodeField;
    private JButton addIllnessCodeButton;
    private JSpinner ageSpinner;
    private JComboBox genderComboBox;

    public PatientDialog() {
        this(null);
    }

    public PatientDialog(Patient patient)
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);

        result = DialogResult.NONE;
        this.patient = patient;

        String mode = (patient == null) ? "Create" : "Edit";
        setTitle(String.format(TITLE, mode));
        setResizable(false);
        setPreferredSize(contentPane.getMinimumSize());

        // Setup number formatter for ID field
        NumberFormatter idFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        idField.setFormatterFactory(
                new DefaultFormatterFactory(idFormatter, idFormatter, idFormatter));

        fillPatientInfo();

        saveButton.addActionListener(e -> onOK());

        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        genderComboBox.addActionListener(e -> {
            if (patient != null) {
                switch (genderComboBox.getSelectedIndex()) {
                    case 0:
                        patient.setGender(Gender.MALE);
                        break;
                    case 1:
                        patient.setGender(Gender.FEMALE);
                        break;
                    default:
                        patient.setGender(Gender.OTHER);
                }
            }
        });

        addIllnessCodeButton.addActionListener(e -> onAdd());
    }

    /**
     * Create the GUI and show it.
     * <p/>
     * For thread safety, this method should be invoked from a separate
     * event-dispatching thread.
     */
    public static DialogResult showDialog(Patient patient)
    {
        // Create and set up the window.
        PatientDialog patientDialog = new PatientDialog(patient);

        patientDialog.pack();
        // Ensure frame appears centered on screen.
        // (http://stackoverflow.com/a/2442614/1363247)
        patientDialog.setLocationRelativeTo(null);

        // Display the window.
        patientDialog.setVisible(true);

        return patientDialog.getResult();
    }

    /**
     * Get the result of this dialog.
     * @return DialogResult Result of this dialog
     */
    public DialogResult getResult()
    {
        return result;
    }

    /**
     * Get the Patient this dialog is affecting.
     * @return Patient
     */
    public Patient getPatient() {
        return patient;
    }

    private void fillPatientInfo()
    {
        listModel = new DefaultListModel<>();

        if (patient == null) {
            patient = new Patient();

            saveButton.setText(BUTTON_CREATE_LABEL);
        } else {
            idField.setText(Integer.toString(patient.getPatientId()));
            nameField.setText(patient.getName());

            // Add illness codes to list model
            for (int i = 0; i < patient.getIllnessCodes().size(); i++) {
                listModel.add(listModel.size() - 1, patient.getIllnessCodes().get(i));
            }

            saveButton.setText(BUTTON_SAVE_LABEL);
        }

        illnessCodeList.setModel(listModel);
    }

    private void onAdd()
    {
        if (!illnessCodeField.getText().equals("")) {
            patient.getIllnessCodes().add(illnessCodeField.getText());

            patient.setAge((Integer) ageSpinner.getValue());

            listModel.add(listModel.size(), patient.getIllnessCodes().get(
                    patient.getIllnessCodes().size() - 1
            ));

            illnessCodeField.setText("");
            illnessCodeField.requestFocusInWindow();
        }
    }

    private void onOK()
    {
        patient.setPatientId(Integer.parseInt(idField.getText()));
        patient.setName(nameField.getText());

        List<String> illnessCodes = Collections.list(listModel.elements());
        patient.setIllnessCodes(illnessCodes);

        result = DialogResult.OK;
        dispose();
    }

    private void onCancel()
    {
        result = DialogResult.CANCEL;
        dispose();
    }
}
