    FastClick.attach(document.body);

    var map, geolocation,lng,lat,infoMap,tips,indexInfo = 0;
    tips = document.getElementById('tips');
    //加载地图，调用浏览器定位服务
    map = new AMap.Map('container', {
        resizeEnable: false,
        dragEnable:false,
        doubleClickZoom:false,
        zoomEnable:true,
        keyboardEnable:false,
        zooms:[15,19]
    });
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：无穷大
            buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
            buttonPosition:'RB',
            showCircle: false,
            zoomToAccuracy:false,
            showButton:false
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
        AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
        console.log(geolocation);
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
                pageSize: 50,
                type: '餐饮服务',
                pageIndex: 1,
                city: "010", //城市
                map: map
                //panel: "panel"
            });
            
            var cpoint = [lng,lat]; //中心点坐标
            placeSearch.searchNearBy('', cpoint, 500, function(status, result) {
                infoMap = result.poiList.pois;
                upDataInfo(result.poiList.pois);
                console.log(result);
                });
        });
    }


    function upDataInfo(arr){
               
        var infoWrap = $("#tips");
        var infoArr = arr;
        $(".f-name").text(infoArr[0].name);
        $(".f-desc").text(infoArr[0].name);
        for (var i = 0; i < infoArr.length; i++) {
            var ch = "<div class='tip-wrap hide'>"+
                        "<p class='loc-name'>"+(i+1)+"."+infoArr[i].name+"</p>"+
                        "<p class='loc-address'>"+infoArr[i].address+"</p>"+
                        "<img class='is hide' src='img/is_this.png' />"+
                    "</div>";
            infoWrap.append(ch);            
        };
        $(".tip-wrap").click(function(){
            $(".is").addClass("hide");
            $(this).find("img").addClass("hide");
            $("#container").addClass("map-l");
            $("#info-q").removeClass("hide");
            $("#tips").addClass("hide");
            var name = $(this).find(".loc-name").text();
            var desc = $(this).find(".loc-address").text();
            $(".f-name").text(name);
            $(".f-desc").text(desc);
            $(".sign-now").removeClass("hide");
        });
    }

    $(".f-re").click(function(){
        $(".sign-now").addClass("hide");
        $("#container").removeClass("map-l");
        $("#info-q").addClass("hide");
        $("#tips").removeClass("hide");
        $(".tip-wrap").slice(indexInfo,indexInfo+5).removeClass("hide");
        indexInfo+=5;
    });

    $(".loc-back").click(function(){
        if($(".tips-w").hasClass("hide")){
            window.location.href='index.html';
        }else{
            $("#tips-sing-wrap").addClass("hide");
        }
       
    });

    $("#tips").scroll(function(){
        if (tips.scrollTop + tips.offsetHeight+3>=tips.scrollHeight&&indexInfo<=50) {
            
            var timeout = setTimeout(function(){
                $(".tip-wrap").slice(indexInfo,indexInfo+5).removeClass("hide");
                indexInfo+=5;
                clearTimeout(timeout);
                console.log(123);
            },300);
            
        };
        
    });

    $(".sign-now").click(function(){
        $("#tips-sing-wrap").removeClass("hide");
        $(".loc-op").text($(".f-desc").text());

    });



