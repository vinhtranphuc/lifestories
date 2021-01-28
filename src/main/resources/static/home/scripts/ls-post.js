var commentsCnt = 0;
$(document).ready(function() {
	loadTreeComments();
	$(document).on('click', '.comment-reply-link', function(){
		var commentEle = $(this).closest('.thecomment');
		var commentId = commentEle.attr('commentId');
		$('#respond.comment-respond').attr('commentParentId',commentId);
		commentEle.after($('.comment-respond'));
		$("#cancel-comment-reply").show();
    });

	$(document).on('click', '#cancel-comment-reply', function(){
		$('#respond.comment-respond').attr('commentParentId',0);
		$('#post-comment').append($('.comment-respond'));
		$("#cancel-comment-reply").hide();
    });

	$(document).on('click', '#commentform #submit', function(){
		saveComment();
    });
	
	$(document).on('click', '#post-like', function(){
		handleLike();
    });
	
	// share socials
	var title = $('#post-share-socials').attr('title');
	var summary = $('#post-share-socials').attr('summary');
	
	var origin   = window.location.origin;
	var image = $('#post-share-socials').attr('image');
	var imageFullPath = origin+image;
	
	var url = window.location.href;
	
	$('#post-share-socials .share').ShareLink({
        title: title,
        text: summary,
        image: imageFullPath,
        url: url
    });
});

function loadTreeComments() {
	var postId = $("#postId").val();
	$("#post-comment").html("");
	$("#comments .post-title-box").remove();
	
	$('#respond.comment-respond').remove();
	var tmpInitCommentReply = $('#tmpInitCommentReply').html();
	var template = Handlebars.compile(tmpInitCommentReply+"");
	$("#comments").append(template);
	$.ajax({
		type : "GET",
		url : "/home/api/post/comments/"+postId,
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var treeComments = data.data.comments;
				commentsCnt = 0;
				treeComments.forEach(function(e) {
					createComment($("#post-comment"),e);
				});
				
				if(commentsCnt > 0) {
					// title
					var tmpTitlePostComment = $('#tmpTitlePostComment').html();
					var template = Handlebars.compile(tmpTitlePostComment+"");
					var obj = {};
					obj['commentsCnt'] = commentsCnt;
					var tmpTitlePostComment = template(obj);
					$('#comments').prepend(tmpTitlePostComment);
					
					var commentSession = data.data.commentSession;
					if(commentSession) {
						$("#comment-name").val(commentSession.commentName);
						$("#comment-email").val(commentSession.commentEmail);
						$("#save-comment-info").prop('checked',true);
					} else {
						$("#comment-name").val("");
						$("#comment-email").val("");
						$("#save-comment-info").prop('checked',false);
					}
					$("#comment-content").val("");
				}
				$("#commentsCnt").html(commentsCnt);
			}
		},
		error : function(xhr) {
			if(xhr.responseJSON.message && xhr.responseJSON.message!='') {
	        	alertWarning(xhr.responseJSON.message);
	        }
		}
	});
}

function createComment(container, data) {
	var tmpPostComment = $('#tmpPostComment').html();
	var template = Handlebars.compile(tmpPostComment+"");
	var comment = data;
	var tmpPostComment = template(comment);
	var tmpPostCommentEle = $(tmpPostComment);
	container.append(tmpPostCommentEle);
	if(data.childComments && data.childComments.length > 0) {
		data.childComments.forEach(function(e) {
			createComment(tmpPostCommentEle,e);
		});		
	}
	commentsCnt++;
}

function saveComment() {
	var name = $("#comment-name").val();
	var email = $("#comment-email").val();
	var comment = $("#comment-content").val();
	var isSaveInfo = $("#save-comment-info").prop("checked") == true;
	var postId = $("#postId").val();
	var commentParentId = $('.comment-respond').attr("commentParentId");
	$.ajax({
		type: "POST"
		,url: "/home/api/post/save-comment"
		,headers: {
			"Content-Type": "application/json"
			,"X-HTTP-Method-Override": "POST"
		}
		,data: JSON.stringify({
			post_id:postId
			,comment_parent_id:commentParentId
			,name:name
			,email:email
			,comment:comment
			,isSaveInfo:isSaveInfo
		})
		,dataType: "json"
		,success: function(data) {
			if(data.status == 200) {
				loadTreeComments();
			}
		}
		,error: function(xhr) {
	        if(xhr.responseJSON.message && xhr.responseJSON.message!='') {
	        	alertWarning(xhr.responseJSON.message);
	        }
		}
	});
}

function handleLike() {
	var postId = $("#postId").val();
	$.ajax({
		type: "PUT"
		,url: "/home/api/post/handle-like"
		,headers: {
			"Content-Type": "application/json"
			,"X-HTTP-Method-Override": "PUT"
		}
		,data: JSON.stringify({
			postId:postId
		})
		,dataType: "json"
		,success: function(data) {
			if(data.status == 200) {
				var crrLikeCnt = parseInt($("#like-cnt").html());
				var likeCnt = parseInt(data.data);
				if(likeCnt == crrLikeCnt) 
					return;
				$("#post-like i").removeClass(function (index, className) {
					return (className.match (/(^|\s)fa-heart\S+/g) || []).join(' ');
				});
				if(likeCnt > crrLikeCnt) {
					$("#post-like i").addClass("fa-heart");
				} else {
					$("#post-like i").addClass("fa-heart-o");
				}
				$("#like-cnt").html(likeCnt);
			}
		}
		,error: function(xhr) {
	        if(xhr.responseJSON.message && xhr.responseJSON.message!='') {
	        	alertWarning(xhr.responseJSON.message);
	        }
		}
	});
}

