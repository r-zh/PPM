package cn.edu.sdu.sc.spepms.system.common.controllers;

import java.util.Date;
import java.util.List;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;
import cn.edu.sdu.sc.spepms.system.common.forms.BankForm;
import cn.edu.sdu.sc.spepms.system.common.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.common.models.Bank;
import cn.edu.sdu.sc.spepms.system.common.models.Project;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.*;;

public class ProjectController extends SecuredController{

    //add the project
    public static Result add(){
        return ok(addProject.render());
    }
    //save the project
    @Transactional
    public static Result save(){
        Form<ProjectForm> form=Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data=form.get();

        Project project=new Project();
        project.setName(data.getName());
        project.setWords(data.getWords());
        project.setType(data.getType());

        JPA.em().persist(project);
        return ok(view.render(project));
    }

    @Transactional
    public static Result view(Long projectId){
        Project project=JPA.em().find(Project.class, projectId);
        return ok(view.render(project));
    }

    //the news list
    @Transactional
    public static Result newsList(){
        List<Project> projects=JPA.em().createQuery("from Project where type='新闻公告'",Project.class).getResultList();
        return ok(newsList.render(projects));
    }

    //the project list
    @Transactional
    public static Result projectList(){
        List<Project> projects=JPA.em().createQuery("from Project where type='理财业务'",Project.class).getResultList();
        return ok(projectList.render(projects));
    }

    @Transactional
    public static Result trade(){
        Form<BankForm> form=Form.form(BankForm.class).bindFromRequest();
        BankForm data=form.get();

        Bank bank=new Bank();
        bank.setCreatedOn(new Date());
        bank.setAccount(data.getNumber());
        bank.setType(data.getType());
        bank.setNumbers(data.getMoney());
        JPA.em().persist(bank);

        User user=getCurrentUser();
        user.getBanks().add(bank);
        if(bank.getType().equals("存入")){
            user.setAccount(user.getAccount()+data.getMoney());
            user.setReceive(data.getMoney());
        }
        else{
            user.setAccount(user.getAccount()-data.getMoney());
            user.setGive(data.getMoney());
            user.setGiveDes(data.getType());
        }
        JPA.em().persist(user);

        return ok(profile.render(getCurrentUser(),getCurrentUser().getBanks()));
    }
}
