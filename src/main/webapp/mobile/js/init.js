//锁住浏览器
document.addEventListener('touchmove', function(ev) {
    ev.stopPropagation();
    ev.preventDefault();
});

//解决click 200延迟
FastClick.attach(document.body);

//滑动框
var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    paginationClickable: true,
    autoplay : 2000,
    loop : true,
    autoplayDisableOnInteraction : false
});


//正文
$("#work").click(function(){
	changeItem("work","setup");
});

$("#setup").click(function(){
	changeItem("setup","work");
});

$(".back").click(function(){
  window.location.href='index.html';
});

$("#work-flow").click(function(){
  window.location.href='workFlow.html';
});

$("#notice").click(function(){
  window.location.href='notice.html';
});

$("#office-sign").click(function(){
  window.location.href='officeSign.html';
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
  var time = con.find("span").eq(1).text();
  var text = con.find("span").eq(0).text();
  $(".detailed-title").find("span").eq(0).text(title);
  $(".detailed-brief").text(time);
  $(".detailed-text").text(text);
  $(".detailed-wrap").removeClass("hide");

  //
  // if($(".unread").hasClass("tip-focus")){

  //   $(this).parent().remove();
  // }
});