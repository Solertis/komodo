/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.model.table;

import static org.komodo.relational.commands.model.table.TableCommandMessages.DeleteIndexCommand.INDEX_DELETED;
import static org.komodo.relational.commands.model.table.TableCommandMessages.General.MISSING_INDEX_NAME;
import static org.komodo.shell.CompletionConstants.MESSAGE_INDENT;
import java.util.ArrayList;
import java.util.List;
import org.komodo.relational.model.Index;
import org.komodo.relational.model.Table;
import org.komodo.shell.api.Arguments;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.repository.Repository.UnitOfWork;

/**
 * A shell command to delete an Index from a Table.
 */
public final class DeleteIndexCommand extends TableShellCommand {

    static final String NAME = "delete-index"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public DeleteIndexCommand( final WorkspaceStatus status ) {
        super( NAME, true, status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected boolean doExecute() throws Exception {
        final String columnName = requiredArgument( 0, getMessage(MISSING_INDEX_NAME) );

        final Table table = getTable();
        table.removeIndex( getTransaction(), columnName );

        // Print success message
        print(MESSAGE_INDENT, getMessage(INDEX_DELETED,columnName));
        
        return true;
    }
    
    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#tabCompletion(java.lang.String, java.util.List)
     */
    @Override
    public int tabCompletion( final String lastArgument,
                              final List< CharSequence > candidates ) throws Exception {
        final Arguments args = getArguments();

        final UnitOfWork uow = getTransaction();
        final Table table = getTable();
        final Index[] indexes = table.getIndexes( uow );
        List<String> existingIndexNames = new ArrayList<String>(indexes.length);
        for(Index index : indexes) {
            existingIndexNames.add(index.getName(uow));
        }
        
        if ( args.isEmpty() ) {
            if ( lastArgument == null ) {
                candidates.addAll( existingIndexNames );
            } else {
                for ( final String item : existingIndexNames ) {
                    if ( item.toUpperCase().startsWith( lastArgument.toUpperCase() ) ) {
                        candidates.add( item );
                    }
                }
            }

            return 0;
        }

        // no tab completion
        return -1;
    }
    
}
