function jumpToPage(page){
	window.location.href=page;
}

function getRequestUrlByMenuUrl(menuUrl){
	return "pages/"+menuUrl+".html";
}

function getEmKey(){
	var href = window.location.href;
	return href.substring(href.indexOf("=")+1);
}

function strToInt(str){
	return parseInt(str);
}
