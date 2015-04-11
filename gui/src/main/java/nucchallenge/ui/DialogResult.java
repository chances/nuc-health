package nucchallenge.ui;

/**
 * Specifies identifiers to indicate the return value of a dialog.
 */
public enum DialogResult {
    /**
     * The dialog return value is Abort (usually sent from a button labeled Abort).
     */
    ABORT,
    /**
     * The dialog return value is Cancel (usually sent from a button labeled Cancel).
     */
    CANCEL,
    /**
     * The dialog return value is Ignore (usually sent from a button labeled Ignore).
     */
    IGNORE,
    /**
     * The dialog return value is No (usually sent from a button labeled No).
     */
    NO,
    /**
     * Nothing is returned from the dialog. This means that the modal dialog continues running.
     */
    NONE,
    /**
     * The dialog return value is OK (usually sent from a button labeled OK).
     */
    OK,
    /**
     * The dialog return value is Retry (usually sent from a button labeled Retry).
     */
    RETRY,
    /**
     * The dialog box return value is Yes (usually sent from a button labeled Yes).
     */
    YES
}
