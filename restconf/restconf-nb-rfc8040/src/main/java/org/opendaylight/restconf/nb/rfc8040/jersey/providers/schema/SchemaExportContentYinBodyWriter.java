/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.restconf.nb.rfc8040.jersey.providers.schema;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.stream.XMLStreamException;
import org.opendaylight.restconf.common.schema.SchemaExportContext;
import org.opendaylight.restconf.nb.rfc8040.Rfc8040;
import org.opendaylight.restconf.nb.rfc8040.utils.RestconfConstants;
import org.opendaylight.yangtools.yang.model.export.YinExportUtils;

@Provider
@Produces({ Rfc8040.MediaTypes.YIN + RestconfConstants.XML })
public class SchemaExportContentYinBodyWriter implements MessageBodyWriter<SchemaExportContext> {

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        return type.equals(SchemaExportContext.class);
    }

    @Override
    public long getSize(final SchemaExportContext context, final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(final SchemaExportContext context, final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws
        WebApplicationException {
        try {
            YinExportUtils.writeModuleAsYinText(context.getModule(), entityStream);
        } catch (final XMLStreamException e) {
            throw new IllegalStateException(e);
        }
    }
}
