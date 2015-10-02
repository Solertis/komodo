/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.teiid;

import java.util.Arrays;
import java.util.List;
import org.komodo.relational.Messages;
import org.komodo.relational.commands.RelationalShellCommand;
import org.komodo.relational.teiid.Teiid;
import org.komodo.relational.teiid.internal.TeiidImpl;
import org.komodo.shell.api.WorkspaceStatus;

/**
 * A base class for @{link {@link Teiid Teiid}-related shell commands.
 */
abstract class TeiidShellCommand extends RelationalShellCommand {

    protected static final String ADMIN_PORT = "adminPort"; //$NON-NLS-1$
    protected static final String ADMIN_PASSWORD = "adminPswd"; //$NON-NLS-1$
    protected static final String ADMIN_SECURE = "adminSecure"; //$NON-NLS-1$
    protected static final String ADMIN_USER = "adminUser"; //$NON-NLS-1$
    protected static final String JDBC_PORT = "jdbcPort"; //$NON-NLS-1$
    protected static final String JDBC_PASSWORD = "jdbcPswd"; //$NON-NLS-1$
    protected static final String JDBC_SECURE = "jdbcSecure"; //$NON-NLS-1$
    protected static final String JDBC_USER = "jdbcUser"; //$NON-NLS-1$

    protected static final List< String > ALL_PROPS = Arrays.asList( new String[] { ADMIN_PORT, ADMIN_PASSWORD, ADMIN_SECURE,
                                                                                    ADMIN_USER, JDBC_PORT, JDBC_PASSWORD,
                                                                                    JDBC_SECURE, JDBC_USER } );

    protected TeiidShellCommand( final String name,
                                 final WorkspaceStatus status ) {
        super( status, name );
    }

    protected Teiid getTeiid() throws Exception {
        return new TeiidImpl( getTransaction(), getRepository(), getPath() );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public final boolean isValidForCurrentContext() {
        try {
            return TeiidImpl.RESOLVER.resolvable(getTransaction(), getContext());
        } catch (Exception ex) {
            // exception returns false
        }
        return false;
    }

    @Override
    protected String getMessage(Enum< ? > key, Object... parameters) {
        return Messages.getString(TeiidCommandMessages.RESOURCE_BUNDLE,key.toString(),parameters);
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printHelp(int indent)
     */
    @Override
    public void printHelp( final int indent ) {
        print( indent, Messages.getString( TeiidCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".help" ) ); //$NON-NLS-1$
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printUsage(int indent)
     */
    @Override
    public void printUsage( final int indent ) {
        print( indent, Messages.getString( TeiidCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".usage" ) ); //$NON-NLS-1$
    }

}
