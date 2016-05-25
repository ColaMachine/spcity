
/**
 * index
 * @authors pingyf
 * @date    2015-12-02 11:33:53
 * @version $Id$
 */
'use strict';

var areaText = {
    'placeholder': '请输入留言',
    'content': '',
    'commentId': '',
    'commentUserId': '',
    'commentUserName': '',
    'buttonText': '发布',
    'canReply': true
};

var messages = {'messages': []};
var pageNum = 1;

// 昵称过滤器
Vue.filter('filterNickName', function (nickName, isMerchant) {
    if(isMerchant){
        return '店主';
    } else {
        return nickName || '游客';
    }
});

var messageList = new Vue({
    el: '#message',
    data: messages,
    created: function(){
        getMessageList(1);
    },
    methods: {
        reply: function(userName, commentId, commentUserId, commentUserName, isMerchant){
            var nickName;
            var filter = Vue.filter('filterNickName');
            nickName = filter(userName, isMerchant);
            msgboard.$set('placeholder', '回复：' + nickName);
            msgboard.$set('content', '');
            areaText.commentId = commentId;
            areaText.commentUserId = commentUserId;
            areaText.commentUserName = commentUserName;
            //console.log(userName);
        }
    }
});

var msgboard = new Vue({
    el: '#messageboard',
    data: areaText,
    methods: {
        post: function(){
            var toPost;

            if(areaText.content.length === 0){
                alert('留言不能为空');
                return false;
            }
            msgboard.$set('buttonText', '发布中...');
            msgboard.$set('canReply', false);
            if(areaText.commentId && !msgboard.$get('canReply')){
                toPost = areaText;
                var _id = areaText.commentId;

                httpx.post({
                    url: PATH + 'ms/comment/reply',
                    data: toPost,
                    success: function(response){
                        console.log(response);
                        response = JSON.parse(response);
                        if(response.success){
                            msgboard.$set('content', '');
                            msgboard.$set('commentId', '');
                            msgboard.$set('commentUserId', '');
                            msgboard.$set('commentUserName', '');
                            msgboard.$set('placeholder', '请输入留言');
                            msgboard.$set('buttonText', '发布');
                            console.log(response.data);
                            for(var index in messageList.messages){
                                if(messageList.messages[index].id == _id){
                                    messageList.messages[index].reply.push(response.data);
                                }
                            }
                        } else {
                            alert(response.message);
                        }
                    },
                    error: function(method, url){
                        console.log(method, url);
                        alert('回复失败');
                        msgboard.$set('buttonText', '发布');
                    }
                });

            } else if(!msgboard.$get('canReply')) {
                toPost = {
                    content: areaText.content
                };

                console.log(areaText.content);
                httpx.post({
                    url: PATH + 'ms/comment/add',
                    data: toPost,
                    success: function(response){
                        console.log(response);
                        response = JSON.parse(response);
                        if(response.success){
                            msgboard.$set('content', '');
                            msgboard.$set('buttonText', '发布');
                            console.log(response.data);
                            //getMessageList(1);
                            var toAdd = {
                                "id": response.data.commentId,
                                "merchantId": response.data.merchantId,
                                "commentUserId": response.data.commentUserId,
                                "commentUserName": response.data.commentUserName,
                                "commentGrade": null,
                                "content": response.data.content,
                                "status": 1,
                                "statusDate": null,
                                "createDate": null,
                                "createDateStr": response.data.date,
                                "merchantDo": response.data.merchantDo,
                                "commentUserFaceInfo": response.data.faceInfo || PATH + 'static/weizhan/src/img/messageboard/avatar.png',
                                "reply":[],
                                "pubUser":null
                            };

                            messageList.messages.splice(0, 0, toAdd);
                        } else {
                            alert(response.message);
                        }
                    },
                    error: function(method, url){
                        console.log(method, url);
                        alert('回复失败');
                        msgboard.$set('buttonText', '发布');
                    }
                });
            }

        }
    }
});

var moreMessage;
moreMessage = new Vue({
    el: '#more-message',
    data: {
        'buttonText': '加载中......'
    },
    methods: {
        load: function () {
            moreMessage.buttonText = '加载中......';
            if (messageList.messages.length === 0) {
                pageNum = 0;
            }
            httpx.getJSON({
                url: PATH + 'ms/comment/get?page=' + (pageNum + 1),
                success: function (response) {
                    console.log(response);
                    console.log(pageNum);
                    if (response.success) {
                        pageNum += 1;
                        var index;
                        if (response.data.length) {
                            for (index in response.data) {
                                console.log(response.data[index]);
                                messageList.messages.push(response.data[index]);
                            }
                            moreMessage.buttonText = '查看更多';
                        } else {
                            pageNum -= 1;
                            moreMessage.buttonText = '没有更多的留言';
                            //alert('到底了！');
                        }

                    } else {
                        moreMessage.buttonText = '获取失败,请重试';
                        //alert('获取失败,请重试');
                    }
                },
                error: function (method, url) {
                    console.log(method, url);
                    moreMessage.buttonText = '获取失败,请稍后重试';
                }
            });
        }
    }
});

var carousel = new Vue({
    el: '#carousel',
    data: {
        images: [],
        imageNum: 0,
        durationTime: 3000,
        speed: 100,
        timerAuto: null,
        style: {
            left: 0,
            width: '25'
        },
        touch: {
            isTouched: false,
            x: 0,
            y: 0,
            direction: 1  // 0：右  1：左
        }
    },
    created: function(){
        getCarouselImage();
    },
    methods: {
        changeImage: function(direction){
            var self = this,
                timer;
            if(direction === 1 && direction.length !== 0){
                timer = setInterval(function(){
                    if(self.style.left > -(self.imageNum + 1) * 100 && self.imageNum !== self.images.length - 1){
                        self.style.left = self.style.left - 100 / self.speed;
                    } else {
                        // self.imageNum = 0;
                        clearInterval(timer);
                        if(self.imageNum === self.images.length - 1){
                            self.imageNum = 0;
                            self.style.left = 0;
                        } else {
                            ++self.imageNum;
                        }
                    }
                }, 24 / 1000);
            } else if(direction === 0 && direction.length !== 0) {
                timer = setInterval(function(){
                    if(self.style.left < -(self.imageNum - 1) * 100 && self.imageNum !== 0){
                        self.style.left = self.style.left + 100 / self.speed;
                    } else {
                        // self.imageNum = 0;
                        if(self.imageNum !== 0){
                            --self.imageNum;
                        } else {
                            var imageLength = self.images.length - 1;
                            self.imageNum = imageLength;
                            self.style.left = -imageLength * 100;
                        }

                        clearInterval(timer);
                        if(self.imageNum === 0){
                            self.style.left = 0;
                        }
                    }
                }, 24 / 1000);
            } else {
                return false;
            }

        },
        startDrag: function(){
            event.preventDefault();
            console.log(event.changedTouches[0]);
            var self = this;
            self.touch.isTouched = true;
            self.touch.x = event.changedTouches[0].clientX;
            self.touch.y = event.changedTouches[0].clientY;
            clearInterval(self.timerAuto);
        },
        startMove: function(){
            event.preventDefault();
            var self = this;

            console.log('moving');
            if(self.touch.isTouched){
                console.log('X:',event.changedTouches[0].clientX - self.touch.x);
                console.log('Y:',Math.abs(event.changedTouches[0].clientY - self.touch.y));
                if(event.changedTouches[0].clientX - self.touch.x > 70 && Math.abs(event.changedTouches[0].clientY - self.touch.y) < 50){
                    // 向右滑动
                    self.touch.direction = 0;
                } else if(event.changedTouches[0].clientX - self.touch.x < -70 && Math.abs(event.changedTouches[0].clientY - self.touch.y) < 50){
                    // 向左滑动
                    self.touch.direction = 1;
                } else {
                    self.touch.direction = '';
                }
            }
        },
        endDrag: function(){
            event.preventDefault();
            console.log('end');
            var self = this;
            self.touch.isTouched = false;
            self.changeImage(self.touch.direction);
            self.autoPlay();
        },
        autoPlay: function(){
            var self = this;
            self.timerAuto = setInterval(function(){
                self.changeImage(1);
            }, self.durationTime);
        }
    }
});

function getMessageList(pageNum){
    httpx.getJSON({
        url: PATH + 'ms/comment/get?page=' + pageNum || 1,
        success: function(response){
            console.log(response);
            messageList.$set('messages', response.data);
            moreMessage.buttonText = '查看更多';
        },
        error: function(method, url){
            console.log(method, url);
            moreMessage.buttonText = '获取失败,请稍后重试';
        }
    });
}

function getCarouselImage(){
    httpx.getJSON({
        url: PATH + 'ms/getslides',
        success: function(response){
            console.log(response.data);
            if(response.success){
                var imageNum = response.data.length;
                carousel.$set('images', response.data);
                carousel.$set('style.width', 100 / imageNum);
                // carousel.$set('style.focusRight', window.screen.availWidth / 2 + 20 * imageNum);
            }
            carousel.autoPlay();
        },
        error: function(method, url){
            console.log(method, url);
        }
    });
}

function errorImg() {
    var img=event.srcElement;
    img.src = PATH + "static/weizhan/src/img/messageboard/avatar.png";
    img.onerror = null;
}


window.onload = function(){
    console.log(location.search);
    //document.getElementById('login').href = document.getElementById('login').href + location.search;
};