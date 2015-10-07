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
package org.komodo.shell;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.komodo.shell.commands.ShowChildrenCommand;

/**
 * Test Class to test ShowStatusCommand
 *
 */
@SuppressWarnings({"javadoc", "nls"})
public class ShowChildrenCommandTest extends AbstractCommandTest {

    private static final String SHOW_CHILDREN1 = "showChildren1.txt";

	/**
	 * Test for StatusCommand
	 */
	public ShowChildrenCommandTest( ) {
		super();
	}

    @Test
    public void testShowChildren1() throws Exception {
        setup(SHOW_CHILDREN1, ShowChildrenCommand.class);

        execute();

        String expectedOutput = "There are no children for Workspace \"/workspace\".\n"; //$NON-NLS-1$

        String writerOutput = getCommandOutput();
        assertEquals(expectedOutput,writerOutput);
        //assertEquals("/workspace", wsStatus.getCurrentContext().getFullName()); //$NON-NLS-1$
    }

}
