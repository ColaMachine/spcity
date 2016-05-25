package cola.machine.common.msgbox;


/*{
 r : 0,
 msg : ""
 data : {}
 page : {
 total : 123
 curpage : 3
 }
 }*/
public class MsgReturn {
/*	private boolean result;
	private String msg;
	private Object data;
	private Page page;

	public MsgReturn() {
	}

	public MsgReturn(boolean b, String msg) {
		this.result = b;
		this.msg = msg;
	}
	*//**
	 * 构造函数
	 * @paramb :是否成功
	 * @param:返回消息
	 * @param:分页列表信息
	 * @return msgResturn;
	 *//*
	public MsgReturn(boolean b, String msg,ListPage listPage) {
		this.result = b;
		this.msg = msg;
		this.setListPage(listPage);
	}
	public boolean isRight() {
		return result;
	}

	public void setRight(boolean flag) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	 * public List<String> getErrors() { return errors; } public void
	 * setErrors(List<String> errors) { this.errors = errors; } public void
	 * addError(String error){ this.errors.add(error); }
	 

	public String toJsonStr() {
		return cola.machine.util.JsonUtils.toJsonString(this.toMap());
	}// src/main/webapp/WEB-INF/classes

	public HashMap toMap() {
		HashMap returnMap = new HashMap();
		returnMap.put(SysConfig.AJAX_RESULT, result ? SysConfig.AJAX_SUCC
				: SysConfig.AJAX_FAIL);
		returnMap.put(SysConfig.AJAX_MSG, this.msg);
		returnMap.put(SysConfig.AJAX_DATA, this.data);
		// returnMap.put(SysConfig.AJAX_PAGE,this.page);
		HashMap pagemap = new HashMap();
		pagemap.put(SysConfig.AJAX_CURPAGE, this.page.getCurrentPage());
		pagemap.put(SysConfig.AJAX_TOTALPAGE, this.page.getTotalPage());
		pagemap.put(SysConfig.AJAX_TOTALCOUNT, this.page.getTotalCount());
		pagemap.put(SysConfig.AJAX_CURPAGE, this.page.getCurrentPage());
		returnMap.put(SysConfig.AJAX_PAGE, pagemap);
		return returnMap;
	}

	public void setListPage(ListPage listPage) {
		this.page = listPage.getPage();
		this.data = listPage.getList();
	}

	public Object getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}

	
	 * public void addData(String key,Object value){ this.data.put(key, value);
	 * }
	 

	public String toString() {
		return this.toJsonStr();
	}
*//**
 * 
 * 说明:demo
 * @param args
 * @return void
 * @author dozen.zhang
 * @date 2015年6月29日上午11:31:35
 *//*
	public static void main(String args[]) {
		MsgReturn msgBox = new MsgReturn();
		msgBox.setMsg("操作成功");
		msgBox.setRight(true);
		List list = new ArrayList();

		for (int i = 0; i < 8; i++) {
			HashMap map = new HashMap();
			map.put("id", i);
			map.put("value", "123123" + i);
			list.add(map);
		}

		ListPage listPage = new ListPage();
		Page page = new Page();
		page.setCurrentPage(2);
		page.setTotalCount(32);
		listPage.setPage(page);
		listPage.setList(list);
		msgBox.setListPage(listPage);
		System.out.println(msgBox.toJsonStr());
	}
*/
}
