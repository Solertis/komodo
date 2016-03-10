/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.server;

import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.CompletionConstants;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to connect to the default server
 */
public final class ServerConnectCommand extends ServerShellCommand {

    static final String NAME = "server-connect"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public ServerConnectCommand( final WorkspaceStatus status ) {
        super( NAME, status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        CommandResult result = null;

        try {
            final String serverName = getWorkspaceServerName();
            print( CompletionConstants.MESSAGE_INDENT, I18n.bind( ServerCommandsI18n.attemptingToConnect, serverName ) );

            try {
                boolean connected = connectWorkspaceServer();
                String connectStatus = connected ? I18n.bind( ServerCommandsI18n.connected )
                                                 : I18n.bind( ServerCommandsI18n.notConnected );

                // Updates available commands upon connecting
                getWorkspaceStatus().updateAvailableCommands();

                result = new CommandResultImpl( I18n.bind( ServerCommandsI18n.teiidStatus,
                                                           serverName,
                                                           connectStatus ) );
            } catch ( Exception ex ) {
                result = new CommandResultImpl( false, I18n.bind( ServerCommandsI18n.connectionError ), ex );
            }
        } catch ( final Exception e ) {
            result = new CommandResultImpl( false, I18n.bind( ServerCommandsI18n.connectionError ), e );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public final boolean isValidForCurrentContext() {
        return !hasConnectedWorkspaceServer();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpDescription(int)
     */
    @Override
    protected void printHelpDescription( final int indent ) {
        print( indent, I18n.bind( ServerCommandsI18n.serverConnectHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( ServerCommandsI18n.serverConnectExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( ServerCommandsI18n.serverConnectUsage ) );
    }

}
