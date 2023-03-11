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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class RedashDriver implements Driver {
	
    public static final Logger log = Logger.getLogger(RedashDriver.class.getName());

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        //info.list(System.out);
        if( url.contains("@") ) {
            final String[] parts = url.substring(12).split("@");
            return new RedashConnection(parts[0], parts[1], info);
        } else {
            return new RedashConnection(url.substring(12), null, info);
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith(RedashConstants.DRIVER_PREFIX) && url.split(":").length == 4;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    	return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public int getMinorVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return RedashDriver.log;
    }

}
