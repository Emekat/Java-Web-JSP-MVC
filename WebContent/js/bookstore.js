/**
 * 
 */$.ready(function() {
	$("span.tooltip_message").hover(function() {
	 $(this).append('<div class="message"><p>Search by Keyword in:<ul><li>Author First Name </li><li>Author Last Name </li><li>Title </li></ul></p></div>');
      }, function() { 
		$("div.message").remove();
		});
		
		$("span.tooltip_img1").hover(function(){$(this).append('<div class="message"><ul><li>Title - Beginning Groovy, Grails and Griffon</li><li>Author: Vishal Layka</li>'+
			'<li>Publisher: Apress</li></ul></div>');
		}, function(){$("div.message").remove();});
		
	});