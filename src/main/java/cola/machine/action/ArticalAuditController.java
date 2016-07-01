package cola.machine.action;


import cola.machine.bean.Artical;
import cola.machine.service.ArticalService;
import cola.machine.util.AuthUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.CheckBox;
import cola.machine.util.rules.Digits;
import cola.machine.util.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by colamachine on 16-6-30.
 */
@Controller
@RequestMapping("/artical")
public class ArticalAuditController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ArticalController.class);
    /** 权限service **/
    @Autowired
    private ArticalService articalService;

    @RequestMapping(value = "/listAudit.htm", method = RequestMethod.GET)
    public String listAuditing() {

        return "/static/html/ArticalListAudit.html";
    }
    @RequestMapping(value = "/preview.htm")
    public String preview(){

        return "/static/html/ArticalList.html";

    }
    @RequestMapping(value = "/audit.htm")
    public String auditview(){

        return "/static/html/ArticalAudit.html";

    }

    /**
     * 审核
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/auditPost.json")
    @ResponseBody
    public Object auditPost(HttpServletRequest request) throws Exception{
        AuthUtil.hasPermissionE(request, "Res_ArticalAuth");
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getResult(303,"参数不存在");
        }

        String statusStr =request.getParameter("status");
        //String remark =request.getParameter("remark");
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", idStr, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("status", statusStr, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        //vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
        Artical artical =new Artical();
        artical=articalService.selectByPrimaryKey(Long.valueOf(idStr));
        if(artical==null){
            return this.getResult(302,"数据不存在");
        }
        if(artical.getStatus()!=1){
            return this.getResult(301,"只有状态为新建的才能被审核");
        }

        //artical.setId(Long.valueOf(idStr));
        // artical.setRemark(remark);
        artical.setStatus(Integer.valueOf(statusStr));
        artical.setUpdatetime(new Timestamp(new Date().getTime()));
        return articalService.save(artical);
    }
}
