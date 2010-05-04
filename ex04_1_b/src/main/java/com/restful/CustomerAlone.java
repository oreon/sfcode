package com.restful;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/")
@Produces("application/xml")
public class CustomerAlone {

	@GET
	@Produces("application/xml")
	@Path("/getCustomer/{id}")
	public String getCustomerById(@PathParam("id") String id) {

		return "<customer><id>"+id+"</id></customer>";

	}

	@POST
	@Consumes("application/xml")
	@Path("/addCustomer/{id}")
	public String addCustomer(String id) {

		return "<addcustomer><id>"+id+"</id><status>SUCCESSFUL</status></addcustomer>";

	}

}