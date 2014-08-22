package cn.edu.sdu.sc.spepms.system.common.forms;

import play.data.validation.Constraints.Required;

/**
 * Login form
 * 
 * @author Peter Fu
 */
public class LoginForm {

    @Required
    private String username;

    @Required
    private String password;

    private boolean remember;

    private String redirectUrl;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
