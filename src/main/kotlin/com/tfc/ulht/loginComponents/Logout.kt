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

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.JOptionPane

class Logout: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (Authentication.alreadyLoggedIn) {
            val userMessage = Messages.showYesNoDialog(
                "Are you sure you want to logout?",
                "Logout", "Yes", "No", Messages.getQuestionIcon()
            )

            if (userMessage == 0) {
                Authentication.alreadyLoggedIn = false
                Messages.showMessageDialog("You have logged out", "Logout Success", Messages.getInformationIcon())
            }
        } else {
            Messages.showMessageDialog("You need to login first", "Logout", Messages.getInformationIcon())
        }
    }
}