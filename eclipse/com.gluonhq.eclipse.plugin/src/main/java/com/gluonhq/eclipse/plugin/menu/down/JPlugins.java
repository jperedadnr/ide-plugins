/*
 * Copyright (c) 2017, Gluon Software
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
package com.gluonhq.eclipse.plugin.menu.down;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.gluonhq.plugin.down.PluginsBean;
import com.gluonhq.plugin.down.PluginsSWT;

public class JPlugins {

    public JPlugins(IFile buildFile) {
        Path buildPath = Paths.get(buildFile.getLocation().makeAbsolute().toOSString());
    		
        List<String> lines = null;
        try {
            lines = Files.readAllLines(buildPath);
        } catch (IOException ex) {
            MessageDialog.openError(new Shell(), "Error", "Error reading build.gradle: " + ex);
        }  

        if (lines != null) {
	        
            PluginsSWT pluginsSWT = new PluginsSWT(null, lines);
            pluginsSWT.open();
	        
            final PluginsBean pluginsBean = pluginsSWT.getPluginsBean();
            if (pluginsBean.getPlugins() != null) {
                try {
                    final List<String> editedBuild = pluginsBean.savePlugins();
                    if (editedBuild != null && !editedBuild.isEmpty()) {
                        Files.write(buildPath, editedBuild, StandardCharsets.UTF_8);
                    } 
                    buildFile.refreshLocal(IResource.DEPTH_ZERO, null);
                } catch (IOException | CoreException ex) {
                    MessageDialog.openError(new Shell(), "Error", "Error writing build.gradle: " + ex);
                }
            }
        }
    }

}
