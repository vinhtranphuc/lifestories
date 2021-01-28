function refreshPostList(container) {
	container.each(function () {
        var $this = $(this),
            $rtl = false;
        if( $('html').attr('dir') === 'rtl' ) {
            $rtl = true;
        }
        var owl_args = {
            rtl               : $rtl,
            loop              : true,
            margin            : 0,
            items             : 1,
            navSpeed          : 600,
            lazyLoad          : true,
            dotsSpeed         : 600,
            nav               : true,
            dots              : false,
            navText           : ['<i class="fa fa-angle-left"></i>', '<i class="fa fa-angle-right"></i>'],
            autoplay          : true,
            autoplayTimeout   : 5000,
            autoHeight        : true,
            autoplayHoverPause: true,
            autoplaySpeed     : 600
        };

        $this.owlCarousel(owl_args);
    });
}