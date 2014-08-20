package cn.edu.sdu.sc.spepms.system.common.controllers;

import java.util.List;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.forms.RegisterForm;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.register;
import cn.edu.sdu.sc.spepms.system.common.views.html.studentHome;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;

public class RegisterController extends Controller {

    public static Result register() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(register.render());
    }

    @Transactional
    public static Result registerSave() {
        Form<RegisterForm> form = Form.form(RegisterForm.class).bindFromRequest();
        RegisterForm data = form.get();

        User user = new User();
        user.setName(data.getName());
        user.setGender(data.getGender());
        user.setPersonalId(data.getPersonalId());
        user.setBirthday(data.getBirthday());
        user.setEmail(data.getEmail());
        user.setHometown(data.getHometown());
        user.setLab(data.getLab());
        user.setMajor(data.getMajor());
        user.setPassword(data.getPassword());
        user.setGrade(data.getGrade());
        user.setPhone(data.getPhone());
        user.setStudentId(data.getStudentId());

        JPA.em().persist(user);

        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(studentHome.render(creationProject));
    }

}
