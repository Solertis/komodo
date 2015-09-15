/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.model;

import static org.komodo.relational.commands.model.ModelCommandMessages.AddSourceCommand.SOURCE_ADDED;
import static org.komodo.relational.commands.model.ModelCommandMessages.General.MISSING_SOURCE_NAME;
import static org.komodo.shell.CompletionConstants.MESSAGE_INDENT;
import org.komodo.relational.model.Model;
import org.komodo.shell.api.WorkspaceStatus;

/**
 * A shell command to add a source to a Model.
 */
public final class AddSourceCommand extends ModelShellCommand {

    static final String NAME = "add-source"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public AddSourceCommand( final WorkspaceStatus status ) {
        super( NAME, true, status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected boolean doExecute() throws Exception {
        final String sourceName = requiredArgument( 0, getMessage(MISSING_SOURCE_NAME) );

        final Model model = getModel();
        model.addSource( getTransaction(), sourceName );

        // Print success message
        print(MESSAGE_INDENT, getMessage(SOURCE_ADDED,sourceName));
        
        return true;
    }

}
