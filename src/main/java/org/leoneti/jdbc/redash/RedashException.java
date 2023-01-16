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

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

public class RedashException extends SQLException {

    private static final long serialVersionUID = -6564276202943638764L;

    public RedashException() {
    }

    public RedashException(String reason) {
        super(reason);
    }

    public RedashException(Throwable cause) {
        super(cause);
    }

    public RedashException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public RedashException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public RedashException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public RedashException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public RedashException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }

    private String errorMessage = null;
    private int errorCode=-1;

    public RedashException(JSONObject jsnerr) {
        super();
        if( jsnerr == null ) {
            this.errorMessage = RedashResource.getString("errorMessageNotFound");
        } else if( jsnerr.has("error") ) {
            try {
                this.errorMessage = jsnerr.getString("error");
            } catch (JSONException ee) {
                this.errorMessage = jsnerr.getJSONObject("error").getString("message");
                this.errorCode = jsnerr.getJSONObject("error").getInt("code");
            }
        } else if( jsnerr.has("message") ) {
            this.errorMessage = jsnerr.getString("message");
        }
    }
    
    @Override
    public String getMessage() {
        if( this.errorMessage != null )
            return this.errorMessage;
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        if( this.errorMessage != null )
            return this.errorMessage;
        return super.getLocalizedMessage();
    }
    
    @Override
    public int getErrorCode() {
        if( this.errorCode != -1) return this.errorCode;
        return super.getErrorCode();
    }
}
