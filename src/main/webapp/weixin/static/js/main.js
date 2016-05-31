/**
 * Created by zhuxuehuang on 16/5/17.
 */

// 请求后端接口基础路径，末尾不含/
var BASE_PATH = '/spcity';

var width = document.documentElement.clientWidth;
if (width > 750) {
    width = 750;
}
document.documentElement.style.fontSize = width / 7.5 + 'px';


var app = angular.module('app', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/expert/list', {
            templateUrl: 'static/template/expert.html',
            controller: 'ExpertController'
        })
        .when('/meeting/list', {
            templateUrl: 'static/template/meeting.html',
            controller: 'MeetingController'
        })
        .when('/meeting/view', {
            templateUrl: 'static/template/meeting_view.html',
            controller: 'MeetingViewController'
        })
        .when('/partner/list', {
            templateUrl: 'static/template/partner.html',
            controller: 'PartnerController'
        })
        .otherwise({
            redirectTo: '/expert/list'
        });
}]);

app.factory('Page', function () {
    var title = '海绵城市';
    return {
        title: function () {
            return title;
        },
        setTitle: function (newTitle) {
            title = newTitle;
        }
    };
});

app.controller('MainController', function ($scope, Page) {
    $scope.Page = Page;
});

function getDate(dateInt) {
    var newDate = new Date(dateInt);
    return newDate.getFullYear() + '-' + (newDate.getMonth()+1) + '-' + newDate.getDay();
}


// 专家智库
app.controller('ExpertController', function ($scope, Page, $http) {
    Page.setTitle('专家智库');

    $http.get(BASE_PATH + '/expert/list.json').success(function (resp, status, headers, config) {
        if (resp.r != 0) {
            alert(resp.msg || '网络出错了，请稍后重试');
            return ;
        }
        if (resp.data.length == 0) {
            alert('还没有肉容');
            return ;
        }
        $scope.expertList = resp.data;
    });
});


// 行业峰会
app.controller('MeetingController', function ($scope, Page, $http) {
    Page.setTitle('行业峰会');

    $http.get(BASE_PATH + '/artical/list.json').success(function (resp, status, headers, config) {
        if (resp.r != 0) {
            alert(resp.msg || '网络出错了，请稍后重试');
            return ;
        }
        if (resp.data.length == 0) {
            alert('还没有肉容');
            return ;
        }
        for (var i in resp.data) {
            var item = resp.data[i];
            resp.data[i].createtime = getDate(item.createtime);
        }
        $scope.articalList = resp.data;
    });
});


// 查看行业峰会
app.controller('MeetingViewController', function ($scope, Page, $http, $location, $sce) {
    Page.setTitle('查看行业峰会');

    var id = $location.search()['id'];

    $http.get(BASE_PATH + '/artical/view.json?id=' + id).success(function (resp, status, headers, config) {
        if (resp.r != 0) {
            alert(resp.msg || '网络出错了，请稍后重试');
            return ;
        }
        resp.data.bean.createtime = getDate(resp.data.bean.createtime);
        resp.data.bean.content = $sce.trustAsHtml(resp.data.bean.content);
        $scope.expert = resp.data.bean;
    });
});

// 合作伙伴列表
app.controller('PartnerController', function ($scope, Page, $http, $location, $sce) {
    Page.setTitle('合作伙伴');

    $http.get(BASE_PATH + '/partner/list.json').success(function (resp, status, headers, config) {
        if (resp.r != 0) {
            alert(resp.msg || '网络出错了，请稍后重试');
            return ;
        }
        if (resp.data.length == 0) {
            alert('还没有肉容');
            return ;
        }
        $scope.partnerList = resp.data;
    });
});