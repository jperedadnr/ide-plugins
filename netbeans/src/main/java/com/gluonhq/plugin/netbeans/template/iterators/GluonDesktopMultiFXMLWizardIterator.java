/*
 * Copyright (c) 2018, Gluon Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.plugin.netbeans.template.iterators;

import com.gluonhq.plugin.netbeans.template.OptInHelper;
import com.gluonhq.plugin.netbeans.template.visuals.GluonUserOptInPanel;
import com.gluonhq.plugin.netbeans.template.visuals.GluonDesktopWizardPanel;
import com.gluonhq.plugin.netbeans.template.visuals.GluonViewWizardPanel;
import com.gluonhq.plugin.templates.GluonProject;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;

// TODO define position attribute
@TemplateRegistration(folder = "Project/Gluon", displayName = "#gluonDesktopMultiFXML_displayName", description = "gluonDesktopMultiFXMLDescription.html", iconBase = "com/gluonhq/plugin/templates/icons/GluonDesktop_16x16.png")
@Messages("gluonDesktopMultiFXML_displayName=Gluon Desktop - Multi View Project (FXML)")
public class GluonDesktopMultiFXMLWizardIterator extends GluonBaseWizardIterator {

    private GluonDesktopMultiFXMLWizardIterator() {
        super(GluonProject.DESKTOP_MULTIVIEWFXML.getType());
    }

    public static GluonDesktopMultiFXMLWizardIterator createIterator() {
        return new GluonDesktopMultiFXMLWizardIterator();
    }

    @Override
    protected WizardDescriptor.Panel[] createPanels() {
        if (!OptInHelper.alreadyOptedIn()) {
            return new WizardDescriptor.Panel[]{new GluonUserOptInPanel(), 
                new GluonDesktopWizardPanel(), new GluonViewWizardPanel(false)};
        } else {
            restoreOptIn();
        }
        return new WizardDescriptor.Panel[]{new GluonDesktopWizardPanel(), new GluonViewWizardPanel(false)};
    }

    @Override
    protected String[] createSteps() {
        if (!OptInHelper.alreadyOptedIn()) {	
            return new String[]{
                NbBundle.getMessage(GluonUserOptInPanel.class, "LBL_UserOptIn"),
                NbBundle.getMessage(GluonUserOptInPanel.class, "LBL_CreateProjectStep"),
                NbBundle.getMessage(GluonUserOptInPanel.class, "LBL_CreateViewsStep")
            };
        } else {
            return new String[]{
                NbBundle.getMessage(GluonUserOptInPanel.class, "LBL_CreateProjectStep"),
                NbBundle.getMessage(GluonUserOptInPanel.class, "LBL_CreateViewsStep")
            };
        }
    }

}
