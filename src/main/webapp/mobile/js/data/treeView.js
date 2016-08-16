/**
 * Created by gsw on 16-8-10.
 */
$(function () {


    $(".plus-peo").click(function () {
        $(".detailed-item-wrap").addClass("hide");
        $(".widget-box").removeClass("hide");
    });
    $(".list-manage").delegate('div', "click", function () {
        if($(this).attr("data-value")){
            return false;
        }
        $(this).remove();
    });

    $(".lable-manager").click(function () {
        var id = $(this).attr('data-value');
        var choose = $(".ch-div-manager");
        $(".div-list-man").addClass("hide");
        choose.addClass("hide");
        $("#" + id).removeClass("hide");
    });

    $.ajax({
        type: 'GET',
        url: "/whale/moblie/api/v1/orgs/trees",
        data: null,
        dataType: 'json',
        success: function (data, textStatus) {
            if (data.res == 1) {
                var treeObj = $("#preOrgTree");
                $.fn.zTree.init(treeObj, setting, data.obj);
                zTree_Menu = $.fn.zTree.getZTreeObj("preOrgTree");
                curMenu = zTree_Menu.getNodes()[0].children[0];
                zTree_Menu.selectNode(curMenu);

                // treeObj.hover(function () {
                //     if (!treeObj.hasClass("showIcon")) {
                //         treeObj.addClass("showIcon");
                //     }
                // }, function() {
                //     treeObj.removeClass("showIcon");
                // });

            }
        },
        error: function () {
            return;
        },
        beforeSend: function () {
        },
        complete: function () {
        }
    });
});

var curMenu = null, zTree_Menu = null;
var setting = {
    view: {
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        onClick: clickPreOrg
    }
};

function addDiyDom(treeId, treeNode) {
    var spaceWidth = 5;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
    }
}

function beforeClick(treeId, treeNode) {
    if (treeNode.level == 0) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
        return false;
    }
    return true;
}

function clickPreOrg(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
        nodes = zTree.getSelectedNodes(), v = "", n = "", o = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";//获取name值
        n += nodes[i].id + ",";	//获取id值
        o += nodes[i].other + ",";//获取自定义值
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (n.length > 0) n = n.substring(0, n.length - 1);
    if (o.length > 0) o = o.substring(0, o.length - 1);
    if (o != 'r') {
        zTree.expandNode(treeNode);
        return false;
    } else {
        $(".detailed-item-wrap").removeClass("hide")
        $(".widget-box").addClass("hide")
        $(".list-manage").append(
            "<div class='peo-manage'>" +
            "<p>" + v + "</p>" +
            "<p class='name-manage hide'>" + n + "</p>" +
            "</div>");
    }
}
