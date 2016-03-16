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
package org.komodo.relational.commands.workspace;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;

/**
 * Test Class to test {@link UploadVdbCommand}.
 */
@SuppressWarnings( { "javadoc", "nls" } )
public final class UploadVdbCommandTest extends AbstractCommandTest {

    private static final String UPLOAD_VDB = UploadVdbCommandTest.class.getClassLoader()
    																						.getResource("AzureService-vdb.xml").getFile();

    @Test
    public void shouldUploadVdb() throws Exception {
        final String[] commands = { "upload-vdb myVdb " + UPLOAD_VDB };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo);
        Vdb[] vdbs = wkspMgr.findVdbs(getTransaction());

        assertEquals(1, vdbs.length);
        assertEquals("myVdb", vdbs[0].getName(getTransaction()));
    }

    @Test( expected = AssertionError.class )
    public void shouldNotUploadVdbIfExists() throws Exception {
        final String[] commands = { "create-vdb myVdb vdbPath ",
                                    "upload-vdb myVdb " + UPLOAD_VDB };
        execute( commands );
    }
    
    @Test
    public void shouldUploadVdbIfExistsWithOverwrite() throws Exception {
        final String[] commands = { "create-vdb myVdb vdbPath ",
                                    "upload-vdb myVdb " + UPLOAD_VDB + " -o" };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo);
        Vdb[] vdbs = wkspMgr.findVdbs(getTransaction());

        assertEquals(1, vdbs.length);
        assertEquals("myVdb", vdbs[0].getName(getTransaction()));
    }
    
}
