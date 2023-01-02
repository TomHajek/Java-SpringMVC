package com.springmvc.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Whenever user creates a group, it will be tied to the user, that nobody else can edit or delete his group
 * So we are going to use security context holder, that is going to pull user information from the cookie
 */
public class SecurityUtil {

    public static String getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //returns current username
            return authentication.getName();
        }

        return null;
    }

}
