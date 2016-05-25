package cola.machine.util.rules;


import cola.machine.util.DateUtil;
import cola.machine.util.StringUtil;


public class DateValue extends Rule {
	public String formatStr="";
	public DateValue(String formatStr) {
		this.formatStr=formatStr;
	}
	public DateValue() {
    }
	@Override
	public boolean valid() throws Exception {
		if (StringUtil.isBlank(this.getValue())) {
			return true;
		}
			
		java.util.Date dtValue = DateUtil.parseToDateTry(this.getValue());
		
		if (dtValue == null) {
			this.setMessage("请输入日期");
			return false;
		}
		else {
			return true;
		}
	}

}
