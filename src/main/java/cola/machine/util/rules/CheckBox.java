package cola.machine.util.rules;


public class CheckBox extends Rule{
	private String[] cherkArr;
	public String[] getCherkArr() {
		return cherkArr;
	}
	public void setCherkArr(String[] cherkArr) {
		this.cherkArr = cherkArr;
	}

	public CheckBox(String[] cherkArr) {
		super();
		this.cherkArr = cherkArr;
	}
	
	@Override
	public boolean valid() throws Exception{
        if (this.getValue() == null) {
            return true;
        }

		boolean result = false;
		if(this.getCherkArr()!=null){
			for(String item : this.getCherkArr()){
				if(item.equalsIgnoreCase(this.getValue())){
					result = true;
					break;
				}
			}
		}
		
		if(result){
			return true;
		}else {
			this.setMessage("请选择");
			return false;
		}
	}
}
