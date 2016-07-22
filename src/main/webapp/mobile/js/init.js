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

$("#work-flow").click(function(){
  window.location.href='workFlow.html';
});

$(".back").click(function(){
  window.location.href='index.html';
});

//切换
function changeItem(id1,id2){
	$("#"+id1).find("img").attr("src","img/"+id1+"_b.png");
	$("#"+id1).find("span").css("color","#56ABE4");
	$("#"+id2).find("img").attr("src","img/"+id2+"_h.png");
	$("#"+id2).find("span").css("color","#666");
}