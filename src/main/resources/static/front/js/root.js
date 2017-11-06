
   $(document).ready(function(){
        $('#cus-id').hover(function() {
            $("#cus-info").css('display', 'block');
        }, function() {
           $("#cus-info").css('display', 'none');
        });
        $("#cus-info").hover(function() {
            $(this).css('display', 'block');;
        }, function() {
            $(this).css('display', 'none');
        });
        console.log('ready');
    })
   $(document).ready(function(){
        $('#cus_id').hover(function() {
            $("#cus_info").css('display', 'block');
        }, function() {
           $("#cus_info").css('display', 'none');
        });
            $("#cus_info").hover(function() {
                $(this).css('display', 'block');;
            }, function() {
                $(this).css('display', 'none');
            });
    })

   $(document).ready(function() {  
        $(".tab_content").hide();   
        $("ul.tabs li:first").addClass("active").show(); 
        $(".tab_content:first").show(); 
  
        $("ul.tabs li").click(function() {  
            $("ul.tabs li").removeClass("active");  
            $(this).addClass("active");
            $(".tab_content").hide();   
            var activeTab = $(this).find("a").attr("href");   
            $(activeTab).fadeIn();   
            return false;  
        });  
        $(".car-box").mouseover(function(){
               $(this).find(".car-box-bk").css("display","none");
        });
        $(".car-box").mouseout(function(){
               $(this).find(".car-box-bk").css("display","block");
        });
        $(".store-box").mouseover(function(){
               $(this).find(".store-box-bk").css("display","none");
        });
        $(".store-box").mouseout(function(){
               $(this).find(".store-box-bk").css("display","block");
        });   
    });  
