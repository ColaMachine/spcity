package cola.machine.util.rules;

import cola.machine.util.StringUtil;


public class AlphaRule extends Rule {
	
	public AlphaRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkAlpha(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入字母");
				return false;
			}
		}
	}

}
