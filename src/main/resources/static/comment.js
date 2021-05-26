
function del(postId, userId, commentId){
    const comment = document.getElementsByClassName("comment");

    let delCom = confirm("Are sure you want to delete this comment");

    if(delCom){
        $.ajax({
            type: 'POST',
            url: '/deleteComment',
            data: {postId, userId, commentId},

            success: function(data){
                alert(data);
                window.location.reload();
            },
            error: function(){
                alert('error deleting post');
            }
        });
    }
}

function edit(postId, userId, commentId){
console.log("working");
    const editedComment = document.getElementsByClassName("edit-comment");

    for (let i = 0; i < editedComment.length; i++){
        let comment = editedComment[i].value.trim();

        if(comment != ""){
            const editPost = confirm("Are you sure you want to edit comment");
            console.log(editPost)
            if(editPost){
                $.ajax({
                    type: 'POST',
                    url: '/editComment',
                    data: { postId, userId, commentId, "editedComment": comment},

                    success: function(data){
                        window.location.reload();
                    },
                    error: function(){
                        alert('error editing post');
                    }
                });
            }
        }
    }
}