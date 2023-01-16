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

public abstract class TraceLog {

    private boolean trace = false;
    private Class<?> caller;

    public TraceLog() {
    }

    public TraceLog(boolean trace, Class<?> clazz) {
        this.caller = clazz;
        this.trace = trace;
    }

    public boolean isTraced() {
        return trace;
    }

    protected void logMethod(String method, Object... param) {
        if (this.trace) {
            Utils.logMethod(String.format("%s.%s", caller.getName(), method), param);
        }
    }

    protected void logMethodWithReturn(String method, Object ret, Object... param) {
        if (this.trace) {
            Utils.logMethodWithReturn(String.format("%s.%s", caller.getName(), method), ret, param);
        }
    }

}
