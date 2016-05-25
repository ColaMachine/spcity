$(document).ready(function(){
var imageUtil = new zImageUtil({
				imgDom: document.getElementById("headImg"),
				inputDom: document.getElementById("headInput"),
				postUrl: PATH+"/image/upload.json",
				maxWidth: 200,
				maxHeight: 200,
				callback: function(data) {

					//TODO

				}
			});


			console.log("image uplodad init complete");

})

var userEditForm={

}