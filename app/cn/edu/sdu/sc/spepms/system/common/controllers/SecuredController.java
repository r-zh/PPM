package cn.edu.sdu.sc.spepms.system.common.controllers;

import play.mvc.Controller;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class SecuredController extends Controller {

}
