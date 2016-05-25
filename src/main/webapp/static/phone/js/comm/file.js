/**
 * 文件上传
 * @authors pingyf
 * @date    2015-10-30 10:31:38
 * @version $Id$
 */

(function(window){

    var fileUpload = function(fs, url, fun){
        return new fileUpload.prototype.uploadFile(fs, url, fun)
    };

    fileUpload.prototype = {

        uploadFile: function(fs, url, fun){
            var self = this;
            var len = fs.length;
            url = url || '';
            for(var index = 0; index < len; index++){
                self.sendFile(fs[index], url, fun);
            }
        },

        sendFile: function(file, url, fun){
            var self = this;
            var xhr = new XMLHttpRequest(),
                fd = new FormData();

            fd.append('file', file);
            // 上传中
            xhr.upload.addEventListener("progress", function(e) {
                self.onProgress(file, e.loaded, e.total);
            }, false);

            xhr.onreadystatechange = function(){
                if(xhr.readyState == 4 && xhr.status == 200){
                    fun.call(this, eval("("+xhr.responseText+")"));
                }else if(xhr.readyState == 4 && xhr.status != 200){
                    fun.call(this, xhr);
                }
            };
            xhr.open('POST', url);
            xhr.send(fd);
        },

        onProgress: function(file, loaded, total){
            console.log(loaded + ':' + total);
        }
    };

    fileUpload.prototype.uploadFile.prototype = fileUpload.prototype;

    window.fileUpload = fileUpload
}(window));



// 用法
// 文件控件发生变化时，调用uploadFile函数，触发上传功能
// file.onchange = function() {
//     file.uploadFile(this.files, '/picuploads/', function(data){
// 	    console.log(data);
// 	    if(data.ResultCode == '0'){
// 	        $('.info-avatar-img').attr('src', data.Data.Path);
// 	    }
// 	});
// };
// // 在区域内释放拖入文件时，调用文件上传函数
// area.ondrop = function(ev) {
//     ev.preventDefault();
//     var dt = ev.dataTransfer;
//     file.uploadFile(this.files, '/picuploads/', function(data){
// 	    console.log(data);
// 	    if(data.ResultCode == '0'){
// 	        $('.info-avatar-img').attr('src', data.Data.Path);
// 	    }
// 	});
// };