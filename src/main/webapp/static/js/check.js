\/*---------------------------------------------------------------------------*\
|  Subject: JavaScript Framework
|  Author:  cola.machine
|  Created: 2014-4-25 17:20:30
|  Version: v1.0
|-----------------------------------
|  QQ:371452875
|  Copyright (c) cola.machine   MIT-style license
|  The above copyright notice and this permission notice shall be
|  included in all copies or substantial portions of the Software
\*---------------------------------------------------------------------------*/
/**
 * @author colamachine
 * 日期格式化
 */
var checker={
		
};
checker.isPhone=function(value){
	var re=new RegExp("^\d{11}$");
	return re.test(value);
}