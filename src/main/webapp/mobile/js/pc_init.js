$(".btn-div").click(function(){
	var index = $(this).index();
	$(".btn-div").find("i").addClass("hide");
	$(this).find("i").removeClass("hide");
	$(".item-div").addClass("hide");
	$(".item-div").eq(index).removeClass("hide");

});