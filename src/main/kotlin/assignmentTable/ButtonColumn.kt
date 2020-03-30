/*-
 * Plugin Drop Project
 * 
 * Copyright (C) 2019 Yash Jahit
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package assignmentTable

import com.tfc.ulht.assignmentComponents.Assignment
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

class ButtonColumn(assignmentList: List<Assignment>) : JFrame() {

    private var data = Array(assignmentList.size) { Array(3) { "" } }
    private val headers = arrayOf("Assignment ID", "", "")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Available Assignments")

    init {
        assignmentList.forEach { (a) -> println(a) }
        var iterator = 0
        for (assignment in assignmentList) {
            data[iterator][0] = assignment.assignmentId
            iterator++
        }

        val table = JTable(data, headers)
        table.rowHeight = 30

        /**
         * Open more info
         * */
        table.columnModel.getColumn(1).cellRenderer =
            ButtonRenderer("More info")
        table.columnModel.getColumn(1).cellEditor = ButtonEditor(JTextField(), "More info")

        /**
         * Select assignment for upload
         */
        table.columnModel.getColumn(2).cellRenderer =
            ButtonRenderer("Select assignment")
        table.columnModel.getColumn(2).cellEditor = ButtonEditor(JTextField(), "Select assignment")

        val scrollPane = JScrollPane(table)

        frame.maximumSize = Dimension(250, 250)
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}