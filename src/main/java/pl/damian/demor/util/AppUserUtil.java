package pl.damian.demor.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AppUserUtil {

    public static String getLoggedUserUsername(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
