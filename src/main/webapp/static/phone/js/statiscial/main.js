/**
 * 
 * @authors Ping YF (koakumaping@163.com)
 * @date    2015-11-10 17:04:36
 * @version $Id$
 */

$(function(){
	var flag = false;

	$('#visit-order').on('tap', function(){
		var $visitList = $('#visit-list');

		var list = $visitList.children(),
			result = [],
			output = [],
			map = [];

		for(var index = 0; index < list.length; index++){
			var num = $(list[index]).find('#visit-num').text();
			result.push(list[index]);
			map.push({num:num, index:index});
		}

		var json,
            i;
		json = JsonSort(map,'num');

		if(flag){
			for(i = json.length-1; i >= 0; i--){
				output[json.length-i] = result[json[i].index].outerHTML;
			}
		} else {
			for(i = 0; i < json.length; i++){
				output[i] = result[json[i].index].outerHTML;
			}
		}
	
		$visitList.empty().append(output);

		flag = !flag;
		console.log(flag);
	});

	/*
	    @function     JsonSort 对json排序
	    @param        json     用来排序的json
	    @param        key      排序的键值
	*/
	function JsonSort(json,key){
	    //console.log(json);
	    for(var j=1,jl=json.length;j < jl;j++){
	        var temp = json[j],
	            val = temp[key],
	            i = j-1;
	        while(i >=0 && Number(json[i][key]) > val){
	            json[i+1] = json[i];
	            i = i-1;
	        }
	        json[i+1] = temp;

	    }
	    // console.log(json);
	    return json;
	}
});