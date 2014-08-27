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

import java.util.List;
import org.komodo.modeshape.teiid.cnd.TeiidSqlLexicon;
import org.komodo.modeshape.teiid.parser.LanguageVisitor;
import org.komodo.modeshape.teiid.parser.TeiidParser;
import org.komodo.modeshape.teiid.sql.symbol.Expression;
import org.komodo.spi.query.sql.lang.IQuery;

public class Query extends QueryCommand implements IQuery<Select, From, Into, Criteria, GroupBy, OrderBy, Query, Expression, LanguageVisitor> {

    public Query(TeiidParser p, int id) {
        super(p, id);
        setType(TYPE_QUERY);
    }

    /**
     * @return row constructor flag
     */
    public boolean isRowConstructor() {
        Object property = getProperty(TeiidSqlLexicon.Query.ROW_CONSTRUCTOR_PROP_NAME);
        return property == null ? false : Boolean.parseBoolean(property.toString());
    }

    /**
     * @param rowConstructor
     */
    public void setRowConstructor(boolean rowConstructor) {
        setProperty(TeiidSqlLexicon.Query.ROW_CONSTRUCTOR_PROP_NAME, rowConstructor);
    }

    @Override
    public Query getProjectedQuery() {
        return this;
    }

    @Override
    public List<Expression> getProjectedSymbols() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Select getSelect() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.SELECT_REF_NAME, Select.class);
    }

    @Override
    public void setSelect(Select select) {
        setChild(TeiidSqlLexicon.Query.SELECT_REF_NAME, select);
    }

    @Override
    public From getFrom() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.FROM_REF_NAME, From.class);
    }

    @Override
    public void setFrom(From from) {
        setChild(TeiidSqlLexicon.Query.FROM_REF_NAME, from);
    }

    @Override
    public Into getInto() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.INTO_REF_NAME, Into.class);
    }

    @Override
    public void setInto(Into into) {
        setChild(TeiidSqlLexicon.Query.INTO_REF_NAME, into);
    }

    @Override
    public Criteria getCriteria() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.CRITERIA_REF_NAME, Criteria.class);
    }

    @Override
    public void setCriteria(Criteria where) {
        setChild(TeiidSqlLexicon.Query.CRITERIA_REF_NAME, where);
    }

    @Override
    public Criteria getHaving() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.HAVING_REF_NAME, Criteria.class);
    }

    @Override
    public void setHaving(Criteria having) {
        setChild(TeiidSqlLexicon.Query.HAVING_REF_NAME, having);
    }

    @Override
    public GroupBy getGroupBy() {
        return getChildforIdentifierAndRefType(TeiidSqlLexicon.Query.GROUP_BY_REF_NAME, GroupBy.class);
    }

    @Override
    public void setGroupBy(GroupBy groupBy) {
        setChild(TeiidSqlLexicon.Query.GROUP_BY_REF_NAME, groupBy);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.getCriteria() == null) ? 0 : this.getCriteria().hashCode());
        result = prime * result + ((this.getFrom() == null) ? 0 : this.getFrom().hashCode());
        result = prime * result + ((this.getGroupBy() == null) ? 0 : this.getGroupBy().hashCode());
        result = prime * result + ((this.getHaving() == null) ? 0 : this.getHaving().hashCode());
        result = prime * result + ((this.getInto() == null) ? 0 : this.getInto().hashCode());
        result = prime * result + ((this.getSelect() == null) ? 0 : this.getSelect().hashCode());
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
        Query other = (Query)obj;
        if (this.getCriteria() == null) {
            if (other.getCriteria() != null)
                return false;
        } else if (!this.getCriteria().equals(other.getCriteria()))
            return false;
        if (this.getFrom() == null) {
            if (other.getFrom() != null)
                return false;
        } else if (!this.getFrom().equals(other.getFrom()))
            return false;
        if (this.getGroupBy() == null) {
            if (other.getGroupBy() != null)
                return false;
        } else if (!this.getGroupBy().equals(other.getGroupBy()))
            return false;
        if (this.getHaving() == null) {
            if (other.getHaving() != null)
                return false;
        } else if (!this.getHaving().equals(other.getHaving()))
            return false;
        if (this.getInto() == null) {
            if (other.getInto() != null)
                return false;
        } else if (!this.getInto().equals(other.getInto()))
            return false;
        if (this.getSelect() == null) {
            if (other.getSelect() != null)
                return false;
        } else if (!this.getSelect().equals(other.getSelect()))
            return false;
        return true;
    }

    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Query clone() {
        Query clone = new Query(this.getTeiidParser(), this.getId());

        if (getCriteria() != null)
            clone.setCriteria(getCriteria().clone());
        if (getSelect() != null)
            clone.setSelect(getSelect().clone());
        if (getFrom() != null)
            clone.setFrom(getFrom().clone());
        if (getGroupBy() != null)
            clone.setGroupBy(getGroupBy().clone());
        if (getHaving() != null)
            clone.setHaving(getHaving().clone());
        if (getInto() != null)
            clone.setInto(getInto().clone());
        if (getOrderBy() != null)
            clone.setOrderBy(getOrderBy().clone());
        if (getLimit() != null)
            clone.setLimit(getLimit().clone());
        if (getWith() != null)
            clone.setWith(cloneList(getWith()));
        if (getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if (getOption() != null)
            clone.setOption(getOption().clone());

        clone.setRowConstructor(isRowConstructor());

        copyMetadataState(clone);

        return clone;
    }

}
