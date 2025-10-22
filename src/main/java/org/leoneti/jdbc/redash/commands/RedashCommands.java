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
package org.leoneti.jdbc.redash.commands;

public enum RedashCommands {

    HELP,
    USERS, USER,
    MYQUERIES, MY_QUERIES, QUERIES, ALL_QUERIES, 
    //ADMIN_QUERIES,
    SHOW_QUERY,
    DATA_SOURCES,
    PAGINATOR
    ;

    public String regex() {
        if( this == SHOW_QUERY )
            return String.format( "(?i)%s%s", this.name().replaceAll("_", "[\\\\s_]*") , "\\s+(\\d+)" );
        if( this == PAGINATOR )
            return String.format( "(?ims)%s%s", this.name().replaceAll("_", "[\\\\s_]*") , "\\s+(\\w+)\\s+(.*)" );
        return String.format( "(?i)%s", this.name().replaceAll("_", "[\\\\s_]*") );
    }

    public String strCmd() {
        if( this == SHOW_QUERY )
            return String.format( "%s%s", this.name().replaceAll("_", " ") , " <id>" );
        if( this == PAGINATOR )
            return String.format( "%s%s", this.name().replaceAll("_", " ") , " <column> <sql>" );
        return String.format( this.name().replaceAll("_", " ") );
    }
}
