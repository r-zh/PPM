package cn.edu.sdu.sc.spepms.framework.wireframe;

import play.mvc.Http.Context;

public class Wireframe {

    public static final String CTX_KEY_WIREFRAME = "_wireframe";

    private boolean showBusinessMenu;

    /**
     * Fetch wireframe object from current HTTP context
     */
    public static Wireframe current() {
        Object wireframeInContext = Context.current().args.get(CTX_KEY_WIREFRAME);
        if (wireframeInContext != null) {
            return (Wireframe) wireframeInContext;
        }

        Wireframe wireframe = new Wireframe();
        Wireframe.setCurrent(wireframe);
        return wireframe;
    }

    /**
     * Put wireframe object to current HTTP context
     * 
     * @param wireframe
     */
    static void setCurrent(Wireframe wireframe) {
        Context.current().args.put(CTX_KEY_WIREFRAME, wireframe);
    }

    public boolean isShowBusinessMenu() {
        return showBusinessMenu;
    }

    public void setShowBusinessMenu(boolean showBusinessMenu) {
        this.showBusinessMenu = showBusinessMenu;
    }

}
