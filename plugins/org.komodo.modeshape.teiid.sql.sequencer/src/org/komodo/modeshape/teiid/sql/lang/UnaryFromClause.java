/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.komodo.modeshape.teiid.sql.lang;

import java.util.Collection;
import org.komodo.modeshape.teiid.cnd.TeiidSqlLexicon;
import org.komodo.modeshape.teiid.parser.LanguageVisitor;
import org.komodo.modeshape.teiid.parser.TeiidParser;
import org.komodo.modeshape.teiid.sql.symbol.GroupSymbol;
import org.komodo.spi.query.sql.lang.IUnaryFromClause;

public class UnaryFromClause extends FromClause implements IUnaryFromClause<GroupSymbol, LanguageVisitor> {

    public UnaryFromClause(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public GroupSymbol getGroup() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.UnaryFromClause.GROUP_REF_NAME, GroupSymbol.class);
    }

    @Override
    public void setGroup(GroupSymbol group) {
        setChild(TeiidSqlLexicon.UnaryFromClause.GROUP_REF_NAME, group);
    }

    public Command getExpandedCommand() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.UnaryFromClause.EXPANDED_COMMAND_REF_NAME, Command.class);
    }

    public void setExpandedCommand(Command command) {
        setChild(TeiidSqlLexicon.UnaryFromClause.EXPANDED_COMMAND_REF_NAME, command);
    }

    @Override
    public void collectGroups(Collection<GroupSymbol> groups) {
        groups.add(getGroup());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.getExpandedCommand() == null) ? 0 : this.getExpandedCommand().hashCode());
        result = prime * result + ((this.getGroup() == null) ? 0 : this.getGroup().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnaryFromClause other = (UnaryFromClause)obj;
        if (this.getExpandedCommand() == null) {
            if (other.getExpandedCommand() != null)
                return false;
        } else if (!this.getExpandedCommand().equals(other.getExpandedCommand()))
            return false;
        if (this.getGroup() == null) {
            if (other.getGroup() != null)
                return false;
        } else if (!this.getGroup().equals(other.getGroup()))
            return false;
        return true;
    }

    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public UnaryFromClause clone() {
        UnaryFromClause clone = new UnaryFromClause(this.getTeiidParser(), this.getId());

        if (getGroup() != null)
            clone.setGroup(getGroup().clone());
        if (getExpandedCommand() != null)
            clone.setExpandedCommand(getExpandedCommand().clone());
        clone.setOptional(isOptional());
        clone.setMakeInd(isMakeInd());
        clone.setNoUnnest(isNoUnnest());
        clone.setMakeDep(isMakeDep());
        clone.setMakeNotDep(isMakeNotDep());
        clone.setPreserve(isPreserve());

        return clone;
    }

}
