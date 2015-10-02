/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.view;

import java.util.Arrays;
import java.util.List;
import org.komodo.relational.Messages;
import org.komodo.relational.commands.RelationalShellCommand;
import org.komodo.relational.model.View;
import org.komodo.relational.model.internal.ViewImpl;
import org.komodo.shell.api.WorkspaceStatus;

/**
 * A base class for @{link {@link View View}-related shell commands.
 */
abstract class ViewShellCommand extends RelationalShellCommand {

    protected static final String DESCRIPTION = "description"; //$NON-NLS-1$
    protected static final String CARDINALITY = "cardinality"; //$NON-NLS-1$
    protected static final String MATERIALIZED = "materialized"; //$NON-NLS-1$
    protected static final String MATERIALIZED_TABLE = "materialized_table"; //$NON-NLS-1$
    protected static final String NAME_IN_SOURCE = "nameinsource"; //$NON-NLS-1$
    protected static final String UPDATABLE = "updatable"; //$NON-NLS-1$
    protected static final String UUID = "uuid"; //$NON-NLS-1$
    protected static final String ON_COMMIT_VALUE = "onCommitValue"; //$NON-NLS-1$
    protected static final String QUERY_EXPRESSION = "queryExpression"; //$NON-NLS-1$
    protected static final String SCHEMA_ELEMENT_TYPE = "schemaElementType"; //$NON-NLS-1$
    protected static final String TEMPORARY_TABLE_TYPE = "temporary"; //$NON-NLS-1$

    protected static final List< String > ALL_PROPS = Arrays.asList( new String[] { DESCRIPTION, CARDINALITY, MATERIALIZED,
                                                                                    MATERIALIZED_TABLE, NAME_IN_SOURCE,
                                                                                    ON_COMMIT_VALUE, QUERY_EXPRESSION,
                                                                                    SCHEMA_ELEMENT_TYPE, TEMPORARY_TABLE_TYPE,
                                                                                    UPDATABLE, UUID, } );

    protected ViewShellCommand( final String name,
                                final WorkspaceStatus status ) {
        super( status, name );
    }

    protected View getView() throws Exception {
        return new ViewImpl( getTransaction(), getRepository(), getPath() );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public final boolean isValidForCurrentContext() {
        try {
            return ViewImpl.RESOLVER.resolvable(getTransaction(), getContext());
        } catch (Exception ex) {
            // exception returns false
        }
        return false;
    }

    @Override
    protected String getMessage(Enum< ? > key, Object... parameters) {
        return Messages.getString(ViewCommandMessages.RESOURCE_BUNDLE,key.toString(),parameters);
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printHelp(int indent)
     */
    @Override
    public void printHelp( final int indent ) {
        print( indent, Messages.getString( ViewCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".help" ) ); //$NON-NLS-1$
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printUsage(int indent)
     */
    @Override
    public void printUsage( final int indent ) {
        print( indent, Messages.getString( ViewCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".usage" ) ); //$NON-NLS-1$
    }

}
