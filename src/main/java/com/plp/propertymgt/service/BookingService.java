package com.plp.propertymgt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import  com.google.gson.internal.LinkedTreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plp.motiondetection.model.Incident;
import com.plp.propertymgt.business.BookingBusiness;
import com.plp.propertymgt.model.Booking;

@Path("/bookings") 
public class BookingService {
	
	BookingBusiness bookingBusiness = new BookingBusiness();
	
	@GET
	@Path("/getbookings/{date}")
	public Response getBookings(@PathParam("date") String date) throws ParseException{
				
		Date bookingDate = new SimpleDateFormat( "yyyyMMdd" ).parse( date);
		List<Booking> bookings =  bookingBusiness.getBookings(bookingDate);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
		String json =  gson.toJson(bookings);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/getlastincidents")
	public Response getLastIncidents() throws ParseException{
		List<Incident> incidents =  bookingBusiness.getLastIncidents();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
		String json =  gson.toJson(incidents);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@OPTIONS
	@POST
	@Path("/add")
	@Consumes("application/json")
    public Response addBooking(Object bookingObject) throws ParseException {
		
		if (bookingObject.getClass() == ArrayList.class)
		{
			try{
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ArrayList<LinkedTreeMap> arrayList = (ArrayList<LinkedTreeMap>) bookingObject;
				
				for (LinkedTreeMap<?,?> linkedTree : arrayList){
					Booking booking = Booking.getFromLinkedTreeMap(linkedTree);
					
					bookingBusiness.addBooking(booking);
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
    public Response deleteBookings(Object bookingObject) throws ParseException {
		
		if (bookingObject.getClass() == ArrayList.class)
		{
			try{
				List<Booking> bookings = new ArrayList<Booking>();
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ArrayList<LinkedTreeMap> arrayList = (ArrayList<LinkedTreeMap>) bookingObject;
				
				for (LinkedTreeMap<?,?> linkedTree : arrayList){
					Booking booking = Booking.getFromLinkedTreeMap(linkedTree);
					bookings.add(booking);
				}
				bookingBusiness.deleteBookings(bookings);
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
    public Response updateBooking(Object bookingObject) throws ParseException {
		
		if (bookingObject.getClass() == LinkedTreeMap.class)
		{
			try{
				LinkedTreeMap<?,?> linkedTree = (LinkedTreeMap<?,?>) bookingObject;
				
				Booking booking = Booking.getFromLinkedTreeMap(linkedTree);
				bookingBusiness.updateBooking(booking);
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
