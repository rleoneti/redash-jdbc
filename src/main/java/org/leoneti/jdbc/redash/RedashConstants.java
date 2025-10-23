/*****************************************************************************************
* Copyright (C) 2023-2023  Ricardo Leoneti                           Date: 2023-01-15
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
*
* Contributors:
*     Ricardo Leoneti <ricardo.leoneti@gmail.com>    - initial API and implementation
* 
*****************************************************************************************/
package org.leoneti.jdbc.redash;

public @interface RedashConstants {

    public static final String DRIVER_NAME = "Redash JDBC driver";
    
    public static final String DRIVER_PREFIX = "jdbc:redash:";
    
    public static final String DRIVER_PROPERTY_QUERY_NAME = "queryName";

    public static final String DRIVER_PROPERTY_SSL = "ssl";

    public static final String DRIVER_PROPERTY_USER_AGENT = "userAgent";

    public static final String DRIVER_PROPERTY_PAGE_SIZE = "pageSize";

    public static final String DRIVER_PROPERTY_TRACE = "trace";

    public static final String DRIVER_PROPERTY_RESULTSET_TRACE = "resultSet.trace";

    public static final String DEFAULT_SCHEMA = "DEFAULT";
    
    public static final String DRIVER_PROPERTY_RESULTSET_CACHE_TTL = "resultSet.cache.ttl";
    
    public static final String DRIVER_PROPERTY_RESULTSET_FETCH_SIZE = "resultSet.fetch.size";
    
    /*****************
     * HTTP Constants
     *****************/
    public static final String HTTP_PARAM_COUNT = "count";
    
    public static final String HTTP_PARAM_PAGE = "page";
    
    public static final String HTTP_PARAM_PAGE_SIZE = "page_size";
    
}
