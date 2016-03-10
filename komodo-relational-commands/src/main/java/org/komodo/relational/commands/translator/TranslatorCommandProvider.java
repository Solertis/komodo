/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.relational.commands.translator;

import java.util.HashSet;
import java.util.Set;
import org.komodo.relational.vdb.Translator;
import org.komodo.shell.api.ShellCommand;
import org.komodo.shell.api.ShellCommandProvider;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.KException;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.Repository;

/**
 * A shell command provider for Translators.
 */
public class TranslatorCommandProvider implements ShellCommandProvider {

    /**
     * Constructs a command provider for Translator shell commands.
     */
    public TranslatorCommandProvider() {
        // nothing to do
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#provideCommands()
     */
    @Override
    public Set< Class< ? extends ShellCommand > > provideCommands() {
        final Set< Class< ? extends ShellCommand > > result = new HashSet< Class< ? extends ShellCommand > >();

        result.add( SetTranslatorPropertyCommand.class );
        result.add( UnsetTranslatorPropertyCommand.class );
        result.add( TranslatorRenameCommand.class );

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#resolve(org.komodo.spi.repository.Repository.UnitOfWork,
     *      org.komodo.spi.repository.KomodoObject)
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public Translator resolve ( final Repository.UnitOfWork uow, final KomodoObject kObj ) throws KException {
        if(Translator.RESOLVER.resolvable(uow, kObj)) {
            return Translator.RESOLVER.resolve(uow, kObj);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#getStatusMessage(org.komodo.shell.api.WorkspaceStatus)
     */
    @Override
    public String getStatusMessage( final WorkspaceStatus wsStatus ) {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#initWorkspaceState(org.komodo.shell.api.WorkspaceStatus)
     */
    @Override
    public void initWorkspaceState(WorkspaceStatus wsStatus) {
        // Init any workspace state
    }

}
