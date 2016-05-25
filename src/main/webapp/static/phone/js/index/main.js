/**
 * index
 * @authors pingyf
 * @date    2015-10-30 11:33:53
 * @version $Id$
 */
var messageHtml =
    '<div class="msgboard-list-item">'+
    '<div class="clr-fix">'+
    '<div class="left row-6 clr-fix">'+
    '<div class="left row-2">'+
    '<img class="row-10" src="{0}">'+
    '</div>'+
    '<div class="left row-8 normal-text">'+
    '<span class="msgboard-nick">{1}</span>'+
    '</div>'+
    '</div>'+
    '<div class="left row-4 clr-fix">'+
    '<span class="right gray-text">{2}</span>'+
    '</div>'+
    '</div>'+
    '<div class="msgboard-message gray-text normal-text">'+
    '<span>{3}</span>'+
    '</div>'+
    '<div class="msgboard-reply clr-fix">'+
    '<input type="hidden" class="commentUserId" value="{4}">'+
    '<input type="hidden" class="commentId" value="{5}">'+
    '<div class="right row-5 right-text small-text"><a class="blue-text" href="#msgboard">回复</a></div>'+
    '</div>'+
    '{6}'+
    '</div>';

var replyHtml = '<div class="msgboard-reply-list back-gray smaller-text">{0}</div>';
var replyInnerHtml =
    '<div class="row-9">'+
    '<a class="blue-text" href="javascript:void(0);">{0}</a>：<span>{1}</span>'+
    '</div>';
//'<div class="row-9">'+
//    '<a class="blue-text" href="#msgboard">店主</a>回复<a class="blue-text" href="#msgboard">Lina</a>：<span>谢谢</span>'+
//'</div>';

$(function(){
    'use strict';
    var height,
        width;
    var pageNum = 1;

    width = document.body.offsetWidth;
    height = width * 9 / 16;
    $(".carousel").show().yxMobileSlider({height: height+'px'});

    if($('.carousel-edit').length > 0){
        // 计算输入框的高度
        var sI = setInterval(function(){
            if($('.carousel-edit').find('img[alt=add]').height() != 0){
                clearInterval(sI);
                $('.carousel-edit-input').css({
                    'height': $('.carousel-edit').find('img[alt=add]').height()
                });
            }
        }, 50)
    }

    getList(pageNum);

    $('#msg-submit').on('click', function(){

        if($('#msg-textarea').attr('data-comment-id')){
            replyMsg();
        } else {
            addMsg();
        }
    });

    $('#more').on('click', function(){
        $(this).text('加载中...');
        getList(pageNum);
        $(this).text('查看更多');
    });

    function replay(){
        var name = $(this).parent().parent().prev().prev().find('.msgboard-nick').text();
        var commentUserId = $(this).parent().parent().find('.commentUserId').val();
        var commentId = $(this).parent().parent().find('.commentId').val();
        console.log(commentId);
        $('#msg-textarea')
            .attr('placeholder',format('回复 {0}：', name))
            .attr('data-comment-id', commentId)
            .attr('data-comment-user-id', commentUserId)
            .attr('data-comment-user-name', name);
    }

    function addMsg(){
        var message = $('#msg-textarea').val();
        if(message == ''){
            tip('请输入留言信息');
            return false;
        }

        var data = {
            content: message
        };

        loading.show();
        $.post(
            PATH + 'ms/comment/add',
            data
        )
            .success(function(data){
                setTimeout(function(){
                    loading.hide();
                }, 500);
                if(data.success){
                    var userName = data.data.commentUserName || '游客';
                    userName = data.data.merchantDo==0 ? userName : "店主";
                    $('.msgboard-list').prepend(
                        format(messageHtml,
                            data.data.faceInfo || PATH + '/static/weizhan/blue/img/messageboard/avatar.png',
                            userName,
                            data.data.date,
                            data.data.content,
                            data.data.commentUserId,
                            data.data.commentId,
                            ''
                        )
                    );
                    $('#msg-textarea').val('');

                    var $msgReply = $('.msgboard-reply');
                    $msgReply.find('a.blue-text').off('tap', replay);
                    $msgReply.find('a.blue-text').on('tap', replay);
                }
            })
            .fail(function(){
                setTimeout(function(){
                    loading.hide();
                    tip('添加评论失败,请重试');
                }, 5000);
            });
    }

    function replyMsg(){

        var $msgboard = $('#msg-textarea');

        var message = $msgboard.val(),
            commentId = $msgboard.attr('data-comment-id'),
            userId = $msgboard.attr('data-comment-user-id'),
            userName = $msgboard.attr('data-comment-user-name');

        if(message == ''){
            tip('请输入留言信息');
            return false;
        }

        var data = {
            commentId: commentId,
            commentUserId: userId,
            commentUserName: userName,
            content: message
        };

        loading.show();
        $.post(
            PATH + 'ms/comment/reply',
            data
        )
            .success(function(data){
                setTimeout(function(){
                    loading.hide();
                }, 500);
                console.log(data);
                if(data.success){
                    $('#msg-textarea')
                        .attr('placeholder', '请输入留言')
                        .attr('data-comment-id', '')
                        .attr('data-comment-user-id', '')
                        .attr('data-comment-user-name', '').val('');

                    var $replyList = $('.commentId[value="'+commentId+'"]').parent().next();

                    console.log($replyList);
                    userName = data.data.replyUserId ? data.data.replyUserName : '游客';
                    userName = data.data.merchantDo==0 ? '店主':userName;
                    if($replyList.hasClass('msgboard-reply-list')) {
                        $replyList.append(format(replyInnerHtml, userName, message));
                    } else {
                        console.log(format(replyHtml, format(replyInnerHtml, userName, message)));
                        $('.commentId[value="'+commentId+'"]').parent().parent().append(format(replyHtml, format(replyInnerHtml, userName, message)));
                    }
                }
            })
            .fail(function(){
                setTimeout(function(){
                    loading.hide();
                    tip('fail');
                }, 5000);
            });
    }


    function getList(pageNumber, showLoading){

        pageNum += 1;
        $.get(PATH + 'ms/comment/get?page='+pageNumber)
            .done(function(data){
                console.log(data);
                if(data.success){
                    var list = data.data;

                    if(!list.length){
                        console.log(format('收到有{0}条留言', list.length));
                        if(pageNum === 2){
                            $('.more').find('button').text('没有留言');
                            pageNum -= 1;
                            return false;
                        } else {
                            $('.more').find('button').text('没有更多留言');
                            pageNum -= 1;
                            return false;
                        }
                    }

                    for(var index = 0; index < list.length; index++){
                        console.log(list[index]);

                        var innerHtml = '';
                        for(var replyIndex = 0; replyIndex < list[index].reply.length; replyIndex++){
                            //console.log(list[index].reply[replyIndex]);

                            var replyData = list[index].reply[replyIndex];

                            var fromUserId = replyData.replyUserId;
                            var fromUserName = replyData.replyUserName || '游客';
                            var toUserId = replyData.commentId;
                            var toUserName = replyData.commentUserName;

                            var content = replyData.content;

                            var flag = replyData.merchantDo;
                            if(flag){
                                fromUserName = '店主'
                            }

                            innerHtml = innerHtml + format(replyInnerHtml, fromUserName, content);
                        }

                        var rphl = '';
                        if(innerHtml !== ''){
                            rphl = format(replyHtml, innerHtml);
                        }

                        //console.log(rphl);
                        console.log(list[index].merchantDo);
                        if(list[index].merchantDo){
                            list[index].commentUserName = '店主';
                        }

                        var html = format(messageHtml,
                            list[index].commentUserFaceInfo,
                            list[index].commentUserName || '游客',
                            list[index].createDateStr,
                            list[index].content,
                            list[index].commentUserId,
                            list[index].id,
                            rphl
                        );

                        $('.msgboard-list').append(html);
                    }

                    var $msgReply = $('.msgboard-reply');
                    $msgReply.find('a.blue-text').off('tap', replay);
                    $msgReply.find('a.blue-text').on('tap', replay);
                }
            }).fail(function(){
                //alert('失败');
                tip('获取失败，请重试');
            });
    }

    //免认证
    //is_authed
    if(!!$("#_is_authed").val()){
        //alert($("#_LoginURL").val());
        var _dev_id = $("#_dev_id").val();
        var _account_id = $("#_account_id").val();
        $.ajax({
            url: $("#_LoginURL").val(),
            dataType: 'JSONP',
            jsonp: 'callback',
            header:{
                'cache-control':'no-cache'
            },
            data:{
                auth_id: $("#_auth_id").val(),
                auth_type: $("#_auth_type").val(),
                dev_id: _dev_id,
                mac: $("#_user_mac").val(),
                site_id: $("#_site_id").val(),
                dev_mac: $("#_dev_mac").val(),
                user_ip: $("#_upser_ip").val(),
                ac_name: $("#_ac_name").val(),
                from: $("#_from").val(),
                browser_type: $("#_browser").val(),
                terminal_type: $("#_terminal_type").val(),
                is_authed:$("#_is_authed").val()
            },
            async:false,
            success: function(data, textStatus, jqXHR){
                if(data.result=='OK'){
                    alert("免认证成功.");
                    //免认证成功后，把is_authed改为false
                    $.get("/ms/hasauthed",function(data,status){
                        if(data.success){
                            console.log("hasauthed ok.");
                        }else{
                            console.log("hasauthed faile.");
                        }
                    });
                    //var ps = {
                    //    dev_id:_DEVID_,
                    //    user_mac:_USERMAC_,
                    //    ap_mac:_DEVICEMAC_,
                    //    browser:$.client.browser,
                    //    osbrand:$.client.os,
                    //    terminal_type: $.client.os,
                    //    from:'',
                    //    url:_REFERERURL_,
                    //    portal_type:'',
                    //    site_id:_SITEID_,
                    //    token:data.token,
                    //    user_ip:_USERIP_,
                    //    gw_address:'',
                    //    gw_port:'',
                    //    user_id:'',
                    //    is_authed:($base.hasClass('authed') ? true:false)
                    //};
                    // 认证完成后处理（创建用户、用户日志和相关数据）
                    //AuthFinish.do_finish('', $.client.browser, $.client.os, data.token, ps);
                }else{
                    alert("免认证失败：" + data.message);
                    //清楚cookie
                    $.get("/ms/delcookie", function(data,status){
                        if(data.success){
                            console.log("delcookie 成功.");
                        }else{
                            console.log("delcookie 失败.");
                        }
                    });
                    //刷新index页面，仅设置account_id和dev_id
                    window.location.href = "/ms/index?account_id="+_account_id + "&dev_id=" + _dev_id;
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                //alert(textStatus || '系统异常，请稍后再试...');
                //刷新index页面，仅设置account_id和dev_id
                window.location.href = "/ms/index?account_id="+_account_id + "&dev_id=" + _dev_id;
            },
            complete: function(XHR, textStatus){
                //$el.attr('disabled', false);
            }
        });
    }


});


function queryComment(pageNum) {
    var data = {
        pageNum:pageNum
    };
    $.ajax({
        url: '/ms/member/comment/findPager',
        dataType: 'text',
        data: data,
        async: false,
        type: "POST",
        success: function(data, textStatus, jqXHR){
            if(data != null && data != ""){
                $("#commentinfo").append(data);
            } else {
                $("#moreBut").hide();
            }
        },
        error: function(XHR, textStatus, errorThrown) {
            alert(textStatus || '系统异常，请稍后再试...');
        },
        complete: function(XHR, textStatus){
        }
    });
}


//评论
function comment() {
    var data = {
        pageNum:pageNum
    };
    $.ajax({
        url: '/ms/member/comment/findPager',
        dataType: 'text',
        data: data,
        async: false,
        type: "POST",
        success: function(data, textStatus, jqXHR){
            if(data != null && data != ""){
                $("#commentinfo").append(data);
            } else {
                $("#moreBut").hide();
            }
        },
        error: function(XHR, textStatus, errorThrown) {
            alert("添加评论异常");
        },
        complete: function(XHR, textStatus){
        }
    });
}