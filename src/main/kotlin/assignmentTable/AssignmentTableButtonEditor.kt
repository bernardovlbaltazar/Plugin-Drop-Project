package assignmentTable

import com.tfc.ulht.assignmentComponents.ListAssignment
import com.tfc.ulht.submissionComponents.ListSubmissions
import java.awt.Component
import java.awt.Desktop
import javax.swing.*
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener


internal class AssignmentTableButtonEditor(
    txt: JTextField?,
    private val label: String,
    private val frame: JFrame
) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var assignmentId: String = ""
    private var assignmentDetails: String = ""

    init {
        button.isOpaque = true
        button.addActionListener { fireEditingStopped() }
    }

    override fun getTableCellEditorComponent(
        table: JTable, obj: Any,
        selected: Boolean, row: Int, col: Int
    ): Component {
        if (selected) {
            button.foreground = table.selectionForeground
            button.background = table.selectionBackground
        } else {
            button.foreground = table.foreground
            button.background = table.background
        }

        this.assignmentId = table.model.getValueAt(row, 0).toString()
        this.assignmentDetails = table.model.getValueAt(row, 3).toString()
        this.row = row
        this.column = col

        /*
        println("Row: $row : Column: $col") // GET LOCATION OF
        println(table.model.getValueAt(row, 0)) // GET VALUE FROM ROW X AND COLUMN 0 (ID)
        */
        button.text = label
        clicked = true
        return button
    }

    override fun getCellEditorValue(): Any {
        if (clicked) {
            if (column == 3) {
                ListSubmissions(assignmentId)
            } else if (column == 4) { // MORE INFO
                val ed1 = JEditorPane("text/html", assignmentDetails) // PREPARE HTML VIEWER
                ed1.isEditable = false
                val tempFrame = JFrame()
                tempFrame.add(ed1)
                tempFrame.isVisible = true
                tempFrame.setSize(600, 600)

                // ABILITY TO CLICK ON HYPERLINK
                ed1.addHyperlinkListener { e ->
                    if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                        // Do something with e.getURL() here
                        if(Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().browse(e.url.toURI())
                        }
                    }
                }


            } else if (column == 5) { // SELECT ASSIGNMENT
                ListAssignment.selectedAssignmentId = assignmentId
                JOptionPane.showMessageDialog(null, "The assignment with ID: $assignmentId was selected")
                //frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))

            }
        }
        clicked = false
        return label
    }

    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
