/*
 * Copyright (c) 1997-2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.servlet.jsp.jstl.core;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.PageContext;

/**
 * Class supporting access to configuration settings.
 */
public class Config {

    /*
     * I18N/Formatting actions related configuration data
     */

    /**
     * Name of configuration setting for application- (as opposed to browser-) based preferred locale
     */
    public static final String FMT_LOCALE = "jakarta.servlet.jsp.jstl.fmt.locale";

    /**
     * Name of configuration setting for fallback locale
     */
    public static final String FMT_FALLBACK_LOCALE = "jakarta.servlet.jsp.jstl.fmt.fallbackLocale";

    /**
     * Name of configuration setting for i18n localization context
     */
    public static final String FMT_LOCALIZATION_CONTEXT = "jakarta.servlet.jsp.jstl.fmt.localizationContext";

    /**
     * Name of localization setting for time zone
     */
    public static final String FMT_TIME_ZONE = "jakarta.servlet.jsp.jstl.fmt.timeZone";

    /*
     * SQL actions related configuration data
     */

    /**
     * Name of configuration setting for SQL data source
     */
    public static final String SQL_DATA_SOURCE = "jakarta.servlet.jsp.jstl.sql.dataSource";

    /**
     * Name of configuration setting for maximum number of rows to be included in SQL query result
     */
    public static final String SQL_MAX_ROWS = "jakarta.servlet.jsp.jstl.sql.maxRows";

    /*
     * Private constants
     */
    private static final String PAGE_SCOPE_SUFFIX = ".page";
    private static final String REQUEST_SCOPE_SUFFIX = ".request";
    private static final String SESSION_SCOPE_SUFFIX = ".session";
    private static final String APPLICATION_SCOPE_SUFFIX = ".application";

    /**
     * Looks up a configuration variable in the given scope.
     *
     * <p>
     * The lookup of configuration variables is performed as if each scope had its own name space, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param pageContext Page context in which the configuration variable is to be looked up
     * @param name Configuration variable name
     * @param scope Scope in which the configuration variable is to be looked up
     *
     * @return The <code>java.lang.Object</code> associated with the configuration variable, or null if it is not defined.
     */
    public static Object get(PageContext pageContext, String name, int scope) {
        switch (scope) {
        case PageContext.PAGE_SCOPE:
            return pageContext.getAttribute(name + PAGE_SCOPE_SUFFIX, scope);
        case PageContext.REQUEST_SCOPE:
            return pageContext.getAttribute(name + REQUEST_SCOPE_SUFFIX, scope);
        case PageContext.SESSION_SCOPE:
            return get(pageContext.getSession(), name);
        case PageContext.APPLICATION_SCOPE:
            return pageContext.getAttribute(name + APPLICATION_SCOPE_SUFFIX, scope);
        default:
            throw new IllegalArgumentException("unknown scope");
        }
    }

    /**
     * Looks up a configuration variable in the "request" scope.
     *
     * <p>
     * The lookup of configuration variables is performed as if each scope had its own name space, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param request Request object in which the configuration variable is to be looked up
     * @param name Configuration variable name
     *
     * @return The <code>java.lang.Object</code> associated with the configuration variable, or null if it is not defined.
     */
    public static Object get(ServletRequest request, String name) {
        return request.getAttribute(name + REQUEST_SCOPE_SUFFIX);
    }

    /**
     * Looks up a configuration variable in the "session" scope.
     *
     * <p>
     * The lookup of configuration variables is performed as if each scope had its own name space, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     * </p>
     *
     * @param session Session object in which the configuration variable is to be looked up
     * @param name Configuration variable name
     *
     * @return The <code>java.lang.Object</code> associated with the configuration variable, or null if it is not defined, if
     * session is null, or if the session is invalidated.
     */
    public static Object get(HttpSession session, String name) {
        Object ret = null;
        if (session != null) {
            try {
                ret = session.getAttribute(name + SESSION_SCOPE_SUFFIX);
            } catch (IllegalStateException ex) {
            } // when session is invalidated
        }
        return ret;
    }

    /**
     * Looks up a configuration variable in the "application" scope.
     *
     * <p>
     * The lookup of configuration variables is performed as if each scope had its own name space, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param context Servlet context in which the configuration variable is to be looked up
     * @param name Configuration variable name
     *
     * @return The <code>java.lang.Object</code> associated with the configuration variable, or null if it is not defined.
     */
    public static Object get(ServletContext context, String name) {
        return context.getAttribute(name + APPLICATION_SCOPE_SUFFIX);
    }

    /**
     * Sets the value of a configuration variable in the given scope.
     *
     * <p>
     * Setting the value of a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param pageContext Page context in which the configuration variable is to be set
     * @param name Configuration variable name
     * @param value Configuration variable value
     * @param scope Scope in which the configuration variable is to be set
     */
    public static void set(PageContext pageContext, String name, Object value, int scope) {
        switch (scope) {
        case PageContext.PAGE_SCOPE:
            pageContext.setAttribute(name + PAGE_SCOPE_SUFFIX, value, scope);
            break;
        case PageContext.REQUEST_SCOPE:
            pageContext.setAttribute(name + REQUEST_SCOPE_SUFFIX, value, scope);
            break;
        case PageContext.SESSION_SCOPE:
            pageContext.setAttribute(name + SESSION_SCOPE_SUFFIX, value, scope);
            break;
        case PageContext.APPLICATION_SCOPE:
            pageContext.setAttribute(name + APPLICATION_SCOPE_SUFFIX, value, scope);
            break;
        default:
            throw new IllegalArgumentException("unknown scope");
        }
    }

    /**
     * Sets the value of a configuration variable in the "request" scope.
     *
     * <p>
     * Setting the value of a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param request Request object in which the configuration variable is to be set
     * @param name Configuration variable name
     * @param value Configuration variable value
     */
    public static void set(ServletRequest request, String name, Object value) {
        request.setAttribute(name + REQUEST_SCOPE_SUFFIX, value);
    }

    /**
     * Sets the value of a configuration variable in the "session" scope.
     *
     * <p>
     * Setting the value of a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param session Session object in which the configuration variable is to be set
     * @param name Configuration variable name
     * @param value Configuration variable value
     */
    public static void set(HttpSession session, String name, Object value) {
        session.setAttribute(name + SESSION_SCOPE_SUFFIX, value);
    }

    /**
     * Sets the value of a configuration variable in the "application" scope.
     *
     * <p>
     * Setting the value of a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not replace one stored in a different scope.
     *
     * @param context Servlet context in which the configuration variable is to be set
     * @param name Configuration variable name
     * @param value Configuration variable value
     */
    public static void set(ServletContext context, String name, Object value) {
        context.setAttribute(name + APPLICATION_SCOPE_SUFFIX, value);
    }

    /**
     * Removes a configuration variable from the given scope.
     *
     * <p>
     * Removing a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not impact one stored in a different scope.
     *
     * @param pageContext Page context from which the configuration variable is to be removed
     * @param name Configuration variable name
     * @param scope Scope from which the configuration variable is to be removed
     */
    public static void remove(PageContext pageContext, String name, int scope) {
        switch (scope) {
        case PageContext.PAGE_SCOPE:
            pageContext.removeAttribute(name + PAGE_SCOPE_SUFFIX, scope);
            break;
        case PageContext.REQUEST_SCOPE:
            pageContext.removeAttribute(name + REQUEST_SCOPE_SUFFIX, scope);
            break;
        case PageContext.SESSION_SCOPE:
            pageContext.removeAttribute(name + SESSION_SCOPE_SUFFIX, scope);
            break;
        case PageContext.APPLICATION_SCOPE:
            pageContext.removeAttribute(name + APPLICATION_SCOPE_SUFFIX, scope);
            break;
        default:
            throw new IllegalArgumentException("unknown scope");
        }
    }

    /**
     * Removes a configuration variable from the "request" scope.
     *
     * <p>
     * Removing a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not impact one stored in a different scope.
     *
     * @param request Request object from which the configuration variable is to be removed
     * @param name Configuration variable name
     */
    public static void remove(ServletRequest request, String name) {
        request.removeAttribute(name + REQUEST_SCOPE_SUFFIX);
    }

    /**
     * Removes a configuration variable from the "session" scope.
     *
     * <p>
     * Removing a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not impact one stored in a different scope.
     *
     * @param session Session object from which the configuration variable is to be removed
     * @param name Configuration variable name
     */
    public static void remove(HttpSession session, String name) {
        session.removeAttribute(name + SESSION_SCOPE_SUFFIX);
    }

    /**
     * Removes a configuration variable from the "application" scope.
     *
     * <p>
     * Removing a configuration variable is performed as if each scope had its own namespace, that is, the same
     * configuration variable name in one scope does not impact one stored in a different scope.
     *
     * @param context Servlet context from which the configuration variable is to be removed
     * @param name Configuration variable name
     */
    public static void remove(ServletContext context, String name) {
        context.removeAttribute(name + APPLICATION_SCOPE_SUFFIX);
    }

    /**
     * Finds the value associated with a specific configuration setting identified by its context initialization parameter
     * name.
     *
     * <p>
     * For each of the JSP scopes (page, request, session, application), get the value of the configuration variable
     * identified by <code>name</code> using method <code>get()</code>. Return as soon as a non-null value is found. If no value is
     * found, get the value of the context initialization parameter identified by <code>name</code>.
     *
     * @param pageContext Page context in which the configuration setting is to be searched
     * @param name Context initialization parameter name of the configuration setting
     *
     * @return The <code>java.lang.Object</code> associated with the configuration setting identified by <code>name</code>, or null
     * if it is not defined.
     */
    public static Object find(PageContext pageContext, String name) {
        Object ret = get(pageContext, name, PageContext.PAGE_SCOPE);
        if (ret == null) {
            ret = get(pageContext, name, PageContext.REQUEST_SCOPE);
            if (ret == null) {
                if (pageContext.getSession() != null) {
                    // check session only if a session is present
                    ret = get(pageContext, name, PageContext.SESSION_SCOPE);
                }
                if (ret == null) {
                    ret = get(pageContext, name, PageContext.APPLICATION_SCOPE);
                    if (ret == null) {
                        ret = pageContext.getServletContext().getInitParameter(name);
                    }
                }
            }
        }

        return ret;
    }
}
