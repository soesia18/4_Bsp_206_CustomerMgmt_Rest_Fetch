package at.htlkaindorf.custmgmt;

import at.htlkaindorf.custmgmt.beans.Customer;
import at.htlkaindorf.custmgmt.db.CustomerDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

// /api/customers/2

@Path("/customers")
public class CustomerResource {

    @Context
    UriInfo uriInfo;

    //HTTP Methods
    //GET, POST, PUT, DELETE (CRUD-Operations)
    //C ... Create -> POST
    //R ... Read -> GET
    //U ... Update -> PUT
    //D ... Delete -> DELETE

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") int id) {
        try {
            Customer customer = CustomerDB.getInstance().getCustomer(id);
            return Response.ok(customer).build();
            //return Response.status(Response.Status.OK).entity(customer).build();
        } catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // /api/customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return CustomerDB.getInstance().getAllCustomer();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response removeCustomer(@PathParam("id") int id) {
        try {
            Customer customer = CustomerDB.getInstance().removeCustomer(id);
            return Response.ok(customer).build();
        } catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //Bsp.: Customer mit ID 7
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer (Customer customer) {
        try {
            CustomerDB.getInstance().addCustomer(customer);

            //http://localhost:8080/custmgmt-1.0-SNAPSHOT/api/customers/7

            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path("" + customer.getId());

            return Response.created(uriBuilder.build()).entity(customer).build();
        } catch (KeyAlreadyExistsException kaee) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCustomer (Customer customer) {
        try {
            CustomerDB.getInstance().replaceCustomer(customer);

            return Response.ok().entity(customer).build();
        } catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}