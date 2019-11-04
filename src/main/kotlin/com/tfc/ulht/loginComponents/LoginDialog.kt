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

package com.tfc.ulht.loginComponents

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class LoginDialog {

    val usernameField = JTextField()
    val usernameLabel = JLabel("Username:   ")
    val passwordField = JPasswordField()
    val passwordLabel = JLabel("Password:   ")

    fun assembleDialog(panel: JPanel) {
        val gbPanel = GridBagLayout()
        val gbc = GridBagConstraints()

        panel.layout = gbPanel

        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 5; gbc.gridheight = 2
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0; gbc.weighty = 1.0
        gbc.anchor = GridBagConstraints.NORTH
        gbc.insets = Insets(0, 10, 0, 0)
        gbPanel.setConstraints(usernameLabel, gbc)
        panel.add(usernameLabel)

        gbc.gridx = 6; gbc.gridy = 4; gbc.gridwidth = 12; gbc.gridheight = 2
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0; gbc.weighty = 0.0
        gbc.anchor = GridBagConstraints.NORTH
        gbPanel.setConstraints(usernameField, gbc)
        panel.add(usernameField)

        gbc.gridx = 1; gbc.gridy = 8; gbc.gridwidth = 5; gbc.gridheight = 2
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0; gbc.weighty = 1.0
        gbc.anchor = GridBagConstraints.NORTH
        gbc.insets = Insets(0, 10, 0, 0)
        gbPanel.setConstraints(passwordLabel, gbc)
        panel.add(passwordLabel)

        gbc.gridx = 6; gbc.gridy = 8; gbc.gridwidth = 12; gbc.gridheight = 2
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0; gbc.weighty = 0.0
        gbc.anchor = GridBagConstraints.NORTH
        gbPanel.setConstraints(passwordField, gbc)
        panel.add(passwordField)


        val opt = arrayOf("Log In", "Cancel")
        val option = JOptionPane.showOptionDialog(
            null, panel, "Log In",
            JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, opt, opt[0]
        )

        if (option == 0) {
            val response = Authentication().checkCredentials(
                usernameField.getText().trim(),
                String(passwordField.password)
            )

            if (response) {
                JOptionPane.showMessageDialog(null, "Login Success")
            } else {
                JOptionPane.showMessageDialog(
                    null, "Login Wrong!", "Error!",
                    JOptionPane.ERROR_MESSAGE
                )
                assembleDialog(JPanel())
            }

        } else {
            println("Cancel!")
        }
    }
}