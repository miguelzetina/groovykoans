/*
 * Copyright (c) 2012-2014 nadavc <https://twitter.com/nadavc>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the WTFPL, Version 2, as published by Sam Hocevar.
 * See the COPYING file for more details.
 */

package org.groovykoans.koan09

class NukeInterceptor implements Interceptor {

    boolean isAuthorized = true

    @Override
    Object beforeInvoke(Object obj, String methodName, Object[] args) {
        // ------------ START EDITING HERE ----------------------

        // args of functions called
        if (methodName == 'nukeCity' && args[0] != 'admin')
            isAuthorized = false
        null

        // ------------ STOP EDITING HERE  ----------------------
    }

    @Override
    Object afterInvoke(Object obj, String methodName, Object[] args, Object result) {
        // ------------ START EDITING HERE ----------------------

        isAuthorized = true
        result

        // ------------ STOP EDITING HERE  ----------------------
    }

    @Override
    boolean doInvoke() {
        // ------------ START EDITING HERE ----------------------

        isAuthorized

        // ------------ STOP EDITING HERE  ----------------------
    }
}

