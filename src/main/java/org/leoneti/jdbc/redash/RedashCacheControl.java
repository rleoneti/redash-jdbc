/*****************************************************************************************
* Copyright (C) 2023-2023  Ricardo Leoneti                           Date: 2025-10-23
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
*
* Contributors:
*     Ricardo Leoneti <ricardo.leoneti@gmail.com>    - cache implementation
* 
*****************************************************************************************/
package org.leoneti.jdbc.redash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.leoneti.Utils;

public class RedashCacheControl {

    class CachedResult {
        public CachedResult(int result_id) {
            this.result_id = result_id;
        }
        public long executionTime = System.currentTimeMillis();
        public int result_id;
    }
    
    private Map<String,CachedResult> cache = new HashMap<String,CachedResult>(50);
    private RedashConnection con;
    private int cache_ttl;
    
    public RedashCacheControl(RedashConnection con, int cache_ttl) {
        this.con = con;
        this.cache_ttl = cache_ttl;
    }
    
    public boolean checkValidCache(CachedResult cache) {
        if( cache == null ) return false;
        if( System.currentTimeMillis() - cache.executionTime > cache_ttl*1000 )
            return false;
        
        return true;
    }
    
    public int checkAndGetCachedResult(String query) {
        try {
            final String queryId = String.format( "%s:%s", this.con.getCatalog() ,Utils.md5(query) );
            final CachedResult cache = this.cache.get(queryId);
            if( checkValidCache(cache) ) {
                return cache.result_id;
            }
        } catch (Exception e) {
            RedashDriver.log.log(Level.SEVERE, e.getMessage(), e);
        }
        return -1;
    }
    
    public void add(String query, int result_id) {
        try {
            final String queryId = String.format( "%s:%s", this.con.getCatalog() ,Utils.md5(query) );
            this.cache.put(queryId, new CachedResult(result_id) );
        } catch (Exception e) {
            RedashDriver.log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        cleanOlderCache();
    }

    public void cleanOlderCache() {
        final Iterator<Map.Entry<String,CachedResult>> iterator = this.cache.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String,CachedResult> entry = iterator.next();
            if( !checkValidCache( entry.getValue() ) ) {
                iterator.remove();
            }
        }
    }

}
