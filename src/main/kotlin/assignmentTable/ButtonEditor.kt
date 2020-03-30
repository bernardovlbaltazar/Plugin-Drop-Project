package assignmentTable

import com.tfc.ulht.assignmentComponents.ListAssignment
import java.awt.Component
import java.net.URI
import javax.swing.*


internal class ButtonEditor(txt: JTextField?, private val label: String) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var assignmentId: String = ""

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
            if (column == 1) { // MORE INFO
                // TODO Receive form and show in IntelliJ
                val openWeb = java.awt.Desktop.getDesktop()
                openWeb.browse(URI("http://localhost:8080/upload/$assignmentId"))

            } else if (column == 2) { // SELECT ASSIGNMENT
                ListAssignment.selectedAssignmentId = assignmentId
                JOptionPane.showMessageDialog(null, "The assignment with ID: $assignmentId was selected")
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
