package cn.edu.sdu.sc.spepms.system.common.utils;

import play.mvc.Http;

public class ContextUtil {

    public static String getCurrentUsername() {
        return Http.Context.current().session().get("username");
    }

    public static Long getCurrentUserId() {
        return Long.valueOf(Http.Context.current().session().get("userId"));
    }

}
