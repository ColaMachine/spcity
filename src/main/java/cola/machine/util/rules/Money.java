package cola.machine.util.rules;


import cola.machine.util.StringUtil;

public class Money extends Rule {
	
	public Money() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isNumeric(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入有效金额");
				return false;
			}
		}
	}

}
