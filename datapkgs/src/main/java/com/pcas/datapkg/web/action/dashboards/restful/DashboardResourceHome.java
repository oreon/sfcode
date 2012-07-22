package com.pcas.datapkg.web.action.dashboards.restful;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;

import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.framework.Home;
import org.jboss.seam.resteasy.ResourceHome;
import org.jboss.seam.resteasy.ResourceQuery;

import java.util.Date;
import java.util.List;

import com.pcas.datapkg.dashboards.Dashboard;

@Name("dashboardResourceHome")
@Path("dashboard")
public class DashboardResourceHome extends ResourceHome<Dashboard, Long> {
	@In(create = true)
	private EntityHome<Dashboard> dashboardAction;

	@Override
	public Home<?, Dashboard> getEntityHome() {
		return dashboardAction;
	}

}