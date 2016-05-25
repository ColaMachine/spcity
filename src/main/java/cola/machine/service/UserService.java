package cola.machine.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import cola.machine.bean.*;
import cola.machine.common.msgbox.ErrorMsg;
import cola.machine.dao.*;
import cola.machine.util.*;
import cola.machine.util.log.LogUtil;
import cola.machine.util.log.ServiceCode;
import cola.machine.util.log.ServiceMsg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.action.ResultDTO;
import cola.machine.common.msgbox.MsgReturn;
import cola.machine.constants.SysConfig;
import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

/**
 * @author colamachine
 *
 */
@Service("userService")
public class UserService extends SysUserService{
	ServiceCode serviceCode= ServiceCode.USER_SERVICE;
    private static final Logger logger = LoggerFactory
            .getLogger(UserService.class);
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ActiveMapper activeMapper;
	@Autowired
	private PwdrstMapper pwdrstMapper;
	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private  SysUserRoleMapper sysUserRoleMapper;
	public SysUser getUserByUserName(String loginname) {
		SysUser user = userMapper.selectUserByUsername(loginname);
		return user;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

/*	public SysUser saveUser(SysUser user) {
		if (user != null) {
			if (user.getId()==null || user.getId()==0) {
				//
			}

		}
		return user;
	}*/

	public SysUser getUserByEmail(String email) {
		SysUser user = userMapper.selectUserByEmail(email);
		return user;
	}
	public SysUser getUserByTelno(String email) {
		SysUser user = userMapper.selectUserByTelno(email);
		return user;
	}


	public int countAll() {
		return userMapper.countAll();
	}




	/**
	 * 登录验证
	 */
	public ResultDTO loginValid(String email, String UnencryptedPwd) {
		// / this.userMapper.getUsersByParam(map)
	String pwd = MD5Utils.MD5Encode(UnencryptedPwd);
		if (StringUtil.isBlank(email) || StringUtil.isBlank(UnencryptedPwd)) {
			return ResultUtil.getWrongResultFromCfg("err.account.empty");
		}
		
			HashMap<String, String> params = new HashMap<String, String>();
			if(StringUtil.isEmail(email)){
	            //是手机号码
			    params.put("email", email);
	        }else if(StringUtil.isPhone(email)){
	            params.put("telno", email);
	        }else{
	            return ResultUtil.getResult(ResultUtil.fail,"既不是手机号也不是邮箱");
	        }
			
			params.put("password", pwd);
			List list = sysUserMapper.listByParams(params);
			if (list != null && list.size() > 0) {
				SysUser  user = (SysUser) list.get(0);
//				returnMap.putAll(userMap);
				
				/* SysUser user =new SysUser();
				  user.setEmail(MapUtils.getString(userMap, "email"));
				  user.setTelno(MapUtils.getString(userMap, "telno"));
				  user.setUsername(MapUtils.getString(userMap, "username"));
				  user.setId(MapUtils.getLong(userMap, "userid"));
				  user.setStatus(MapUtils.getIntValue(userMap, "active"));*/
				  
				ResultDTO result = ResultUtil.getSuccResult();
				result.setData(user);
				return result;
			} else {
				return ResultUtil
						.getWrongResultFromCfg("err.accountorpwd.wrong");
			}
	}

	/**
	 * 注册
	 */
	public ResultDTO saveRegisterUser(SysUser user) {
		user.setStatus(0);//什么都没初始化过 激活过
		// / this.userMapper.getUsersByParam(map)
		// 校验数据

		//ValidateUtil<User> valid = new ValidateUtil<User>();
		/*
		 * ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 * Validator validator = factory.getValidator(); //
		 * validator.validateValue(User.class,"email",user.getEmail(), //
		 * javax.validation.groups.Default.class);
		 * Set<ConstraintViolation<User>> constraintViolations = validator
		 * .validate(user);
		 */

		/*ResultDTO result = valid.valid(user);
		if (result.getR() != 1) {
			return result;
		}*/
		/*
		 * if (!constraintViolations.isEmpty()) { HashMap errorMap = new
		 * HashMap(); for (ConstraintViolation<User> constraintViolation :
		 * constraintViolations) {
		 * errorMap.put(constraintViolation.getPropertyPath(),
		 * constraintViolation.getMessage()); } result = FAIL; msg = "注册失败";
		 * errors=errorMap;
		 * 
		 * }else{
		 */

		// 检测是否有冲突的用户
		// TODO 改成int 数
		//检查是否有相同账号用户
		//TODO 改成count 就可以了
		SysUser sameEmailUser =new SysUser();
		if(StringUtil.isNotEmpty(user.getEmail())){
			 sameEmailUser = this.getUserByEmail(user.getEmail());

		}else if(StringUtil.isNotEmpty(user.getTelno())){
			sameEmailUser = this.getUserByTelno(user.getTelno());
		}else{
			return ResultUtil.getResult(301,"手机号或邮箱地址必填");
		}

		if (sameEmailUser != null) {
			return ResultUtil.getResult(303,"手机号或邮箱地址已被注册");
		} else {
			user.setEmail(user.getEmail());
			user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
			this.sysUserMapper.insert(user);

			if(StringUtil.isNotEmpty(user.getEmail())) {
				// 插入激活数据
				Active active = new Active();
				active.setUserid(user.getId());
				active.setActiveid(UUIDUtil.getUUID());
				this.activeMapper.insertActive(active);


				//TODO assign guest role
				SysUserRole sysUserRole=new SysUserRole();
				sysUserRole.setUid(user.getId());
				HashMap params =new HashMap();
				params.put("code","role_guest");
				List<SysRole> sysUserRoles=  roleMapper.listByParams(params);
				if(sysUserRoles==null || sysUserRoles.size()==0){
					return ResultUtil.getResult("");
				}
				sysUserRole.setRoleid(sysUserRoles.get(0).getId());
				sysUserRoleMapper.insert(sysUserRole);
				// 发送激活邮件
				try {
					EmailUtil.send(user.getEmail(), "你的邮件验证码:" + active.getActiveid() + "");
				}catch (Exception e){
					LogUtil.system(serviceCode,218,user.getEmail()+active.getActiveid(),e.getMessage()+" 发送邮件失败","");
					return ResultUtil.getResult(serviceCode, ErrorMsg.THIRD_PART_ERROR, 219, ServiceMsg.SEND_FAIL);
				}
			}
			return ResultUtil.getSuccResult();
			// }
		}
	}

/*	String MSG = "msg";
	String ERRORS = "errrors";
	String RESULT = "result";*/

	/* (non-Javadoc)
	 * @see cola.machine.service.UserService#saveSenPwdrstEmail(java.lang.String)
	 * @author colamachine
	 */
	public ResultDTO saveSendPwdrstEmail(String email) {

		// 格式判断

		if (! StringUtil.isEmail(email)) {
			return ResultUtil.getResult(301,"邮箱格式不正确");
		}
		// 数据判断
		SysUser user = userMapper.selectUserByEmail(email);
		if (user == null ||user.getId()==null||user.getId()==0 ) {
			return ResultUtil.getWrongResultFromCfg("mail.not.register");
		}
		// 判断是否有未使用的邮件重置数据
		// 根据used=0 和 userid 来判断
		List list = pwdrstMapper.selectUnusedPwdrstByUserId(user.getId());
		Pwdrst pwdrst = new Pwdrst();
		if (list != null && list.size() > 0) {
			pwdrst = (Pwdrst) list.get(0);
			// 防止重复发送激活邮件
		} else {
			// List list=
			// 保存数据
			pwdrst = new Pwdrst();
			pwdrst.setUserid(user.getId());
			pwdrst.setUsed(false);
			pwdrst.setCreatetime(new Timestamp((new Date()).getTime()));
			pwdrst.setIdpwdrst(UUIDUtil.getUUID());
			pwdrstMapper.insertPwdrst(pwdrst);
			// 发送邮件
		}


		// 这个类主要来发送邮件

		// sms.sendTextMail(mailInfo);//发送文体格式
		try {
			EmailUtil.send(email,pwdrst.getIdpwdrst());
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResultUtil.getResult(301,"发送邮件失败");
		}

		// 发送邮件
		// 是否已经发送过重置邮件了
		// 如果有没有用过的重置信息 重复利用


		return ResultUtil.getSuccResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * cola.machine.calendar.user.service.UserService#updateUserActive(java.
	 * lang.String)
	 *
	 * @author colamachine 涉及两张表 user 表 和 active 表 user表有active 字段标记账户是否激活状态
	 * active 表记录激活过程和激活码
	 */
	public ResultDTO updateUserActive(String activeid) {

		Active active = this.activeMapper.selectActiveById(activeid);
		if (active == null || StringUtil.isBlank(active.getActiveid())) {
			return ResultUtil.getWrongResultFromCfg("active.url.not.valid");
		}
		if (active.isActived()) {
			return ResultUtil.getWrongResultFromCfg("active.url.used");
		}
		active.setActived(true);
		active.setActivedtime(new Timestamp((new Date()).getTime()));
		this.activeMapper.updateActive(active);
		SysUser user = this.sysUserMapper.selectByPrimaryKey(active.getUserid());
		user.setStatus(2);
		this.sysUserMapper.updateByPrimaryKey(user);

		return ResultUtil.getSuccResult();
		/*HashMap returnMap = new HashMap();
		returnMap.put("user", user);
		returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
		returnMap.put(SysConfig.AJAX_MSG, "激活成功");
		return returnMap;*/
	}
	@Autowired
	private ValidCodeService validCodeService;
	/* (non-Javadoc)
	 * @see cola.machine.service.UserService#savePwdrst(java.lang.String, java.lang.String)
	 * @author colamachine
	 */
	public ResultDTO savePwdrst(String account,String pwd, String code) {
		// 数据格式校验
		ResultDTO msgBox = RegexUtil.pwd(pwd);
		if (msgBox.getR() !=ResultUtil.succ) {
			return msgBox;
		}
		if (StringUtil.isBlank(code)) {
			return ResultUtil.getWrongResultFromCfg("pwd.reset.code.wrong");
		}
		Long userid=null;
		if(StringUtil.isPhone(account)){
			SysUser user = this.getUserByTelno(account);
			if(user==null)
				return  ResultUtil.get(ServiceMsg.USER_NOT_FOUND);
			ResultDTO result = validCodeService.smsValidCode("calendar", account, code);
			userid =user.getId();
		}else if(StringUtil.isEmail(account)){

			SysUser user = this.getUserByEmail(account);
			if(user==null)
				return  ResultUtil.getResult(ServiceMsg.USER_NOT_FOUND);
			userid=user.getId();
			// db校验
			Pwdrst pwdrst = pwdrstMapper.selectPwdrstById(code);
			if (pwdrst == null || pwdrst.getUserid()!= userid) {
				return ResultUtil.getResult(ServiceMsg.VALIDCODE_MATCH_ERR);
			}
			if (pwdrst.isUsed()) {
				return ResultUtil.getResult(ServiceMsg.VALIDCODE_USED);
			}

			pwdrst.setUsed(true);
			pwdrstMapper.used(code);
			 userid = pwdrst.getUserid();
		}else{
			return ResultUtil.getResult(ServiceMsg.ACCOUNT_FORMAT_ERR);
		}



		SysUser user = new SysUser();
		user.setId(userid);
		user.setPassword(MD5Utils.MD5Encode(pwd));
		userMapper.resetPwd(user);
		return ResultUtil.getSuccResult();
	}

	/**
	 * 更新用户状态
	 * @param status
	 * @param userid
     */
	public void updateStatus(int status ,Long userid) {
		SysUser user =new SysUser();
		user.setStatus(status);
		user.setId(userid);
		userMapper.updateStatus(user);
	}


	public ResultDTO activeWithEmail(String email,String code){
		if(StringUtil.isBlank(email)|| !StringUtil.isEmail(email) || StringUtil.isBlank(code)){
			return ResultUtil.getResult(serviceCode,ErrorMsg.PARAM_ERROR,395,ServiceMsg.PARAM_ERR);
		}

		Active active = this.activeMapper.selectActiveById(code);

		if (active == null || StringUtil.isBlank(active.getActiveid())) {
			return ResultUtil.getResult(ServiceMsg.VALIDCODE_ERR);
		}
		if (active.isActived()) {
			return ResultUtil.getResult(ServiceMsg.VALIDCODE_USED);
		}


		SysUser olduser =sysUserMapper.selectByPrimaryKey(active.getUserid());
		if(!email.equals(olduser.getEmail())){
			return ResultUtil.getResult(serviceCode,ErrorMsg.PARAM_ERROR,410,ServiceMsg.PARAM_ERR);
		}
		active.setActived(true);
		active.setActivedtime(new Timestamp((new Date()).getTime()));
		this.activeMapper.updateActive(active);
		SysUser user = this.sysUserMapper.selectByPrimaryKey(active.getUserid());
		user.setStatus(2);
		this.sysUserMapper.updateByPrimaryKey(user);

		return ResultUtil.getSuccResult();
	}
}
