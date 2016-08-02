    FastClick.attach(document.body);

    var map, geolocation,lng,lat;
    //加载地图，调用浏览器定位服务
    map = new AMap.Map('container', {
        resizeEnable: true,
        dragEnable:false,
        doubleClickZoom:false,
        zoomEnable:true,
        keyboardEnable:false
    });
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：无穷大
            buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
            buttonPosition:'RB',
            showCircle: false,
            zoomToAccuracy:true,
            showButton:false
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
        AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
    });
    //解析定位结果
    function onComplete(data) {

        lng = data.position.getLng();
        lat = data.position.getLat();
        getAround();

    }
    //解析定位错误信息
    function onError(data) {
        document.getElementById('tip').innerHTML = '定位失败';
    };


    function getAround(){
        AMap.service(["AMap.PlaceSearch"], function() {
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize: 5,
                type: '餐饮服务',
                pageIndex: 1,
                city: "010", //城市
                map: map
                //panel: "panel"
            });
            
            var cpoint = [lng,lat]; //中心点坐标
            placeSearch.searchNearBy('', cpoint, 200, function(status, result) {
                upDataInfo(result.poiList.pois);
                });
        });
    }


    function upDataInfo(arr){
        var infoWrap = $("#tips");
        var infoPar = $(".tip-wrap");
        var infoArr = arr;
        for (var i = 0; i < infoArr.length; i++) {
            infoPar.eq(i).find(".loc-name").text((i+1)+"."+infoArr[i].name);
            infoPar.eq(i).find(".loc-address").text(infoArr[i].address);
        };
        infoWrap.removeClass("hide");
        infoPar.click(function(){
            $(".is").addClass("hide");
            $(this).find("img").removeClass("hide");
        });
    }
