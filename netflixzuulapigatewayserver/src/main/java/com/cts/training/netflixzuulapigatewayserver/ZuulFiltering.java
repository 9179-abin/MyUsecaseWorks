package com.cts.training.netflixzuulapigatewayserver;




import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Component;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;



@Component
public class ZuulFiltering extends ZuulFilter {



	@Override
	public String filterType() {
	    return "pre";
	}

	@Override
	public int filterOrder() {
	    return 10;
	}

	@Override
	public boolean shouldFilter() {
	    return true;
	}
	
	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
	    
	    System.out.println("\n\n\n"+request.getHeader("Authorization")+"\n\n\n");
	    System.out.println("\n\n\n"+request.getHeader("ignoredHeaders")+"\n\n\n");
	    ctx.addZuulRequestHeader("Authorization", request.getHeader("Authorization"));
	    System.out.println("\n\n\n"+ctx.getZuulRequestHeaders()+"\n\n\n");
	    
	    return null;
	}
	 
	

}
