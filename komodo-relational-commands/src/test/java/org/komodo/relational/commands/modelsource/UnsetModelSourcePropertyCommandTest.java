/*
 * Copyright 2014 JBoss Inc
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
package org.komodo.relational.commands.modelsource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.model.Model;
import org.komodo.relational.vdb.ModelSource;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;

/**
 * Test Class to test {@link UnsetModelSourcePropertyCommand}.
 */
@SuppressWarnings( {"javadoc", "nls"} )
public final class UnsetModelSourcePropertyCommandTest extends AbstractCommandTest {

    @Test
    public void testUnsetProperty1() throws Exception {
        final String[] commands = {
            "create-vdb myVdb vdbPath",
            "cd myVdb",
            "add-model myModel",
            "cd myModel",
            "add-source mySource",
            "cd mySource",
            "set-property sourceJndiName myJndi",
            "unset-property sourceJndiName" };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo, getTransaction());
        Vdb[] vdbs = wkspMgr.findVdbs(getTransaction());

        assertEquals(1, vdbs.length);

        Model[] models = vdbs[0].getModels(getTransaction());
        assertEquals(1, models.length);
        assertEquals("myModel", models[0].getName(getTransaction())); //$NON-NLS-1$

        ModelSource[] sources = models[0].getSources(getTransaction());
        assertEquals(1, sources.length);
        assertEquals("mySource", sources[0].getName(getTransaction())); //$NON-NLS-1$

        assertEquals(null, sources[0].getJndiName(getTransaction()));
    }

    @Test
    public void testTabCompleter()throws Exception{
    	ArrayList<CharSequence> candidates=new ArrayList<>();
    	setup("commandFiles","addSources.cmd");
    	final String[] commands = { "cd mySource1" };
    	final CommandResult result = execute( commands );
        assertCommandResultOk(result);

    	candidates.add(ModelSourceShellCommand.TRANSLATOR_NAME);
    	assertTabCompletion("unset-property sourceT", candidates);

    	assertTabCompletion("unset-property sourcet", candidates);
    }

}
