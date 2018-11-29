package com.plp.propertymgt.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.plp.propertymgt.business.MemberBusiness;
import com.plp.propertymgt.model.Booking;
import com.plp.propertymgt.model.Member;

@Path("/members")
public class MemberService {

	MemberBusiness memberBusiness = new MemberBusiness();
	
	@GET
	@Path("/get/{tenantid}")
	public Response getBookings(@PathParam("tenantid") int tenantid) throws ParseException{
				
		List<Member> members =  memberBusiness.getMembers(tenantid);
		
		Gson gson = new GsonBuilder().create();
		String json =  gson.toJson(members);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@OPTIONS
	@POST
	@Path("/add")
	@Consumes("application/json")
    public Response addBooking(Object memberObject) throws ParseException {
		
		if (memberObject.getClass() == ArrayList.class)
		{
			try{
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ArrayList<LinkedTreeMap> arrayList = (ArrayList<LinkedTreeMap>) memberObject;
				
				for (LinkedTreeMap<?,?> linkedTree : arrayList){
					Member member = Member.getFromLinkedTreeMap(linkedTree);
					
					memberBusiness.addMember(member);
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
        return Response.ok().header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Credentials", "true")
        		.header("Access-Control-Allow-Methods", "POST")
        		.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	@OPTIONS
	@POST
	@Path("/delete")
	@Consumes("application/json")
    public Response deleteMembers(Object memberObject) throws ParseException {
		
		if (memberObject.getClass() == ArrayList.class)
		{
			try{
				List<Member> members = new ArrayList<Member>();
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ArrayList<LinkedTreeMap> arrayList = (ArrayList<LinkedTreeMap>) memberObject;
				
				for (LinkedTreeMap<?,?> linkedTree : arrayList){
					Member member = Member.getFromLinkedTreeMap(linkedTree);
					members.add(member);
				}
				
				int tenantid = 0;
				
				if (members.size() > 0){
					tenantid = members.get(0).getTenantId();
				}
				
				memberBusiness.deleteMembers(members,tenantid);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
        return Response.ok().header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Credentials", "true")
        		.header("Access-Control-Allow-Methods", "POST")
        		.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	@OPTIONS
	@POST
	@Path("/update")
	@Consumes("application/json")
    public Response updateMember(Object memberObject) throws ParseException {
		
		if (memberObject.getClass() == LinkedTreeMap.class)
		{
			try{
				LinkedTreeMap<?,?> linkedTree = (LinkedTreeMap<?,?>) memberObject;
				
				Member member= Member.getFromLinkedTreeMap(linkedTree);
				memberBusiness.updateMember(member);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
        return Response.ok().header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Credentials", "true")
        		.header("Access-Control-Allow-Methods", "POST")
        		.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
}
