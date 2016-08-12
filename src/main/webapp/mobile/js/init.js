//锁住浏览器
// document.addEventListener('touchmove', function(ev) {
//     ev.stopPropagation();
//     ev.preventDefault();
// });

//解决click 200延迟
FastClick.attach(document.body);



var thatKey = 0;


//正文
$("#work").click(function(){
	changeItem("work","setup");
});

$("#setup").click(function(){
	changeItem("setup","work");
  window.location.href='setUp.html';
});

$(".back, #work").click(function(){
  window.location.href='index.html';
});

$("#work-flow").click(function(){
  window.location.href='workFlow.html';
});

$("#notice").click(function(){
  window.location.href='notice.html';
});

$("#office-sign").click(function(){
  window.location.href='signInside.html';
});

$("#loc-sign").click(function(){
  window.location.href='loc.html';
});

$(".s-ad").click(function(){
  var index = $(this).attr("data-swiper-slide-index");
  window.location.href='notice.html?index='+index;
});



//notice
$(".tip-lable").click(function(){
  $(".tip-lable").removeClass("tip-focus");
  $(this).addClass("tip-focus");
  $(".tips-item-wrap").removeClass("hide");

  if ($(this).hasClass("unread")) {
    $(".tips-item-wrap").eq(1).addClass("hide");
  }else{
    $(".tips-item-wrap").eq(0).addClass("hide");
  };
});

$(".tip-del").click(function(){
  $(this).parent().parent().remove();
});

//切换
function changeItem(id1,id2){
	$("#"+id1).find("img").attr("src","img/"+id1+"_b.png");
	$("#"+id1).find("span").css("color","#56ABE4");
	$("#"+id2).find("img").attr("src","img/"+id2+"_h.png");
	$("#"+id2).find("span").css("color","#666");
}

//notice
$(".notice-back").click(function(){
  if($(".detailed-wrap").hasClass("hide")){
    window.location.href='index.html';

  }else{
    $(".detailed-wrap").addClass("hide");
  }
});

$(".tip-img-wrap, .tip-text-wrap").click(function(){
  var con = $(this).parent();
  
  var title = con.find(".tip-title").text();

  var text = con.find("span").eq(0).text();
  $(".detailed-title").find("span").eq(0).text(title);
  $(".detailed-text").text(text);
  $(".detailed-wrap").removeClass("hide");

  var sum = parseInt($("#fsum").text());
  if(sum!=""&&con.attr("key")!="1"){
    sum==0?0:sum--;
    $("#fsum").text(sum+")");
    con.attr("key","1");
  }
});

$(".forLeave").click(function(){
  window.location.href="leave.html";
  // $(".detailed-item-wrap").addClass("hide");
  // $(".detailed-item-wrap").eq(0).removeClass("hide");
  // thatKey = 0;
});

$(".wiped").click(function(){
  window.location.href="claim.html";
  // $(".detailed-item-wrap").addClass("hide");
  // $(".detailed-item-wrap").eq(1).removeClass("hide");
  // thatKey = 1;
});

$(".busTra").click(function(){
  window.location.href="overtime.html";
  // $(".detailed-item-wrap").addClass("hide");
  // $(".detailed-item-wrap").eq(2).removeClass("hide");
  // thatKey = 2;
});

$(".fillCard").click(function(){
  window.location.href="patch.html";
  // $(".detailed-item-wrap").addClass("hide");
  // $(".detailed-item-wrap").eq(3).removeClass("hide");
  // thatKey = 3;
});


//work-flow

$(".wf-back").click(function(){
  // if (!($(".detailed-item-wrap").eq(thatKey).hasClass("hide"))) {
  //
  //   $(".detailed-item-wrap").addClass("hide");
  //
  // }else{

    window.location.href='index.html';

  // };
});



//setup
$("#setup-s").click(function(){
  window.location.href="/whale/system_logout";
});


//initiate
// $(".initiate-back").click(function(){
//   window.location.href='workFlow.html';
// });

$("#initiate").click(function(){
   window.location.href='mystart.html?type=0';
});

$("#wait").click(function(){
  window.location.href='wait.html?type=0';
})

$("#finish").click(function(){
  window.location.href='finish.html?type=0';
})

$("#initiate1").click(function(){
  window.location.href='mystart.html?type=1';
});

$("#reviewFile").click(function(){
  window.location.href='wait.html?type=1';
});

$("#readedFile").click(function(){
  window.location.href='finish.html?type=1';
});



