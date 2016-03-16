/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.workspace;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.komodo.core.KomodoLexicon;
import org.komodo.relational.commands.RelationalShellCommand;
import org.komodo.relational.datasource.Datasource;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.Arguments;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.TabCompletionModifier;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.KException;
import org.komodo.spi.constants.ExportConstants;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.komodo.ui.DefaultLabelProvider;
import org.komodo.utils.StringUtils;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to export a Datasource from Workspace context.
 */
public final class ExportDatasourceCommand extends RelationalShellCommand {

    static final String NAME = "export-datasource"; //$NON-NLS-1$

    private static final String OVERWRITE_1 = "-o"; //$NON-NLS-1$
    private static final String OVERWRITE_2 = "--overwrite"; //$NON-NLS-1$
    private static final List< String > VALID_OVERWRITE_ARGS = Arrays.asList( new String[] { OVERWRITE_1, OVERWRITE_2 } );

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public ExportDatasourceCommand( final WorkspaceStatus status ) {
        super( status, NAME );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        final boolean workspaceContext = isWorkspaceContext();
        final int fileNameIndex = ( workspaceContext ? 1 : 0 );
        String datasourceName = null;
        String fileName = null;

        try {
            if ( workspaceContext ) {
                datasourceName = requiredArgument( 0, I18n.bind( WorkspaceCommandsI18n.missingDatasourceName ) );
            } else {
                datasourceName = getContext().getName( getTransaction() );
            }

            fileName = requiredArgument( fileNameIndex, I18n.bind( WorkspaceCommandsI18n.missingOutputFileName ) );

            // If there is no file extension, add .xml
            if ( fileName.indexOf( DOT ) == -1 ) {
                fileName = fileName + DOT + "xml"; //$NON-NLS-1$
            }

            final String overwriteArg = optionalArgument( ( fileNameIndex + 1 ), null );
            final boolean overwrite = !StringUtils.isBlank( overwriteArg );

            // make sure overwrite arg is valid
            if ( overwrite && !VALID_OVERWRITE_ARGS.contains( overwriteArg ) ) {
                return new CommandResultImpl( false, I18n.bind( WorkspaceCommandsI18n.overwriteArgInvalid, overwriteArg ), null );
            }

            // Determine if the Datasource exists
            if ( workspaceContext
                 && !getWorkspaceManager().hasChild( getTransaction(), datasourceName, KomodoLexicon.DataSource.NODE_TYPE ) ) {
                return new CommandResultImpl( false, I18n.bind( WorkspaceCommandsI18n.datasourceNotFound, datasourceName ), null );
            }

            final Datasource datasourceToExport = getDatasource( workspaceContext, datasourceName );
            final File file = new File( fileName );

            // If file exists, must have overwrite option
            if(file.exists() && !overwrite) {
                return new CommandResultImpl( false,
                                              I18n.bind( WorkspaceCommandsI18n.fileExistsOverwriteDisabled, fileName ),
                                              null );
            }

            if ( file.createNewFile() || ( file.exists() && overwrite ) ) {
                final UnitOfWork uow = getTransaction();
                Properties properties = new Properties();
                properties.put( ExportConstants.USE_TABS_PROP_KEY, true );
                final String sourceXml = datasourceToExport.export( uow, properties );

                // Write the file
                try{
                    Files.write(Paths.get(file.getPath()), sourceXml.getBytes());
                    return new CommandResultImpl( I18n.bind( WorkspaceCommandsI18n.datasourceExported,
                                                             datasourceToExport.getName( uow ),
                                                             fileName,
                                                             overwrite ) );
                } catch ( final Exception e ) {
                    return new CommandResultImpl( false, I18n.bind( WorkspaceCommandsI18n.errorWritingFile, fileName ), e );
                }
            }

            return new CommandResultImpl( false, I18n.bind( WorkspaceCommandsI18n.outputFileError, fileName ), null );
        } catch ( final Exception e ) {
            return new CommandResultImpl( e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return ( isWorkspaceContext() ? 3 : 2 );
    }

    private Datasource getDatasource( final boolean workspaceContext,
                                      final String datasourceName ) throws KException {
        assert !StringUtils.isBlank( datasourceName );
        KomodoObject kobject = null;

        if ( workspaceContext ) {
            kobject = getWorkspaceManager().getChild( getTransaction(), datasourceName, KomodoLexicon.DataSource.NODE_TYPE );
        } else {
            kobject = getContext();
        }

        assert ( kobject != null );
        return Datasource.RESOLVER.resolve( getTransaction(), kobject );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public boolean isValidForCurrentContext() {
        return ( isDatasourceContext() || isWorkspaceContext() );
    }

    private boolean isDatasourceContext() {
        try {
            return Datasource.RESOLVER.resolvable( getTransaction(), getContext() );
        } catch ( final Exception e ) {
            return false;
        }
    }

    private boolean isWorkspaceContext() {
        final String path = getContext().getAbsolutePath();
        return ( DefaultLabelProvider.WORKSPACE_PATH.equals( path ) || DefaultLabelProvider.WORKSPACE_SLASH_PATH.equals( path ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpDescription(int)
     */
    @Override
    protected void printHelpDescription( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.exportDatasourceHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.exportDatasourceExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.exportDatasourceUsage ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#tabCompletion(java.lang.String, java.util.List)
     */
    @Override
    public TabCompletionModifier tabCompletion( final String lastArgument,
                                                final List< CharSequence > candidates ) throws Exception {
        final Arguments args = getArguments();

        if ( isWorkspaceContext() ) {
            // arg 0 = vdb name, arg 1 = output file name, arg 2 = overwrite
            final KomodoObject[] datasources = getWorkspaceManager().findDatasources( getTransaction() );

            if ( args.isEmpty() && ( datasources.length != 0 ) ) {
                for ( final KomodoObject datasource : datasources ) {
                    final String name = datasource.getName( getTransaction() );

                    if ( ( lastArgument == null ) || name.startsWith( lastArgument ) ) {
                        candidates.add( name );
                    }
                }
            } else if ( args.size() == 2 ) {
                candidates.add( OVERWRITE_2 );
            }
        } else if ( args.size() == 1 ) { // Datasource context
            // arg 0 = output file name (no completion), arg 1 = overwrite
            candidates.add( OVERWRITE_2 );
        }

        return TabCompletionModifier.AUTO;
    }

}
