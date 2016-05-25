package cola.machine.util.rules;


public class NotEmpty extends Rule{
	

	
	public NotEmpty(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null || this.getValue().trim().equals("") ){
				message = "不能为空";
				return false;
			}else {
				return true;
			}
		
	}
}
