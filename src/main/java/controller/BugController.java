package controller;

import domain.repsonse.BugListResponse;
import domain.repsonse.BugResponse;
import entity.Bug;
import service.BugService;

import io.javalin.http.Handler;

public class BugController {

    private static BugService bugService=new BugService();

    public static Handler bugList=ctx->{
         ctx.json(bugService.getAll());
    };

    public static Handler getBugById= ctx-> {
        Integer id = Integer.parseInt(ctx.pathParam("bug_id"));
        BugResponse bug=null;
        if (id!=null) bug=bugService.getById(id);
        if(bug==null) ctx.html("Not Found");
        else ctx.json(bug);
    };

    public static Handler deleteBugById= ctx -> {
        Integer id = Integer.parseInt(ctx.pathParam("bug_id"));
        BugListResponse deletedBug=null;
        if(id!=null) deletedBug=bugService.deleteById(id);
        if(deletedBug!=null) ctx.json(deletedBug);
        else ctx.html("not found");
    };

    public static Handler addBug=ctx ->{
        Bug bug = ctx.bodyAsClass(Bug.class);
        if(bug.getDescription()==null)
            ctx.html("Invalid Input");
        else ctx.json(bugService.insert(bug));
    };

    public static Handler updateBug=ctx->{
        Bug bug = ctx.bodyAsClass(Bug.class);
        System.out.println(bug);
        BugListResponse updated;
        updated=bugService.update(bug);
        if(updated!=null) ctx.json(updated);
        else ctx.html("not found");
    };


    public static Handler bugsByAssignee=ctx->{
        Integer assigned_To = Integer.parseInt(ctx.pathParam("assignedTo"));
        BugListResponse bug=null;
        if (assigned_To!=null) bug=bugService.getByAssignee(assigned_To);
        if(bug.getBugs().size()==0) ctx.html("Not Found");
        else ctx.json(bug);

    };

}
