package com.kutash.taxibuber.controller;

/**
 * The type Router.
 */
public class Router {

    /**
     * The enum Route type.
     */
    public enum RouteType{
        /**
         * Forward route type.
         */
        FORWARD, /**
         * Redirect route type.
         */
        REDIRECT
    }

    private String page;
    private RouteType route = RouteType.FORWARD;

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    RouteType getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(RouteType route) {
        if (route==null){
            this.route=RouteType.FORWARD;
        }
        this.route = route;
    }
}
