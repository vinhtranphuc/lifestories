$(document).ready(function() {
	$(document).on('click', '.ls-post-pager', function(){
        var row = $(this).parent('.penci-mega-row');
		var categoryId = row.attr('categoryId');
		var page = row.attr('page');
		var pageSize = row.attr('pageSize');
		var nextPage = parseInt(page)+($(this).hasClass('prev')?-1:$(this).hasClass('next')?1:0);
		loadMenuPosts(row,categoryId,pageSize,nextPage);
    });
});

function loadMenuPosts(row,categoryId,pageSize,page) {
	var container = $(row);
	container.html("");
	
	// add loader
	var tmpPostSpinner = $('#tmpPostSpinner').html();
	tmpPostSpinner = Handlebars.compile(tmpPostSpinner+"");
	container.append(tmpPostSpinner)
	$.ajax({
		type : "GET",
		url : "/home/api/posts?categoryId="+categoryId+"&pageSize="+pageSize+"&page="+page,
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var result = data.data;
				
				container.html("");
				
				var page = result.page;
				var isLast = result.isLast;
				
				// display prev
				if(parseInt(page) > 1) {
					var tmpPostPagerPrev = $('#tmpPostPagerPrev').html();
					tmpPostPagerPrev = Handlebars.compile(tmpPostPagerPrev+"");
					container.append(tmpPostPagerPrev);
				}
				
				// display list
				var list = result.list;
				list.forEach(function(e) {
					var tmpPostSmall = $('#tmpPostSmall').html();
					var template = Handlebars.compile(tmpPostSmall+"");
					var post = {};
					post = e;
					post['postUrl'] = '/home/post/'+e.post_id;
					var imagePath = (e.first_thumbnail)?e.first_thumbnail.standard_image_path:'';
					post['image_path'] = imagePath;
					post['style'] = 'display: inline-block; background-image: url("'+imagePath+'");'
					tmpPostSmall = template(post);
					container.append(tmpPostSmall);
				});
				
				// display next
				if(isLast != 'true' && isLast != true) {
					var tmpPostPagerNext = $('#tmpPostPagerNext').html();
					tmpPostPagerNext = Handlebars.compile(tmpPostPagerNext+"");
					container.append(tmpPostPagerNext);
				}
				
				container.attr('page',page);
				container.attr('pageSize',pageSize);
			}
		},
		error : function(xhr) {
			if(xhr.responseJSON.message && xhr.responseJSON.message!='') {
	        	alertWarning(xhr.responseJSON.message);
	        }
		}
	});
}
