package cola.machine.util.rules;


public class BooleanValue extends Rule {
	
	@Override
	public boolean valid() throws Exception {
        if (this.getValue() == null) {
            return true;
        }

		if (this.getValue() != null && 
				(this.getValue().equalsIgnoreCase(Boolean.TRUE.toString()) 
						|| this.getValue().equalsIgnoreCase(Boolean.FALSE.toString()))){
			return true;
		}
		else {
			this.setMessage("请选择");
			return false;			
		}
	
	}
}
