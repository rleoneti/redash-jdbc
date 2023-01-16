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
package org.leoneti;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.leoneti.jdbc.redash.RedashConnection;
import org.leoneti.jdbc.redash.RedashConstants;
import org.leoneti.jdbc.redash.RedashDriver;
import org.leoneti.jdbc.redash.RedashHttp;

public class Utils {

    protected static final Logger log = Logger.getLogger(RedashDriver.class.getName());
    
    private static String formatParameters(Object... param) {
        String[] strParam = new String[param.length];
        for (int i = 0; i < param.length; i++) {
            if (param[i] == null)
                strParam[i] = "null";
            else if (param[i] instanceof Number || param[i] instanceof Boolean || param[i] instanceof BigDecimal)
                strParam[i] = String.valueOf(param[i]);
            else if (param[i] instanceof Class<?>)
                strParam[i] = ((Class<?>) param[i]).getName();
            else
                strParam[i] = "\"" + param[i].toString() + "\"";
        }
        return String.join(",", strParam);
    }

    public static void logMethodWithReturn(String method, Object ret, Object... param) {
        log.info(String.format("%s(%s) : %s", method, formatParameters(param) , ret));
    }
    
    public static void logMethod(String method, Object... param) {
        log.info(String.format("%s(%s)", method, formatParameters(param)));
    }

    public static void main(String[] args) {
        System.out.println("\n  The information about");
        System.out.println("OS Name:\t\t" + System.getProperty("os.name"));
        System.out.println("OS Version:\t\t" + System.getProperty("os.version"));
        System.out.println("OS Architecture:\t" + System.getProperty("os.arch"));
        System.out.println();
        System.out.println("JAVA Name:\t\t" + System.getProperty("java.vm.name"));
        System.out.println("JAVA Version:\t\t" + System.getProperty("java.version"));
        System.out.println("SUN Architecture:\t" + System.getProperty("sun.arch.data.model"));
        System.out.println();
        System.out.println("Driver:\t\t\t" + RedashConnection.class.getPackage().getImplementationTitle() );
        System.out.println("Driver Version:\t\t" + RedashConnection.class.getPackage().getImplementationVersion() );
        System.out.println("Driver Useragent:\t" + RedashHttp.DEFAULT_USERAGENT );
        System.out.println("Driver Connect String:\t" + RedashConstants.DRIVER_PREFIX + "{host}[@{database}]" );
        System.out.println("\n");
    }
}
