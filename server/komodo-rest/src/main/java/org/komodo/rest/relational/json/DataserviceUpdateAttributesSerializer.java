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
package org.komodo.rest.relational.json;

import static org.komodo.rest.Messages.Error.UNEXPECTED_JSON_TOKEN;

import java.io.IOException;

import org.komodo.rest.Messages;
import org.komodo.rest.relational.request.KomodoDataserviceUpdateAttributes;
import org.komodo.rest.relational.request.KomodoTeiidAttributes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A GSON serializer/deserializer for {@status KomodoDataserviceUpdateAttribute}s.
 */
public final class DataserviceUpdateAttributesSerializer extends TypeAdapter< KomodoDataserviceUpdateAttributes > {

    /**
     * {@inheritDoc}
     *
     * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
     */
    @Override
    public KomodoDataserviceUpdateAttributes read( final JsonReader in ) throws IOException {
        final KomodoDataserviceUpdateAttributes updateAttrs = new KomodoDataserviceUpdateAttributes();
        in.beginObject();

        while ( in.hasNext() ) {
            final String name = in.nextName();

            switch ( name ) {
                case KomodoDataserviceUpdateAttributes.DATASERVICE_NAME_LABEL:
                	updateAttrs.setDataserviceName(in.nextString());
                    break;
                case KomodoDataserviceUpdateAttributes.DATASERVICE_VIEW_TABLE_PATH_LABEL:
                	updateAttrs.setViewTablePath(in.nextString());
                    break;
                case KomodoDataserviceUpdateAttributes.DATASERVICE_MODEL_SOURCE_PATH_LABEL:
                	updateAttrs.setModelSourcePath(in.nextString());
                    break;
                default:
                    throw new IOException( Messages.getString( UNEXPECTED_JSON_TOKEN, name ) );
            }
        }

        in.endObject();

        return updateAttrs;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter, java.lang.Object)
     */
    @Override
    public void write( final JsonWriter out,
                       final KomodoDataserviceUpdateAttributes value ) throws IOException {

        out.beginObject();

        out.name(KomodoDataserviceUpdateAttributes.DATASERVICE_NAME_LABEL);
        out.value(value.getDataserviceName());

        out.name(KomodoDataserviceUpdateAttributes.DATASERVICE_VIEW_TABLE_PATH_LABEL);
        out.value(value.getViewTablePath());

        out.name(KomodoDataserviceUpdateAttributes.DATASERVICE_MODEL_SOURCE_PATH_LABEL);
        out.value(value.getModelSourcePath());

        out.endObject();
    }

}
